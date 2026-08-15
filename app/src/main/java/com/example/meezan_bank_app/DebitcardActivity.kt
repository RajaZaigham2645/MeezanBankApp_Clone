package com.example.meezan_bank_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meezan_bank_app.ui.theme.Meezan_bank_appTheme

class DebitcardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Meezan_bank_appTheme {
                AppBackground {
                    DebitCardScreen()
                }
            }
        }
    }
}

@Composable
fun DebitCardScreen() {
    val context = LocalContext.current
    var isBlocked by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    if (context is ComponentActivity) {
                        context.onBackPressedDispatcher.onBackPressed()
                    }
                },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Text(
                text = "Debit Card",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            IconButton(
                onClick = { /* Handle menu */ },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Optimized Image height to fix excessive spacing
        Image(
            painter = painterResource(id = R.drawable.debitcardnew),
            contentDescription = "Debit Card",
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .padding(horizontal = 24.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Card Details Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            // User Name and Account Info
            Text(
                text = "ZAIGHAM SHAHID RAJA",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF9B4DFF) // AccentPurple
            )
            Text(
                text = "Current Account",
                fontSize = 13.sp,
                color = Color(0xFFACA3C4) // SecondaryText
            )
            Text(
                text = "98290111621305",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(modifier = Modifier.alpha(0.1f), color = Color.White)

            // List Options
            
            // Temporary Block
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Temporary Block",
                    fontSize = 16.sp,
                    color = Color.White
                )
                Switch(
                    checked = isBlocked,
                    onCheckedChange = { isBlocked = it },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = Color.Red,
                        checkedThumbColor = Color.White,
                        uncheckedTrackColor = Color.DarkGray
                    )
                )
            }
            HorizontalDivider(modifier = Modifier.alpha(0.1f), color = Color.White)

            // Card Permissions
            ListOptionItem(text = "Card Permissions")
            HorizontalDivider(modifier = Modifier.alpha(0.1f), color = Color.White)

            // Forget/Reset Card PIN
            ListOptionItem(text = "Forget/Reset Card PIN")
            HorizontalDivider(modifier = Modifier.alpha(0.1f), color = Color.White)
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun ListOptionItem(text: String, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = Color.White
        )
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = Color(0xFF7A7291) // TertiaryText
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DebitCardScreenPreview() {
    Meezan_bank_appTheme {
        AppBackground {
            DebitCardScreen()
        }
    }
}
