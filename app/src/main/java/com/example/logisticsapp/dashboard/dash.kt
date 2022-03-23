import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.logisticsapp.LogisticViewModel

@Composable
fun DashboardScreen(navController: NavController, vm:LogisticViewModel){
    Text(text = "Welcome to Dashboard")
}