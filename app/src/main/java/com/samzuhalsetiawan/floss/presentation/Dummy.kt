package com.samzuhalsetiawan.floss.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

class Dummy {
   init {

   }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Dum() {
   val sliderState = remember { SliderState() }
   Slider(
      value = 0.5f,
      onValueChange = {

      }
   )
}