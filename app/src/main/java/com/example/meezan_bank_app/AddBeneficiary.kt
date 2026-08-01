package com.example.meezan_bank_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meezan_bank_app.ui.theme.Meezan_bank_appTheme

class AddBeneficiary : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Meezan_bank_appTheme {
                AppBackground {
                    AddBeneficiaryScreen()
                }
            }
        }
    }
}

data class Bank(
    val name: String,
    val icon: String
)

@Composable
fun AddBeneficiaryScreen() {
    var searchText by remember { mutableStateOf("") }
    var selectedBank by remember { mutableStateOf("Meezan Bank") }

    val banks = listOf(
        Bank("Meezan Bank", "M"),
        Bank("Other Banks", "O"),
        Bank("Jazz Cash Wallet", "J"),
        Bank("EasyPaisa-Telenor Bank", "E"),
        Bank("HBL KONNECT", "H"),
        Bank("Bank Al-Habib", "B"),
        Bank("UBL", "U"),
        Bank("Bank Alfalah", "B")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp)
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
                onClick = { /* Handle back navigation */ },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Text(
                text = "Select Bank",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            // Empty spacer for alignment
            Spacer(modifier = Modifier.size(40.dp))
        }

        // Search Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = {
                    Text(
                        text = "Search",
                        color = Color.White.copy(alpha = 0.7f)
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.White.copy(alpha = 0.7f)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(24.dp)
                    ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.White
                ),
                shape = RoundedCornerShape(24.dp),
                singleLine = true
            )
        }

        // Bank List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(banks) { bank ->
                BankItem(
                    bank = bank,
                    isSelected = bank.name == selectedBank,
                    onBankClick = { selectedBank = bank.name }
                )
            }
        }
    }
}

@Composable
fun BankItem(
    bank: Bank,
    isSelected: Boolean,
    onBankClick: () -> Unit
) {
    val gradientModifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .clickable { onBankClick() }

    when (bank.name) {
        "Meezan Bank" -> {
            Box(modifier = gradientModifier.background(Brush.horizontalGradient(listOf(Color(0xFF6C2BD9), Color(0xFF9B4DFF), Color(0xFFC24DFF))))) {
                BankItemContent(bank)
            }
        }
        "Jazz Cash Wallet" -> {
            Box(modifier = gradientModifier.background(Brush.horizontalGradient(listOf(Color(0xFFFF6B00), Color(0xFFFF9100), Color(0xFFFFB700))))) {
                BankItemContent(bank)
            }
        }
        "EasyPaisa-Telenor Bank" -> {
            Box(modifier = gradientModifier.background(Brush.horizontalGradient(listOf(Color(0xFF00C853), Color(0xFF1DE9B6), Color(0xFF00E676))))) {
                BankItemContent(bank)
            }
        }
        "HBL KONNECT" -> {
            Box(modifier = gradientModifier.background(Brush.horizontalGradient(listOf(Color(0xFF006D64), Color(0xFF00A191), Color(0xFF00C4B4))))) {
                BankItemContent(bank)
            }
        }
        "Bank Al-Habib" -> {
            Box(modifier = gradientModifier.background(Brush.horizontalGradient(listOf(Color(0xFF800000), Color(0xFFA52A2A), Color(0xFFDAA520))))) {
                BankItemContent(bank)
            }
        }
        "UBL" -> {
            Box(modifier = gradientModifier.background(Brush.horizontalGradient(listOf(Color(0xFF003366), Color(0xFF0056B3), Color(0xFF007BFF))))) {
                BankItemContent(bank)
            }
        }
        "Bank Alfalah" -> {
            Box(modifier = gradientModifier.background(Brush.horizontalGradient(listOf(Color(0xFFB22222), Color(0xFFDC143C), Color(0xFFFF0000))))) {
                BankItemContent(bank)
            }
        }
        "Other Banks" -> {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f)),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .padding(12.dp)
                        .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(text = bank.name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }
        }
        else -> {
            Card(
                modifier = Modifier.fillMaxWidth().clickable { onBankClick() },
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f)),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                BankItemContent(bank)
            }
        }
    }
}

@Composable
fun BankItemContent(bank: Bank) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val logoId = when (bank.name) {
            "Meezan Bank" -> R.drawable.meezan_bank_logo
            "Jazz Cash Wallet" -> R.drawable.jazzcashlogo
            "EasyPaisa-Telenor Bank" -> R.drawable.easypaisalogo
            "HBL KONNECT" -> R.drawable.hbllogo
            "Bank Al-Habib" -> R.drawable.bankalhabiblogo
            "UBL" -> R.drawable.ubllogo
            "Bank Alfalah" -> R.drawable.bankalfalahlogo
            else -> null
        }

        if (logoId != null) {
            Image(
                painter = painterResource(id = logoId),
                contentDescription = null,
                modifier = Modifier.size(44.dp)
            )
        } else {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = bank.icon, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }

        Spacer(modifier = Modifier.width(14.dp))

        Text(
            text = bank.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddBeneficiaryPreview() {
    Meezan_bank_appTheme {
        AppBackground {
            AddBeneficiaryScreen()
        }
    }
}
