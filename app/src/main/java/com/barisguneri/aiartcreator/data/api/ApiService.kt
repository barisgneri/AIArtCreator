package com.barisguneri.aiartcreator.data.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("text2image")
    @FormUrlEncoded
    suspend fun getTextToImage(
        @Field("prompt") prompt: String,
        @Field("guidance") guidance: Int,
        @Field("steps") steps: Int,
        @Field("height") height: Int,
        @Field("upscale") upscale: Int,
        @Field("width") width: Int,
        @Field("sampler") sampler: String,
        @Header("X-RapidAPI-Key") apiKey: String
    ): Response<ResponseBody>
}
