package tw.edu.ncku.an.setcard_game

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val switchButton = findViewById<Button>(R.id.switch1)


        switchButton.setOnClickListener {
            val navController = findNavController(R.id.nav_host_fragment_container)
            val currentDestination = navController.currentDestination?.id

            if (currentDestination == R.id.gameboard_frag) {
                navController.navigate(R.id.action_gameboard_frag_to_history_frag)
            } else if (currentDestination == R.id.history_frag) {
                navController.popBackStack()
            }
        }
    }
}