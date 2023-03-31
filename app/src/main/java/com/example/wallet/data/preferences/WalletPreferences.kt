package com.example.wallet.data.preferences

//class WalletPreferences(context: Context) {
//    private val preferencesManager =
//        PreferenceManager.getDefaultSharedPreferences(context.applicationContext)
//    private val editPreferences = preferencesManager.edit()

//    var isFirstLogin: Boolean
//        get() = preferencesManager.getBoolean(USER_NEW.id, true)
//        set(value) = editPreferences.putBoolean(USER_NEW.id, value).apply()
//    var fingerPrintLogin: Boolean
//        get() = preferencesManager.getBoolean(USE_FINGERPRINT_TO_LOGIN.id, true)
//        set(value) = editPreferences.putBoolean(USE_FINGERPRINT_TO_LOGIN.id, value).apply()
//    var userName: Flow<String>
//        get() {
//            return flow {
//                Log.d("Update username", "Update")
//                val name = preferencesManager.getString(USER_NAME.id, "").toString()
//                emit(name)
//            }
//        }
//        set(value) {
//            runBlocking {
//                launch {
//                    value.collect {
//                        Log.d("Get username", "Get")
//                        editPreferences.putString(USER_NAME.id, it).apply()
//                    }
//                }
//            }
//        }

//        set(value) {
//            CoroutineScope(Dispatchers.IO).launch {
//                value.collect {
//                    editPreferences.putString(USER_NAME.id, it).apply()
//                }
//            }
//        }
//    var userPin: String
//        get() = preferencesManager.getString(USER_PIN.id, "").toString()
//        set(value) = editPreferences.putString(USER_PIN.id, value).apply()
//    var userEmail: String
//        get() = preferencesManager.getString(USER_EMAIL.id, "").toString()
//        set(value) = editPreferences.putString(USER_EMAIL.id, value).apply()

// Бюджет
//    var currentMonthIncomes: Int
//        get() = preferencesManager.getInt(BudgetSettingsIds.CURRENT_MONTH_INCOMES.id, 0)
//        set(value) = editPreferences.putInt(BudgetSettingsIds.CURRENT_MONTH_INCOMES.id, value)
//            .apply()
//    var currentMonthExpanses: Int
//        get() = preferencesManager.getInt(BudgetSettingsIds.CURRENT_MONTH_EXPANSES.id, 0)
//        set(value) = editPreferences.putInt(BudgetSettingsIds.CURRENT_MONTH_EXPANSES.id, value)
//            .apply()
//    var currentMaxBudget: Int
//        get() = preferencesManager.getInt(BudgetSettingsIds.CURRENT_MAX_BUDGET.id, 0)
//        set(value) = editPreferences.putInt(BudgetSettingsIds.CURRENT_MAX_BUDGET.id, value).apply()
//
//    fun registrationUser(name: String, email: String, pin: String) {
//        with(editPreferences) {
//            putBoolean(USER_NEW.id, false)
//            putString(USER_NAME.id, name)
//            putString(USER_EMAIL.id, email)
//            putString(USER_PIN.id, pin)
//            apply()
//        }
//    }
//}
