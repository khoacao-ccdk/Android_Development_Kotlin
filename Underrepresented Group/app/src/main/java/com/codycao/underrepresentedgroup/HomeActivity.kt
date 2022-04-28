package com.codycao.underrepresentedgroup

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.codycao.underrepresentedgroup.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding
    val FROM_DONATE_ACTIVITY: Int = 1

    //Listeners
    private val buttons_clickListener: View.OnClickListener = View.OnClickListener {
        val theIntent = when(it.id){
            R.id.button_biography -> Intent(HomeActivity@this, BiographyActivity::class.java)
            R.id.button_more_info -> Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/Bui_Tuong_Phong"))
            else -> Intent()
        }
        if(theIntent.resolveActivity(packageManager) != null)
            startActivity(theIntent)
    }

    private val button_donate_clickListener: View.OnClickListener = View.OnClickListener {
        val theIntent = Intent(HomeActivity@this, DonateActivity::class.java)
        startActivityForResult(theIntent, FROM_DONATE_ACTIVITY)
    }

    private val textviews_clickListener: View.OnClickListener = View.OnClickListener {
        val location: String = when(it.id){
            R.id.textView_person_place_of_birth -> "geo:0,0?q=Hanoi+Vietnam"
            R.id.textView_person_location -> "geo:0,0?q=Santa+Ana+Cemetery+CA"
            else -> ""
        }
        val theIntent = Intent(Intent.ACTION_VIEW, Uri.parse(location))
        if(theIntent.resolveActivity(packageManager) != null)
            startActivity(theIntent)
    }

    //Handle result from Donate Activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && data != null){
            if(data.getBooleanExtra(DonateActivity.EXTRA_RECEIPT, false)) {
                val receiptIntent = Intent(Intent.ACTION_SENDTO,
                    Uri.parse("smsto:" + data.getStringExtra(DonateActivity.EXTRA_PHONE_NUMBER)))
                if (receiptIntent.resolveActivity(packageManager) != null) {
                    receiptIntent.putExtra("sms_body",
                        String.format("Thank you %s for your donation of $%.2f using card number ending in %s",
                            data.getStringExtra(DonateActivity.EXTRA_FULL_NAME),
                            data.getFloatExtra(DonateActivity.EXTRA_AMOUNT, 0.0f),
                            data.getStringExtra(DonateActivity.EXTRA_CREDIT_CARD)!!.split("-")[3]))
                    startActivity(receiptIntent)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonBiography.setOnClickListener(buttons_clickListener)
        binding.buttonDonate.setOnClickListener(button_donate_clickListener)
        binding.buttonMoreInfo.setOnClickListener(buttons_clickListener)
        binding.textViewPersonPlaceOfBirth.setOnClickListener(textviews_clickListener)
        binding.textViewPersonLocation.setOnClickListener(textviews_clickListener)
    }
}