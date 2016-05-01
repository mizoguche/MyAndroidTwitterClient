package info.mizoguche.mytwitterclient.application.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import info.mizoguche.mytwitterclient.R
import info.mizoguche.mytwitterclient.databinding.ActivityLicensesBinding

class LicensesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_licenses)

        val binding = DataBindingUtil.setContentView<ActivityLicensesBinding>(this, R.layout.activity_licenses)
        binding.webView.loadUrl("file:///android_asset/licenses.html")

        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "ライセンス"
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> finish()

        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun createIntent (context: Context) : Intent {
            return Intent(context, LicensesActivity::class.java)
        }
    }
}
