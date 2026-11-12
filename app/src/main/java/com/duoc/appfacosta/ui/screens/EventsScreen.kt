package com.duoc.appfacosta.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duoc.appfacosta.data.model.EventKind
import com.duoc.appfacosta.data.model.SecurityEvent
import com.duoc.appfacosta.ui.components.EventRow
import com.duoc.appfacosta.ui.theme.TextMuted

private val filters = listOf("Todos", "Alertas", "Eventos", "Accesos")

@Composable
fun EventsScreen(events: List<SecurityEvent>) {
    var filter by remember { mutableStateOf("Todos") }

    val filtered = events.filter {
        when (filter) {
            "Alertas" -> it.kind == EventKind.ALERTA || it.kind == EventKind.CRITICA
            "Eventos" -> it.kind == EventKind.EVENTO || it.kind == EventKind.RONDA
            "Accesos" -> it.kind == EventKind.ACCESO
            else -> true
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Historial", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text("Turno actual · 27 Oct · 14:00 - 22:00", color = TextMuted, fontSize = 12.sp)
        Spacer(Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            filters.forEach { f ->
                FilterChip(
                    selected = filter == f,
                    onClick = { filter = f },
                    label = { Text(f) }
                )
            }
        }
        Spacer(Modifier.height(12.dp))

        if (filtered.isEmpty()) {
            Text("No hay eventos en esta categoría", color = TextMuted)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(filtered) { EventRow(it) }
            }
        }
    }
}