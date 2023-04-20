package com.example.pricewatch.watchlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.example.pricewatch.databinding.ItemTickerDetailBinding
import com.example.pricewatch.model.CryptoPriceItem
import java.math.BigDecimal
import java.math.RoundingMode

class WatchlistAdapter (private val clickListener: OnTickerClicked):
    ListAdapter<CryptoPriceItem, WatchlistAdapter.ItemViewHolder>(TickerDiffCallback()) {

    inner class ItemViewHolder(private val binding: ItemTickerDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: CryptoPriceItem) {
            binding.textTicker.text = item.symbol
            binding.textVolume.text = item.quoteVolume.round().toPlainString()
            binding.textPrice.text = item.lastPrice
            binding.textChangePercent.text = item.priceChangePercent
            binding.textChange.text = item.priceChange
            binding.root.setOnClickListener { clickListener.onTickerClicked(item) }
        }
    }

    fun BigDecimal.round() = this.setScale(1, RoundingMode.HALF_UP)

    private class TickerDiffCallback : DiffUtil.ItemCallback<CryptoPriceItem>() {
        override fun areItemsTheSame(oldItem: CryptoPriceItem, newItem: CryptoPriceItem): Boolean =
            oldItem.symbol == newItem.symbol

        override fun areContentsTheSame(oldItem: CryptoPriceItem, newItem: CryptoPriceItem): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = ItemTickerDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    fun interface OnTickerClicked {
        fun onTickerClicked(detail: CryptoPriceItem)
    }
}