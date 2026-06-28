package com.example.bkashclone.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ── Brand colors ──────────────────────────────────────────────────────────────
private val BkashPink      = Color(0xFFE2136E)
private val BkashPinkDark  = Color(0xFFB5005A)
private val BkashGold      = Color(0xFFF5A623)
private val IconBg         = Color(0xFFF2F2F2)
private val NavUnselected  = Color(0xFF757575)
private val PromoBannerBg  = Color(0xFFCC0057)
private val SectionBg      = Color(0xFFF5F5F5)

// ── Data models ───────────────────────────────────────────────────────────────
data class ServiceItem(
    val label: String,
    val icon: ImageVector,
    val tint: Color
)

data class NavItem(
    val label: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector
)

// ── Service grid data ─────────────────────────────────────────────────────────
private val serviceItems = listOf(
    ServiceItem("সেন্ড মানি",        Icons.Default.Send,                  Color(0xFFE2136E)),
    ServiceItem("মোবাইল রিচার্জ",    Icons.Default.PhoneAndroid,          Color(0xFF00897B)),
    ServiceItem("ক্যাশ আউট",         Icons.Default.AttachMoney,           Color(0xFF1E88E5)),
    ServiceItem("পেমেন্ট",           Icons.Default.ShoppingBag,           Color(0xFFFF8F00)),
    ServiceItem("অ্যাড মানি",        Icons.Default.AccountBalanceWallet,  Color(0xFF7B1FA2)),
    ServiceItem("পে বিল",            Icons.Default.Lightbulb,             Color(0xFF37474F)),
    ServiceItem("সেভিংস",            Icons.Default.Savings,               Color(0xFFE2136E)),
    ServiceItem("লোন",               Icons.Default.CreditCard,            Color(0xFF795548)),
    ServiceItem("ইন্যুরেন্স",        Icons.Default.Shield,                Color(0xFF0288D1)),
    ServiceItem("বিকাশ টু ব্যাংক",   Icons.Default.AccountBalance,        Color(0xFFE2136E)),
    ServiceItem("এডুকেশন ফি",        Icons.Default.MenuBook,              Color(0xFF37474F)),
    ServiceItem("মাইক্রোফাইন্যান্স", Icons.Default.Handshake,             Color(0xFF1565C0)),
    ServiceItem("টোল",               Icons.Default.DirectionsCar,         Color(0xFF37474F)),
    ServiceItem("রিকোয়েস্ট মানি",    Icons.Default.PersonAdd,             Color(0xFFE2136E)),
    ServiceItem("রেমিটেন্স",         Icons.Default.Public,                Color(0xFF2E7D32)),
    ServiceItem("ডোনেশন",            Icons.Default.Favorite,              Color(0xFFE2136E)),
)

private val navItems = listOf(
    NavItem("হোম",        Icons.Filled.Home,            Icons.Filled.Home),
    NavItem("আমার বিকাশ", Icons.Outlined.Person,        Icons.Filled.Person),
    NavItem("QR স্ক্যান", Icons.Outlined.QrCodeScanner, Icons.Filled.QrCodeScanner),
    NavItem("ইনবক্স",     Icons.Outlined.Mail,          Icons.Filled.Mail),
)

// ── Reusable: Avatar circle ───────────────────────────────────────────────────
@Composable
private fun AvatarCircle(initials: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(52.dp)
            .clip(CircleShape)
            .background(Color(0xFF4CAF50)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// ── Reusable: Balance pill button ─────────────────────────────────────────────
@Composable
private fun BalancePill(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White)
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(BkashPink),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "৳", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
        }
        Text(
            text = "ব্যালেন্স দেখুন",
            color = Color(0xFF212121),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

// ── Reusable: Hero header ─────────────────────────────────────────────────────
@Composable
private fun HomeHeroSection(
    userName: String,
    onSearch: () -> Unit,
    onMenu: () -> Unit,
    onBalance: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
    ) {
        // ── Layer 1: deep crimson base gradient ───────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFD0005E),
                            Color(0xFFB5005A),
                            Color(0xFF8B003A)
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(0f, 800f)
                    )
                )
        )

        // ── Layer 2: Canvas — diagonal rays, large stadium arc circles ────────
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Warm upper-right radial highlight
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0x40FF6090),
                        Color(0x00FF6090)
                    ),
                    center = Offset(w * 0.85f, h * 0.15f),
                    radius = w * 0.55f
                ),
                radius = w * 0.55f,
                center = Offset(w * 0.85f, h * 0.15f)
            )

            // Large translucent white circle arc — bottom-right stadium rim
            drawCircle(
                color = Color(0x18FFFFFF),
                radius = w * 0.75f,
                center = Offset(w * 1.05f, h * 0.88f)
            )

            // Second arc — slightly smaller, slightly offset, more transparent
            drawCircle(
                color = Color(0x10FFFFFF),
                radius = w * 0.58f,
                center = Offset(w * 0.95f, h * 0.95f)
            )

            // Left-side ellipse arc for depth
            drawOval(
                color = Color(0x12FFFFFF),
                topLeft = Offset(-w * 0.35f, h * 0.45f),
                size = Size(w * 0.6f, h * 0.9f)
            )

            // Diagonal ray lines (spotlight burst from bottom-center)
            val rayColor = Color(0x0AFFFFFF)
            val cx = w * 0.5f
            val cy = h * 1.1f
            val rayCount = 14
            repeat(rayCount) { i ->
                val angleDeg = -160f + i * (140f / (rayCount - 1))
                val angleRad = Math.toRadians(angleDeg.toDouble()).toFloat()
                val endX = cx + kotlin.math.cos(angleRad) * w * 1.2f
                val endY = cy + kotlin.math.sin(angleRad) * h * 1.5f
                drawLine(
                    color = rayColor,
                    start = Offset(cx, cy),
                    end = Offset(endX, endY),
                    strokeWidth = w * 0.06f,
                    cap = StrokeCap.Butt
                )
            }

            // Bottom green pitch strip — the football field visible in screenshots
            val pitchTop = h * 0.74f
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF1B5E20), Color(0xFF2E7D32)),
                    startY = pitchTop,
                    endY = h
                ),
                topLeft = Offset(0f, pitchTop),
                size = Size(w, h - pitchTop)
            )

            // Pitch lighter green horizontal band (center stripe)
            drawRect(
                color = Color(0x22FFFFFF),
                topLeft = Offset(0f, pitchTop),
                size = Size(w, h * 0.04f)
            )

            // Goal posts — two vertical white lines + crossbar at pitch top
            val postInset = w * 0.34f
            val postH     = h * 0.13f
            val postTop   = pitchTop - postH
            val lineW     = 3f
            // Left post
            drawLine(Color(0xCCFFFFFF), Offset(postInset, postTop), Offset(postInset, pitchTop), lineW)
            // Right post
            drawLine(Color(0xCCFFFFFF), Offset(w - postInset, postTop), Offset(w - postInset, pitchTop), lineW)
            // Crossbar
            drawLine(Color(0xCCFFFFFF), Offset(postInset, postTop), Offset(w - postInset, postTop), lineW)

            // Subtle semi-circle at centre of pitch (kick-off arc)
            val arcPath = Path().apply {
                addArc(
                    oval = Rect(
                        center = Offset(w * 0.5f, pitchTop),
                        radius = w * 0.10f
                    ),
                    startAngleDegrees = 180f,
                    sweepAngleDegrees = 180f
                )
            }
            drawPath(arcPath, color = Color(0x55FFFFFF), style = Stroke(width = 2f))
        }

        // ── Layer 3: footballer silhouette icon ───────────────────────────────
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 28.dp)
        ) {
            Icon(
                imageVector = Icons.Default.SportsSoccer,
                contentDescription = null,
                tint = Color(0xFF1B5E20),
                modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.BottomCenter)
            )
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = Color(0xFF1A1A1A),
                modifier = Modifier
                    .size(90.dp)
                    .offset(y = (-6).dp)
            )
        }

        // ── Layer 4: bottom fade so white card below looks attached ───────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0x22000000))
                    )
                )
        )

        // ── Layer 5: UI controls (avatar, name, balance pill, icons) ─────────
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AvatarCircle(initials = "B")
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = userName,
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    BalancePill(onClick = onBalance)
                }
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.25f))
                        .clickable { onSearch() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable { onMenu() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = BkashPink,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }
    }
}

// ── Reusable: single service tile ────────────────────────────────────────────
@Composable
private fun ServiceTile(item: ServiceItem, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(vertical = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(62.dp)
                .clip(CircleShape)
                .background(IconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.label,
                tint = item.tint,
                modifier = Modifier.size(30.dp)
            )
        }
        Spacer(modifier = Modifier.height(7.dp))
        Text(
            text = item.label,
            fontSize = 12.sp,
            color = Color(0xFF212121),
            textAlign = TextAlign.Center,
            lineHeight = 16.sp,
            maxLines = 2
        )
    }
}

// ── Reusable: 4-column service grid (supports expand/collapse) ────────────────
@Composable
private fun ServiceGrid(
    items: List<ServiceItem>,
    onItemClick: (ServiceItem) -> Unit
) {
    val columns = 4
    val rows = (items.size + columns - 1) / columns

    Column {
        repeat(rows) { rowIndex ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(columns) { colIndex ->
                    val index = rowIndex * columns + colIndex
                    if (index < items.size) {
                        Box(modifier = Modifier.weight(1f)) {
                            ServiceTile(
                                item = items[index],
                                onClick = { onItemClick(items[index]) }
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

// ── Reusable: expand / collapse toggle button ─────────────────────────────────
@Composable
private fun GridToggleButton(expanded: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .border(1.dp, BkashPink, RoundedCornerShape(24.dp))
                .clickable { onClick() }
                .padding(horizontal = 24.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = if (expanded) "বন্ধ করুন" else "আরো দেখুন",
                color = BkashPink,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp
                else Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = BkashPink,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

// ── Promo slide data ──────────────────────────────────────────────────────────
private data class PromoSlide(
    val bgStart: Color,
    val bgEnd: Color,
    val content: @Composable BoxScope.() -> Unit
)

private val promoSlides: List<PromoSlide> = listOf(
    // Slide 1 — Pay Later (পে-লেটার করে পেমেন্ট করলেই স্পেশাল অফার)
    PromoSlide(bgStart = Color(0xFF1A1A2E), bgEnd = Color(0xFF2D2D44)) {
        Row(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left: phone mockup placeholder
            Box(
                modifier = Modifier
                    .size(width = 80.dp, height = 110.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFE2136E).copy(alpha = 0.25f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PhoneAndroid,
                    contentDescription = null,
                    tint = Color(0xFFE2136E),
                    modifier = Modifier.size(48.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("পে-লেটার করে", color = Color(0xFFFFD700), fontSize = 17.sp, fontWeight = FontWeight.ExtraBold)
                Text("পেমেন্ট করলেই", color = Color(0xFFFFD700), fontSize = 17.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFFFFD700))
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text("স্পেশাল অফার*", color = Color(0xFFCC0057), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFFFFD700))
                    .padding(horizontal = 10.dp, vertical = 8.dp)
            ) {
                Text("ট্যাপ করুন", color = Color(0xFF212121), fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }
    },
    // Slide 2 — Weekend Cashback (উইকেন্ড মানেই ক্যাশব্যাক উইন)
    PromoSlide(bgStart = Color(0xFF0D47A1), bgEnd = Color(0xFF1565C0)) {
        Row(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("উইকেন্ড", color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                Text("মানেই", color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                Text("ক্যাশব্যাক", color = BkashPink, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                Text("উইন", color = BkashPink, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
            }
            Column(
                modifier = Modifier.weight(2f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Two cashback cards
                listOf("৳৯৯ বিকাশ\nক্যাশব্যাক", "৳১৪৯ বিকাশ\nক্যাশব্যাক").forEach { label ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(6.dp))
                            .background(BkashPink)
                            .padding(horizontal = 8.dp, vertical = 6.dp)
                    ) {
                        Text(label, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(BkashGold)
                    .padding(horizontal = 10.dp, vertical = 8.dp)
            ) {
                Text("ট্যাপ করুন", color = Color.Black, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }
    },
    // Slide 3 — Loan offer (লোন নিলেই স্পেশাল অফার)
    PromoSlide(bgStart = BkashPink, bgEnd = BkashPinkDark) {
        Row(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("লোন", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                Text("নিলেই", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CreditCard,
                    contentDescription = null,
                    tint = BkashPink,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("স্পেশাল", color = BkashGold, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
                Text("অফার*", color = BkashGold, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .padding(horizontal = 10.dp, vertical = 8.dp)
            ) {
                Text("ট্যাপ করুন", color = BkashPink, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }
    },
    // Slide 4 — DPS reward (প্রথম ডিপিএস খললেই ২৬০০ বোনাস পয়েন্ট)
    PromoSlide(bgStart = PromoBannerBg, bgEnd = Color(0xFF8B0040)) {
        Row(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("প্রথম",  color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Text("ডিপিএস", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Text("খললেই", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFF5E6A0))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("২৬০০", color = BkashGold, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold)
                    Text("বোনাস রিওয়ার্ড পয়েন্ট", color = Color(0xFF5D4037), fontSize = 9.sp, fontWeight = FontWeight.Medium, textAlign = TextAlign.Center)
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("৳",   color = BkashPink, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text("১০০", color = BkashPink, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(BkashPink)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text("ট্যাপ করুন", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                }
                Text("পেমেন্ট কুপন*", color = Color.White.copy(alpha = 0.8f), fontSize = 9.sp)
            }
        }
    },
    // Slide 5 — ATM Cashout offer (প্রথমবার এটিএম ক্যাশ আউট-এ ৳১০০ অফার)
    PromoSlide(bgStart = Color(0xFF212121), bgEnd = Color(0xFF424242)) {
        Row(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ATM placeholder
            Box(
                modifier = Modifier
                    .size(width = 80.dp, height = 110.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF303030)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AccountBalance,
                    contentDescription = null,
                    tint = BkashPink,
                    modifier = Modifier.size(40.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("প্রথমবার",    color = Color.White,   fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Text("এটিএম ৳",    color = BkashGold,    fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
                Text("ক্যাশ আউট-এ", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("৳",   color = BkashGold, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Text("১০০", color = BkashGold, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
                    Text(" অফার*", color = Color.White, fontSize = 12.sp)
                }
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(BkashGold)
                    .padding(horizontal = 10.dp, vertical = 8.dp)
            ) {
                Text("ট্যাপ করুন", color = Color.Black, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }
    },
)

// ── Reusable: Promotional banner carousel with live dot indicator ──────────────
@Composable
private fun PromoBannerCarousel(onSlideClick: (Int) -> Unit = {}) {
    val pageCount = promoSlides.size
    val pagerState = rememberPagerState(pageCount = { pageCount })

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
        ) { page ->
            val slide = promoSlides[page]
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 4.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(slide.bgStart, slide.bgEnd),
                            start = Offset(0f, 0f),
                            end = Offset(900f, 400f)
                        )
                    )
                    .clickable { onSlideClick(page) }
            ) {
                slide.content(this)
            }
        }

        // Live dot indicator
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(pageCount) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .size(if (isSelected) 10.dp else 7.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) BkashPink else Color(0xFFCCCCCC))
                )
            }
        }
    }
}

// ── Reusable: Section header with optional "সব দেখুন" link ───────────────────
@Composable
private fun SectionHeader(title: String, showSeeAll: Boolean = true, onSeeAll: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF212121)
        )
        if (showSeeAll) {
            Text(
                text = "সব দেখুন",
                fontSize = 13.sp,
                color = BkashPink,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable { onSeeAll() }
            )
        }
    }
}

// ── Reusable: Quick Features section ─────────────────────────────────────────
@Composable
private fun QuickFeaturesSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(bottom = 16.dp)
    ) {
        SectionHeader(title = "কুইক ফিচারসমূহ", showSeeAll = false)

        // Row 1: outlined shortcut chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val chips = listOf(
                Pair(Icons.Default.Send,  "017121..."),
                Pair(Icons.Default.Send,  "Net Ru..."),
                Pair(Icons.Default.Lock,  "লকড"),
            )
            chips.forEach { (icon, label) ->
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .clickable { }
                        .padding(horizontal = 10.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = BkashPink,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(text = label, fontSize = 12.sp, color = Color(0xFF212121))
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Row 2: colored feature cards
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            data class FeatureCard(val icon: ImageVector, val label: String, val bg: Color, val iconTint: Color)
            val cards = listOf(
                FeatureCard(Icons.Default.CardGiftcard, "মাই অফারস", Color(0xFFFFF8EE), Color(0xFFFF8F00)),
                FeatureCard(Icons.Default.Discount,     "কুপন",      Color(0xFFEAF8F8), Color(0xFF00897B)),
                FeatureCard(Icons.Default.EmojiEvents,  "রিওয়ার্ডস", Color(0xFFFFF8EE), Color(0xFFF5A623)),
            )
            cards.forEach { card ->
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(card.bg)
                        .clickable { }
                        .padding(horizontal = 10.dp, vertical = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = card.icon,
                        contentDescription = card.label,
                        tint = card.iconTint,
                        modifier = Modifier.size(28.dp)
                    )
                    Text(
                        text = card.label,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF212121)
                    )
                }
            }
        }
    }
}

// ── Reusable: Gaming / Entertainment card ─────────────────────────────────────
@Composable
private fun GamingCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF1A1A2E))
    ) {
        // Dark banner placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF6A0572), Color(0xFF1A1A2E)),
                        start = Offset(0f, 0f),
                        end = Offset(800f, 400f)
                    )
                ),
            contentAlignment = Alignment.BottomStart
        ) {
            // Overlay icons as visual decoration
            Icon(
                imageVector = Icons.Default.SportsEsports,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.12f),
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterEnd)
                    .padding(end = 12.dp)
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "ডাউনলোড ছাড়াই গেমিং",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 13.sp
                )
                Text(
                    text = "বিকাশ অ্যাপে সম্ভব",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Sub-links row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf(
                Pair(Icons.Default.SportsEsports, "গেম জোন"),
                Pair(Icons.Default.School,        "লার্নিং সেন্টার"),
            ).forEach { (icon, label) ->
                Row(
                    modifier = Modifier.clickable { },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFEDE7F6)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = label,
                            tint = Color(0xFF7B1FA2),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Text(text = label, fontSize = 13.sp, color = Color(0xFF212121), fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

// ── Reusable: Bundle section (horizontal scroll) ──────────────────────────────
@Composable
private fun BundleSection(onSeeAll: () -> Unit = {}) {
    Column(modifier = Modifier.fillMaxWidth()) {
        SectionHeader(title = "বিকাশ বান্ডেল", onSeeAll = onSeeAll)
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            data class BundleCard(val title: String, val duration: String, val price: String, val save: String)
            val bundles = listOf(
                BundleCard("10 সেন্ড মানি", "30 দিন", "৳41", "বাঁচবে ৳9"),
                BundleCard("100 সেন্ড মানি", "30 দিন", "৳99", "বাঁচবে ৳41"),
                BundleCard("50 সেন্ড মানি", "60 দিন", "৳69", "বাঁচবে ৳31"),
            )
            bundles.forEach { bundle ->
                Column(
                    modifier = Modifier
                        .width(180.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .clickable { }
                        .padding(14.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE8F5E9)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = null,
                                tint = BkashPink,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Column {
                            Text(text = bundle.title, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF212121))
                            Text(text = bundle.duration, fontSize = 11.sp, color = Color(0xFF757575))
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Text(text = bundle.price, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = BkashPink)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(BkashPink)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(text = bundle.save, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(imageVector = Icons.Default.Send, contentDescription = null, tint = BkashPink, modifier = Modifier.size(12.dp))
                        Text(text = bundle.title, fontSize = 11.sp, color = Color(0xFF757575))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

// ── Reusable: Suggestions section (horizontal scroll) ────────────────────────
@Composable
private fun SuggestionsSection(onSeeAll: () -> Unit = {}) {
    Column(modifier = Modifier.fillMaxWidth()) {
        SectionHeader(title = "সাজেশন", onSeeAll = onSeeAll)
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            data class SuggestionItem(val label: String, val icon: ImageVector, val bgColor: Color, val iconTint: Color)
            val suggestions = listOf(
                SuggestionItem("গেমস্টার",        Icons.Default.SportsEsports, Color(0xFFEDE7F6), Color(0xFF7B1FA2)),
                SuggestionItem("বিশ্বকাপ কুইজ",   Icons.Default.Quiz,          Color(0xFFFFEBEE), Color(0xFFE2136E)),
                SuggestionItem("শেয়ারট্রিপ",      Icons.Default.Flight,        Color(0xFFE3F2FD), Color(0xFF1565C0)),
                SuggestionItem("গোয়ায়ান",         Icons.Default.DirectionsBus, Color(0xFFE8F5E9), Color(0xFF2E7D32)),
                SuggestionItem("Football Sigma", Icons.Default.SportsSoccer,  Color(0xFFFFEBEE), Color(0xFFE2136E)),
            )
            suggestions.forEach { item ->
                Column(
                    modifier = Modifier
                        .width(72.dp)
                        .clickable { },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(item.bgColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = item.iconTint,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Text(
                        text = item.label,
                        fontSize = 11.sp,
                        color = Color(0xFF212121),
                        textAlign = TextAlign.Center,
                        lineHeight = 14.sp,
                        maxLines = 2
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

// ── Reusable: Offers section (horizontal scroll) ──────────────────────────────
@Composable
private fun OffersSection(onSeeAll: () -> Unit = {}) {
    Column(modifier = Modifier.fillMaxWidth()) {
        SectionHeader(title = "অফার", onSeeAll = onSeeAll)
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            data class OfferCard(val title: String, val subtitle: String, val bgColor: Color, val icon: ImageVector, val iconTint: Color)
            val offers = listOf(
                OfferCard("আইফোন ১৭প্রো ম্যাক্স",    "কাবিক",  Color(0xFFFCE4EC), Icons.Default.PhoneAndroid, Color(0xFFE2136E)),
                OfferCard("৳১,০০০ ক্যাশব্যাক",       "এরনা",   Color(0xFFFFF8E1), Icons.Default.Devices,      Color(0xFFF5A623)),
                OfferCard("স্মার্ট অফার",              "বিকাশ",  Color(0xFFE8F5E9), Icons.Default.LocalOffer,   Color(0xFF2E7D32)),
            )
            offers.forEach { offer ->
                Column(
                    modifier = Modifier
                        .width(160.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(offer.bgColor)
                        .clickable { }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(offer.bgColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = offer.icon,
                            contentDescription = null,
                            tint = offer.iconTint.copy(alpha = 0.35f),
                            modifier = Modifier.size(64.dp)
                        )
                    }
                    Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)) {
                        Text(text = offer.title,    fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF212121), maxLines = 2, lineHeight = 16.sp)
                        Text(text = offer.subtitle, fontSize = 11.sp, color = Color(0xFF757575))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

// ── Reusable: Bottom navigation bar ──────────────────────────────────────────
@Composable
private fun BkashBottomNav(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp,
        modifier = Modifier.height(64.dp)
    ) {
        navItems.forEachIndexed { index, item ->
            val selected = index == selectedIndex
            NavigationBarItem(
                selected = selected,
                onClick = { onItemSelected(index) },
                icon = {
                    Icon(
                        imageVector = if (selected) item.selectedIcon else item.icon,
                        contentDescription = item.label,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 11.sp,
                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = BkashPink,
                    selectedTextColor = BkashPink,
                    unselectedIconColor = NavUnselected,
                    unselectedTextColor = NavUnselected,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

// ── Main HomeScreen ───────────────────────────────────────────────────────────
@Composable
fun HomeScreen(
    userName: String = "Bishawjit Malakar",
    onServiceClick: (ServiceItem) -> Unit = {},
    onSearch: () -> Unit = {},
    onMenu: () -> Unit = {},
    onBalance: () -> Unit = {},
    onPromoClick: () -> Unit = {}
) {
    var selectedNavIndex by remember { mutableIntStateOf(0) }
    var gridExpanded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val visibleServices = if (gridExpanded) serviceItems else serviceItems.take(8)

    Scaffold(
        bottomBar = {
            BkashBottomNav(
                selectedIndex = selectedNavIndex,
                onItemSelected = { selectedNavIndex = it }
            )
        },
        containerColor = SectionBg
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            // ── Hero ──────────────────────────────────────────────────────────
            HomeHeroSection(
                userName = userName,
                onSearch = onSearch,
                onMenu = onMenu,
                onBalance = onBalance
            )

            // ── Service grid card ─────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(Color.White)
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                ServiceGrid(
                    items = visibleServices,
                    onItemClick = onServiceClick
                )
                Spacer(modifier = Modifier.height(4.dp))
                GridToggleButton(
                    expanded = gridExpanded,
                    onClick = { gridExpanded = gridExpanded.not() }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ── Promo banner carousel + live dots ────────────────────────────
            PromoBannerCarousel(onSlideClick = { _ -> onPromoClick() })

            Spacer(modifier = Modifier.height(20.dp))

            // ── Quick Features ────────────────────────────────────────────────
            QuickFeaturesSection()

            Spacer(modifier = Modifier.height(12.dp))

            // ── Gaming card ───────────────────────────────────────────────────
            GamingCard()

            Spacer(modifier = Modifier.height(20.dp))

            // ── Bundle section ────────────────────────────────────────────────
            Column(modifier = Modifier.fillMaxWidth().background(Color.White)) {
                BundleSection()
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ── Suggestions section ───────────────────────────────────────────
            Column(modifier = Modifier.fillMaxWidth().background(Color.White)) {
                SuggestionsSection()
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ── Offers section ────────────────────────────────────────────────
            Column(modifier = Modifier.fillMaxWidth().background(Color.White)) {
                OffersSection()
            }
        }
    }
}

// ── Preview ───────────────────────────────────────────────────────────────────
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen()
    }
}