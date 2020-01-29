package kz.robot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kz.robot.ui.robot.RobotDetailFragment
import kz.robot.ui.robot.RobotsFragment
import kz.robot.util.Constant

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .add(R.id.robotsContainer, RobotsFragment.newInstance())
                .commit()
        }

        fab.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.robotsContainer, RobotDetailFragment.newInstance(Constant.CREATE_ROBOT))
                .addToBackStack(null)
                .commit()
        }

    }
}
