package com.tuyoleni.smartspend

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tuyoleni.smartspend.authentication.User
import com.tuyoleni.smartspend.data.budget.Budget
import com.tuyoleni.smartspend.data.earnings.Earnings
import com.tuyoleni.smartspend.data.spending.Spending
import java.io.IOException
import java.time.LocalDate

val firestore = FirebaseFirestore.getInstance()


// Firestore Repository
object FireStoreRepository {

    private const val SPENDING_COLLECTION = "spending"
    private const val EARNINGS_COLLECTION = "earnings"
    private const val USERS_COLLECTION = "users"
    private const val BUDGET_COLLECTION = "budgets"

    private fun updateBudget(documentId: String, budget: Budget) {
        val budgetMap = budget.toMap()
        firestore.collection(BUDGET_COLLECTION).document(documentId).update(budgetMap)
            .addOnSuccessListener {
                println("Budget updated with ID: $documentId")
            }.addOnFailureListener { e ->
                // Handle the error
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun toBudget(data: Map<String, Any>): Budget {
        val threshHold = data["threshHold"] as Long
        val createdString = data["created"] as String
        val created = LocalDate.parse(createdString)
        val category = data["category"] as String
        return Budget(threshHold.toInt(), created, category)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun toObjectEarning(data: Map<String, Any>): Earnings {
        val amount = data["amount"] as Long
        val createdString = data["date"] as String
        val date = LocalDate.parse(createdString)
        val category = data["category"] as String
        return Earnings(date, category, amount.toInt())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun toObjectSpending(data: Map<String, Any>): Spending {
        val amount = data["amount"] as Long
        val createdString = data["date"] as String
        val date = LocalDate.parse(createdString)
        val category = data["category"] as String
        return Spending(date, category, amount.toInt())
    }

    suspend fun addUser(user: User) {
        firestore.collection(USERS_COLLECTION).add(user).addOnSuccessListener { documentReference ->
            println("User added with ID: ${documentReference.id}")
        }.addOnFailureListener { e ->
            println("Error adding user: ${e.message}")
        }
    }

    // Authentication Functions
    fun registerUser(user: User, navController: NavController) {
        val auth = FirebaseAuth.getInstance() // Initialize in non-static context

        auth.createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                navController.navigate("login")
            } else {
                println("Registration failed: ${task.exception?.message}")
                // Handle registration failure
            }
        }
    }

    @Composable
    fun LoginUser(user: User, navController: NavController) {
        val auth = FirebaseAuth.getInstance() // Initialize in non-static context

        auth.signInWithEmailAndPassword(user.email, user.password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                navController.navigate("home")
            } else {
                println("Login failed: ${task.exception?.message}")
                // Handle login failure with SnackBar
            }
        }

        val snackbarHostState = remember { SnackbarHostState() }
        SnackbarHost(hostState = snackbarHostState)

        LaunchedEffect(key1 = true) {
            snackbarHostState.showSnackbar("Login failed. Please try again.")
        }
    }

    suspend fun addBudget(budget: Budget) {
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

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getBudget(callback: (List<Budget>) -> Unit) {
        firestore.collection(BUDGET_COLLECTION).get().addOnSuccessListener { documents ->
            val budgetList = mutableListOf<Budget>()
            for (document in documents) {
                val budget = toBudget(document.data)
                budgetList.add(budget)
            }
            callback(budgetList)
        }.addOnFailureListener { e ->
            println("Error getting budgets: ${e.message}")
            callback(emptyList())
        }
    }

    suspend fun deleteBudget(budget: Budget) {
        firestore.collection(BUDGET_COLLECTION).whereEqualTo("category", budget.category).get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    //Do some
                } else {
                    documents.first().reference.delete()
                }
            }.addOnFailureListener { e ->
                // Handle the error
            }
    }

    suspend fun getUser(uid: String, callback: (User?) -> Unit) {
        val userDocRef = firestore.collection(USERS_COLLECTION).document(uid)
        userDocRef.get().addOnSuccessListener { documentSnapshot ->
            val user = documentSnapshot.toObject(User::class.java)
            callback(user)
        }.addOnFailureListener { e ->
            println("Error getting user: ${e.message}")
            callback(null)
        }
    }

    suspend fun addSpending(spending: Spending) {
        try {
            val spendingMap = spending.toMap()
            firestore.collection(SPENDING_COLLECTION).add(spendingMap)
                .addOnSuccessListener { documentReference ->
                    println("Spending data added with ID: ${documentReference.id}")
                }.addOnFailureListener { e ->
                    throw IOException("Error adding spending data: ${e.message}", e)
                }
        } catch (e: IOException) {
            println("Error converting spending to map: ${e.message}")
        }
    }

    suspend fun addEarnings(earnings: Earnings) {
        try {
            val earningsMap = earnings.toMap()
            firestore.collection(EARNINGS_COLLECTION).add(earningsMap)
                .addOnSuccessListener { documentReference ->
                    println("Earnings data added with ID: ${documentReference.id}")
                }.addOnFailureListener { e ->
                    throw IOException("Error adding earnings data: ${e.message}", e)
                }
        } catch (e: IOException) {
            println("Error converting earnings to map: ${e.message}")
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getSpending(): List<Spending> {
        val spendingList = mutableListOf<Spending>()
        firestore.collection(SPENDING_COLLECTION).get().addOnSuccessListener { documents ->
            for (document in documents) {
                val spending = toObjectSpending(document.data)
                spendingList.add(spending)
            }
        }.addOnFailureListener { e ->
            println("Error getting Spending: ${e.message}")
        }
        return spendingList
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getEarnings(): List<Earnings> {
        val earningsList = mutableListOf<Earnings>()
        firestore.collection(EARNINGS_COLLECTION).get().addOnSuccessListener { documents ->
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
