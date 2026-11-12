package com.duoc.appfacosta.data

import com.duoc.appfacosta.data.model.Camera
import com.duoc.appfacosta.data.model.EventKind
import com.duoc.appfacosta.data.model.SecurityEvent

object MockData {
    val cameras = listOf(
        Camera("CAM-01", "Entrada Principal", "Zona Sur", online = true, recording = true),
        Camera("CAM-04", "Perímetro Norte", "Zona Sur", online = true, recording = true),
        Camera("CAM-07", "Bodega A", "Zona Sur", online = false, recording = false),
        Camera("CAM-02", "Estacionamiento", "Zona Sur", online = true, recording = true)
    )

    val events = listOf(
        SecurityEvent(1, EventKind.ALERTA, "Movimiento detectado", "Perímetro Norte · Zona 02", "14:32"),
        SecurityEvent(2, EventKind.ACCESO, "Acceso autorizado", "Entrada Principal · Credencial #4892", "14:10"),
        SecurityEvent(3, EventKind.CRITICA, "Cámara desconectada", "Bodega A · Error de enlace", "13:55"),
        SecurityEvent(4, EventKind.RONDA, "Ronda completada", "Guardián · Ruta Sur completa", "13:42")
    )

    val alerts = listOf(
        SecurityEvent(
            10, EventKind.CRITICA, "Movimiento no autorizado",
            "Perímetro Norte · CAM-04 · 92% persona detectada", "14:32", status = "Pendiente"
        ),
        SecurityEvent(
            11, EventKind.ALERTA, "Puerta abierta fuera de horario",
            "Almacén Central · Puerta D-02 · 4 min abierta", "13:48", status = "Pendiente"
        ),
        SecurityEvent(
            12, EventKind.ALERTA, "Batería baja - Sensor",
            "Perímetro Este · SEN-11 · 12% restante", "12:41", status = "Falsa alarma"
        )
    )
}
