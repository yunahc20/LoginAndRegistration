package com.example.loginandregistration

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import database.R
import java.lang.RuntimeException


class LoanAdapter(private val dataSet: MutableList<Loan>, private val context: Activity) :
    RecyclerView.Adapter<LoanAdapter.ViewHolder>() {

    companion object{
        const val TAG= "LoanAdapter"
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val textViewLoanee: TextView
        val textViewLoaneeRepaid: TextView
        val textViewAmountDue: TextView
        val layout: ConstraintLayout

        init{
            textViewLoanee = view.findViewById(R.id.textView_loaneeName)
            textViewLoaneeRepaid = view.findViewById(R.id.textView_loaneeRepaidOrNot)
            textViewAmountDue = view.findViewById(R.id.textView_amountOwed)
            layout = view.findViewById(R.id.layout_itemLoan)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_loan, viewGroup, false)
        return ViewHolder(view)
    }


}