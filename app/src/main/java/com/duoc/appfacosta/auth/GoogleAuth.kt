package com.duoc.appfacosta.auth

import android.app.Activity
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.duoc.appfacosta.data.model.UserSession
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential

object GoogleAuth {

    suspend fun signIn(activity: Activity, webClientId: String): Result<UserSession> = try {
        val option = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(webClientId)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(option)
            .build()

        val response = CredentialManager.create(activity).getCredential(activity, request)
        val credential = response.credential

        if (credential is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) {
            val google = GoogleIdTokenCredential.createFrom(credential.data)
            Result.success(
                UserSession(
                    name = google.displayName ?: google.id,
                    email = google.id
                )
            )
        } else {
            Result.failure(IllegalStateException("Credencial no soportada"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}
