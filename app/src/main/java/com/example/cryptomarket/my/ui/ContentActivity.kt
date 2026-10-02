package com.example.cryptomarket.my.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptomarket.databinding.ActivityContentBinding
import com.example.cryptomarket.my.model.Account
import com.example.cryptomarket.my.model.CoinItem
import kotlin.jvm.java

class ContentActivity : BaseActivity() {

    private lateinit var binding: ActivityContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentUser = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("EXTRA_ACCOUNT", Account::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("EXTRA_ACCOUNT") as? Account
        }


        val mockData = mutableListOf(
            CoinItem("bitcoin", "Bitcoin", "btc", 64000.50, 2.5),
            CoinItem("ethereum", "Ethereum", "eth", 3450.00, -1.2),
            CoinItem("tether", "Tether", "usdt", 1.00, 0.01),
            CoinItem("binancecoin", "BNB", "bnb", 590.20, 1.8),
            CoinItem("solana", "Solana", "sol", 145.30, -5.4),
            CoinItem("ripple", "XRP", "xrp", 0.58, 0.5),
            CoinItem("usdc", "USDC", "usdc", 1.00, 0.00),
            CoinItem("dogecoin", "Dogecoin", "doge", 0.12, 10.5),
            CoinItem("cardano", "Cardano", "ada", 0.45, -2.1),
            CoinItem("avalanche", "Avalanche", "avax", 35.80, 4.2),
            CoinItem("tron", "TRON", "trx", 0.11, 1.2),
            CoinItem("shiba", "Shiba Inu", "shib", 0.00002, -3.1),
            CoinItem("polkadot", "Polkadot", "dot", 7.15, 0.4),
            CoinItem("chainlink", "Chainlink", "link", 18.30, 5.5),
            CoinItem("polygon", "Polygon", "matic", 0.75, -1.0),
            CoinItem("litecoin", "Litecoin", "ltc", 82.40, 0.8),
            CoinItem("near", "NEAR Protocol", "near", 5.20, 2.1),
            CoinItem("aptos", "Aptos", "apt", 9.10, -4.5),
            CoinItem("uniswap", "Uniswap", "uni", 7.80, 3.3),
            CoinItem("stellar", "Stellar", "xlm", 0.10, -0.5)
        )

        val adapter = CoinAdapter(mockData)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.swipeRefresh.setOnRefreshListener {
            for (i in mockData.indices) {
                val currentCoin = mockData[i]
                val randomVolatility = kotlin.random.Random.nextDouble(-5.0, 5.0)

                mockData[i] = currentCoin.copy(
                    priceUsd = currentCoin.priceUsd * (1 + randomVolatility / 100),
                    changePercent24Hr = randomVolatility
                )
            }
            adapter.notifyDataSetChanged()
            binding.swipeRefresh.isRefreshing = false
        }

        binding.btnSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java).apply {
                putExtra("EXTRA_ACCOUNT", currentUser)
            }
            startActivity(intent)
        }
    }
}