package com.example.levelup.data.repository

import com.example.levelup.data.model.Profile
import kotlinx.coroutines.delay

interface IProfileRepository {
    suspend fun getProfile(email: String): Profile
    suspend fun updateProfile(profile: Profile)
}

class ProfileRepository : IProfileRepository {

    private var cached: Profile? = null

    override suspend fun getProfile(email: String): Profile {
        delay(300)
        return cached ?: Profile(
            name = "Gamer Pro",
            email = email,
            phone = "",
            address = "",
            isDuoc = email.endsWith("@duoc.cl", ignoreCase = true)
        ).also { cached = it }
    }

    override suspend fun updateProfile(profile: Profile) {
        delay(300)
        cached = profile
    }
}
