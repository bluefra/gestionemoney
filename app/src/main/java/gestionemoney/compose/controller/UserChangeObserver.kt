package gestionemoney.compose.controller


interface UserChangeObserver {
    fun updateUser(user: UserWrapper)

    fun updateError(error: String)
}