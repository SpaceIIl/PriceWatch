package com.example.pricewatch.watchlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.example.pricewatch.R
import com.example.pricewatch.databinding.ItemTickerDetailBinding
import com.example.pricewatch.model.CryptoPriceItem
import java.math.BigDecimal
import java.math.RoundingMode

class WatchlistAdapter (private val clickListener: OnTickerClicked):
    ListAdapter<CryptoPriceItem, WatchlistAdapter.ItemViewHolder>(TickerDiffCallback()) {

    private var pinnedItemIndex: Int = -1

    inner class ItemViewHolder(private val binding: ItemTickerDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: CryptoPriceItem) {
            with(binding) {
                textTicker.text = item.symbol
                textVolume.text = binding.root.context.getString(R.string.usd, item.quoteVolume.round(0))
                textPrice.text = binding.root.context.getString(R.string.usd, item.lastPrice.round())
                textChangePercent.text = binding.root.context.getString(R.string.percentage, item.priceChangePercent.round())
                textChange.text = binding.root.context.getString(R.string.usd, item.priceChange.round())
                //root.setOnClickListener { clickListener.onTickerClicked(item) }

                itemView.setOnClickListener {
                    val previousIndex = pinnedItemIndex
                    pinnedItemIndex = adapterPosition

                    if (previousIndex != -1) {
                        notifyItemChanged(previousIndex)
                    }

                    notifyItemChanged(pinnedItemIndex)

                    // Handle the click event
                    val clickedItem = getItem(adapterPosition)
                    clickListener.onTickerClicked(clickedItem)

                    // Rearrange the dataset
                    rearrangeDataset()

                    // Notify the adapter of the dataset change
                    //notifyDataSetChanged()
                }
            }
        }
    }

    private fun rearrangeDataset() {
        if (pinnedItemIndex != -1 && pinnedItemIndex < currentList.size) {
            val items = currentList.toMutableList()
            val pinnedItem = items.removeAt(pinnedItemIndex)
            items.add(0, pinnedItem)
            submitList(items)
        }
    }

    fun BigDecimal.round(scale: Int = 2) = this.setScale(scale, RoundingMode.HALF_DOWN)
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