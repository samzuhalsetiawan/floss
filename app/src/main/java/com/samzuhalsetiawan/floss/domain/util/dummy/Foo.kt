package com.samzuhalsetiawan.floss.domain.util.dummy

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun Foo(
   onDrag: (dragAmount: Float) -> Unit
) {
   Box(
      modifier = Modifier.size(150.dp)
         .border(1.dp, Color.Black)
         .pointerInput(Unit) {
            detectHorizontalDragGestures { _, dragAmount ->
               onDrag(dragAmount)
            }
         }
   )
}