package ru.neuromantics.wallet.data.models

enum class SettingsIds(val id: String) {
    USER_NEW("SETTINGS_USER_NEW"),
    USER_NAME("SETTINGS_USER_NAME"),
    USE_FINGERPRINT_TO_LOGIN("SETTINGS_USE_FINGERPRINT_TO_LOGIN"),
    USER_PIN("SETTINGS_USER_PIN"),
    USER_EMAIL("SETTINGS_USER_EMAIL")
}