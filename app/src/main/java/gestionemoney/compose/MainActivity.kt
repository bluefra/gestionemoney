package gestionemoney.compose


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import gestionemoney.compose.navigation.NavigationGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
           val navController = rememberNavController()
            NavigationGraph(
                navController = navController
            )
        }
    }
}

