package com.example.cryptomarket.my.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptomarket.databinding.ItemCoinBinding
import com.example.cryptomarket.my.model.CoinItem

class CoinAdapter(private val coins: List<CoinItem>) : RecyclerView.Adapter<CoinAdapter.CoinViewHolder>() {

    class CoinViewHolder(val binding: ItemCoinBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(coin: CoinItem) {
            binding.tvCoinName.text = coin.name
            binding.tvCoinSymbol.text = coin.symbol.uppercase()
            binding.tvCoinPrice.text = "$${coin.priceUsd}"

            val changeText = String.format("%.2f%%", coin.changePercent24Hr)

            if (coin.changePercent24Hr >= 0) {
                binding.tvCoinChange.text = "+$changeText"
                binding.tvCoinChange.setTextColor(Color.parseColor("#2E7D32"))
                binding.cardCategory.setStrokeColor(Color.parseColor("#81C784"))
                binding.cardCategory.setCardBackgroundColor(Color.parseColor("#E8F5E9"))
            } else {
                binding.tvCoinChange.text = changeText
                binding.tvCoinChange.setTextColor(Color.parseColor("#C62828"))
                binding.cardCategory.setStrokeColor(Color.parseColor("#E57373"))
                binding.cardCategory.setCardBackgroundColor(Color.parseColor("#FFEBEE"))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val binding = ItemCoinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.bind(coins[position])
    }

    override fun getItemCount(): Int = coins.size
}