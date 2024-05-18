@file:Suppress("EqualsBetweenInconvertibleTypes")

package com.tuyoleni.smartspend.components.elements.cards

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.tuyoleni.smartspend.data.budget.Budget

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun SwipeToDeleteContainer(
    item: Budget,
    onDelete: (Budget) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (Budget) -> Unit,
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
            Box() {
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(14.dp))
            .padding(16.dp), contentAlignment = Alignment.CenterEnd
    ) {
        Row {
            Text("Delete Budget")
            Spacer(modifier = Modifier.width(12.dp))
            Icon(
                imageVector = Icons.Default.Delete, contentDescription = "Delete"
            )
        }
    }
}
