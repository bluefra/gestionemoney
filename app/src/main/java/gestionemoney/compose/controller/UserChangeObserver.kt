package gestionemoney.compose.controller

/**
 * class used to notify the subscriber of changes for the user information
 * @see DBInfoConnection
 */
interface UserChangeObserver {
    /**
     * the reading request to the db has been successful and the data are stored in parameter
     */
    fun updateUser(user: UserWrapper)

    /**
     * the reading request to the db has been un-successful and the parameter contain the error msg
     */
    fun updateError(error: String)
}