package com.example.cryptomarket.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.example.cryptomarket.databinding.ActivityStartBinding
import com.example.cryptomarket.my.model.Account
import com.example.cryptomarket.my.ui.BaseActivity
import com.example.cryptomarket.my.ui.ContentActivity
import com.example.cryptomarket.my.ui.RegistrationActivity

class StartActivity : BaseActivity() {

    private lateinit var binding: ActivityStartBinding
    private var currentUser: Account? = null

    private val registrationLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            @Suppress("DEPRECATION")
            currentUser = result.data?.getSerializableExtra("EXTRA_ACCOUNT") as? Account
            updateUI() // Обновляем экран при наличии информации о пользователе
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateUI()

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            currentUser?.let {
                intent.putExtra("PREFILL_LOGIN", it.login)
            }
            registrationLauncher.launch(intent)
        }

        binding.btnContent.setOnClickListener {
            val intent = Intent(this, ContentActivity::class.java).apply {
                putExtra("EXTRA_ACCOUNT", currentUser)
            }
            startActivity(intent)
        }
    }


    private fun updateUI() {
        if (currentUser == null) {
            // Случай первого входа
            binding.tvGreeting.text = "Добро пожаловать, Аноним!"
            binding.btnRegister.text = "Авторизация / Регистрация"
        } else {
            val user = currentUser!!
            val prefix = if (user.isMale) "Уважаемый" else "Уважаемая"
            binding.tvGreeting.text = "Добро пожаловать,\n$prefix ${user.login}!"
            binding.btnRegister.text = "Сменить аккаунт"
        }
    }
}