package com.developers1993.cleanarchitecturenotesapp.featureNotes.presentation.add_edit

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.developers1993.cleanarchitecturenotesapp.R
import com.developers1993.cleanarchitecturenotesapp.featureNotes.presentation.add_edit.component.TransparentEditText
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNotesScreen(
    navController: NavHostController?, viewmodel: AddEditNotesViewmodel = hiltViewModel()
) {

    val snackBarHostState = remember { SnackbarHostState() }
    val titleState = viewmodel.title.value
    val detailsState = viewmodel.details.value

    LaunchedEffect(key1 = true) {
        viewmodel.eventFlow.collectLatest { event ->
            when (event) {
                UiEvents.SaveNotes -> {
                    navController?.navigateUp()

                }

                is UiEvents.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(event.message)
                }
            }
        }
    }
    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackBarHostState) { data ->
            Snackbar(
                modifier = Modifier.border(2.dp, MaterialTheme.colorScheme.secondary),
                snackbarData = data
            )
        }
    }, topBar = {
        TopAppBar(title = {
            TopBarUI(saveClick = { viewmodel.onEvent(AddEditNotesEvent.SaveNotes) })
        })
    }) { padding ->
        Surface(modifier = Modifier.padding(padding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                TransparentEditText(
                    text = titleState.text,
                    hint = titleState.hint,
                    onValueChange = { text ->
                        viewmodel.onEvent(AddEditNotesEvent.OnTitleTextChange(text))
                    },
                    isHintVisible = titleState.isHintVisible,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(16.dp))
                TransparentEditText(
                    modifier = Modifier.fillMaxHeight(),
                    text = detailsState.text,
                    hint = detailsState.hint,
                    onValueChange = {
                        viewmodel.onEvent(AddEditNotesEvent.OnDescriptionTextChange(it))
                    },
                    isHintVisible = detailsState.isHintVisible,
                    textStyle = MaterialTheme.typography.bodyLarge

                )
            }
        }
    }
}

@Composable
fun TopBarUI(saveClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(R.string.add_edit_notes), Modifier.wrapContentWidth())
        Row(
            Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(end = 24.dp), Arrangement.End
        ) {
            Icon(
                painter = rememberVectorPainter(image = Icons.Filled.Check),
                contentDescription = stringResource(R.string.save),
                modifier = Modifier
                    .size(32.dp)
                    .clickable(onClick = saveClick)
            )
        }

    }
}

@Preview
@Composable
private fun AddNotesPreview() {
    AddEditNotesScreen(null)
}