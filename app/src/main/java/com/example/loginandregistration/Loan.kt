package com.example.loginandregistration

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Loan(
    var lendee: String = "Anyone",
    var reasonForLoan: String = "Lunch",
    var totalAmountOwed: Double = 5.00,
    var dateFullyRepaid: Date? = null,
    var dateLoaned: Date = Date(1678726569347),
    var paid: Double= 3.50,
    var fullyRepaid: Boolean = false,
    var ownerId: String = "",
    var objectId: String = ""
) : Parcelable {
    fun balanceRemaining(): Double{
        return totalAmountOwed - paid
    }
}
