@file:Suppress("EqualsBetweenInconvertibleTypes")

package com.example.navigation.components.elements.cards

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun SwipeToDeleteContainer(
    item: String,
    onDelete: (String) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (String) -> Unit,
) {
    var isRemoved by remember { mutableStateOf(false) }
    val state = rememberSwipeToDismissBoxState(confirmValueChange = {
        if (it.equals(SwipeToDismissBoxValue.EndToStart)) {
            isRemoved = true
        }
        it == SwipeToDismissBoxValue.EndToStart
    })

    AnimatedVisibility(
        visible = !isRemoved, exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration), shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismissBox(state = state, enableDismissFromStartToEnd = true, backgroundContent = {
            SwipeDeleteBackground(swipeDismissState = state)
        }, content = {
            Box(modifier = Modifier.padding(start = 24.dp, end = 16.dp)) {
                content(item)
            }
        })
    }



    LaunchedEffect(key1 = isRemoved) {
        if (isRemoved) {
            onDelete(item)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeDeleteBackground(
    swipeDismissState: SwipeToDismissBoxState,
) {
    val color by remember {
        derivedStateOf {
            if (swipeDismissState.dismissDirection.equals(DismissDirection.EndToStart)) {
                Color.Red
            } else {
                Color.Transparent
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color.White
        )
    }
}
