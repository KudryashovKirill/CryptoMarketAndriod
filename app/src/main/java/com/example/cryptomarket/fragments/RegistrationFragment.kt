package com.example.cryptomarket.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cryptomarket.R
import com.example.cryptomarket.databinding.ActivityRegistrationBinding
import com.example.cryptomarket.my.model.Account
import com.google.android.material.snackbar.Snackbar

class RegistrationFragment : BaseFragment() {

    private var _binding: ActivityRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

            findNavController().previousBackStackEntry?.savedStateHandle?.set("NEW_ACCOUNT", account)

            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}