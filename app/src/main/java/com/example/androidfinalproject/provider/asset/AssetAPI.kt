package com.example.androidfinalproject.provider.asset

import com.example.androidfinalproject.utils.ResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AssetAPI {
    @GET("listassets/{id}")
    fun getListAssetByProviderID(@Path("id")id: String): Call<ResponseData>
    @GET("providerreports/daily")
    fun getReportAssetDaily(@Query("id")id: String): Call<ResponseData>
    @GET("providerreports/monthly")
    fun getReportAssetMonthly(@Query("id")id: String): Call<ResponseData>
    @GET("provider/asset/review/{id}")
    fun getRatingAsset(@Path("id")id:String):Call<ResponseData>

}