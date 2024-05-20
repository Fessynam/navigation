package com.tuyoleni.smartspend.data.spending

import android.os.Build
import androidx.annotation.RequiresApi
import com.tuyoleni.smartspend.FireStoreRepository

val firebaseFirestore= FireStoreRepository

@RequiresApi(Build.VERSION_CODES.O)
val spending = firebaseFirestore.getSpending()