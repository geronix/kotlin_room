package kz.robot.data.repository

import io.reactivex.Single
import kz.robot.data.ApiService
import kz.robot.data.db.RobotAppDatabase
import kz.robot.data.db.entity.Robot

interface RobotRepository {
    fun getRobotList(offset: Int, rowCount: Int): Single<List<Robot>>
    fun updateRobot(robot: Robot): Single<Int>
    fun createRobot(robot: Robot): Single<Long>
    fun deleteRobot(robot: Robot): Single<Int>
//    fun getRobotById(id: Int) : Single<Response<Robot>>
//    fun createRobot(robots: Robot): Single<Response<Any>>
//    fun editRobot(id: Int, robots: Robot): Single<Response<Any>>
//    fun deleteRobot(id: Int): Single<Response<Any>>
}

class RobotRepositoryImpl(private val api : ApiService,
                          private val db : RobotAppDatabase) : RobotRepository{

    init {
        db.robotDao().createRobots()
    }

    override fun getRobotList(offset: Int, rowCount: Int): Single<List<Robot>> {
        return db.robotDao().getRobots(offset, rowCount)
    }

    override fun updateRobot(robot: Robot): Single<Int> {
        return db.robotDao().update(robot)
    }

    override fun createRobot(robot: Robot): Single<Long> {
        return db.robotDao().insert(robot)
    }

    override fun deleteRobot(robot: Robot): Single<Int> {
        return db.robotDao().delete(robot)
    }

//    override fun getRobotById(id: Int): Single<Response<Robot>> {
//        return api.getRobotById(id)
//    }
//
//    override fun createRobot(robots: Robot): Single<Response<Any>> {
//        return api.createRobot(robots)
//    }
//
//    override fun editRobot(id: Int, robots: Robot): Single<Response<Any>> {
//        return api.editRobot(id, robots)
//    }
//
//    override fun deleteRobot(id: Int): Single<Response<Any>> {
//        return api.deleteRobot(id)
//    }

}