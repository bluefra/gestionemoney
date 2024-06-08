package gestionemoney.compose.controller
/**
 * class used to notify the subscriber of the authentication exit status
 * @see DBauthentication
 */
interface AuthObserver {
    /**
     * the login/register request has been accepted and the data are stored in the parameter
     */
    fun onSuccess(data: HashMap<String, String?>)
    /**
     * the login/register request has been rejected and the error message is passed in the parameter
     */
    fun onFail(error: String)
}