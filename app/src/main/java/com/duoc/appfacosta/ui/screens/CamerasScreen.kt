package com.duoc.appfacosta.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duoc.appfacosta.data.model.Camera
import com.duoc.appfacosta.ui.components.DarkCard
import com.duoc.appfacosta.ui.components.Pill
import com.duoc.appfacosta.ui.theme.*

@Composable
fun CamerasScreen(cameras: List<Camera>) {
    var selected by remember { mutableStateOf(cameras.first()) }
    var recording by remember(selected) { mutableStateOf(selected.recording) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text("Cámaras", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        // Vista en vivo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text(
                if (selected.online) "EN VIVO · ${selected.id}" else "SIN SEÑAL",
                color = if (selected.online) Success else TextMuted,
                fontSize = 13.sp
            )
            Box(Modifier.align(Alignment.TopStart).padding(10.dp)) {
                Text(selected.name, color = TextMain, fontWeight = FontWeight.SemiBold)
            }
            if (selected.online && recording) {
                Box(Modifier.align(Alignment.TopEnd).padding(10.dp)) { Pill("● REC", Danger) }
            }
        }

        // Controles
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            ControlButton("Foto") { }
            ControlButton(if (recording) "Parar" else "Grabar") { recording = !recording }
            ControlButton("Luz") { }
            ControlButton("Full") { }
        }

        // Estado del dispositivo
        DarkCard {
            Text("Estado del dispositivo", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                InfoCell("Estado", if (selected.online) "En línea" else "Desconectada")
                InfoCell("Señal", if (selected.online) "Excelente" else "—")
                InfoCell("Grabación", if (recording) "Nube + Local" else "Pausada")
            }
        }

        Text("Todas las cámaras", fontWeight = FontWeight.SemiBold)
        cameras.forEach { cam ->
            DarkCard(onClick = { selected = cam }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(cam.name, fontWeight = FontWeight.SemiBold)
                        Text("${cam.id} · ${cam.zone}", color = TextMuted, fontSize = 12.sp)
                    }
                    Pill(if (cam.online) "En línea" else "Offline", if (cam.online) Success else TextMuted)
                }
            }
        }
    }
}

@Composable
private fun ControlButton(label: String, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Stroke),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = TextMain)
    ) { Text(label, fontSize = 13.sp) }
}

@Composable
private fun InfoCell(label: String, value: String) {
    Column {
        Text(label, color = TextMuted, fontSize = 11.sp)
        Text(value, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
    }
}
