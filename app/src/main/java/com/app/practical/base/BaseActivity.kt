package com.app.practical.base
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.app.practical.db.DeliveryDatabase
import com.app.practical.utils.networkutils.AlertUtils
import com.app.practical.utils.networkutils.NetworkUtil
import org.koin.androidx.scope.ScopeActivity



abstract class BaseActivity<B:ViewDataBinding,V:BaseViewModel> : ScopeActivity(),View.OnClickListener,RootView {


    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun initView()
    private  val MIN_CLICK_INTERVAL: Long = 1000
    lateinit var binding:B
    protected var mViewModel: V? = null
   lateinit var   db: DeliveryDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
          db = DeliveryDatabase.getDatabase(this)
        this.mViewModel = if (mViewModel == null) getViewModel() else mViewModel

        initView()
        onReady(savedInstanceState)
        setVariable(binding)
        setViewModelObservers()

    }

     fun performDataBinding(){
         binding=DataBindingUtil.setContentView(this,getLayoutId())
         binding.executePendingBindings()
     }


    private var mLastClickTime: Long = 0
    abstract fun onSingleClick(v: View?)

    abstract fun getViewModel(): V


    override fun onClick(v: View) {
        val currentClickTime = SystemClock.uptimeMillis()
        val elapsedTime = currentClickTime - mLastClickTime
        if (elapsedTime <= MIN_CLICK_INTERVAL) return
        onSingleClick(v)
        mLastClickTime = currentClickTime
    }


    abstract fun onReady(savedInstanceState: Bundle?)

    abstract fun setVariable(mViewDataBinding: B)

    abstract fun setViewModelObservers()

    abstract fun networkError(throwable: Throwable?)



    fun isNetworkConnected(): Boolean {
        return NetworkUtil.isNetworkConnected(applicationContext)
    }








    /**
     * show progress Dialog for application
     */
    open fun showProgressDialog() {
        hideProgressDialog()
        AlertUtils.showCustomProgressDialog(this)
    }

    /**
     * Hide progress Dialog for whole application
     */
    open fun hideProgressDialog() {
        AlertUtils.dismissDialog()
    }
























}