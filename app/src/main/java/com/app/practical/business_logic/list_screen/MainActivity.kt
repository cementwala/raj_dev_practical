package com.app.practical.business_logic.list_screen
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.viewModelScope
import com.app.practical.R
import com.app.practical.base.BaseActivity
import com.app.practical.business_logic.delivery_details.DeliveryDetailActivity
import com.app.practical.callbacks.OnItemSelected
import com.app.practical.databinding.ActivityMainBinding
import com.app.practical.model.DeliveryItem
import com.app.practical.utils.networkutils.AlertUtils
import com.app.practical.utils.networkutils.NetworkUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : BaseActivity<ActivityMainBinding, DeliveryListViewModel>() {

    val vm: DeliveryListViewModel by viewModel { parametersOf() }
    lateinit var deliveryAdapter: DeliveryAdapter
    var deliveryList = ArrayList<DeliveryItem>()
    var filterDeliveryList = ArrayList<DeliveryItem>()


    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        supportActionBar?.let {

            it.title=getString(R.string.things_to_deliver)

        }
        vm.viewModelScope.launch(Dispatchers.IO) {
            deliveryList = db.deliveryDao().getDeliveries() as ArrayList<DeliveryItem> /* = java.util.ArrayList<com.app.practical.model.DeliveryItem> */
        }
        binding.edtSearch.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
              updateDeliveries(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

    }

    override fun onSingleClick(v: View?) {

    }

    override fun getViewModel(): DeliveryListViewModel {
        return vm
    }

    override fun onReady(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            binding.rvDeliveries.setHasFixedSize(true)
            deliveryAdapter = DeliveryAdapter(ArrayList(), object : OnItemSelected {
                override fun onSelected(pos: Int) {
                    startActivity(
                        Intent(
                            this@MainActivity,
                            DeliveryDetailActivity::class.java
                        ).also {
                            it.putExtra("Deliver", deliveryAdapter.deliveryList[pos])
                        })
                }
            })
            binding.rvDeliveries.adapter = deliveryAdapter

            if (NetworkUtil.isNetworkConnected(this)) {
                vm.callDeliveryListApi()
            } else {
                vm.viewModelScope.launch(Dispatchers.IO) { }

            }

        }


    }

    override fun setVariable(mViewDataBinding: ActivityMainBinding) {


    }

    override fun setViewModelObservers() {
        // observe data here
        vm.loadingObserver.observe(this) {

            if (it){
                showLoader()
            }else{
                hideLoader()
            }
        }
        vm.deliveryListObserver.observe(this,
            onChange = {
                vm.viewModelScope.launch(Dispatchers.IO) {
                    db.deliveryDao().addDelivery(it)
                    vm.viewModelScope.launch(Dispatchers.Main) {
                       updateDeliveries()
                    }
                }
            },
            onError = { code: Int, message: String ->
                vm.viewModelScope.launch(Dispatchers.IO) {
                    db.deliveryDao().addDelivery(getOfflineList())
                    deliveryList = getOfflineList()
                    vm.viewModelScope.launch(Dispatchers.Main) {
                    updateDeliveries()
                    }
                }

            }
        )

    }

    override fun networkError(throwable: Throwable?) {

    }

    override fun showSnackBar(message: String) {

    }

    override fun showSnackBar(message: String, showOk: Boolean) {

    }

    override fun showSnackBar(view: View, message: String, showOk: Boolean) {

    }

    override fun showErrorSnackBar() {

    }

    override fun showLoader() {
        AlertUtils.showCustomProgressDialog(this)
    }

    override fun hideLoader() {
        AlertUtils.dismissDialog()
    }

    override fun showKeyBoard() {

    }

    override fun hideKeyBoard() {

    }

    override fun showDialogWithOneAction(
        title: String?,
        message: String?,
        positiveButton: String?,
        positiveFunction: (DialogInterface, Int) -> Unit
    ) {

    }

    override fun showDialogWithTwoActions(
        title: String?,
        message: String?,
        positiveName: String?,
        negativeName: String?,
        positiveFunction: (DialogInterface, Int) -> Unit,
        negativeFunction: (DialogInterface, Int) -> Unit
    ) {

    }

    override fun hideDialog() {

    }

    fun getOfflineList(): ArrayList<DeliveryItem> {
        val deliveryList = ArrayList<DeliveryItem>()

        deliveryList.add(
            DeliveryItem(
                0,
                1,
                "Deliver item to Rishi  at Nanjangud, Mysore, Karnataka, India",
                "https://picsum.photos/seed/picsum/200/300",
                DeliveryItem.Location("Nanjangud, Mysore, Karnataka, India", 12.120000, 76.680000)
            )
        )
        deliveryList.add(
            DeliveryItem(
                0,
                2,
                "Deliver item to Rahul  at Chittorgarh, Rajasthan, India ",
                "https://picsum.photos/seed/picsum/200/300",
                DeliveryItem.Location("Chittorgarh, Rajasthan, India", 24.879999, 74.629997)
            )
        )
        deliveryList.add(
            DeliveryItem(
                0,
                3,
                "Deliver item to Palak at Ratnagiri, Maharashtra, India",
                "https://picsum.photos/seed/picsum/200/300",
                DeliveryItem.Location("Ratnagiri, Maharashtra, India", 16.994444, 73.300003)
            )
        )
        deliveryList.add(
            DeliveryItem(
                0,
                4,
                "Deliver item to Maulik at Goregaon, Mumbai, Maharashtra, India",
                "https://picsum.photos/seed/picsum/200/300",
                DeliveryItem.Location("Goregaon, Mumbai, Maharashtra, India", 19.155001, 72.849998)
            )
        )
        deliveryList.add(
            DeliveryItem(
                0,
                5,
                "Deliver item to nikhil at Pindwara, Rajasthan, India",
                "https://picsum.photos/seed/picsum/200/300",
                DeliveryItem.Location("Pindwara, Rajasthan, India", 24.794500, 73.055000)
            )
        )
        deliveryList.add(
            DeliveryItem(
                0,
                6,
                "Deliver item to Raj at Raipur, Chhattisgarh, India",
                "https://picsum.photos/seed/picsum/200/300",
                DeliveryItem.Location("Raipur, Chhattisgarh, India", 21.250000, 81.629997)
            )
        )
        deliveryList.add(
            DeliveryItem(
                0,
                7,
                "Deliver item to Nishi at Gokak, Karnataka, India",
                "https://picsum.photos/seed/picsum/200/300",
                DeliveryItem.Location("Gokak, Karnataka, India", 16.166700, 74.833298)
            )
        )
        deliveryList.add(
            DeliveryItem(
                0,
                8,
                "Deliver item to Divya at Lucknow, Uttar Pradesh, India",
                "https://picsum.photos/seed/picsum/200/300",
                DeliveryItem.Location("Lucknow, Uttar Pradesh, India", 26.850000, 80.949997)
            )
        )
        deliveryList.add(
            DeliveryItem(
                0,
                9,
                "Deliver item to Rohit at Delhi, India",
                "https://picsum.photos/seed/picsum/200/300",
                DeliveryItem.Location("Delhi, India", 28.679079, 77.069710)
            )
        )
        deliveryList.add(
            DeliveryItem(
                0,
                10,
                "Deliver item to Sanjay at Mumbai, Maharashtra, India",
                "https://picsum.photos/seed/picsum/200/300",
                DeliveryItem.Location("Mumbai, Maharashtra, India", 19.076090, 72.877426)
            )
        )
        deliveryList.add(
            DeliveryItem(
                0,
                11,
                "Deliver item to Prem at Sagar, Karnataka, India",
                "https://picsum.photos/seed/picsum/200/300",
                DeliveryItem.Location("Sagar, Karnataka, India", 14.167040, 75.040298)
            )
        )

        return deliveryList
    }

    fun updateDeliveries(searchValue:String="") {
        vm.viewModelScope.launch(Dispatchers.IO) {
            deliveryList = db.deliveryDao().getDeliveries() as ArrayList<DeliveryItem> /* = java.util.ArrayList<com.app.practical.model.DeliveryItem> */
            vm.viewModelScope.launch(Dispatchers.Main) {
                var list=ArrayList<DeliveryItem>()
                if (searchValue.isNotEmpty()){
                    deliveryList.forEach {
                        if (it.description.contains(searchValue,true))
                            list.add(it)
                    }
                }else{
                    list.addAll(deliveryList)
                }


                if (list.isNotEmpty()){
                    deliveryAdapter.updateItems(list)
                    binding.tvNoData.visibility=View.GONE
                    binding.rvDeliveries.visibility=View.VISIBLE
                } else{
                   binding.tvNoData.visibility=View.VISIBLE
                   binding.rvDeliveries.visibility=View.GONE
                }
            }
        }
    }
}