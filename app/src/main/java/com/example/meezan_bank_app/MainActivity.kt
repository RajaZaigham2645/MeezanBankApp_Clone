package com.example.meezan_bank_app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.meezan_bank_app.ui.theme.Meezan_bank_appTheme
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import org.json.JSONObject

// ============================================================
//  COLOR SYSTEM
// ============================================================
val BgTop = Color(0xFF2E1A52)
val BgMid = Color(0xFF190F2A)
val BgBottom = Color(0xFF0A0712)

val AccentPurple = Color(0xFF9B4DFF)
val AccentPurpleDeep = Color(0xFF6C2BD9)
val AccentPink = Color(0xFFC24DFF)
val AccentBlue = Color(0xFF4D8CFF)

val PrimaryText = Color.White
val SecondaryText = Color(0xFFACA3C4)
val TertiaryText = Color(0xFF7A7291)

val PositiveGreen = Color(0xFF3DDC97)
val NegativeRed = Color(0xFFFF6B7A)
val WarningYellow = Color(0xFFFFC857)

val GlassBorder = Color.White.copy(alpha = 0.14f)
val GlassFillTop = Color.White.copy(alpha = 0.12f)
val GlassFillBottom = Color.White.copy(alpha = 0.04f)

// ============================================================
//  ACTIVITY
// ============================================================
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Meezan_bank_appTheme {
                AppBackground {
                    HomeScreen()
                }
            }
        }
    }
}

/**
 * Base gradient background plus soft, radial glow "blobs".
 * Performance Optimized: Replaced expensive 'blur' with efficient radial gradients.
 */
@Composable
fun AppBackground(content: @Composable BoxScope.() -> Unit) {
    val mainGradient = remember { Brush.verticalGradient(listOf(BgTop, BgMid, BgBottom)) }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(mainGradient)
            .drawBehind {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(AccentPurple.copy(alpha = 0.15f), Color.Transparent),
                        center = Offset(-50.dp.toPx(), -40.dp.toPx()),
                        radius = 400.dp.toPx()
                    ),
                    radius = 400.dp.toPx(),
                    center = Offset(-50.dp.toPx(), -40.dp.toPx())
                )
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(AccentPink.copy(alpha = 0.12f), Color.Transparent),
                        center = Offset(size.width + 40.dp.toPx(), size.height * 0.3f),
                        radius = 350.dp.toPx()
                    ),
                    radius = 350.dp.toPx(),
                    center = Offset(size.width + 40.dp.toPx(), size.height * 0.3f)
                )
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(AccentPurpleDeep.copy(alpha = 0.18f), Color.Transparent),
                        center = Offset(20.dp.toPx(), size.height + 20.dp.toPx()),
                        radius = 450.dp.toPx()
                    ),
                    radius = 450.dp.toPx(),
                    center = Offset(20.dp.toPx(), size.height + 20.dp.toPx())
                )
            }
    ) {
        content()
    }
}

// ============================================================
//  REUSABLE GLASS SURFACE
// ============================================================
@Composable
fun GlassSurface(
    modifier: Modifier = Modifier,
    cornerRadius: androidx.compose.ui.unit.Dp = 22.dp,
    borderAlpha: Float = 0.14f,
    glowColor: Color = Color.White.copy(alpha = 0.05f),
    fillColors: List<Color> = listOf(GlassFillTop, GlassFillBottom),
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    val shape = remember(cornerRadius) { RoundedCornerShape(cornerRadius) }
    val backgroundBrush = remember(fillColors) { Brush.verticalGradient(colors = fillColors) }
    
    Box(
        modifier = modifier
            .shadow(
                elevation = 12.dp,
                shape = shape,
                ambientColor = Color.Black.copy(alpha = 0.4f),
                spotColor = Color.Black.copy(alpha = 0.2f)
            )
            .clip(shape)
            .background(backgroundBrush)
            .border(BorderStroke(1.dp, Color.White.copy(alpha = borderAlpha)), shape)
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        content = content
    )
}

// ============================================================
//  HOME SCREEN
// ============================================
@Composable
fun BoxScope.HomeScreen() {
    val context = LocalContext.current
    val sharedPrefs = remember { context.getSharedPreferences("MeezanBankPrefs", Context.MODE_PRIVATE) }
    
    val loggedInUsername = sharedPrefs.getString("logged_in_username", "") ?: ""
    val userDataStr = sharedPrefs.getString("user_$loggedInUsername", null)
    
    var accountName by remember { mutableStateOf("Zaigham") }
    var accountNumber by remember { mutableStateOf("3001 2944 4678 1556") }
    
    LaunchedEffect(userDataStr) {
        if (userDataStr != null) {
            val json = JSONObject(userDataStr)
            accountName = json.optString("fullName", "Zaigham")
            accountNumber = json.optString("accountNumber", "3001 2944 4678 1556")
        }
    }

    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = remember { listOf("Home", "Transactions", "Invest", "Profile") }
    var showShareDialog by remember { mutableStateOf(false) }

    val startScanner = {
        val options = GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
        val scanner = GmsBarcodeScanning.getClient(context, options)
        scanner.startScan()
            .addOnSuccessListener { barcode ->
                val rawValue: String? = barcode.rawValue
                Toast.makeText(context, "Scanned: $rawValue", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Scan failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
            .padding(bottom = 130.dp)
    ) {
        Spacer(Modifier.height(8.dp))
        TopBar()
        Spacer(Modifier.height(14.dp))
        BalanceHeroSection(accountName, accountNumber)
        Spacer(Modifier.height(16.dp))
        AccountActionPills(onShareClick = { showShareDialog = true })
        Spacer(Modifier.height(20.dp))
        MoneyActionButtons()
        Spacer(Modifier.height(28.dp))

        QuickStatsRow()
        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Services",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryText
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                tabs.forEachIndexed { index, tab ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (selectedTab == index) AccentPurple.copy(alpha = 0.25f)
                                else Color.Transparent
                            )
                            .clickable { selectedTab = index }
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = tab,
                            fontSize = 11.sp,
                            fontWeight = if (selectedTab == index) FontWeight.SemiBold else FontWeight.Medium,
                            color = if (selectedTab == index) PrimaryText else TertiaryText
                        )
                    }
                }
            }
        }
        Spacer(Modifier.height(14.dp))
        ServicesGrid()
        Spacer(Modifier.height(28.dp))
        TransactionsSection()
        Spacer(Modifier.height(16.dp))
    }
    
    if (showShareDialog) {
        ShareAccountDialog(accountName, accountNumber, onDismiss = { showShareDialog = false })
    }
    
    BottomNavBar(onQrClick = { startScanner() }, modifier = Modifier)
}

// ============================================================
//  TOP BAR
// ============================================================
@Composable
fun TopBar() {
    val context = LocalContext.current
    val sharedPrefs = remember { context.getSharedPreferences("MeezanBankPrefs", Context.MODE_PRIVATE) }
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .drawBehind {
                // Border line below top bar
                drawLine(
                    color = Color.White.copy(alpha = 0.15f),
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 1.dp.toPx()
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.meezan_bank_logo),
            contentDescription = "Meezan Bank Logo",
            modifier = Modifier.height(45.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(AccentPurple, AccentPink),
                            start = Offset(0f, 0f),
                            end = Offset(1f, 1f)
                        )
                    )
                    .padding(2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(Color(0xFF1A0F2E)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = PrimaryText.copy(alpha = 0.8f),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                GlassIconButton(icon = Icons.Default.Notifications, badgeCount = 3)
                Spacer(Modifier.width(10.dp))
                GlassIconButton(icon = Icons.AutoMirrored.Filled.Logout, onClick = {
                    sharedPrefs.edit().remove("logged_in_username").apply()
                    context.startActivity(Intent(context, LoginActivity::class.java))
                })
            }
        }
    }
}

@Composable
fun GlassIconButton(icon: ImageVector, badgeCount: Int? = null, onClick: () -> Unit = {}) {
    Box {
        GlassSurface(
            modifier = Modifier.size(44.dp),
            cornerRadius = 14.dp,
            onClick = onClick
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = PrimaryText.copy(alpha = 0.9f),
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(22.dp)
            )
        }
        if (badgeCount != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 2.dp, y = (-2).dp)
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(NegativeRed),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = badgeCount.toString(),
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

// ============================================================
//  BALANCE HERO (Compact & Structured)
// ============================================================
@Composable
fun BalanceHeroSection(name: String, accountNum: String) {
    var isVisible by remember { mutableStateOf(true) }

    GlassSurface(
        modifier = Modifier.fillMaxWidth(),
        cornerRadius = 24.dp,
        glowColor = AccentPurple.copy(alpha = 0.12f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Hello,",
                        fontSize = 10.sp,
                        color = SecondaryText,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = name,
                        fontSize = 15.sp,
                        color = PrimaryText,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.08f))
                        .clickable { isVisible = !isVisible }
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = if (isVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = null,
                            tint = SecondaryText,
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            text = if (isVisible) "Hide" else "Show",
                            fontSize = 9.sp,
                            color = SecondaryText,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Rs",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Light,
                    color = AccentPurple,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier.padding(bottom = 4.dp, end = 4.dp)
                )
                Text(
                    text = if (isVisible) "459,195.00" else "••••••••",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryText,
                    fontFamily = FontFamily.Serif,
                    letterSpacing = (-0.5).sp
                )
            }

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White.copy(alpha = 0.05f))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "CURRENT ACCOUNT",
                        fontSize = 8.sp,
                        color = TertiaryText,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = accountNum,
                        fontSize = 12.sp,
                        color = SecondaryText,
                        fontFamily = FontFamily.Monospace
                    )
                }
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(5.dp).clip(CircleShape).background(PositiveGreen))
                    Spacer(Modifier.width(5.dp))
                    Text(
                        text = "MAIN BRANCH",
                        fontSize = 8.sp,
                        color = TertiaryText,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// ============================================================
//  SHARE / STATEMENT PILLS
// ============================================================
@Composable
fun AccountActionPills(onShareClick: () -> Unit) {
    val context = LocalContext.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        GlassPillButton(
            text = "Share Account",
            icon = Icons.Default.Share,
            modifier = Modifier.weight(1f),
            onClick = onShareClick
        )
        GlassPillButton(
            text = "View Statement",
            icon = Icons.Default.Description,
            modifier = Modifier.weight(1f),
            onClick = {
                val intent = Intent(context, ViewStatementActivity::class.java)
                context.startActivity(intent)
            }
        )
        GlassPillButton(
            text = "Manage Cards",
            icon = Icons.Default.CreditCard,
            modifier = Modifier.weight(1f),
            onClick = {
                val intent = Intent(context, DebitcardActivity::class.java)
                context.startActivity(intent)
            }
        )
    }
}

@Composable
fun GlassPillButton(
    text: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    GlassSurface(
        modifier = modifier.height(44.dp),
        cornerRadius = 22.dp,
        borderAlpha = 0.18f,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = PrimaryText.copy(alpha = 0.85f),
                modifier = Modifier.size(14.dp)
            )
            Text(
                text = text,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = PrimaryText.copy(alpha = 0.9f),
                maxLines = 1,
                textAlign = TextAlign.Center
            )
        }
    }
}

// ============================================================
//  SHARE DIALOG
// ============================================================
@Composable
fun ShareAccountDialog(name: String, accountNum: String, onDismiss: () -> Unit) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    Dialog(onDismissRequest = onDismiss) {
        GlassSurface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            cornerRadius = 28.dp,
            glowColor = AccentPurple.copy(alpha = 0.2f),
            fillColors = listOf(BgTop.copy(alpha = 0.98f), BgMid.copy(alpha = 0.95f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Account Details",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryText,
                    fontFamily = FontFamily.Serif
                )
                
                Spacer(Modifier.height(24.dp))
                
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White.copy(alpha = 0.08f))
                        .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Column {
                            Text(
                                text = "ACCOUNT HOLDER",
                                fontSize = 9.sp,
                                color = TertiaryText,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = name,
                                fontSize = 16.sp,
                                color = PrimaryText,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        
                        Divider(color = Color.White.copy(alpha = 0.15f))
                        
                        Column {
                            Text(
                                text = "ACCOUNT NUMBER",
                                fontSize = 9.sp,
                                color = TertiaryText,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = accountNum,
                                fontSize = 18.sp,
                                color = AccentPurple,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
                
                Spacer(Modifier.height(28.dp))
                
                Button(
                    onClick = {
                        clipboardManager.setText(AnnotatedString("Name: $name\nAccount: $accountNum"))
                        Toast.makeText(context, "Details copied to clipboard", Toast.LENGTH_SHORT).show()
                        onDismiss()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AccentPurpleDeep
                    )
                ) {
                    Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Copy Details", fontWeight = FontWeight.Bold)
                }
                
                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Close", color = SecondaryText)
                }
            }
        }
    }
}

// ============================================================
//  SEND / ADD MONEY
// ============================================================
@Composable
fun MoneyActionButtons() {
    val context = LocalContext.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(56.dp)
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(18.dp),
                    ambientColor = AccentPurple.copy(alpha = 0.4f),
                    spotColor = AccentPurple.copy(alpha = 0.3f)
                )
                .clip(RoundedCornerShape(18.dp))
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(AccentPurpleDeep, AccentPurple, AccentPink)
                    )
                )
                .clickable {
                    val intent = Intent(context, SendMoneyActivity::class.java)
                    context.startActivity(intent)
                },
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.Send,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    "Send Money",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }

        GlassSurface(
            modifier = Modifier
                .weight(1f)
                .height(56.dp),
            cornerRadius = 18.dp,
            borderAlpha = 0.18f,
            glowColor = AccentBlue.copy(alpha = 0.1f),
            onClick = {}
        ) {
            Row(
                modifier = Modifier.align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = null,
                    tint = PrimaryText,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    "Add Money",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryText
                )
            }
        }
    }
}

// ============================================================
//  QUICK STATS ROW
// ============================================================
@Composable
fun QuickStatsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        QuickStatCard(
            label = "Monthly Spend",
            value = "Rs 18,500",
            change = "+12%",
            icon = Icons.AutoMirrored.Filled.TrendingUp,
            iconColor = PositiveGreen
        )
        QuickStatCard(
            label = "Rewards Points",
            value = "2,450",
            change = "+48 pts",
            icon = Icons.Default.Star,
            iconColor = WarningYellow
        )
        QuickStatCard(
            label = "Active Loans",
            value = "0",
            change = "All clear",
            icon = Icons.Default.CheckCircle,
            iconColor = PositiveGreen
        )
    }
}

@Composable
fun RowScope.QuickStatCard(
    label: String,
    value: String,
    change: String,
    icon: ImageVector,
    iconColor: Color
) {
    GlassSurface(
        modifier = Modifier
            .weight(1f)
            .height(72.dp),
        cornerRadius = 16.dp,
        borderAlpha = 0.10f,
        onClick = {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = label,
                    fontSize = 9.sp,
                    color = TertiaryText,
                    letterSpacing = 0.5.sp
                )
                Icon(
                    icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(14.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = value,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryText
                )
                Text(
                    text = change,
                    fontSize = 9.sp,
                    color = if (change.startsWith("+")) PositiveGreen else TertiaryText,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

// ============================================================
//  SERVICES GRID
// ============================================================
data class ServiceItem(val name: String, val icon: ImageVector, val badge: String? = null)

@Composable
fun ServicesGrid() {
    val context = LocalContext.current
    val items = remember {
        listOf(
            ServiceItem("Bills & Top-up", Icons.AutoMirrored.Filled.ReceiptLong),
            ServiceItem("Debit Card", Icons.Default.CreditCard),
            ServiceItem("Raast Payments", Icons.Default.Payments, badge = "New"),
            ServiceItem("Zakat & Sadqat", Icons.Default.VolunteerActivism),
            ServiceItem("Feedback", Icons.Default.ChatBubbleOutline),
            ServiceItem("Settings", Icons.Default.Settings)
        )
    }

    GlassSurface(
        modifier = Modifier.fillMaxWidth(),
        cornerRadius = 24.dp,
        borderAlpha = 0.12f
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items.chunked(3).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowItems.forEach { item ->
                        ServiceGridItem(
                            item = item,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                if (item.name == "Debit Card") {
                                    val intent = Intent(context, DebitcardActivity::class.java)
                                    context.startActivity(intent)
                                } else if (item.name == "Bills & Top-up") {
                                    val intent = Intent(context, BillsandTopup::class.java)
                                    context.startActivity(intent)
                                }
                            }
                        )
                    }
                    if (rowItems.size < 3) {
                        repeat(3 - rowItems.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ServiceGridItem(item: ServiceItem, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(vertical = 4.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(Color.White.copy(alpha = 0.08f), CircleShape)
                    .border(1.dp, Color.White.copy(alpha = 0.12f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.name,
                    tint = PrimaryText,
                    modifier = Modifier.size(24.dp)
                )
            }
            if (item.badge != null) {
                Surface(
                    color = PositiveGreen,
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 2.dp, y = (-2).dp)
                        .shadow(4.dp, RoundedCornerShape(6.dp))
                ) {
                    Text(
                        text = item.badge,
                        fontSize = 7.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                    )
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = item.name,
            color = SecondaryText,
            fontSize = 10.5.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            maxLines = 2,
            lineHeight = 13.sp,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 2.dp)
        )
    }
}

// ============================================================
//  TRANSACTIONS
// ============================================================
data class TxItem(
    val title: String,
    val refLabel: String,
    val refId: String,
    val amount: String,
    val isCredit: Boolean,
    val icon: ImageVector
)

@Composable
fun TransactionsSection() {
    val today = remember {
        listOf(
            TxItem("Peso Bill Payment", "UID", "1122334455", "-4,500.00", false, Icons.AutoMirrored.Filled.ReceiptLong),
            TxItem("SUIGAS Bill Payment", "UID", "1122334455", "-4,500.00", false, Icons.Default.LocalFireDepartment),
            TxItem("Money Received from Malik", "TRX ID", "1122334455", "+6,500.00", true, Icons.Default.SwapHoriz)
        )
    }
    val yesterday = remember {
        listOf(
            TxItem("Peso Bill Payment", "UID", "1122334455", "-4,500.00", false, Icons.AutoMirrored.Filled.ReceiptLong),
            TxItem("SUIGAS Bill Payment", "UID", "1122334455", "-4,500.00", false, Icons.Default.LocalFireDepartment)
        )
    }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recent Transactions",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryText
            )
            GlassSurface(
                modifier = Modifier.height(32.dp).padding(horizontal = 2.dp),
                cornerRadius = 16.dp,
                onClick = {}
            ) {
                Row(
                    modifier = Modifier.align(Alignment.Center).padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text("View All", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = AccentPurple)
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = AccentPurple, modifier = Modifier.size(14.dp))
                }
            }
        }

        Spacer(Modifier.height(14.dp))

        GlassSurface(
            modifier = Modifier.fillMaxWidth(),
            cornerRadius = 22.dp
        ) {
            Column(modifier = Modifier.padding(vertical = 6.dp)) {
                TxGroupLabel("Today")
                today.forEachIndexed { index, tx ->
                    TxRow(tx, showDivider = index != today.lastIndex)
                }
                Spacer(Modifier.height(4.dp))
                TxGroupLabel("Yesterday")
                yesterday.forEachIndexed { index, tx ->
                    TxRow(tx, showDivider = index != yesterday.lastIndex)
                }
            }
        }
    }
}

@Composable
fun TxGroupLabel(label: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 16.dp, top = 10.dp, bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(AccentPurple))
        Text(text = label, fontSize = 11.5.sp, fontWeight = FontWeight.SemiBold, color = TertiaryText, letterSpacing = 1.sp)
    }
}

@Composable
fun TxRow(item: TxItem, showDivider: Boolean = true) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(
                    modifier = Modifier.size(42.dp).clip(CircleShape).background(if (item.isCredit) PositiveGreen.copy(alpha = 0.15f) else AccentPurple.copy(alpha = 0.18f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(item.icon, contentDescription = null, tint = if (item.isCredit) PositiveGreen else AccentPurple, modifier = Modifier.size(19.dp))
                }
                Column {
                    Text(text = item.title, color = PrimaryText, fontSize = 13.5.sp, fontWeight = FontWeight.Medium, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(text = item.refLabel, color = TertiaryText, fontSize = 10.sp)
                        Text(text = ":", color = TertiaryText, fontSize = 10.sp)
                        Text(text = item.refId, color = SecondaryText, fontSize = 10.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }
            Text(text = (if (item.isCredit) "+" else "") + item.amount, color = if (item.isCredit) PositiveGreen else NegativeRed, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        }
        if (showDivider) {
            Box(
                modifier = Modifier.fillMaxWidth().padding(start = 70.dp, end = 16.dp).height(1.dp)
                    .background(Brush.horizontalGradient(colors = listOf(Color.White.copy(alpha = 0.02f), Color.White.copy(alpha = 0.08f), Color.White.copy(alpha = 0.02f))))
            )
        }
    }
}

// ============================================================
//  BOTTOM NAV BAR
// ============================================================
data class NavItem(val label: String, val icon: ImageVector)

@Composable
fun BoxScope.BottomNavBar(onQrClick: () -> Unit, modifier: Modifier = Modifier) {
    val leftItems = remember { listOf(NavItem("Products", Icons.Default.CardGiftcard), NavItem("Locate", Icons.Default.Place), NavItem("Discounts", Icons.Default.Percent)) }
    val rightItems = remember { listOf(NavItem("Qibla", Icons.Default.Inventory2), NavItem("Contact", Icons.Default.Call), NavItem("FAQs", Icons.Default.Help)) }

    Box(
        modifier = modifier.align(Alignment.BottomCenter).fillMaxWidth()
            .background(Brush.verticalGradient(colors = listOf(Color.Transparent, BgBottom.copy(alpha = 0.8f), BgBottom)))
            .navigationBarsPadding()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Box(contentAlignment = Alignment.TopCenter) {
                Box(
                    modifier = Modifier.fillMaxWidth().height(80.dp).padding(top = 12.dp)
                        .shadow(12.dp, RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                        // Solid base to prevent background visibility
                        .background(BgBottom, shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                        // Refined Glass Gradient for Bottom Nav Bar
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.15f),
                                    Color.White.copy(alpha = 0.02f)
                                )
                            ),
                            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
                        )
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    AccentPurpleDeep.copy(alpha = 0.4f),
                                    AccentPurple.copy(alpha = 0.2f),
                                    AccentPurpleDeep.copy(alpha = 0.4f)
                                )
                            ),
                            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
                        )
                        .border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                ) {
                    Row(modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                        Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.SpaceEvenly) {
                            leftItems.forEach { NavIcon(it, Color.White) }
                        }
                        Spacer(Modifier.width(80.dp))
                        Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.SpaceEvenly) {
                            rightItems.forEach { NavIcon(it, Color.White) }
                        }
                    }
                }
                Box(
                    modifier = Modifier.size(72.dp).shadow(12.dp, CircleShape).clip(CircleShape).background(AccentPurpleDeep).border(4.dp, Color.White, CircleShape).clickable { onQrClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Default.QrCodeScanner, contentDescription = "QR Scanner", tint = Color.White, modifier = Modifier.size(32.dp))
                }
            }
        }
    }
}

@Composable
fun NavIcon(item: NavItem, tint: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.clip(RoundedCornerShape(12.dp)).clickable { }.padding(vertical = 4.dp, horizontal = 4.dp)) {
        Icon(item.icon, contentDescription = item.label, tint = tint, modifier = Modifier.size(24.dp))
        Spacer(Modifier.height(2.dp))
        Text(item.label, fontSize = 10.sp, color = tint, fontWeight = FontWeight.SemiBold)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    Meezan_bank_appTheme { AppBackground { HomeScreen() } }
}
