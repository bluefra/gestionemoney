package gestionemoney.compose.controller


interface UserChangeObserver {
    fun update(user: UserWrapper)
}