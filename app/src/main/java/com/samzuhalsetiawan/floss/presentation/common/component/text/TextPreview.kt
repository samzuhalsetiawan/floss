package com.samzuhalsetiawan.floss.presentation.common.component.text

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samzuhalsetiawan.floss.presentation.common.component.text.headlinetext.HeadlineText
import com.samzuhalsetiawan.floss.presentation.common.component.text.normaltext.NormalText
import com.samzuhalsetiawan.floss.presentation.common.component.text.subtext.SubText
import com.samzuhalsetiawan.floss.presentation.theme.FlossTheme

@Preview(showBackground = true)
@Composable
private fun TextPreview() {
   FlossTheme {
      Column(
         verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
         HeadlineText(
            modifier = Modifier.border(1.dp, Color.Black),
            text = "Headline Text"
         )
         NormalText(
            modifier = Modifier.border(1.dp, Color.Black),
            text = "Normal Text"
         )
         SubText(
            modifier = Modifier.border(1.dp, Color.Black),
            text = "Sub Text"
         )
      }
   }
}