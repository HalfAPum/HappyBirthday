package com.narvatov.happybirthday.ui.common

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.narvatov.happybirthday.R
import com.narvatov.happybirthday.utils.createImageFile
import java.util.Objects
import com.narvatov.happybirthday.BuildConfig

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoBottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    onPhotoSelected: (Uri?) -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = { onDismissRequest.invoke() },
        sheetState = sheetState
    ) {
        val context = LocalContext.current
        val file = context.createImageFile()
        val uri = FileProvider.getUriForFile(
            Objects.requireNonNull(context),
            BuildConfig.APPLICATION_ID + ".provider", file
        )

        val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            onPhotoSelected.invoke(uri)
        }

        val permissionLauncherCamera = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            if (it) {
                Toast.makeText(context, context.getString(R.string.permission_camera_granted), Toast.LENGTH_SHORT).show()
                cameraLauncher.launch(uri)
            } else {
                Toast.makeText(context, context.getString(R.string.permission_camera_denied), Toast.LENGTH_SHORT).show()
            }
        }

        val pickMedia =
            rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
                onPhotoSelected.invoke(it)
            }

        val permissionLauncherStorage = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            if (it) {
                Toast.makeText(context, context.getString(R.string.permission_storage_granted), Toast.LENGTH_SHORT).show()
                pickMedia.launch(
                    PickVisualMediaRequest(
                        mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            } else {
                Toast.makeText(context, context.getString(R.string.permission_storage_denied), Toast.LENGTH_SHORT).show()
            }
        }

        Row(modifier = Modifier.fillMaxWidth().height(200.dp)) {
            ModalSheetButton(
                text = stringResource(R.string.camera_picture),
                onClick = {
                    val permissionCheckResult = ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CAMERA,
                    )

                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                        cameraLauncher.launch(uri)
                    } else {
                        permissionLauncherCamera.launch(Manifest.permission.CAMERA)
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(8.dp)
            )

            ModalSheetButton(
                text = stringResource(R.string.gallery_picture),
                onClick = {
                    val permissionCheckResult = ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.READ_MEDIA_IMAGES,
                    )

                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                        pickMedia.launch(
                            PickVisualMediaRequest(
                                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    } else {
                        permissionLauncherStorage.launch(Manifest.permission.READ_MEDIA_IMAGES)
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(8.dp)
            )
        }
    }
}

@Composable
private fun ModalSheetButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = { onClick.invoke() },
        modifier = modifier,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}