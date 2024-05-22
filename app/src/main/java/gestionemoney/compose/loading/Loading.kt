package gestionemoney.compose.loading
import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    fun CreateLoading(navController: NavController, name: String?, surname: String?) {
        Log.w("loading param", "$name $surname")
        val context = LocalContext.current
        val  standard_cat = stringArrayResource(R.array.standard_category)
        val standard_cat_img = stringArrayResource(R.array.standard_category_image)
        Log.w("check internet", "yes")
        var connectionMade by remember { mutableStateOf(false) }
        var showError by remember { mutableStateOf(false)}
            LaunchedEffect(key1 = connectionMade) {
                if (!connectionMade && ConnectionCheck().checkForInternet(context)) {
                    Log.w("loading", "connectionMade = true")
                    Log.w("check internet", "yes")
                    if (name != null && surname != null) {
                        Log.w("register loading", "$name $surname")
                        connection.register(
                            navController,
                            standard_cat,
                            standard_cat_img,
                            name,
                            surname
                        )
                    } else {
                        connection.connect(
                            navController
                        )
                    }
                    connectionMade = true
                } else if (!ConnectionCheck().checkForInternet(context)) {
                    showError = true
                    Log.w("loading", "connectionMade = false")
                }
            }
        if(showError) {
            ErrorAlert(navController, "internet missing")
        }
            Loading()
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
     private var navController: NavController? = null
     private var standardCategory: Array<String>? = null
     private var standardImage: Array<String>?= null
     private var isUserDBset = false
     private var isInfoDBset = false
     private  var isRegister = false
     private var regName: String? = null
     private var regSurname: String? = null

     fun register(nav: NavController, sCat: Array<String>, sImg: Array<String>, name: String, surname: String) {
        Log.w("loading", "regist")
         standardCategory = sCat
         standardImage = sImg
         isRegister = true
         regName = name
         regSurname = surname
         Log.w("loading", "setted isRegister $isRegister")
         connect(nav)
     }

     fun connect(nav: NavController) {
         Log.w("loading","connecting")
         navController = nav
         val uid = DBauthentication.getInstance().getUID()
         if(uid == null) {
             Log.w("loading", "uid = null")
             nav.navigate(Screens.Login.route){
                 popUpTo(0){
                     inclusive = true
                 }
             }
             return
         }
         Log.w("loading","control passed")
         DBInfoConnection.getInstance().addInfoObserver(this)
         DBUserConnection.getInstance().addUserObserver(this)
         DBUserConnection.getInstance().connectUser(uid)
         DBInfoConnection.getInstance().connectInfo(uid)
     }
     override fun updateUser(user: UserWrapper) {
         Log.w("loading", "userUpdated")
         DBUserConnection.getInstance().removeUserObserver(this)
         if(isRegister) {
             Log.w("loading", "adding category")
             if (standardCategory != null && standardImage != null) {
                 if (user.createCategories(standardCategory!!, standardImage!!)) {
                     DBUserConnection.getInstance().writeUser()
                 }
             }
         }
         isUserDBset = true
         navigate()
     }

     override fun updateError(error: String) {
         TODO("Not yet implemented")
     }

     override fun updateInfo(info: InfoWrapper) {
         Log.w("loading","updatedInfo")
         DBInfoConnection.getInstance().removeInfoObserver(this)
         Log.w("loading", "isRegister $isRegister")
         if(isRegister) {
             Log.w("loading", "adding info")
             val map: HashMap<String, String> = HashMap()
             map["name"] = regName!!
             map["surname"] = regSurname!!
             Log.w("loading", "info ${map}")
             DBInfoConnection.getInstance().writeMultipleInfo(map)
         }
         isInfoDBset = true
         navigate()
     }

     fun navigate() {
         Log.w("loading","chiamato")
         if(isUserDBset && isInfoDBset) {
             Log.w("loading","navigate")
             navController?.navigate((Screens.Homepage.route)){
                 popUpTo(0){
                     inclusive = false
                 }
             }
         }
     }
 }
