package com.example.cryptomarket.my.ui

import android.content.Intent
import android.os.Bundle
import com.example.cryptomarket.R
import com.google.android.material.snackbar.Snackbar
import com.example.cryptomarket.databinding.ActivityRegistrationBinding
import com.example.cryptomarket.my.model.Account


class RegistrationActivity : BaseActivity() {

    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefilledLogin = intent.getStringExtra("PREFILL_LOGIN")
        if (!prefilledLogin.isNullOrEmpty()) {
            binding.etEmail.setText(prefilledLogin)
        }

        binding.btnSubmit.setOnClickListener {
            val login = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()

            if (login.isEmpty()) {
                Snackbar.make(binding.root, "Пожалуйста, введите имя", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.length < 4) {
                Snackbar.make(binding.root, "Пароль должен быть не менее 4 символов", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.rgGender.checkedRadioButtonId == -1) {
                Snackbar.make(binding.root, "Пожалуйста, выберите пол", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val isMale = binding.rgGender.checkedRadioButtonId == R.id.rbMale
            val account = Account(login, isMale)

            val resultIntent = Intent().apply {
                putExtra("EXTRA_ACCOUNT", account)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}