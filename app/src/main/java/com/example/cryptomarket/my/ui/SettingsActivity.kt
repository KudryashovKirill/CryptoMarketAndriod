package com.example.cryptomarket.my.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.cryptomarket.databinding.ActivitySettingsBinding
import com.example.cryptomarket.my.model.Account

class SettingsActivity : BaseActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentUser = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("EXTRA_ACCOUNT", Account::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("EXTRA_ACCOUNT") as? Account
        }

        if (currentUser != null) {
            val genderText = if (currentUser.isMale) "Мужской" else "Женский"
            binding.tvUserProfile.text = "Текущий пользователь:\nИмя: ${currentUser.login}\nПол: $genderText"
        } else {
            binding.tvUserProfile.text = "Вы вошли как Аноним"
        }

        val currentNightMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
        if (currentNightMode == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
            binding.rbDark.isChecked = true
        } else {
            binding.rbLight.isChecked = true
        }

        var isBackupExists = false
        fun updateBackupUI() {
            if (isBackupExists) {
                binding.tvBackupStatus.text = "Резервная копия от 21.09.2026 доступна"
                binding.tvBackupStatus.setTextColor(android.graphics.Color.parseColor("#2E7D32"))
                binding.btnCreateBackup.isEnabled = false
                binding.btnRestoreBackup.isEnabled = true
                binding.btnDeleteBackup.isEnabled = true
            } else {
                binding.tvBackupStatus.text = "Резервная копия не найдена"
                binding.tvBackupStatus.setTextColor(android.graphics.Color.parseColor("#C62828"))
                binding.btnCreateBackup.isEnabled = true
                binding.btnRestoreBackup.isEnabled = false
                binding.btnDeleteBackup.isEnabled = false
            }
        }
        updateBackupUI()

        binding.btnCreateBackup.setOnClickListener {
            isBackupExists = true
            updateBackupUI()
            Toast.makeText(this, "Резервная копия успешно создана", Toast.LENGTH_SHORT).show()
        }
        binding.btnRestoreBackup.setOnClickListener {
            Toast.makeText(this, "Данные восстановлены", Toast.LENGTH_SHORT).show()
        }
        binding.btnDeleteBackup.setOnClickListener {
            isBackupExists = false
            updateBackupUI()
            Toast.makeText(this, "Резервная копия удалена", Toast.LENGTH_SHORT).show()
        }

        binding.rgTheme.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.rbLight.id -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.rbDark.id -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}