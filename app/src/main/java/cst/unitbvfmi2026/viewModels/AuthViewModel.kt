package cst.unitbvfmi2026.viewModels

import android.os.Message
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


data class AuthState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)


class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState

    val isLoggedIn: Boolean
        get() = auth.currentUser != null //param nu va avea o val a lui si il folosim ca intermediar pt accesarea valorii din currentUser -> pastrare incapsulare
    //se verif sesiunea curent in Firebase daca currentUser != null
    fun login(email: String, password: String, onSuccess: () -> Unit) {
       _authState.value= AuthState(true)

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Log.e("Login", "Success")
                _authState.value= AuthState()
                onSuccess()
            }
            .addOnFailureListener {error ->
                Log.e("Login", "Failed")
                _authState.value= AuthState(errorMessage = error.message )

            }
    }

    fun register(email: String, password: String, onSuccess: () -> Unit) {
        _authState.value= AuthState(true)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Log.e("Register", "Success")
                _authState.value= AuthState()
                onSuccess()
            }
            .addOnFailureListener {
                Log.e("Register", "Failed")
                _authState.value= AuthState(errorMessage = it.message )
            }
    }

    fun logout() {
        auth.signOut()
    }

}