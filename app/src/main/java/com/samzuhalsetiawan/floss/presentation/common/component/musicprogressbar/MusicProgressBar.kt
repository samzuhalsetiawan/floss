package com.samzuhalsetiawan.floss.presentation.common.component.musicprogressbar

import androidx.annotation.FloatRange
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.samzuhalsetiawan.floss.presentation.common.util.formatMillisToTime
import com.samzuhalsetiawan.floss.presentation.theme.FlossTheme
import kotlin.math.roundToInt

@Composable
fun MusicProgressBar(
   modifier: Modifier = Modifier,
   currentDuration: Long = 0L,
   maxDuration: Long = 0L,
   onSeekTo: (Long) -> Unit = {}
) {
   Column(
      modifier = modifier,
      verticalArrangement = Arrangement.spacedBy(2.dp)
   ) {
      ProgressBar(
         modifier = Modifier
            .fillMaxWidth(),
         progress = currentDuration / maxDuration.toFloat(),
         onSeekTo = {
            onSeekTo((maxDuration * it).toLong())
         }
      )
      TimeIndicator(
         modifier = Modifier
            .fillMaxWidth(),
         currentDuration = currentDuration,
         maxDuration = maxDuration
      )
   }
}

@Composable
private fun ProgressBar(
   modifier: Modifier = Modifier,
   @FloatRange(from = 0.0, to = 1.0) progress: Float,
   onSeekTo: (Float) -> Unit
) {
   var containerWidth by remember { mutableIntStateOf(0) }
   var rawThumbOffset by remember { mutableFloatStateOf(0f) }
   LaunchedEffect(containerWidth, progress) {
      rawThumbOffset = containerWidth * progress
   }
   Box(
      modifier = modifier
         .onSizeChanged { containerWidth = it.width }
         .pointerInput(onSeekTo) {
            awaitPointerEventScope {
               while (true) {
                  val event = awaitPointerEvent()
                  when (event.type) {
                     PointerEventType.Press -> {
                        rawThumbOffset = event.changes.first().position.x
                     }
                     PointerEventType.Move -> {
                        rawThumbOffset = event.changes.first().position.x
                     }
                     PointerEventType.Release -> {
                        val pos = rawThumbOffset
                           .coerceIn(0f, containerWidth.toFloat()) / containerWidth
                        onSeekTo(pos)
                     }
                  }
               }
            }
         },
      contentAlignment = Alignment.CenterStart
   ) {
      val thumbOffset by remember {
         derivedStateOf { rawThumbOffset.coerceIn(0f, containerWidth.toFloat()) }
      }

      Track(
         modifier = Modifier
            .fillMaxWidth()
            .height(4.dp),
         progress = thumbOffset / containerWidth
      )
      Thumb(
         modifier = Modifier
            .height(10.dp)
            .aspectRatio(1f)
            .offset { IntOffset((thumbOffset - (10.dp.toPx() / 2)).roundToInt(), 0) }
      )
   }
}

@Composable
private fun Track(
   modifier: Modifier = Modifier,
   @FloatRange(from = 0.0, to = 1.0) progress: Float,
) {
   BoxWithConstraints(
      modifier = modifier
         .background(
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = RoundedCornerShape(50)
         )
         .clip(RoundedCornerShape(50))
   ) {
      Box(
         modifier = Modifier
            .width(maxWidth * progress)
            .height(maxHeight)
            .background(
               color = MaterialTheme.colorScheme.primary,
               shape = RoundedCornerShape(50)
            )
      )
   }
}

@Composable
private fun Thumb(
   modifier: Modifier = Modifier
) {
   Box(
      modifier = modifier
         .background(
            color = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(50)
         )
         .clip(RoundedCornerShape(50))
   )
}

@Composable
private fun TimeIndicator(
   modifier: Modifier = Modifier,
   currentDuration: Long = 0L,
   maxDuration: Long = 0L
) {
   val currentDurationFormatted = remember(currentDuration) {
      formatMillisToTime(currentDuration)
   }
   val maxDurationFormatted = remember(maxDuration) {
      formatMillisToTime(maxDuration)
   }
   Row(
      modifier = modifier,
      horizontalArrangement = Arrangement.SpaceBetween
   ) {
      Text(
         text = currentDurationFormatted,
         style = MaterialTheme.typography.bodySmall
      )
      Text(
         text = maxDurationFormatted,
         style = MaterialTheme.typography.bodySmall
      )
   }
}

@Preview(showBackground = true)
@Composable
private fun MusicProgressBarPreview() {
   FlossTheme {
      MusicProgressBar(
         modifier = Modifier.fillMaxWidth()
            .padding(14.dp)
      )
   }
}