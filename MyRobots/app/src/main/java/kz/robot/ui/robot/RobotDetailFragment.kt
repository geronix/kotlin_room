package kz.robot.ui.robot

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_robot_detail.*
import kz.robot.R
import kz.robot.util.Constant
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RobotDetailFragment: Fragment() {

    companion object {
        private const val KEY_CREATE_UPDATE = "key_create_update"

        fun newInstance(action: Int) = RobotDetailFragment().apply {
            arguments = bundleOf(
                KEY_CREATE_UPDATE to action)
        }
    }

    private val model by sharedViewModel<RobotViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_robot_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val saveRobot: Int? = arguments?.getInt(KEY_CREATE_UPDATE)

        when (saveRobot) {
            Constant.UPDATE_ROBOT -> {
                val robot = model.robot

                if(robot != null){
                    etNameRobot.setText(robot.name)
                    etTypeRobot.setText(robot.type)
                    etYearRobot.setText(robot.year)
                }
            }
            Constant.CREATE_ROBOT -> model.robot = null
        }

        button.setOnClickListener {
            model.saveRobot(
                saveRobot!!, etNameRobot.text.toString(),
                etTypeRobot.text.toString(), etYearRobot.text.toString())
        }

        model.responseEvent.observe(this,
            Observer(function = fun(responseEvent: RobotViewModel.ResponseEvent?) {
                if(responseEvent != null){
                    if(responseEvent.isLoading){
                    } else {
                        if (responseEvent.isSuccess) {
                            activity?.onBackPressed()
                        } else if (responseEvent.error != null) {
                            Toast.makeText(activity, responseEvent.error, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            })
        )
    }


}