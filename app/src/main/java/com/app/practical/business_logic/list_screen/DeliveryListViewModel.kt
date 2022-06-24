package com.app.practical.business_logic.list_screen

import com.app.practical.base.BaseViewModel
import com.app.practical.model.DeliveryItem
import com.app.practical.network.api.APILiveData
import com.app.practical.network.api.ApiService

class DeliveryListViewModel(private  var apiService: ApiService) :BaseViewModel() {
    val deliveryListObserver = APILiveData<List<DeliveryItem>>()

    fun callDeliveryListApi() {
        baseDataSource.execute(this, {  apiService.getDeliveryList()}, deliveryListObserver)
    }
}