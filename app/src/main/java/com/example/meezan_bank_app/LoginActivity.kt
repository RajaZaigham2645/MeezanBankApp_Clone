package com.example.meezan_bank_app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meezan_bank_app.ui.theme.Meezan_bank_appTheme
import org.json.JSONObject

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Meezan_bank_appTheme {
                AppBackground {
                    LoginScreen()
                }
            }
        }
    }
}

@Composable
fun LoginScreen() {
    val context = LocalContext.current
    val sharedPrefs = remember { context.getSharedPreferences("MeezanBankPrefs", Context.MODE_PRIVATE) }
    
    var isLoginMode by remember { mutableStateOf(true) }
    var regStep by remember { mutableIntStateOf(1) }

    // Login fields
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberUsername by remember { mutableStateOf(true) }
    var passwordVisible by remember { mutableStateOf(false) }

    // Registration Step 1 fields
    var regFullName by remember { mutableStateOf("") }
    var regCnic by remember { mutableStateOf("") }
    var regEmail by remember { mutableStateOf("") }
    var regPhone by remember { mutableStateOf("") }

    // Registration Step 2 fields
    var regUsername by remember { mutableStateOf("") }
    var regPassword by remember { mutableStateOf("") }
    var regConfirmPassword by remember { mutableStateOf("") }
    var regPasswordVisible by remember { mutableStateOf(false) }
    var regConfirmPasswordVisible by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 24.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        
        // Logo Section
        Box(
            modifier = Modifier
                .size(90.dp)
                .shadow(12.dp, CircleShape)
                .clip(CircleShape)
                .background(Color.White)
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.meezan_bank_logo),
                contentDescription = "Meezan Bank Logo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }

        // Tabs (Login / Register)
        GlassSurface(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            cornerRadius = 12.dp
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(if (isLoginMode) AccentPurple.copy(alpha = 0.2f) else Color.Transparent)
                        .clickable { 
                            isLoginMode = true 
                            regStep = 1
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Login",
                            color = if (isLoginMode) PrimaryText else SecondaryText,
                            fontSize = 15.sp,
                            fontWeight = if (isLoginMode) FontWeight.Bold else FontWeight.Normal
                        )
                        if (isLoginMode) {
                            Box(modifier = Modifier.width(30.dp).height(2.dp).background(AccentPurple))
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(if (!isLoginMode) AccentPurple.copy(alpha = 0.2f) else Color.Transparent)
                        .clickable { 
                            isLoginMode = false 
                            // Clear registration data when switching to Register tab
                            regFullName = ""
                            regCnic = ""
                            regEmail = ""
                            regPhone = ""
                            regUsername = ""
                            regPassword = ""
                            regConfirmPassword = ""
                            regPasswordVisible = false
                            regConfirmPasswordVisible = false
                            regStep = 1
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Register",
                            color = if (!isLoginMode) PrimaryText else SecondaryText,
                            fontSize = 15.sp,
                            fontWeight = if (!isLoginMode) FontWeight.Bold else FontWeight.Normal
                        )
                        if (!isLoginMode) {
                            Box(modifier = Modifier.width(30.dp).height(2.dp).background(AccentPurple))
                        }
                    }
                }
            }
        }

        if (isLoginMode) {
            // Login Form
            GlassSurface(
                modifier = Modifier.fillMaxWidth(),
                cornerRadius = 20.dp
            ) {
                Column(
                    modifier = Modifier.padding(18.dp)
                ) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(AccentPurple.copy(alpha = 0.3f))
                                .clickable { Toast.makeText(context, "Balance feature coming soon", Toast.LENGTH_SHORT).show() }
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Visibility, contentDescription = null, tint = PrimaryText, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("View Balance", color = PrimaryText, fontSize = 11.sp)
                            }
                        }
                    }

                    Text("Username", color = SecondaryText, fontSize = 13.sp)
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AccentPurple,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.1f),
                            focusedTextColor = PrimaryText,
                            unfocusedTextColor = PrimaryText,
                            cursorColor = AccentPurple
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 2.dp)
                    ) {
                        Checkbox(
                            checked = rememberUsername,
                            onCheckedChange = { rememberUsername = it },
                            colors = CheckboxDefaults.colors(checkedColor = AccentPurple, uncheckedColor = SecondaryText),
                            modifier = Modifier.size(32.dp)
                        )
                        Text(
                            text = buildAnnotatedString {
                                append("Remember my ")
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = PrimaryText)) {
                                    append("Username")
                                }
                            },
                            fontSize = 13.sp,
                            color = SecondaryText
                        )
                    }

                    Text("Password", color = SecondaryText, fontSize = 13.sp)
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            Text(
                                text = if (passwordVisible) "HIDE" else "SHOW",
                                color = AccentPurple,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                modifier = Modifier.clickable { passwordVisible = !passwordVisible }.padding(end = 8.dp)
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AccentPurple,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.1f),
                            focusedTextColor = PrimaryText,
                            unfocusedTextColor = PrimaryText,
                            cursorColor = AccentPurple
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp)
                    )

                    Text(
                        text = "Forgot Password?",
                        color = AccentPurple,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(top = 4.dp).clickable { /* Handle forgot password */ }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .shadow(8.dp, RoundedCornerShape(10.dp))
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(AccentPurpleDeep, AccentPurple, AccentPink)
                                )
                            )
                            .clickable {
                                if (username.isEmpty() || password.isEmpty()) {
                                    Toast.makeText(context, "Please enter credentials", Toast.LENGTH_SHORT).show()
                                    return@clickable
                                }
                                val userData = sharedPrefs.getString("user_$username", null)
                                if (userData != null) {
                                    val json = JSONObject(userData)
                                    if (json.getString("password") == password) {
                                        sharedPrefs.edit().putString("logged_in_username", username).apply()
                                        context.startActivity(Intent(context, MainActivity::class.java))
                                    } else {
                                        Toast.makeText(context, "Invalid Password", Toast.LENGTH_SHORT).show()
                                    }
                                } else {
                                    Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show()
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Log In", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                }
            }
        } else {
            // Register Form
            GlassSurface(
                modifier = Modifier.fillMaxWidth(),
                cornerRadius = 20.dp
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    if (regStep == 1) {
                        Text("Full name as per CNIC", color = SecondaryText, fontSize = 13.sp)
                        OutlinedTextField(
                            value = regFullName,
                            onValueChange = { regFullName = it },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = PrimaryText, unfocusedTextColor = PrimaryText, focusedBorderColor = AccentPurple),
                            singleLine = true, shape = RoundedCornerShape(10.dp)
                        )

                        Text("CNIC (13 digits)", color = SecondaryText, fontSize = 13.sp)
                        OutlinedTextField(
                            value = regCnic,
                            onValueChange = { if (it.length <= 13) regCnic = it },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = PrimaryText, unfocusedTextColor = PrimaryText, focusedBorderColor = AccentPurple),
                            singleLine = true, shape = RoundedCornerShape(10.dp)
                        )

                        Text("Email", color = SecondaryText, fontSize = 13.sp)
                        OutlinedTextField(
                            value = regEmail,
                            onValueChange = { regEmail = it },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = PrimaryText, unfocusedTextColor = PrimaryText, focusedBorderColor = AccentPurple),
                            singleLine = true, shape = RoundedCornerShape(10.dp)
                        )

                        Text("Phone No.", color = SecondaryText, fontSize = 13.sp)
                        OutlinedTextField(
                            value = regPhone,
                            onValueChange = { regPhone = it },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = PrimaryText, unfocusedTextColor = PrimaryText, focusedBorderColor = AccentPurple),
                            singleLine = true, shape = RoundedCornerShape(10.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Box(
                            modifier = Modifier.fillMaxWidth().height(50.dp).clip(RoundedCornerShape(10.dp))
                                .background(Brush.horizontalGradient(listOf(AccentPurpleDeep, AccentPurple)))
                                .clickable {
                                    if (regFullName.isNotEmpty() && regCnic.length == 13 && regEmail.isNotEmpty() && regPhone.isNotEmpty()) {
                                        regStep = 2
                                    } else {
                                        Toast.makeText(context, "Please fill all fields correctly", Toast.LENGTH_SHORT).show()
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Next", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    } else {
                        Text("Username", color = SecondaryText, fontSize = 13.sp)
                        OutlinedTextField(
                            value = regUsername,
                            onValueChange = { regUsername = it },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = PrimaryText, unfocusedTextColor = PrimaryText, focusedBorderColor = AccentPurple),
                            singleLine = true, shape = RoundedCornerShape(10.dp)
                        )

                        Text("Password", color = SecondaryText, fontSize = 13.sp)
                        OutlinedTextField(
                            value = regPassword,
                            onValueChange = { regPassword = it },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            visualTransformation = if (regPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                Text(
                                    text = if (regPasswordVisible) "HIDE" else "SHOW",
                                    color = AccentPurple,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    modifier = Modifier.clickable { regPasswordVisible = !regPasswordVisible }.padding(end = 8.dp)
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = PrimaryText, unfocusedTextColor = PrimaryText, focusedBorderColor = AccentPurple),
                            singleLine = true, shape = RoundedCornerShape(10.dp)
                        )

                        Text("Confirm Password", color = SecondaryText, fontSize = 13.sp)
                        OutlinedTextField(
                            value = regConfirmPassword,
                            onValueChange = { regConfirmPassword = it },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            visualTransformation = if (regConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                Text(
                                    text = if (regConfirmPasswordVisible) "HIDE" else "SHOW",
                                    color = AccentPurple,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    modifier = Modifier.clickable { regConfirmPasswordVisible = !regConfirmPasswordVisible }.padding(end = 8.dp)
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = PrimaryText, unfocusedTextColor = PrimaryText, focusedBorderColor = AccentPurple),
                            singleLine = true, shape = RoundedCornerShape(10.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Box(
                                modifier = Modifier.weight(1f).height(50.dp).clip(RoundedCornerShape(10.dp))
                                    .background(Color.White.copy(alpha = 0.1f))
                                    .clickable { regStep = 1 },
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Back", color = PrimaryText, fontSize = 16.sp)
                            }
                            Box(
                                modifier = Modifier.weight(1f).height(50.dp).clip(RoundedCornerShape(10.dp))
                                    .background(Brush.horizontalGradient(listOf(AccentPurpleDeep, AccentPurple, AccentPink)))
                                    .clickable {
                                        if (regUsername.isNotEmpty() && regPassword.isNotEmpty() && regPassword == regConfirmPassword) {
                                            val randomAccount = (1..4).map { (1..4).map { (0..9).random() }.joinToString("") }.joinToString(" ")
                                            val userObj = JSONObject().apply {
                                                put("fullName", regFullName)
                                                put("cnic", regCnic)
                                                put("email", regEmail)
                                                put("phone", regPhone)
                                                put("username", regUsername)
                                                put("password", regPassword)
                                                put("accountNumber", randomAccount)
                                            }
                                            sharedPrefs.edit().putString("user_$regUsername", userObj.toString()).apply()
                                            Toast.makeText(context, "Registration Successful! Account: $randomAccount", Toast.LENGTH_LONG).show()
                                            
                                            // Reset registration states after success
                                            regFullName = ""
                                            regCnic = ""
                                            regEmail = ""
                                            regPhone = ""
                                            regUsername = ""
                                            regPassword = ""
                                            regConfirmPassword = ""
                                            regPasswordVisible = false
                                            regConfirmPasswordVisible = false
                                            
                                            isLoginMode = true
                                            regStep = 1
                                        } else {
                                            Toast.makeText(context, "Passwords do not match or fields are empty", Toast.LENGTH_SHORT).show()
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Register", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            }
                        }
                    }
                }
            }
        }

        // Biometric Section
        GlassSurface(
            modifier = Modifier.width(170.dp).height(110.dp),
            cornerRadius = 18.dp
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(AccentPurple.copy(alpha = 0.25f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Fingerprint,
                        contentDescription = "Biometric Login",
                        tint = PrimaryText,
                        modifier = Modifier.size(34.dp)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Biometric Login",
                    color = PrimaryText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Version info
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("v 1.1.58 (280)", color = SecondaryText.copy(alpha = 0.6f), fontSize = 11.sp)
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginActivityPreview() {
    Meezan_bank_appTheme {
        AppBackground {
            LoginScreen()
        }
    }
}
