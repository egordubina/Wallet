package com.example.wallet.domain.usecases

import com.example.wallet.data.repository.UserRepository

class RegistrationUserUseCase(private val userRepository: UserRepository) {
    suspend fun registrationUser(name: String, email: String, pin: String) {
        userRepository.registrationUser(name, email, pin)
        userRepository.setIsFirstLogin()
    }
}