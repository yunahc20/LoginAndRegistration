package com.example.loginandregistration

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.persistence.DataQueryBuilder
import com.example.loginandregistration.databinding.ActivityLoanListBinding

class LoanListActivity : AppCompatActivity() {

    companion object{
        const val TAG = "LoanListActivity"
        const val EXTRA_USER_ID = "UserID"
    }

    private lateinit var userId: String
    private var loans = mutableListOf<Loan>()
    private lateinit var binding: ActivityLoanListBinding
    private  lateinit var adapter: LoanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoanListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userId = intent?.getStringExtra(LoginActivity.EXTRA_USERID).toString()
        Log.d(TAG, "onCreat: $userId")
        retrieveAllData(userId)
    }

    private fun retrieveAllData(userId: String?) {
        Log.d(TAG, "retrieveAllData: Retrieving Loans")
        val place = "ownerId = '$userId'"
        val queryBuilder = DataQueryBuilder.create()
        queryBuilder.whereClause = place
        Backendless.Data.of(Loan::class.java).find(
            queryBuilder,
            object : AsyncCallback<List<Loan>> {
                @SuppressLint("NotifyDataSetChanged")
                override fun handleResponse(foundLoans: List<Loan>?) {
                    Log.d(TAG, "handleResponse: $foundLoans")
                    if (foundLoans != null) {
                        loans = foundLoans as MutableList<Loan>
                        adapter = LoanAdapter(loans, this@LoanListActivity)
                        binding.loanrecyclerview.adapter = adapter
                        binding.loanrecyclerview.layoutManager =
                            LinearLayoutManager(this@LoanListActivity)
                        adapter.notifyDataSetChanged()
                        Log.d(TAG, "handleResponse: data set changed")
                    }
                }

                override fun handleFault(fault: BackendlessFault?) {
                    Log.d(TAG, "handleFault: $fault?.message")
                }
            }
        )
    }
    fun onLoanItemClicked(loan: Loan){
        val intent = Intent(this, LoanDetailActivity::class.java)
        intent.putExtra(LoanDetailActivity.EXTRA_LOAN, loan)
        startActivity(intent)
    }
}