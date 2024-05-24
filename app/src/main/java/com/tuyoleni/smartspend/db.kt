package com.tuyoleni.smartspend

import android.content.ContentValues.TAG
import android.os.Build.VERSION_CODES.O
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.tuyoleni.smartspend.authentication.User
import com.tuyoleni.smartspend.data.budget.Budget
import com.tuyoleni.smartspend.data.earnings.Earnings
import com.tuyoleni.smartspend.data.spending.Spending
import java.io.IOException
import java.time.LocalDate

val firestore = FirebaseFirestore.getInstance()

object FireStoreRepository {
    private const val SPENDING_COLLECTION = "spending"
    private const val EARNINGS_COLLECTION = "earning"
    private const val USERS_COLLECTION = "users"
    const val BUDGET_COLLECTION = "budgets"

    @RequiresApi(O)
    private fun toBudget(data: Map<String, Any>): Budget {
        val user = data["user"] as String
        val threshHold = (data["threshHold"] as Long).toInt()
        val createdString = data["created"] as String
        val created = LocalDate.parse(createdString)
        val category = data["category"] as String
        return Budget(user, threshHold, created, category)
    }

    @RequiresApi(O)
    private fun toObjectEarning(documentSnapshot: DocumentSnapshot): Earnings? {
        return try {
            val user = documentSnapshot.getString("user")
                ?: throw IllegalArgumentException("Missing user field")
            val dateString = documentSnapshot.getString("date")
                ?: throw IllegalArgumentException("Missing date field")
            val date = LocalDate.parse(dateString)
            val category = documentSnapshot.getString("category") ?: throw IllegalArgumentException(
                "Missing category field"
            )
            val amount = documentSnapshot.getString("amount")?.toInt()
                ?: throw IllegalArgumentException("Missing or invalid amount field")

            Earnings(user, date, category, amount)
        } catch (e: Exception) {
            Log.e(TAG, "toObjectEarning: ", e)
            null
        }
    }



    @RequiresApi(O)
    private fun toObjectSpending(documentSnapshot: DocumentSnapshot): Spending? {
        return try {
            val user = documentSnapshot.getString("user")
                ?: throw IllegalArgumentException("Missing user field")
            val dateString = documentSnapshot.getString("date")
                ?: throw IllegalArgumentException("Missing date field")
            val date = LocalDate.parse(dateString)
            val category = documentSnapshot.getString("category") ?: throw IllegalArgumentException(
                "Missing category field"
            )
            val amount = documentSnapshot.getString("amount")?.toInt()
                ?: throw IllegalArgumentException("Missing or invalid amount field")

            Spending(user, date, category, amount)
        } catch (e: Exception) {
            Log.e(TAG, "toObjectSpending: ", e)
            null
        }
    }


    fun addUser(user: User) {
        firestore.collection(USERS_COLLECTION).add(user).addOnSuccessListener { documentReference ->
            println("User added with ID: ${documentReference.id}")
        }.addOnFailureListener { e ->
            println("Error adding user: ${e.message}")
        }
    }

    fun addBudget(budget: Budget) {
        try {
            val budgetMap = budget.toMap()
            firestore.collection(BUDGET_COLLECTION).add(budgetMap)
                .addOnSuccessListener { documentReference ->
                    println("Budget added with ID: ${documentReference.id}")
                }.addOnFailureListener { e ->
                    throw IOException("Error adding budget: ${e.message}", e)
                }
        } catch (e: Exception) {
            throw IOException("Error converting budget to map: ${e.message}", e)
        }
    }

    @RequiresApi(O)
    fun getBudget(callback: (List<Budget>) -> Unit) {
        val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email

        firestore.collection(BUDGET_COLLECTION).whereEqualTo("user", currentUserEmail).get()
            .addOnSuccessListener { documents ->
                val budgetList = mutableListOf<Budget>()
                for (document in documents) {
                    val budget = toBudget(document.data)
                    budgetList.add(budget)
                }
                callback(budgetList)
            }.addOnFailureListener { e ->
                println("Error getting budgets: ${e.message}")
                callback(
                    emptyList()
                )
            }
    }

    fun deleteBudget(budget: Budget) {
        firestore.collection(BUDGET_COLLECTION).whereEqualTo("category", budget.category).get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    // Do something
                } else {
                    documents.first().reference.delete()
                }
            }.addOnFailureListener { _ ->
                // Handle the error
            }
    }

    @RequiresApi(O)
    fun getSpending(): List<Spending> {
        val spendingList = mutableListOf<Spending>()
        val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email

        firestore.collection(SPENDING_COLLECTION).whereEqualTo("user", currentUserEmail).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val spending = toObjectSpending(document)
                    if (spending != null) {
                        spendingList.add(spending)
                    }
                }
            }.addOnFailureListener { e ->
                println("Error getting Spending: ${e.message}")
            }

        return spendingList
    }

    @RequiresApi(O)
    fun getEarnings(): List<Earnings> {
        val earningsList: MutableList<Earnings> = mutableListOf()
        val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email

        // Query the Firestore collection for earnings belonging to the current user
        val query = firestore.collection(EARNINGS_COLLECTION).whereEqualTo("user", currentUserEmail)

        // Add a snapshot listener to the query
        query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e(TAG, "Error getting earnings: ${error.message}")
                return@addSnapshotListener
            }

            // Clear the existing earnings list
            earningsList.clear()

            // Iterate through the snapshot documents and convert them to Earnings objects
            for (document in snapshot!!.documents) {
                val earning = toObjectEarning(document)
                if (earning != null) {
                    earningsList.add(earning)
                }
            }
        }

        // Return the earnings list
        return earningsList
    }
}
