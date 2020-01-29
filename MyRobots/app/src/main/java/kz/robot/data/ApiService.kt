package kz.robot.data


import io.reactivex.Single
import kz.robot.data.dto.Robot
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("robots/api/robots")
    fun getRobotList(): Single<Response<List<Robot>>>

    @GET("robots/api/robots/{id}")
    fun getRobotById(@Path("id") id: Int) : Single<Response<Robot>>

    @POST("robots/api/robots")
    fun createRobot(@Body robot: Robot): Single<Response<Any>>

    @PUT("robots/api/robots/{id}")
    fun editRobot(@Path("id") id: Int, @Body robot: Robot): Single<Response<Any>>

    @DELETE("robots/api/robots/{id}")
    fun deleteRobot(@Path("id") id: Int): Single<Response<Any>>

}