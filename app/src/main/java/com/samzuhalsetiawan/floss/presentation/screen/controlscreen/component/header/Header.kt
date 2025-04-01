package com.samzuhalsetiawan.floss.presentation.screen.controlscreen.component.header

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.samzuhalsetiawan.floss.presentation.common.component.text.normaltext.NormalText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
   modifier: Modifier = Modifier,
) {
   CenterAlignedTopAppBar(
      navigationIcon = {
         IconButton(
            onClick = {}
         ) {
            Icon(
               imageVector = Icons.Default.KeyboardArrowDown,
               contentDescription = null /* TODO: Add content description */
            )
         }
      },
      title = {
         NormalText("Header")
      }
   )
}