package com.example.rasachatbotapp

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.rasachatbotapp.network.Message
import com.example.rasachatbotapp.ui.theme.RasaChatbotAppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.util.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//        Thread.sleep(2000)
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContent {
            RasaChatbotAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }
    }
}

@Destination(start = true)
@Composable
fun MainScreen(navigator: DestinationsNavigator) {
    val viewModel = MainActivityViewModel()
    val context = LocalContext.current
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetworkInfo
    viewModel._connectivityState.value = activeNetwork != null && activeNetwork.isConnected
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopBarSection(
            username = "Du lịch Đà Lạt Bot",
            profile = painterResource(id = R.drawable.gojo),
            isOnline = viewModel._connectivityState.value,
            navigator = navigator
        )
        ChatSection(Modifier.weight(1f), viewModel, navigator = navigator)
        MessageSection(viewModel)
    }

    // Auto send greet message whenever run
//    val viewModel = MainActivityViewModel()
    viewModel.sendMessagetoRasa(
        Message(
            recipient_id = "NHTV",
            text = "Xin chào!",
            time = Calendar.getInstance().time
        )
    )
}

@Composable
fun MessageSection(
    viewModel: MainActivityViewModel,
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = Color.White,
        elevation = 10.dp
    ) {
        OutlinedTextField(
            placeholder = {
                Text("Nhập tin nhắn...")
            },
            value = message.value,
            onValueChange = {
                message.value = it
            },
            shape = RoundedCornerShape(25.dp),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_send),
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier.clickable {
                        // If the message is null or white spaces
                        if (message.value.isNullOrBlank()) {
                            Toast.makeText(
                                context,
                                "Vui lòng nhập nội dung!",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@clickable
                        }

                        if (viewModel._connectivityState.value) {
                            viewModel.sendMessagetoRasa(
                                Message(
                                    text = message.value,
                                    recipient_id = viewModel.username,
                                    time = Calendar.getInstance().time,
                                    isOut = true
                                )
                            )
                        } else {
                            Toast.makeText(
                                context,
                                "Vui lòng kết nối internet!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        message.value = ""
                    }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        )
    }
}
