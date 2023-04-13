package com.example.loginandregistration

import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.loginandregistration.databinding.ActivityLoanDetailBinding

class LoanDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoanDetailBinding
    var loanIsEditable = false
    lateinit var loan : Loan

    companion object {
        val EXTRA_LOAN = "loan"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoanDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loan = intent.getParcelableExtra<Loan>(EXTRA_LOAN) ?: Loan()
        binding.checkBoxLoanDetailIsFullyRepaid.isChecked = loan.fullyRepaid
        binding.editTextLoanDetailInitialLoan.setText(loan.totalAmountOwed.toString())
        binding.editTextLoanDetailBorrower.setText(loan.lendee)
        binding.editTextLoanDetailAmountRepaid.setText(loan.paid.toString())
        binding.textViewLoanDetailAmountStillOwed.text = String.format("Still Owed %.2f", loan.totalAmountOwed - loan.paid)

        binding.buttonLoanDetailSave.setOnClickListener {
            // save loan: decide if the loan is a new one and save new
            // or update existing
            if(loan.ownerId.isBlank()) {
                loan.ownerId =
                    intent.getStringExtra(LoanListActivity.EXTRA_USER_ID)!!
            }
            // get the values from all the fields and update the loan object
            loan.lendee = binding.editTextLoanDetailBorrower.text.toString()
            loan.totalAmountOwed =
                binding.editTextLoanDetailInitialLoan.text.toString().toDouble()
            // make the backendless call to save the object
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_loan_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.menu_item_loan_detail_edit -> {
                toggleEditable()
                true
            }
            R.id.menu_item_loan_detail_delete -> {
                deleteFromBackendless()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteFromBackendless() {
        Backendless.Data.of(Loan::class.java).remove( loan,
            object : AsyncCallback<Long?> {
                override fun handleResponse(response: Long?) {
                    // Person has been deleted. The response is the
                    // time in milliseconds when the object was deleted
                    Toast.makeText(this@LoanDetailActivity, "${loan.lendee} Deleted", Toast.LENGTH_SHORT).show()
                    finish()
                }

                override fun handleFault(fault: BackendlessFault) {
                    Log.d("BirthdayDetail", "handleFault: ${fault.message}")
                }
            })
    }


    private fun toggleEditable() {
        if (loanIsEditable) {
            loanIsEditable = false
            binding.buttonLoanDetailSave.isEnabled = false
            binding.buttonLoanDetailSave.visibility = View.GONE
            binding.checkBoxLoanDetailIsFullyRepaid.isEnabled = false
            binding.editTextLoanDetailBorrower.inputType = InputType.TYPE_NULL
            binding.editTextLoanDetailBorrower.isEnabled = false
            binding.editTextLoanDetailAmountRepaid.inputType = InputType.TYPE_NULL
            binding.editTextLoanDetailAmountRepaid.isEnabled = false
            binding.editTextLoanDetailInitialLoan.inputType = InputType.TYPE_NULL
            binding.editTextLoanDetailInitialLoan.isEnabled = false
            binding.checkBoxLoanDetailIsFullyRepaid.isClickable = false
        } else {
            loanIsEditable = false
            binding.buttonLoanDetailSave.isEnabled = true
            binding.buttonLoanDetailSave.visibility = View.VISIBLE
            binding.checkBoxLoanDetailIsFullyRepaid.isEnabled = true
            binding.editTextLoanDetailBorrower.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            binding.editTextLoanDetailBorrower.isEnabled = true
            binding.editTextLoanDetailAmountRepaid.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            binding.editTextLoanDetailAmountRepaid.isEnabled = true
            binding.editTextLoanDetailInitialLoan.inputType = InputType.TYPE_NUMBER_VARIATION_NORMAL
            binding.editTextLoanDetailInitialLoan.isEnabled = true
            binding.checkBoxLoanDetailIsFullyRepaid.isClickable = true
        }

        binding.buttonLoanDetailSave.setOnClickListener {
            // save loan: decide if the loan is a new one and save new
            // or update existing
            if(loan.ownerId.isBlank()) {
                loan.ownerId =
                    intent.getStringExtra(LoanListActivity.EXTRA_USER_ID)!!
            }
            // get the values from all the fields and update the loan object
            loan.lendee = binding.editTextLoanDetailBorrower.text.toString()
            loan.totalAmountOwed =
                binding.editTextLoanDetailInitialLoan.text.toString().toDouble()
            // make the backendless call to save the object
        }
    }

}


