package com.duoc.appfacosta.data.model

enum class EventKind { ALERTA, CRITICA, EVENTO, ACCESO, RONDA }

data class SecurityEvent(
    val id: Int,
    val kind: EventKind,
    val title: String,
    val subtitle: String,
    val time: String,
    val status: String = ""
)

data class Camera(
    val id: String,
    val name: String,
    val zone: String,
    val online: Boolean,
    val recording: Boolean
)

data class UserSession(val name: String, val email: String) {
    val initials: String get() = name.take(2).uppercase()

    companion object {
        fun fromEmail(email: String) = UserSession(
            name = email.substringBefore("@").replaceFirstChar { it.uppercase() },
            email = email
        )
    }
}
