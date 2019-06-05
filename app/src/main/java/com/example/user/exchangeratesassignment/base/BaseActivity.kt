package com.example.user.exchangeratesassignment.base

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import com.example.engine.engine.Engine
import com.example.engine.service.RatesService

open class BaseActivity : AppCompatActivity() {

    var progress: ProgressDialog? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progress = ProgressDialog(this)

    }

    fun getRatesService(): RatesService {
        return Engine.get().ratesService
    }

    fun showLoading() {
        progress?.apply {
            this.isIndeterminate = false
            this.setMessage("Loading Data")
            this.progress = 0
            this.setCancelable(false)
            this.show()
        }
    }

    fun hideLoading() {
        progress?.apply { this.cancel() }
    }
}
