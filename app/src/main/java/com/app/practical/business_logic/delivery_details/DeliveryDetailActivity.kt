package com.app.practical.business_logic.delivery_details

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.app.practical.R
import com.app.practical.base.BaseActivity
import com.app.practical.databinding.ActivityDeliveryDetailBinding
import com.app.practical.model.DeliveryItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DeliveryDetailActivity : BaseActivity<ActivityDeliveryDetailBinding, DeliveryDetailViewModel>(),
    OnMapReadyCallback {
    val vm: DeliveryDetailViewModel by viewModel { parametersOf() }
    private var deliveryItem: DeliveryItem? = null
    private lateinit var mMap: GoogleMap
    override fun getLayoutId(): Int {
        return R.layout.activity_delivery_detail
    }

    override fun initView() {
        supportActionBar?.let {
            it.title=getString(R.string.delivery_details)
            it.setDisplayHomeAsUpEnabled(true)

        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onSingleClick(v: View?) {
    }

    override fun getViewModel(): DeliveryDetailViewModel {
        return  vm
    }

    override fun onReady(savedInstanceState: Bundle?) {
        if (intent != null) {
            deliveryItem = intent.getSerializableExtra("Deliver") as DeliveryItem
        }
        deliveryItem?.let {
            binding.tvDescription.text = it.description
            Glide
                .with(binding.ivImage.context)
                .applyDefaultRequestOptions(
                    RequestOptions
                    .bitmapTransform(RoundedCorners(20))
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder))
                .load(it.imageUrl)
                .into(binding.ivImage)
        }
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun setVariable(mViewDataBinding: ActivityDeliveryDetailBinding) {
    }

    override fun setViewModelObservers() {

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

    }

    override fun hideLoader() {

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

    override fun onMapReady(p0: GoogleMap) {
        mMap=p0
        val latLng = LatLng(deliveryItem!!.location.lat, deliveryItem!!.location.lng)
        mMap.addMarker(
            MarkerOptions().position(latLng).title(deliveryItem!!.description)
        )
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8f))
    }

}