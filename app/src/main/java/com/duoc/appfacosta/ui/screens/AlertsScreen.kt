package com.duoc.appfacosta.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duoc.appfacosta.data.model.SecurityEvent
import com.duoc.appfacosta.ui.components.DarkCard
import com.duoc.appfacosta.ui.components.Pill
import com.duoc.appfacosta.ui.components.color
import com.duoc.appfacosta.ui.components.label
import com.duoc.appfacosta.ui.theme.*

@Composable
fun AlertsScreen(alerts: SnapshotStateList<SecurityEvent>) {

    fun setStatus(alert: SecurityEvent, status: String) {
        val index = alerts.indexOfFirst { it.id == alert.id }
        if (index >= 0) alerts[index] = alert.copy(status = status)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Alertas", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(
                "Confirma en menos de 2 minutos o se escala a Central",
                color = TextMuted,
                fontSize = 12.sp
            )
            Spacer(Modifier.height(4.dp))
        }
        items(alerts) { alert ->
            val color = alert.kind.color()
            DarkCard {
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    Pill(alert.kind.label(), color)
                    Spacer(Modifier.weight(1f))
                    Text(alert.time, color = TextMuted, fontSize = 12.sp)
                }
                Spacer(Modifier.height(8.dp))
                Text(alert.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(alert.subtitle, color = TextMuted, fontSize = 12.sp)
                Spacer(Modifier.height(10.dp))

                if (alert.status == "Pendiente") {
                    Button(
                        onClick = { setStatus(alert, "Confirmada") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Danger)
                    ) { Text("Confirmar alerta · Enviar a Central") }
                    Spacer(Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = { setStatus(alert, "Falsa alarma") },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("Falsa alarma", color = Warning) }
                } else {
                    Text(
                        "Estado: ${alert.status}",
                        color = if (alert.status == "Confirmada") Danger else TextMuted,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}
