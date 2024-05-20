package com.tuyoleni.smartspend.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.FirebaseFirestoreException
import com.tuyoleni.smartspend.FireStoreRepository
import com.tuyoleni.smartspend.data.earnings.Earnings
import com.tuyoleni.smartspend.data.spending.Spending
import java.time.LocalDate
// this class contain s the function to add spending 
class SpendingEarningManager(private val fireStoreRepository: FireStoreRepository) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun addSpending(date: LocalDate, category: String, amount: Int) {
        val newSpending = Spending(date, category, amount)
        try {
            fireStoreRepository.addSpending(newSpending)
        } catch (e: FirebaseFirestoreException) {
            // Handle Firestore specific exceptions here
        } catch (e: Exception) {
            println("Error adding spending: ${e.message}")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addEarnings(date: LocalDate, category: String, amount: Int) {
        val newEarnings = Earnings(date, category, amount)
        try {
            fireStoreRepository.addEarnings(newEarnings)
        } catch (e: FirebaseFirestoreException) {
            // Handle Firestore specific exceptions here
        } catch (e: Exception) {
            // Handle other potential exceptions
            println("Error adding earnings: ${e.message}")
        }
    }
}
