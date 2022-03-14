package com.dev.currencylayer.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.currencylayer.utils.getFlagResource
import com.dev.currencylayer.data.models.Currency
import com.dev.currencylayer.databinding.ItemCurrencyCardBinding
import java.text.DecimalFormat

class CurrencyAdapter : RecyclerView.Adapter<CurrencyAdapter.AdapterViewHolder>() {

    private var dataSet: List<Currency> = ArrayList()
    private var amountToConvert = 1.0
    private var selectedCurrencyExchangeRate = 1.0

    fun setDataList(dataSet: List<Currency>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    fun updateSelectedCurrencyRate(rate: Double) {
        this.selectedCurrencyExchangeRate = rate
        notifyDataSetChanged()
    }

    fun convert(amountToConvert: Double) {
        this.amountToConvert = amountToConvert
        notifyDataSetChanged()
    }

    private fun calculateExchangeAmount(position: Int): Double {
        return dataSet[position].rate.times(amountToConvert).div(selectedCurrencyExchangeRate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCurrencyCardBinding.inflate(inflater, parent, false)
        return AdapterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val currency = dataSet[position]
        val dec = DecimalFormat("#,###.##")
        currency.amount = dec.format(calculateExchangeAmount(position))
        holder.bind(currency)
    }

    class AdapterViewHolder(private val binding: ItemCurrencyCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currency: Currency) {
            currency.countryFlag = binding.root.context.resources.getFlagResource(currency.code)
            binding.currency = currency
            binding.executePendingBindings()
        }
    }
}