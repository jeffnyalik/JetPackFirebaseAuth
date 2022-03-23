import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.logisticsapp.DestinationScreen
import com.example.logisticsapp.LogisticViewModel
import com.example.logisticsapp.R

@Composable
fun LoginScreen(navController: NavController, vm:LogisticViewModel){
    checkSignedIn(navController = navController, vm = vm )
    val focus = LocalFocusManager.current
    Box(
        modifier = Modifier.fillMaxSize(),
    ){
        val email  = remember {
            mutableStateOf(TextFieldValue())
        }

        val password = remember {
            mutableStateOf(TextFieldValue())
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .verticalScroll(rememberScrollState())
                .background(Color.Transparent),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImageBox(shape = CircleShape)
//            Image(
//                painter = painterResource(id = R.drawable.logistics),
//                contentDescription = "Logistic Image",
//                modifier = Modifier
//                    .width(180.dp)
//                    .height(180.dp)
//                    .padding(top = 30.dp)
//                    .clip(CircleShape)
//
//            )
            Text(
                text = "Logistic Company",
                fontFamily = FontFamily.SansSerif,
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )

            OutlinedTextField(
                value = email.value, onValueChange = {email.value=it},
                label = {Text("Email Address")},
                singleLine = true,
                placeholder = {Text("abcg@gmail.com")},
                modifier = Modifier.padding(2.dp)

            )

            OutlinedTextField(
                value = password.value, onValueChange = {password.value=it},
                label = {Text("Password")},
                placeholder = { Text(text = "**********")},
                visualTransformation = PasswordVisualTransformation()
            )
            Button(
                onClick = {
                    focus.clearFocus(force = true)
                    vm.onLogin(email.value.text, password.value.text)

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 35.dp),
                shape = MaterialTheme.shapes.large
            ) {
                Text(text = "Login")
            }
            TextButton(
                onClick = {
                    navigateTo(navController, DestinationScreen.SignUp)
                },
            ) {
                Text(text = "Not a member? SignUp here..", color = Color.Blue)
            }

        }
        val isLoading = vm.inProgress.value
        if(isLoading){
            CommonProgressSpinner()
        }
    }
}

@Composable
fun ImageBox(shape: Shape){
    Column(modifier= Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.Center)) {
        Image(
            painter = painterResource(id = R.drawable.logistics),
            contentDescription = "My Image",
            modifier = Modifier
                .size(200.dp)
                .clip(shape)
        )
    }
}