package com.justparokq.homeftp.features.login.composables

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.justparokq.homeftp.theme.backgroundDark

interface TextInputContract {
    val value: String
    val onValueChanged: (newValue: String) -> Unit
    val label: String
    val isEnabled: Boolean
    val isError: Boolean
    val errorText: String?
}

class PresetTextInputContract(
    override val value: String,
    override val label: String,
    override val onValueChanged: (String) -> Unit,
    override val isEnabled: Boolean = true,
    override val isError: Boolean = false,
    override val errorText: String? = null,

    focusRequester: FocusRequester? = null,
    imeAction: ImeAction = ImeAction.Done,
    keyboardType: KeyboardType = KeyboardType.Text,
    testTag: String = "",
    onKeyboardActions: () -> Unit,
) : TextInputContract

@Composable
internal fun LoginInputField(
    value: String,
    label: String,
    onValueChanged: (String) -> Unit,
    isEnabled: Boolean = true,
    isError: Boolean = false,
    errorText: String? = null,

    focusRequester: FocusRequester? = null,
    imeAction: ImeAction = ImeAction.Done,
    keyboardType: KeyboardType = KeyboardType.Text,
    testTag: String = "",
    onKeyboardActions: () -> Unit,
) {
    val isPasswordField = remember {
        keyboardType == KeyboardType.Password
    }
    var passwordVisible by rememberSaveable { mutableStateOf(!isPasswordField) }
    val largeLabel = MaterialTheme.typography.labelLarge
    val mediumLabel = MaterialTheme.typography.labelSmall
    var labelFont by remember {
        mutableStateOf(largeLabel)
    }
    OutlinedTextField(
        enabled = isEnabled,
        value = value,
        onValueChange = {
            onValueChanged(it)
        },
        isError = isError,
        textStyle = MaterialTheme.typography.bodyLarge,
        label = {
            Text(
                text = label,
                style = labelFont
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = keyboardType
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onKeyboardActions()
            },
            onNext = {
                onKeyboardActions()
            }
        ),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(),
        visualTransformation = if (passwordVisible) VisualTransformation.None
        else PasswordVisualTransformation(),
        trailingIcon = {
            if (!isPasswordField) return@OutlinedTextField

            val icon = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            val description = if (passwordVisible)
                "Hide password"
            else "Show password"

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    imageVector = icon,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = description
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester ?: FocusRequester())
            .onFocusChanged { focusState ->
                labelFont = if (value.isBlank() && !focusState.isFocused) {
                    largeLabel
                } else {
                    mediumLabel
                }
            }
            .testTag(testTag)
    )
    errorText?.let { text ->
        Text(
            text = text,
            color = MaterialTheme.colorScheme.error,
        )
    }
}
