package com.sina.loginpage.interfaces

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sina.loginpage.MainActivity
import com.sina.loginpage.ui.theme.LoginPageTheme

@Composable
fun LoginForm() {


    Surface {
        var credentials by remember { mutableStateOf(Credentials()) }
        val context = LocalContext.current
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            LoginField(
                value = credentials.userName,
                onChange = { data -> credentials = credentials.copy(userName = data) },
                modifier = Modifier.fillMaxWidth()

            )

            PasswordField(
                value = credentials.password,
                onChange = { data -> credentials = credentials.copy(password = data) },
                submit = { checkCredentials(credentials, context) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            LabeledCheckBox(
                label = "Remember Me",
                onCheckChanged = {
                    credentials = credentials.copy(remember = !credentials.remember)
                },
                isChecked = credentials.remember
            )
            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { checkCredentials(credentials, context) },
                enabled = credentials.isNotEmpty(),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.fillMaxWidth()

            ) {
                Text("Login")
            }
        }
    }
}

fun checkCredentials(credentials: Credentials, context: Context) {
    if (credentials.isNotEmpty() && credentials.userName == "sina") {
        context.startActivity(Intent(context, MainActivity::class.java))
        (context as Activity).finish()
    } else {
        Toast.makeText(context, "فقط با نام کاربری sina وارد شوید", Toast.LENGTH_LONG).show()
    }
}

data class Credentials(
    val userName: String = "",
    val password: String = "",
    val remember: Boolean = false
) {
    fun isNotEmpty(): Boolean {
        return userName.isNotEmpty() && password.isNotEmpty()
    }
}

@Composable
fun LabeledCheckBox(
    label: String,
    onCheckChanged: () -> Unit,
    isChecked: Boolean
) {
    Row(
        Modifier
            .clickable(onClick = onCheckChanged)
            .padding(4.dp)
    ) {
        Checkbox(checked = isChecked, onCheckedChange = null)
        Spacer(modifier = Modifier.size(60.dp))
        Text(label)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Login",
    placeHolder: String = "Enter your login"
) {
    val focusManager = LocalFocusManager.current
    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Person,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
    }

    TextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
        placeholder = { Text(placeHolder) },
        label = { Text(label) },
        singleLine = true,
        visualTransformation = VisualTransformation.None
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(
    value: String,
    onChange: (String) -> Unit,
    submit: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Password",
    placeHolder: String = "Enter your Password"
) {
    val focusManager = LocalFocusManager.current
    var isPasswordVisible by remember { mutableStateOf(true) }

    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Key,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
    }

    val trailingIcon = @Composable {
        IconButton(onClick = {
            isPasswordVisible = !isPasswordVisible
        }) {
            Icon(
                if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )

        }
    }
    TextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(
            onDone = { submit() }),
        placeholder = { Text(placeHolder) },
        label = { Text(label) },
        singleLine = true,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginFormPreview() {
    LoginPageTheme {
        LoginForm()
    }
}