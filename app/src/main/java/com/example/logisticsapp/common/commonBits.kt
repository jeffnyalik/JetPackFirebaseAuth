import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.logisticsapp.LogisticViewModel

@Composable
fun NotificationMessage(vm: LogisticViewModel){
    val notifState = vm.popUpNotification.value
    val notifMessage = notifState?.getContentOrNull()
    if(notifMessage !=null){
        Toast.makeText(LocalContext.current, notifMessage, Toast.LENGTH_LONG).show()
    }
}