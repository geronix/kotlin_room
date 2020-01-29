package kz.robot.data.db.dao

import androidx.room.*
import io.reactivex.Single
import kz.robot.data.db.entity.Robot
import kz.robot.util.Constant


@Dao
interface RobotDAO {

    @Query(Constant.ROBOTS)
    fun createRobots()

    @Query("SELECT * FROM robots ORDER BY id DESC LIMIT :offset, :rowCount")
    fun getRobots(offset: Int, rowCount: Int): Single<List<Robot>>

    @Update
    fun update(robot: Robot): Single<Int>

    @Insert
    fun insert(robot: Robot): Single<Long>

    @Delete
    fun delete(robot: Robot): Single<Int>

}