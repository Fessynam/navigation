package com.tuyoleni.smartspend

import android.content.Context
import com.google.firebase.firestore.DocumentChange
import com.tuyoleni.smartspend.FireStoreRepository.BUDGET_COLLECTION
import showNotification


/**
 * this is a real-time listener on the Firestore collection specified by BUDGET_COLLECTION.
 * This listener triggers whenever there are changes in the collection (documents added, modified, or removed).
 * When a change is detected, it invokes the showNotification function to display a notification with a message
 * indicating the type of change (added, updated, or removed). This allows the app to provide real-time updates
 * to the user about budget-related changes.
 */

fun setupBudgetUpdateListener(context: Context) {
    firestore.collection(BUDGET_COLLECTION)
        .addSnapshotListener { snapshots, e ->
            if (e != null) {
                println("Listen failed: ${e.message}")
                return@addSnapshotListener
            }

            for (dc in snapshots!!.documentChanges) {
                when (dc.type) {
                    DocumentChange.Type.ADDED -> showNotification(context, "New Budget Added", "A new budget has been added.")
                    DocumentChange.Type.MODIFIED -> showNotification(context, "Budget Updated", "A budget has been updated.")
                    DocumentChange.Type.REMOVED -> showNotification(context, "Budget Removed", "A budget has been removed.")
                }
            }
        }
}

