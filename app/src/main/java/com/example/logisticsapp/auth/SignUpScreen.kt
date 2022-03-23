import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.logisticsapp.LogisticViewModel
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.input.TextFieldValue
import com.example.logisticsapp.R
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.logisticsapp.ui.theme.Shapes
import androidx.compose.foundation.shape.CircleShape

@Composable
fun SignUpScreen(navController: NavController, vm: LogisticViewModel){
    Box(
        modifier = Modifier.fillMaxSize(),
    ){
        val email  = remember {
            mutableStateOf(TextFieldValue())
        }
        val username = remember {
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

            Image(
                painter = painterResource(id = R.drawable.logistics),
                contentDescription = "Logistic Image",
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
                    .padding(8.dp)
                    .padding(top = 30.dp)
                    .clip(CircleShape)

            )
            Text(
                text = "Logistic Company",
                fontFamily = FontFamily.SansSerif,
                fontSize = 20.sp,
                color = Color.Green,
                modifier = Modifier.padding(8.dp)
            )

            OutlinedTextField(
                value = username.value, onValueChange = {username.value=it},
                label = {Text("Username")},
                singleLine = true,
                placeholder = {Text("user18239")},
                modifier = Modifier.padding(2.dp)
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
                     vm.onSignUp(
                         username.value.text,
                         email.value.text,
                         password.value.text
                     )

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 35.dp),
                shape = MaterialTheme.shapes.large
            ) {
                Text(text = "Register")
            }
            TextButton(
                onClick = { /*TODO*/ },
            ) {
                Text(text = "Already a user? Login here..", color = Color.Blue)
            }

        }
        val isLoading = vm.inProgress.value
        if(isLoading){
            CommonProgressSpinner()
        }
    }
}