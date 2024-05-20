package com.tuyoleni.smartspend

import android.content.Context
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
fun LoginUser(user: User, navController: NavController, context: Context) {
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

// Firestore Repository
object FireStoreRepository {

    private const val SPENDING_COLLECTION = "spending"
    private const val EARNINGS_COLLECTION = "earnings"
    private const val USERS_COLLECTION = "users"
    private const val BUDGET_COLLECTION = "budgets"

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

    private fun updateBudget(documentId: String, budget: Budget) {
        val budgetMap = budget.toMap()
        firestore.collection(BUDGET_COLLECTION).document(documentId).update(budgetMap)
            .addOnSuccessListener {
                println("Budget updated with ID: $documentId")
            }.addOnFailureListener { e ->
                // Handle the error
            }
    }

    private fun toBudget(data: Map<String, Any>): Budget {
        val threshHold = data["threshHold"] as Long
        val createdString = data["created"] as String
        val created = LocalDate.parse(createdString)
        val category = data["category"] as String
        return Budget(threshHold.toInt(), created, category)
    }

    fun getBudget(callback: (List<Budget>) -> Unit) {
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

    fun deleteBudget(budget: Budget) {
        firestore.collection(BUDGET_COLLECTION).whereEqualTo("category", budget.category).get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    // Budget does not exist, do nothing
                } else {
                    // Budget exists, delete it
                    documents.first().reference.delete()
                }
            }.addOnFailureListener { e ->
                // Handle the error
            }
    }

    fun getUser(uid: String, callback: (User?) -> Unit) {
        val userDocRef = firestore.collection(USERS_COLLECTION).document(uid)
        userDocRef.get().addOnSuccessListener { documentSnapshot ->
            val user = documentSnapshot.toObject(User::class.java)
            callback(user)
        }.addOnFailureListener { e ->
            println("Error getting user: ${e.message}")
            callback(null)
        }
    }

    fun addSpending(spending: Spending) {
        firestore.collection(SPENDING_COLLECTION).add(spending)
            .addOnSuccessListener { documentReference ->
                println("Spending data added with ID: ${documentReference.id}")
            }.addOnFailureListener { e ->
                println("Error adding spending data: ${e.message}")
            }
    }

    fun addEarnings(earnings: Earnings) {
        firestore.collection(EARNINGS_COLLECTION).add(earnings)
            .addOnSuccessListener { documentReference ->
                println("Earnings data added with ID: ${documentReference.id}")
            }.addOnFailureListener { e ->
                println("Error adding earnings data: ${e.message}")
            }
    }

    fun getSpending(callback: (List<Spending>) -> Unit) {
        firestore.collection(SPENDING_COLLECTION).get().addOnSuccessListener { result ->
            val spendingList = result.toObjects(Spending::class.java)
            callback(spendingList)
        }.addOnFailureListener { e ->
            println("Error getting spending data: ${e.message}")
            callback(emptyList())
        }
    }

    fun getEarnings(callback: (List<Earnings>) -> Unit) {
        firestore.collection(EARNINGS_COLLECTION).get().addOnSuccessListener { result ->
            val earningsList = result.toObjects(Earnings::class.java)
            callback(earningsList)
        }.addOnFailureListener { e ->
            println("Error getting earnings data: ${e.message}")
            callback(emptyList())
        }
    }
}
