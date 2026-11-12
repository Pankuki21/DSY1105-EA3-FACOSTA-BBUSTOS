package com.duoc.appfacosta.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duoc.appfacosta.data.model.EventKind
import com.duoc.appfacosta.data.model.SecurityEvent
import com.duoc.appfacosta.ui.theme.Accent
import com.duoc.appfacosta.ui.theme.BgCard
import com.duoc.appfacosta.ui.theme.Danger
import com.duoc.appfacosta.ui.theme.Stroke
import com.duoc.appfacosta.ui.theme.Success
import com.duoc.appfacosta.ui.theme.TextMuted
import com.duoc.appfacosta.ui.theme.Warning

fun EventKind.color(): Color = when (this) {
    EventKind.ALERTA -> Warning
    EventKind.CRITICA -> Danger
    EventKind.EVENTO, EventKind.ACCESO -> Success
    EventKind.RONDA -> Accent
}

fun EventKind.label(): String = when (this) {
    EventKind.ALERTA -> "ALERTA"
    EventKind.CRITICA -> "CRÍTICA"
    EventKind.EVENTO -> "EVENTO"
    EventKind.ACCESO -> "ACCESO"
    EventKind.RONDA -> "RONDA"
}

@Composable
fun Pill(text: String, color: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(color.copy(alpha = 0.15f))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(text, color = color, fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun SectionTitle(text: String, action: String? = null, onAction: () -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        if (action != null) {
            TextButton(onClick = onAction) { Text(action, color = Accent) }
        }
    }
}

@Composable
fun DarkCard(modifier: Modifier = Modifier, onClick: (() -> Unit)? = null, content: @Composable () -> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .let { if (onClick != null) it.clickable(onClick = onClick) else it },
        colors = CardDefaults.cardColors(containerColor = BgCard),
        border = androidx.compose.foundation.BorderStroke(1.dp, Stroke),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) { content() }
    }
}

@Composable
fun EventRow(event: SecurityEvent) {
    val color = event.kind.color()
    DarkCard {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text("●", color = color)
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Pill(event.kind.label(), color)
                    Spacer(Modifier.weight(1f))
                    Text(event.time, color = TextMuted, fontSize = 12.sp)
                }
                Spacer(Modifier.height(4.dp))
                Text(event.title, fontWeight = FontWeight.SemiBold)
                Text(event.subtitle, color = TextMuted, fontSize = 12.sp)
            }
        }
    }
}
