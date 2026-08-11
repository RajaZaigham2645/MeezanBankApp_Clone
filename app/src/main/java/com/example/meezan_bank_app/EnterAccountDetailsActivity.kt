package com.example.meezan_bank_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meezan_bank_app.ui.theme.Meezan_bank_appTheme

class EnterAccountDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val bankName = intent.getStringExtra("BANK_NAME") ?: "Bank"
        
        setContent {
            Meezan_bank_appTheme {
                AppBackground {
                    EnterAccountDetailsScreen(bankName)
                }
            }
        }
    }
}

@Composable
fun EnterAccountDetailsScreen(bankName: String) {
    var accountNumber by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 48.dp)
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { 
                    (context as? ComponentActivity)?.onBackPressedDispatcher?.onBackPressed()
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
                text = "Enter Account Details",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Bank Logo and Name
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val logoId = when (bankName) {
                "Meezan Bank" -> R.drawable.meezan_bank_logo
                "Jazz Cash Wallet" -> R.drawable.jazzcashlogo
                "EasyPaisa-Telenor Bank" -> R.drawable.easypaisalogo
                "Nayapay" -> R.drawable.nayapaylogo
                "Sadapay" -> R.drawable.sadapaylogo
                "HBL KONNECT" -> R.drawable.hbllogo
                "Bank Al-Habib" -> R.drawable.bankalhabiblogo
                "UBL" -> R.drawable.ubllogo
                "Bank Alfalah" -> R.drawable.bankalfalahlogo
                "Askari bank" -> R.drawable.askaribanklogo
                "MCB" -> R.drawable.mcblogo
                "BOP-Bank of Punjab" -> R.drawable.bopbanklogo
                "NBP - (National bank of Pakistan)" -> R.drawable.nbpbanklogo
                "Allied bank" -> R.drawable.alliedbanklogo
                else -> null
            }

            if (logoId != null) {
                Image(
                    painter = painterResource(id = logoId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = bankName.take(1),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = bankName,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Input Field
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = "Account Number / IBAN",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
            )
            
            OutlinedTextField(
                value = accountNumber,
                onValueChange = { accountNumber = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(16.dp)
                    ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedIndicatorColor = Color.White.copy(alpha = 0.3f),
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp),
                placeholder = {
                    Text(
                        text = "Enter number",
                        color = Color.White.copy(alpha = 0.4f)
                    )
                },
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Proceed Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .height(56.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(Color(0xFF6C2BD9), Color(0xFF9B4DFF), Color(0xFFC24DFF))
                    )
                )
                .clickable { /* Proceed Logic */ },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Proceed",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun EnterAccountDetailsPreview() {
    Meezan_bank_appTheme {
        AppBackground {
            EnterAccountDetailsScreen("Meezan Bank")
        }
    }
}
