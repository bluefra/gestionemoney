package gestionemoney.compose.controller

import gestionemoney.compose.model.DateAdapter
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
            map[subscriptionInfo] = DateAdapter().getStringDate(Date())
            map[catecoryPermission] = "true"
            map[expensePermission] = "true"
            map[avgCategoryChar] = UserWrapper.getInstance().getCategoryAvgCharacter().toString()
            map[avgExpenseChar] = UserWrapper.getInstance().getExpenseAvgCharacter().toString()
            map[avgExpenseNumber] = UserWrapper.getInstance().getAvgExpenseNumber().toString()
            map[avgExpenseVal] = UserWrapper.getInstance().getAvgExpenseValue().toString()
            map[lastCatUpdate] = DateAdapter().getStringDate(Date())
            map[lastExpUpdate] = DateAdapter().getStringDate(Date())
            DBInfoConnection.getInstance().writeMultipleInfo(map)
        }

        fun categoryUpdate(updateDate: Boolean) {
            if(!getCategoryPermission()) {
                return
            }
            val map: HashMap<String, String> = HashMap()
            map[avgCategoryChar] = UserWrapper.getInstance().getCategoryAvgCharacter().toString()
            if(updateDate) {
                map[lastCatUpdate] = DateAdapter().getStringDate(Date())
            } else {
                map[lastCatUpdate] = ""
            }
            if(getExpensePermission()) {
                map[avgExpenseChar] = UserWrapper.getInstance().getExpenseAvgCharacter().toString()
                map[avgExpenseNumber] = UserWrapper.getInstance().getAvgExpenseNumber().toString()
                map[avgExpenseVal] = UserWrapper.getInstance().getAvgExpenseValue().toString()
            }
            DBInfoConnection.getInstance().writeMultipleInfo(map)
        }

        fun expenseUpdate(updateDate: Boolean) {
            if(!getExpensePermission()) {
                return
            }
            val map: HashMap<String, String> = HashMap()
            map[avgExpenseChar] = UserWrapper.getInstance().getExpenseAvgCharacter().toString()
            map[avgExpenseNumber] = UserWrapper.getInstance().getAvgExpenseNumber().toString()
            map[avgExpenseVal] = UserWrapper.getInstance().getAvgExpenseValue().toString()
            if(updateDate) {
                map[lastExpUpdate] = DateAdapter().getStringDate(Date())
            } else {
                map[lastExpUpdate] = ""
            }
            DBInfoConnection.getInstance().writeMultipleInfo(map)
        }

        fun setCategoryPermission(value: Boolean) {
            if(value) {
                DBInfoConnection.getInstance().writeSingleInfo(catecoryPermission, "true")
                categoryUpdate(false)
            } else {
                DBInfoConnection.getInstance().writeSingleInfo(catecoryPermission, "false")
                removeCategoryPermission()
            }
        }

        fun getCategoryPermission(): Boolean {
            return InfoWrapper.getInstance().getInfo(catecoryPermission) == "true"
        }

        fun setExpensePermission(value: Boolean) {
            if(value) {
                DBInfoConnection.getInstance().writeSingleInfo(expensePermission, "true")
                expenseUpdate(false)
            } else {
                DBInfoConnection.getInstance().writeSingleInfo(expensePermission, "false")
                removeExpensePermission()
            }
        }

        fun getExpensePermission(): Boolean {
            return InfoWrapper.getInstance().getInfo(expensePermission) == "true"
        }
        fun removeCategoryPermission() {
            WriteLog.getInstance().writeBasicLog("STDI_remove_cat_permission")
            DBInfoConnection.getInstance().removeSingleInfo(avgCategoryChar)
            DBInfoConnection.getInstance().removeSingleInfo(lastCatUpdate)
        }
        fun removeExpensePermission() {
            WriteLog.getInstance().writeBasicLog("STDI_remove_exp_permission")
            DBInfoConnection.getInstance().removeSingleInfo(avgExpenseChar)
            DBInfoConnection.getInstance().removeSingleInfo(avgExpenseNumber)
            DBInfoConnection.getInstance().removeSingleInfo(avgExpenseVal)
            DBInfoConnection.getInstance().removeSingleInfo(lastExpUpdate)
        }
    }
}