package com.samzuhalsetiawan.floss.presentation.common.component.alertbar.missingpermissionbar

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.samzuhalsetiawan.floss.presentation.common.component.button.expanddownbutton.ExpandDownButton
import com.samzuhalsetiawan.floss.presentation.common.component.text.normaltext.NormalText
import com.samzuhalsetiawan.floss.presentation.common.component.text.subtext.SubText

@Composable
fun MissingPermissionBar(
   modifier: Modifier = Modifier,
   expanded: Boolean,
   description: String,
   onExpandButtonClick: () -> Unit,
   onActionButtonClick: () -> Unit,
   onDismissRequest: () -> Unit
) {
   Column(
      modifier = modifier
         .animateContentSize()
   ) {
      Row(
         modifier = Modifier.fillMaxWidth(),
         horizontalArrangement = Arrangement.SpaceBetween,
         verticalAlignment = Alignment.CenterVertically,
      ) {
         NormalText(
            modifier = Modifier.weight(1f),
            text = "Permission not granted"
         )
         ExpandDownButton(onClick = onExpandButtonClick)
      }
      if (expanded) {
         Column(
            horizontalAlignment = Alignment.End
         ) {
            SubText(
               modifier = Modifier.fillMaxWidth(),
               text = description
            )
            Row {
               TextButton(
                  onClick = onActionButtonClick
               ) {
                  Text("Grant Permission")
               }
               TextButton(
                  onClick = onDismissRequest
               ) {
                  Text("Dismiss")
               }
            }
         }
      }
   }
}