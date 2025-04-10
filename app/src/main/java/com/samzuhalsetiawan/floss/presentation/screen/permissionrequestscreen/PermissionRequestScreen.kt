package com.samzuhalsetiawan.floss.presentation.screen.permissionrequestscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.samzuhalsetiawan.floss.presentation.common.component.alertdialog.readaudiofilespermissiondeniedpermanently.ReadAudioFilesPermissionDeniedPermanentlyAlertDialog
import com.samzuhalsetiawan.floss.presentation.common.component.alertdialog.readaudiofilespermissionneeded.ReadAudioFilesPermissionNeededAlertDialog
import com.samzuhalsetiawan.floss.presentation.common.component.button.normalbutton.NormalButton
import com.samzuhalsetiawan.floss.presentation.common.component.text.headlinetext.HeadlineText
import com.samzuhalsetiawan.floss.presentation.common.component.text.normaltext.NormalText
import com.samzuhalsetiawan.floss.presentation.screen.permissionrequestscreen.component.illustration.Illustration
import com.samzuhalsetiawan.floss.presentation.theme.FlossTheme

@Composable
fun PermissionRequestScreen(
   viewModel: PermissionRequestScreenViewModel,
   onNavigationEvent: (PermissionRequestScreenNavigationEvent) -> Unit
) {
   val state by viewModel.state.collectAsStateWithLifecycle()
   val lifecycleOwner = LocalLifecycleOwner.current

   LaunchedEffect(lifecycleOwner) {
      lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
         viewModel.navigationEvent.collect {
            onNavigationEvent(it)
         }
      }
   }

   PermissionRequestScreen(
      state = state,
      onEvent = viewModel::onEvent
   )

   for (alertDialog in state.alertDialogs) {
      when (alertDialog) {
         AlertDialog.ReadAudioFilesPermissionNeededAlertDialog -> {
            ReadAudioFilesPermissionNeededAlertDialog(
               onDismissRequest = {
                  viewModel.onEvent(PermissionRequestScreenEvent.HideAlertDialog(alertDialog))
               },
               onGrantPermissionButtonClick = {
                  viewModel.onEvent(PermissionRequestScreenEvent.AskPermissionButtonClick(alertDialog))
               },
               onIgnoreButtonClick = {
                  viewModel.onEvent(PermissionRequestScreenEvent.PermissionIgnoreButtonClick(alertDialog))
               }
            )
         }
         AlertDialog.ReadAudioFilesPermissionDeniedPermanentlyAlertDialog -> {
            ReadAudioFilesPermissionDeniedPermanentlyAlertDialog(
               onDismissRequest = {
                  viewModel.onEvent(PermissionRequestScreenEvent.HideAlertDialog(alertDialog))
               },
               onOpenSettingsButtonClick = {
                  viewModel.onEvent(PermissionRequestScreenEvent.PermissionOpenSettingsButtonClick)
               },
               onIgnoreButtonClick = {
                  viewModel.onEvent(PermissionRequestScreenEvent.PermissionIgnoreButtonClick(alertDialog))
               }
            )
         }
      }
   }
}

@Composable
private fun PermissionRequestScreen(
   state: PermissionRequestScreenState,
   onEvent: (PermissionRequestScreenEvent) -> Unit
) {
   Scaffold {  scaffoldPadding ->
      Box(
         modifier = Modifier.fillMaxSize()
            .padding(scaffoldPadding),
         contentAlignment = Alignment.Center
      ) {
         Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp),
         ) {
            HeadlineText("Onboarding Screen")
            Illustration(
               modifier = Modifier.size(100.dp)
            )
            NormalText(
               modifier = Modifier.fillMaxWidth(),
               text = "lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum",
               textAlign = TextAlign.Center
            )
            NormalButton(
               text = "Next",
               onClick = {
                  onEvent(PermissionRequestScreenEvent.OnNextButtonClick)
               }
            )
         }
      }
   }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingScreenPreview() {
   FlossTheme {
      PermissionRequestScreen(
         state = PermissionRequestScreenState(),
         onEvent = {}
      )
   }
}