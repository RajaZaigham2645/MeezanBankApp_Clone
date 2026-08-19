package com.example.meezan_bank_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meezan_bank_app.ui.theme.Meezan_bank_appTheme

class BillsandTopup : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Meezan_bank_appTheme {
                AppBackground {
                    BillsScreen()
                }
            }
        }
    }
}

@Composable
fun BillsScreen() {
    val context = LocalContext.current
    val bills = remember {
        listOf(
            BillItem("K-Electric", "1234567890", Icons.Default.LocalFireDepartment),
            BillItem("SSGC", "9876543210", Icons.Default.LocalFireDepartment),
            BillItem("PTCL", "0213456789", Icons.AutoMirrored.Filled.ReceiptLong),
            BillItem("Water Board", "5544332211", Icons.AutoMirrored.Filled.ReceiptLong)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
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
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Text(
                text = "Bills & Top-up",
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "My Bills",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            bills.forEach { bill ->
                BillRow(bill)
                HorizontalDivider(modifier = Modifier.alpha(0.1f), color = Color.White)
            }

            Spacer(modifier = Modifier.height(28.dp))

            // REFINED GLASS GRADIENT BUTTON
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .shadow(
                        elevation = 20.dp,
                        shape = RoundedCornerShape(18.dp),
                        ambientColor = Color(0xFF9B4DFF).copy(alpha = 0.5f),
                        spotColor = Color(0xFFC24DFF).copy(alpha = 0.4f)
                    )
                    .clip(RoundedCornerShape(18.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF6C2BD9).copy(alpha = 0.7f),
                                Color(0xFF9B4DFF).copy(alpha = 0.5f),
                                Color(0xFFC24DFF).copy(alpha = 0.7f)
                            )
                        )
                    )
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.3f),
                                Color.White.copy(alpha = 0.1f)
                            )
                        )
                    )
                    .border(
                        BorderStroke(1.dp, Color.White.copy(alpha = 0.4f)),
                        RoundedCornerShape(18.dp)
                    )
                    .clickable { /* Handle Add New Bill */ },
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Add New Bill",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        letterSpacing = 0.5.sp
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

data class BillItem(val name: String, val id: String, val icon: ImageVector)

@Composable
fun BillRow(bill: BillItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle bill click */ }
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = bill.icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = bill.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                Text(
                    text = "ID: ${bill.id}",
                    fontSize = 14.sp,
                    color = Color(0xFFACA3C4) // SecondaryText
                )
            }
        }
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = Color(0xFF7A7291) // TertiaryText
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BillsScreenPreview() {
    Meezan_bank_appTheme {
        AppBackground {
            BillsScreen()
        }
    }
}
