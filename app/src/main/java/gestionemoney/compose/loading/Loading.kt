package gestionemoney.compose.loading
import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import gestionemoney.compose.R
import gestionemoney.compose.components.ErrorAlert
import gestionemoney.compose.controller.ConnectionCheck
import gestionemoney.compose.controller.DBInfoConnection
import gestionemoney.compose.controller.DBauthentication
import gestionemoney.compose.controller.DBUserConnection
import gestionemoney.compose.controller.InfoChangeObserver
import gestionemoney.compose.controller.InfoWrapper
import gestionemoney.compose.controller.UserChangeObserver
import gestionemoney.compose.controller.UserWrapper
import gestionemoney.compose.navigation.Screens

const val numberOfDots = 7
val dotSize = 25.dp
const val delayUnit = 150
const val duration = numberOfDots * delayUnit
val spaceBetween = 4.dp
val connection = LoadDB()
    @Composable
    fun CreateLoading(navController: NavController) {

        if(ConnectionCheck().checkForInternet(LocalContext.current)) {
            Log.w("check internet", "yes")
            connection.connect(
                navController,
                stringArrayResource(R.array.standard_category),
                stringArrayResource(R.array.standard_category_image)
            )
            Loading()
        } else {
            ErrorAlert(navController,"internet missing")
            Log.w("check internet", "no")
        }
    }
    @Composable
    fun Loading() {

        val maxOffset = (numberOfDots * 2).toFloat()

        val infiniteTransition = rememberInfiniteTransition(label = "")

        @Composable
        fun animateOffsetWithDelay(delay: Int) = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(animation = keyframes {
                durationMillis = duration
                0f at delay with LinearEasing
                maxOffset at delay + delayUnit with LinearEasing
                0f at delay + (duration/2)
            }), label = ""
        )

        val offsets = arrayListOf<State<Float>>()
        for (i in 0 until numberOfDots) {
            offsets.add(animateOffsetWithDelay(delay = i * delayUnit))
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = maxOffset.dp)
        ) {
            offsets.forEach {
                Dot(it.value, colorResource(R.color.orangeLight))
                Spacer(Modifier.width(spaceBetween))
            }
        }
    }

 class LoadDB: UserChangeObserver, InfoChangeObserver{
     private var connecting: Boolean = false
     private var navController: NavController? = null
     private var standardCategory: Array<String>? = null
     private var standardImage: Array<String>?= null
     private var isUserDBset = false
     private var isInfoDBset = false

     fun connect(nav: NavController, sCat: Array<String>, sImg: Array<String>) {
         if(connecting) {
             return
         }
         connecting = true
         navController = nav
         standardCategory = sCat
         standardImage = sImg
         val uid = DBauthentication.getInstance().getUID()
         if(uid == null) {
             nav.navigate(Screens.Login.route){
                 popUpTo(0){
                     inclusive = true
                 }
             }
             return
         }
         DBUserConnection.getInstance().addUserObserver(this)
         DBUserConnection.getInstance().connectUser(uid)
         DBInfoConnection.getInstance().addInfoObserver(this)
         DBInfoConnection.getInstance().connectInfo(uid)
     }

     override fun updateUser(user: UserWrapper) {
         DBUserConnection.getInstance().removeUserObserver(this)
         if(standardCategory != null && standardImage != null) {
             if(user.createCategories(standardCategory!!, standardImage!!)) {
                 DBUserConnection.getInstance().writeUser()
             }
         }
         isUserDBset = true
         navigate()
     }

     override fun updateError(error: String) {
         TODO("Not yet implemented")
     }

     override fun updateInfo(info: InfoWrapper) {
         DBInfoConnection.getInstance().removeInfoObserver(this)
         DBInfoConnection.getInstance().writeSingleInfo("categoryNumber", UserWrapper.getInstance().getCategoryNumber().toString())
         DBInfoConnection.getInstance().writeSingleInfo("expenseTotNumber", UserWrapper.getInstance().getTotalExpenseNumber().toString())
         DBInfoConnection.getInstance().writeSingleInfo("expenseAvgNumber", UserWrapper.getInstance().getAvgExpenseNumber().toString())
         if(info.getHashMap().isEmpty()) {
             val map: HashMap<String, String> = HashMap()
             map["nome"] = "matteo"
             map["cognome"] = "campagnaro"
             DBInfoConnection.getInstance().writeMultipleInfo(map)
             map.remove("nome")
             map.remove("cognome")
             map["codiceFiscale"] = "CMPMTT02P03F241O"
             DBInfoConnection.getInstance().writeMultipleInfo(map)
         }
         isInfoDBset = true
         navigate()
     }

     fun navigate() {
         if(isUserDBset && isInfoDBset) {
             navController?.navigate((Screens.Homepage.route)){
                 popUpTo(0){
                     inclusive = false
                 }
             }
         }
     }

 }
