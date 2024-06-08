package gestionemoney.compose.controller

/**
 * interface used to notify all of the subscriber about the changing in the Info db
 * @see DBInfoConnection
 */
interface InfoChangeObserver {
    /**
     * it's called when the connection to the info db is successful and the parameter contains the
     * fetched data
     */
    fun updateInfo(info: InfoWrapper)
}