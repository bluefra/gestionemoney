package gestionemoney.compose.controller

import gestionemoney.compose.model.DateAdapter
import java.util.Date

/**
 * class used to simplify the management of the extra info of the user.
 * to write any info on the db, the class will use the DBInfoConnection class method, it will never
 * interact directly with the db
 * @see DBInfoConnection
 */
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

        /**
         *  write the standard info, also contained in the companion object with the standard value,
         *  and it will also compute the possible information with the available data.
         *  it will write them on the db (using the method of DBInfoConnection).
         */
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

        /**
         * if the category permission to get personal data is granted, it will evaluate the data and
         * it will write them on the db (using the method of DBInfoConnection).
         * the passed parameter updateDate = true when a new category is created
         */
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

        /**
         * if the expense permission to get personal data is granted, it will evaluate the data and
         * it will write them on the db (using the method of DBInfoConnection).
         * the passed parameter updateDate = true when a new expense is created
         */
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

        /**
         * update the category permission both in local and in the db.
         * if value == false it will remove the data from the db.
         */
        fun setCategoryPermission(value: Boolean) {
            if(value) {
                DBInfoConnection.getInstance().writeSingleInfo(catecoryPermission, "true")
                categoryUpdate(false)
            } else {
                DBInfoConnection.getInstance().writeSingleInfo(catecoryPermission, "false")
                removeCategoryPermission()
            }
        }

        /**
         * return the permission associated with the category
         */
        fun getCategoryPermission(): Boolean {
            return InfoWrapper.getInstance().getInfo(catecoryPermission) == "true"
        }
        /**
         * update the expense permission both in local and in the db.
         * if value == false it will remove the data from the db.
         */
        fun setExpensePermission(value: Boolean) {
            if(value) {
                DBInfoConnection.getInstance().writeSingleInfo(expensePermission, "true")
                expenseUpdate(false)
            } else {
                DBInfoConnection.getInstance().writeSingleInfo(expensePermission, "false")
                removeExpensePermission()
            }
        }
        /**
         * return the permission associated with the expense
         */
        fun getExpensePermission(): Boolean {
            return InfoWrapper.getInstance().getInfo(expensePermission) == "true"
        }

        /**
         * remove the category info from the db.
         */
        fun removeCategoryPermission() {
            WriteLog.getInstance().writeBasicLog("STDI_remove_cat_permission")
            DBInfoConnection.getInstance().removeSingleInfo(avgCategoryChar)
            DBInfoConnection.getInstance().removeSingleInfo(lastCatUpdate)
        }
        /**
         * remove the expense info from the db.
         */
        fun removeExpensePermission() {
            WriteLog.getInstance().writeBasicLog("STDI_remove_exp_permission")
            DBInfoConnection.getInstance().removeSingleInfo(avgExpenseChar)
            DBInfoConnection.getInstance().removeSingleInfo(avgExpenseNumber)
            DBInfoConnection.getInstance().removeSingleInfo(avgExpenseVal)
            DBInfoConnection.getInstance().removeSingleInfo(lastExpUpdate)
        }
    }
}