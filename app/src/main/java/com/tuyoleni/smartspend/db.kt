package com.tuyoleni.smartspend

import android.content.ContentValues.TAG
import android.os.Build.VERSION_CODES.O
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.tuyoleni.smartspend.authentication.User
import com.tuyoleni.smartspend.data.budget.Budget
import com.tuyoleni.smartspend.data.earnings.Earnings
import com.tuyoleni.smartspend.data.spending.Spending
import java.io.IOException
import java.time.LocalDate

val firestore = FirebaseFirestore.getInstance()

object FireStoreRepository {

    private const val SPENDING_COLLECTION = "spending"
    private const val EARNINGS_COLLECTION = "earnings"
    private const val USERS_COLLECTION = "users"
    const val BUDGET_COLLECTION = "budgets"

    private fun updateBudget(documentId: String, budget: Budget) {
        val budgetMap = budget.toMap()
        firestore.collection(BUDGET_COLLECTION).document(documentId).update(budgetMap)
            .addOnSuccessListener {
                println("Budget updated with ID: $documentId")
            }.addOnFailureListener { _ ->
                // Handle the error
            }
    }

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
    private fun toObjectEarning(data: Map<String, Any>): Earnings {
        val amount = (data["amount"] as Long).toInt()
        val createdString = data["date"] as String
        val date = LocalDate.parse(createdString)
        val category = data["category"] as String
        return Earnings(date, category, amount)
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


    suspend fun addUser(user: User) {
        firestore.collection(USERS_COLLECTION).add(user).addOnSuccessListener { documentReference ->
            println("User added with ID: ${documentReference.id}")
        }.addOnFailureListener { e ->
            println("Error adding user: ${e.message}")
        }
    }

    suspend fun addBudget(budget: Budget) {
        try {
            val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email
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
    suspend fun getBudget(callback: (List<Budget>) -> Unit) {
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

    suspend fun deleteBudget(budget: Budget) {
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
        val earningsList = mutableListOf<Earnings>()
        val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email

        firestore.collection(EARNINGS_COLLECTION).whereEqualTo("user", currentUserEmail).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val earnings = toObjectEarning(document.data)
                    earningsList.add(earnings)
                }
            }.addOnFailureListener { e ->
                println("Error getting Earnings: ${e.message}")
            }
        return earningsList
    }
}
