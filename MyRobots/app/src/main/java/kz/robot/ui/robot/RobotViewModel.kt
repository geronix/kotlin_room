package kz.robot.ui.robot

import android.util.Log
import kz.robot.base.BaseViewModel
import kz.robot.data.db.entity.Robot
import kz.robot.data.repository.RobotRepository
import kz.robot.util.Constant
import kz.robot.util.SingleLiveEvent
import kz.robot.util.rx.SchedulerProvider
import kz.robot.util.rx.with

class RobotViewModel(private val robotRepository: RobotRepository,
                     private val scheduler: SchedulerProvider): BaseViewModel() {

    data class ResponseEvent(val isLoading: Boolean = false,
                             val isSuccess: Boolean = false,
                             val error: String? = null)

    val responseEvent = SingleLiveEvent<ResponseEvent>()

    val robotList = SingleLiveEvent<List<Robot>>()
    val removeRobot = SingleLiveEvent<Int>()

    var robot: Robot? = null

    fun loadRobots(offset: Int, rowCount: Int){

        launch {
            responseEvent.value = ResponseEvent(isLoading = true)
            robotRepository
                .getRobotList(offset, rowCount)
                .with(scheduler)
                .subscribe(
                    {d ->

                        responseEvent.postValue(ResponseEvent(isSuccess = true))
                        if (!d.isNullOrEmpty()){
                            robotList.postValue(d)
                        } else {
                            responseEvent.postValue(ResponseEvent(error = "Список роботов не найден!"))
                        }

                    },
                    {e ->
                        responseEvent.postValue(ResponseEvent(error = e.message))
                        e.printStackTrace()
                    }
                )
        }
    }

    fun saveRobot(action: Int, name: String, type: String, year: String){

        when (action) {
            Constant.CREATE_ROBOT -> createRobot(name, type, year)
            Constant.UPDATE_ROBOT -> updateRobot(name, type, year)
        }
    }

    fun updateRobot(name: String, type: String, year: String){

        if(name.isNotEmpty() &&
            type.isNotEmpty() &&
            year.isNotEmpty()){

            robot?.name = name
            robot?.type = type
            robot?.year = year

            launch {
                responseEvent.value = ResponseEvent(isLoading = true)
                robotRepository
                    .updateRobot(robot!!)
                    .with(scheduler)
                    .subscribe(
                        {d ->

                            responseEvent.postValue(ResponseEvent(isSuccess = true))

                        },
                        {e ->
                            responseEvent.postValue(ResponseEvent(error = e.message))
                            e.printStackTrace()
                        }
                    )
            }
        } else {
            responseEvent.postValue(ResponseEvent(error = "Не все поля заполнены!"))
        }
    }

    fun createRobot(name: String, type: String, year: String){

        if(name.isNotEmpty() &&
            type.isNotEmpty() &&
            year.isNotEmpty()){

            val robot = Robot(name = name, type = type, year = year)

            launch {
                responseEvent.value = ResponseEvent(isLoading = true)
                robotRepository
                    .createRobot(robot)
                    .with(scheduler)
                    .subscribe(
                        {d ->

                            responseEvent.postValue(ResponseEvent(isSuccess = true))

                        },
                        {e ->
                            responseEvent.postValue(ResponseEvent(error = e.message))
                            e.printStackTrace()
                        }
                    )
            }
        } else {
            responseEvent.postValue(ResponseEvent(error = "Не все поля заполнены!"))
        }
    }

    fun deleteRobot(robot: Robot){

        launch {
            robotRepository
                .deleteRobot(robot)
                .with(scheduler)
                .subscribe(
                    {d ->

                        responseEvent.postValue(ResponseEvent(isSuccess = true))
                        removeRobot.call()

                    },
                    {e ->
                        responseEvent.postValue(ResponseEvent(error = e.message))
                        e.printStackTrace()
                    }
                )
        }
    }

}