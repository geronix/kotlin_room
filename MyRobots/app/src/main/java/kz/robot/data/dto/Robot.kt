package kz.robot.data.dto

import com.google.gson.annotations.SerializedName

class Robot(
    @SerializedName("name") val name : String = "",
    @SerializedName("type") val type : String = "",
    @SerializedName("year") val year : String = ""
)