package hz.hz.atomchat.app_logic.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface TasksApi {

    @GET("tickets")
    fun getTasks(): Call<List<Task>>

    @POST("tickets/status")
    @FormUrlEncoded
    fun changeTasks(@Field("ticket_id") ticketId: Int, @Field("status") status: Int): Call<Unit>
}

fun createApi(): TasksApi {
    return Retrofit.Builder()
        .baseUrl("http://92.63.103.157:9000/api/client/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TasksApi::class.java)
}
