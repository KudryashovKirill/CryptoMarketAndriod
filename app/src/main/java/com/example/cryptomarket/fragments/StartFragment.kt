package com.example.cryptomarket.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cryptomarket.R
import com.example.cryptomarket.databinding.ActivityStartBinding
import com.example.cryptomarket.my.model.Account

class StartFragment : BaseFragment() {
    private var _binding: ActivityStartBinding? = null
    private val binding get() = _binding!!

    private var currentUser: Account? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = ActivityStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findNavController().currentBackStackEntry?.savedStateHandle
            ?.getLiveData<Account>("NEW_ACCOUNT")?.observe(viewLifecycleOwner) { account ->
                currentUser = account
                updateUI()
            }

        updateUI()

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_start_to_registration)
        }

        binding.btnContent.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("EXTRA_ACCOUNT", currentUser)
            }
            findNavController().navigate(R.id.action_start_to_content, bundle)
        }
    }

    private fun updateUI() {
        if (currentUser == null) {
            binding.tvGreeting.text = "Добро пожаловать, Аноним!"
            binding.btnRegister.text = "Авторизация / Регистрация"
        } else {
            val user = currentUser!!
            val prefix = if (user.isMale) "Уважаемый" else "Уважаемая"
            binding.tvGreeting.text = "Добро пожаловать,\n$prefix ${user.login}!"
            binding.btnRegister.text = "Сменить аккаунт"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}