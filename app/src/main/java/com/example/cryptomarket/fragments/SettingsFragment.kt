package com.example.cryptomarket.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import com.example.cryptomarket.databinding.ActivitySettingsBinding
import com.example.cryptomarket.my.model.Account

class SettingsFragment : BaseFragment() {

    private var _binding: ActivitySettingsBinding? = null
    private val binding get() = _binding!!
    private var isBackupExists = false
    private var backupDate = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivitySettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentUser = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable("EXTRA_ACCOUNT", Account::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getSerializable("EXTRA_ACCOUNT") as? Account
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

        updateBackupUI()

        binding.btnCreateBackup.setOnClickListener {
            isBackupExists = true

            val sdf = java.text.SimpleDateFormat("dd.MM.yyyy HH:mm", java.util.Locale.getDefault())
            backupDate = sdf.format(java.util.Date())

            updateBackupUI()
            Toast.makeText(requireContext(), "Резервная копия успешно создана", Toast.LENGTH_SHORT).show()
        }
        binding.btnRestoreBackup.setOnClickListener {
            Toast.makeText(requireContext(), "Данные восстановлены", Toast.LENGTH_SHORT).show()
        }
        binding.btnDeleteBackup.setOnClickListener {
            isBackupExists = false
            updateBackupUI()
            Toast.makeText(requireContext(), "Резервная копия удалена", Toast.LENGTH_SHORT).show()
        }

        binding.rgTheme.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.rbLight.id -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.rbDark.id -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun updateBackupUI() {
        if (isBackupExists) {
            binding.tvBackupStatus.text = "Резервная копия от $backupDate доступна"
            binding.tvBackupStatus.setTextColor(Color.parseColor("#2E7D32"))
            binding.btnCreateBackup.isEnabled = false
            binding.btnRestoreBackup.isEnabled = true
            binding.btnDeleteBackup.isEnabled = true
        } else {
            binding.tvBackupStatus.text = "Резервная копия не найдена"
            binding.tvBackupStatus.setTextColor(Color.parseColor("#C62828"))
            binding.btnCreateBackup.isEnabled = true
            binding.btnRestoreBackup.isEnabled = false
            binding.btnDeleteBackup.isEnabled = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}