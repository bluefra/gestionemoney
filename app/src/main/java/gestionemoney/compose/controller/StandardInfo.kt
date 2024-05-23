package gestionemoney.compose.controller

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class StandardInfo {
    companion object{
        const val nameInfo = "name"
        const val surnameInfo = "surname"
        const val subscriptionInfo = "subscriptionDate"
        const val catecoryPermission = "categoryPermission"
        const val expensePermission = "expensePermission"
        const val avgCategoryChar = "avgCategoryChar"
        const val avgExpenseChar = "avgExpenseChar"
        const val avgExpenseNumber = "avgExpenseNumber"
        const val avgExpenseVal = "avgExpneseVal"
        const val lastCatUpdate = "dateLastCatUpdate"
        const val lastExpUpdate = "dateLastExpUpdate"

        fun writeStandardInfo(name: String, surname: String) {
            val map: HashMap<String, String> = HashMap()
            map[nameInfo] = name
            map[surnameInfo] = surname
            map[subscriptionInfo] = formatDate(Date())
            map[catecoryPermission] = "true"
            map[expensePermission] = "true"
            map[avgCategoryChar] = UserWrapper.getInstance().getCategoryAvgCharacter().toString()
            map[avgExpenseChar] = UserWrapper.getInstance().getExpenseAvgCharacter().toString()
            map[avgExpenseNumber] = UserWrapper.getInstance().getAvgExpenseNumber().toString()
            map[avgExpenseVal] = UserWrapper.getInstance().getAvgExpenseValue().toString()
            map[lastCatUpdate] = Date().toString()
            map[lastExpUpdate] = Date().toString()
            DBInfoConnection.getInstance().writeMultipleInfo(map)
        }

        fun setCategoryPermission(value: Boolean) {
            if(value) {
                DBInfoConnection.getInstance().writeSingleInfo(catecoryPermission, "true")
            }
            DBInfoConnection.getInstance().writeSingleInfo(catecoryPermission, "false")
        }

        fun getCategoryPermission(): Boolean {
            return InfoWrapper.getInstance().getInfo(catecoryPermission) == "true"
        }

        fun setExpensePermission(value: Boolean) {
            if(value) {
                DBInfoConnection.getInstance().writeSingleInfo(expensePermission, "true")
            }
            DBInfoConnection.getInstance().writeSingleInfo(expensePermission, "false")
        }

        fun getExpensePermission(): Boolean {
            return InfoWrapper.getInstance().getInfo(expensePermission) == "true"
        }

        fun formatDate(date: Date): String {
            return SimpleDateFormat("EEEE dd/MM/yyyy", Locale.getDefault()).format(date)
        }

        fun getDateDifferenceFromNow(date: Date): String {
            val diffInMillies = Date().time - date.time
            val diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(diffInMillies)
            val diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillies)
            val diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillies)
            val diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillies)
            return String.format(
                Locale.getDefault(),
                "%d giorni, %d ore, %d minuti, %d secondi",
                diffInDays,
                diffInHours % 24,
                diffInMinutes % 60,
                diffInSeconds % 60
            )
        }

        fun getDateDifferenceFromNow(date: String): String {
            if(date == "") {
                return date
            }
            return getDateDifferenceFromNow(Date(date))
        }
    }
}