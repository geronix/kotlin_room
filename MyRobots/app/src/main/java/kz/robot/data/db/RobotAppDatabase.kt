package kz.robot.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import kz.robot.data.db.dao.RobotDAO
import kz.robot.data.db.entity.Robot


@Database(entities = [Robot::class], version = 1, exportSchema = false)
abstract class RobotAppDatabase: RoomDatabase() {
    abstract fun robotDao(): RobotDAO
}