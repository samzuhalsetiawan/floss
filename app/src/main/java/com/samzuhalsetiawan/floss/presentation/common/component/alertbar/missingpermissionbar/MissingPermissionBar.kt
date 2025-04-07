package com.samzuhalsetiawan.floss.presentation.common.component.alertbar.missingpermissionbar

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.samzuhalsetiawan.floss.presentation.common.component.button.expanddownbutton.ExpandDownButton
import com.samzuhalsetiawan.floss.presentation.common.component.text.normaltext.NormalText
import com.samzuhalsetiawan.floss.presentation.common.component.text.subtext.SubText

@Composable
fun MissingPermissionBar(
   modifier: Modifier = Modifier,
   state: MissingPermissionBarState = rememberMissingPermissionBarState(),
   description: String,
   onActionButtonClick: () -> Unit,
   onDismissRequest: () -> Unit
) {
   Column(modifier = modifier.animateContentSize()) {
      Row(
         modifier = Modifier.fillMaxWidth(),
         horizontalArrangement = Arrangement.SpaceBetween,
         verticalAlignment = Alignment.CenterVertically,
      ) {
         NormalText(modifier = Modifier.weight(1f), text = "Permission not granted")
         ExpandDownButton(onClick = { state.updateExpanded(!state.expanded) })
      }
      if (state.expanded) {
         Column(horizontalAlignment = Alignment.End) {
            SubText(modifier = Modifier.fillMaxWidth(), text = description)
            Row {
               TextButton(onClick = onActionButtonClick) { Text("Grant Permission") }
               TextButton(onClick = onDismissRequest) { Text("Dismiss") }
            }
         }
      }
   }
}

class MissingPermissionBarState {
   var expanded by mutableStateOf(false)
      private set

   fun updateExpanded(expanded: Boolean) {
      this.expanded = expanded
   }
}

@Composable
fun rememberMissingPermissionBarState() = remember { MissingPermissionBarState() }
