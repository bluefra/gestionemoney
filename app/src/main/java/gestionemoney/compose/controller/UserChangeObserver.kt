package gestionemoney.compose.controller


interface UserChangeObserver {
    fun updateUser(user: UserWrapper)
}