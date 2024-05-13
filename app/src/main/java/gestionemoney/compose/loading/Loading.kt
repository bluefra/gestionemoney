package gestionemoney.compose.loading
import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import gestionemoney.compose.R
import gestionemoney.compose.controller.DBauthentication
import gestionemoney.compose.controller.DBconnection
import gestionemoney.compose.controller.UserChangeObserver
import gestionemoney.compose.controller.UserWrapper
import gestionemoney.compose.navigation.Screens

const val numberOfDots = 7
val dotSize = 10.dp
val dotColor: Color = Color.Yellow
const val delayUnit = 200
const val duration = numberOfDots * delayUnit
val spaceBetween = 2.dp
val connection = LoadDB()
    @Composable
    fun CreateLoading(navController: NavController) {
        connection.connect(navController, stringArrayResource(R.array.standard_category))
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
                Dot(it.value)
                Spacer(Modifier.width(spaceBetween))
            }
        }
    }

 class LoadDB: UserChangeObserver{
     private var connecting: Boolean = false
     private var navController: NavController? = null
     private var standardCategory: Array<String>? = null
     fun connect(nav: NavController, sCat: Array<String>) {
         if(connecting) {
             return
         }
         connecting = true
         navController = nav
         standardCategory = sCat
         val uid = DBauthentication.getInstance().getUID()
         if(uid == null) {
             nav.navigate(Screens.Login.route)
             return
         }
         DBconnection.getInstance().addObserver(this)
         DBconnection.getInstance().connect(uid)
     }
     override fun update(user: UserWrapper) {
         DBconnection.getInstance().removeObserver(this)
         standardCategory?.let { user.createCategories(it) }
         DBconnection.getInstance().writeUser()
         Log.w("user", user.toString())
         navController?.navigate((Screens.Homepage.route))
     }

 }
