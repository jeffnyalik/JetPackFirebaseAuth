package com.example.logisticsapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.logisticsapp.data.Event
import com.example.logisticsapp.data.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import javax.inject.Inject
import kotlin.math.sign

const val USERS = "users"
@HiltViewModel
class LogisticViewModel @Inject constructor(
    val auth: FirebaseAuth,
    val db: FirebaseFirestore
) : ViewModel() {
    val signedIn = mutableStateOf(false)
    val inProgress = mutableStateOf(false)
    val userData = mutableStateOf<UserData?>(null)
    val popUpNotification = mutableStateOf<Event<String>?>(null)

    init {
        val currentUser = auth.currentUser
        signedIn.value = currentUser !=null
        currentUser?.uid?.let { uid ->
            getUserData(uid)
        }
    }

    fun onSignUp(username: String, email: String, password: String){
        inProgress.value = true
        db.collection(USERS).whereEqualTo("email", email).get()
            .addOnSuccessListener { documents ->
                if(documents.size() > 0){
                    handleException(customMessage = "Email already exists")
                    inProgress.value = false
                }else{
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener{task ->
                            if(task.isSuccessful){
                                signedIn.value = true
                                createOrUpdateProfile(username = username)
                            }else{
                                handleException(task.exception, "SIGNED UP FAILED")
                            }
                            inProgress.value = false
                        }
                }
            }
            .addOnFailureListener{
                //code here
            }
    }


    private fun createOrUpdateProfile(
        name: String? = null,
        username: String? = null,
        bio: String? = null,
        imageurl:String? = null
    ){
        val uid = auth.currentUser?.uid
        val userData = UserData(
            userId = uid,
            name = name ?: userData.value?.name,
            username = username ?: userData.value?.username,
            bio = bio ?: userData.value?.bio,
            imageurl = imageurl ?: userData.value?.imageurl
        )

        uid?.let{uid ->
            inProgress.value = true
            db.collection(USERS).document(uid).get()
                .addOnSuccessListener{
                if(it.exists()){
                    it.reference.update(userData.toMap())
                        .addOnSuccessListener {
                            this.userData.value = userData
                            inProgress.value = false
                        }
                        .addOnFailureListener{
                            handleException(it, "Can not update user")
                            inProgress.value = false
                        }
                }else{
                    db.collection(USERS).document(uid).set(userData)
                    getUserData(uid)
                    inProgress.value = false
                }
            }
                .addOnFailureListener{ exec ->
                    handleException(exec, "Can not update user")
                    inProgress.value = false
                }
        }
    }

    private fun getUserData(uid: String){
        inProgress.value = true
        db.collection(USERS).document(uid).get()
            .addOnSuccessListener {
                val user = it.toObject<UserData>()
                userData.value = user
                inProgress.value = false
//                popUpNotification.value = Event("User data retrieved successfully")
            }
            .addOnFailureListener { exec ->
                handleException(exec, "Can not retrieve user data..")
                inProgress.value = false
            }
    }

    fun handleException(exception: Exception? =null, customMessage: String = ""){
        exception?.printStackTrace()
        val errorMsg = exception?.localizedMessage ?: ""
        val message = if (customMessage.isEmpty()) errorMsg else "$customMessage: $errorMsg"
        popUpNotification.value = Event(message)
    }
}