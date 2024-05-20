package com.tuyoleni.smartspend.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.FirebaseFirestoreException
import com.tuyoleni.smartspend.FireStoreRepository
import com.tuyoleni.smartspend.data.earnings.Earnings
import com.tuyoleni.smartspend.data.spending.Spending
import java.time.LocalDate

class SpendingEarningManager(private val fireStoreRepository: FireStoreRepository) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun addSpending(category: String, amount: Int) {
        val newSpending = Spending(LocalDate.now(), category, amount)
        try {
            fireStoreRepository.addSpending(newSpending)
        } catch (e: FirebaseFirestoreException) {
            // Handle Firestore specific exceptions here
        } catch (e: Exception) {
            println("Error adding spending: ${e.message}")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addEarnings(category: String, amount: Int) {
        val newEarnings = Earnings(LocalDate.now(), category, amount)
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
