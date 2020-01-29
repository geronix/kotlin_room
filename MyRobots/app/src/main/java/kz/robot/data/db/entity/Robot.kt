package kz.robot.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "robots")
class Robot (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name ="id")
    val id : Int = 0,

    @ColumnInfo(name ="name")
    var name : String = "",

    @ColumnInfo(name ="type")
    var type : String = "",

    @ColumnInfo(name ="year")
    var year : String = ""

)