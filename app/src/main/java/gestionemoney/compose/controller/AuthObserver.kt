package gestionemoney.compose.controller

interface AuthObserver {
    fun onSuccess(data: HashMap<String, String?>)
    fun onFail(error: String)
}