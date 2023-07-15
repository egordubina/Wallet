package ru.neuromantics.wallet.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface UserRepository {
    val user: Flow<String>
}
class UserRepositoryImpl : UserRepository {
    override val user: Flow<String> = flow { emit("Егор") }
}