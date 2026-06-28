package com.example.bkashclone.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ── Brand colors ──────────────────────────────────────────────────────────────
private val BkashPink   = Color(0xFFE2136E)
private val BkashGray   = Color(0xFF9E9E9E)
private val DividerGray = Color(0xFFE0E0E0)
private val KeypadBg    = Color(0xFFF5F5F5)
private val NextBg      = Color(0xFF9E9E9E)

// ── Reusable: Top bar with back arrow + language toggle ───────────────────────
@Composable
private fun LoginTopBar(
    onBack: () -> Unit,
    isBangla: Boolean,
    onLanguageToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = BkashPink,
            modifier = Modifier
                .size(28.dp)
                .clickable { onBack() }
        )

        // Language toggle pill
        Box(
            modifier = Modifier
                .border(width = 1.dp, color = BkashPink, shape = RoundedCornerShape(50))
                .clickable { onLanguageToggle() }
                .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
            Text(
                text = if (isBangla) "English" else "বাংলা",
                color = BkashPink,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// ── Reusable: bKash bird logo placeholder ─────────────────────────────────────
@Composable
private fun BkashLogo() {
    // Pink stylised bird – represented with a tinted icon block
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(BkashPink.copy(alpha = 0.12f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "✦",          // decorative stand-in; replace with an actual drawable
            color = BkashPink,
            fontSize = 24.sp
        )
    }
}

// ── Reusable: QR code icon ────────────────────────────────────────────────────
@Composable
private fun QrIcon(onClick: () -> Unit) {
    Icon(
        imageVector = Icons.Default.Fingerprint, // swap for a real QR vector drawable
        contentDescription = "QR Login",
        tint = BkashGray,
        modifier = Modifier
            .size(32.dp)
            .clickable { onClick() }
    )
}

// ── Reusable: PIN dot indicator ───────────────────────────────────────────────
@Composable
private fun PinDots(enteredLength: Int, totalDots: Int = 6) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        repeat(totalDots) { index ->
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(
                        if (index < enteredLength) BkashPink else DividerGray
                    )
            )
        }
    }
}

// ── Reusable: single numeric keypad key ───────────────────────────────────────
@Composable
private fun KeypadKey(
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .aspectRatio(1.6f)
            .clickable { onClick() }
    ) {
        Text(
            text = label,
            fontSize = 22.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF212121),
            textAlign = TextAlign.Center
        )
    }
}

// ── Reusable: full custom keypad ──────────────────────────────────────────────
@Composable
private fun NumericKeypad(
    onDigit: (String) -> Unit,
    onDelete: () -> Unit,
    onDone: () -> Unit
) {
    val rows = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf("⌫", "0", "↵")
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(KeypadBg),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        rows.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { key ->
                    KeypadKey(
                        label = key,
                        modifier = Modifier.weight(1f)
                    ) {
                        when (key) {
                            "⌫" -> onDelete()
                            "↵" -> onDone()
                            else -> onDigit(key)
                        }
                    }
                }
            }
        }
    }
}

// ── Reusable: Next button bar ─────────────────────────────────────────────────
@Composable
private fun NextButton(enabled: Boolean, label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (enabled) BkashPink else NextBg)
            .clickable(enabled = enabled) { onClick() }
            .padding(horizontal = 24.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(22.dp)
        )
    }
}

// ── Main screen ───────────────────────────────────────────────────────────────
@Composable
fun LoginScreen(
    accountNumber: String = "+88  015 7125 2327",
    onBack: () -> Unit = {},
    onNext: (pin: String) -> Unit = {},
    onForgotPin: () -> Unit = {},
    onQrLogin: () -> Unit = {},
    onSignup: () -> Unit = {}
) {
    var isBangla by rememberSaveable { mutableStateOf(false) }
    var accountNumber by remember { mutableStateOf(accountNumber) }
    var pin by remember { mutableStateOf("") }
    val maxPin = 6

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // ── Top bar ──────────────────────────────────────────────────────────
        LoginTopBar(
            onBack = onBack,
            isBangla = isBangla,
            onLanguageToggle = { isBangla = isBangla.not() }
        )

        // ── Logo row ─────────────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BkashLogo()
            QrIcon(onClick = onQrLogin)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ── Heading ───────────────────────────────────────────────────────────
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Text(
                text = if (isBangla) "লগ ইন" else "Log In",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF212121)
            )
            Text(
                text = if (isBangla) "আপনার বিকাশ অ্যাকাউন্টে" else "to your bKash account",
                fontSize = 16.sp,
                color = Color(0xFF616161)
            )
        }

        Spacer(modifier = Modifier.height(28.dp))
        HorizontalDivider(color = DividerGray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(20.dp))

        // ── Account number ────────────────────────────────────────────────────
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Text(
                text = if (isBangla) "অ্যাকাউন্ট নম্বর" else "Account Number",
                fontSize = 12.sp,
                color = BkashGray,
                letterSpacing = 0.4.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            BasicTextField(
                value = accountNumber,
                onValueChange = { accountNumber = it },
                textStyle = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121)
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(color = DividerGray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(20.dp))

        // ── PIN field ─────────────────────────────────────────────────────────
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Text(
                text = if (isBangla) "বিকাশ পিন" else "bKash PIN",
                fontSize = 12.sp,
                color = BkashGray,
                letterSpacing = 0.4.sp
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (pin.isEmpty()) {
                    Text(
                        text = if (isBangla) "বিকাশ পিন দিন" else "Enter bKash PIN",
                        fontSize = 15.sp,
                        color = Color(0xFFBDBDBD)
                    )
                } else {
                    PinDots(enteredLength = pin.length)
                }

                Icon(
                    imageVector = Icons.Default.Fingerprint,
                    contentDescription = "Biometric login",
                    tint = BkashPink,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = BkashPink, thickness = 1.dp)
        }

        Spacer(modifier = Modifier.height(10.dp))

        // ── Forgot PIN ────────────────────────────────────────────────────────
        Text(
            text = if (isBangla) "পিন ভুলে গেছেন? পিন রিসেট করুন" else "Forgot PIN? Try PIN Reset",
            color = BkashPink,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .clickable { onForgotPin() }
        )

        Spacer(modifier = Modifier.height(10.dp))

        // ── Sign Up link ──────────────────────────────────────────────────────
        Text(
            text = if (isBangla) "অ্যাকাউন্ট নেই? সাইন আপ করুন" else "Don't have an account? Sign Up",
            color = BkashPink,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .clickable { onSignup() }
        )

        Spacer(modifier = Modifier.weight(1f))

        // ── Next button ───────────────────────────────────────────────────────
        NextButton(
            enabled = pin.isNotEmpty(),
            label = if (isBangla) "পরবর্তী" else "Next",
            onClick = { onNext(pin) }
        )

        // ── Numeric keypad ────────────────────────────────────────────────────
        NumericKeypad(
            onDigit = { digit ->
                if (pin.length < maxPin) pin += digit
            },
            onDelete = {
                if (pin.isNotEmpty()) pin = pin.dropLast(1)
            },
            onDone = {
                if (pin.isNotEmpty()) onNext(pin)
            }
        )
    }
}

// ── Preview ───────────────────────────────────────────────────────────────────
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen()
    }
}