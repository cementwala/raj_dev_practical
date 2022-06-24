package com.app.practical.network.api

import com.app.practical.model.DeliveryItem
import com.app.practical.network.api.URLFactory.GET_DELIVERIES
import retrofit2.Response
import retrofit2.http.GET


interface ApiService {
    @GET(GET_DELIVERIES)
    suspend fun getDeliveryList(): Response<List<DeliveryItem>>


}
