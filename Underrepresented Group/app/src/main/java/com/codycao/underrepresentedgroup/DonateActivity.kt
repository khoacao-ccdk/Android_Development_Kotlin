package com.codycao.underrepresentedgroup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.codycao.underrepresentedgroup.databinding.ActivityDonateBinding

class DonateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDonateBinding
    companion object {
        const val EXTRA_FULL_NAME: String = "com.codycao.underrepresentedgroup.EXTRA_NAME"
        const val EXTRA_PHONE_NUMBER: String = "com.codycao.underrepresentedgroup.EXTRA_PHONE_NUMBER"
        const val EXTRA_CREDIT_CARD: String = "com.codycao.underrepresentedgroup.EXTRA_CREDIT_CARD"
        const val EXTRA_CVC: String = "com.codycao.underrepresentedgroup.EXTRA_CVC"
        const val EXTRA_AMOUNT: String = "com.codycao.underrepresentedgroup.EXTRA_AMOUNT"
        const val EXTRA_RECEIPT: String = "com.codycao.underrepresentedgroup.EXTRA_RECEIPT"
    }


    //Listeners
    private val button_submit_donate_clickListener: View.OnClickListener = View.OnClickListener {
        val fullName: String = binding.editTextDonorName.text.toString()
        val phone: String = binding.editTextDonorPhone.text.toString()
        val creditCard: String = binding.editTextCreditCard.text.toString()
        val cvc: String = binding.editTextCvc.text.toString()
        val amount: String = binding.editTextDonateAmount.text.toString()
        val receipt: Boolean = binding.switchReceiveReceipt.isChecked

        if(fullName == "" || phone.length != 10 || !binding.editTextCreditCard.isCardValid || cvc.length != 3 || amount == "") {
            val myBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
                .setTitle("Insufficient Information")
                .setMessage("Please fill in required fields")
                .setPositiveButton("Ok", null)
            val dialog = myBuilder.create()
            dialog.show()
        }
        else {
            val returnIntent = Intent()
            returnIntent.putExtra(EXTRA_FULL_NAME, fullName)
            returnIntent.putExtra(EXTRA_PHONE_NUMBER, phone)
            returnIntent.putExtra(EXTRA_CREDIT_CARD, creditCard)
            returnIntent.putExtra(EXTRA_CVC, cvc.toInt())
            returnIntent.putExtra(EXTRA_AMOUNT, amount.toFloat())
            returnIntent.putExtra(EXTRA_RECEIPT, receipt)
            setResult(RESULT_OK, returnIntent)
            finish()
        }
    }

    private val editText_credit_card_focusChangeListener: View.OnFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
        if(!hasFocus && !binding.editTextCreditCard.isCardValid){
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                .setTitle("Invalid Information ")
                .setMessage("PLease enter a valid card number")
                .setPositiveButton("OK", null)
            val alert: AlertDialog = builder.create()
            alert.show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editTextCreditCard.onFocusChangeListener = editText_credit_card_focusChangeListener
        binding.buttonSubmitDonate.setOnClickListener(button_submit_donate_clickListener)
    }
}