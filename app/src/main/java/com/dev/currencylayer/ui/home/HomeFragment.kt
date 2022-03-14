package com.dev.currencylayer.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.dev.currencylayer.R
import com.dev.currencylayer.databinding.MainFragmentBinding
import com.dev.currencylayer.ui.adapters.CurrencyAdapter
import com.dev.currencylayer.ui.adapters.SpinnerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    val viewModel by viewModels<HomeViewModel>()
    var bindings: MainFragmentBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindings = DataBindingUtil.inflate(layoutInflater, R.layout.main_fragment, container, false)
        bindings?.apply {
            this.adapter = CurrencyAdapter()
            this.recyclerView.layoutManager = GridLayoutManager(context, 2)
            this.spinnerAdapter = SpinnerAdapter(requireContext())
        }

        bindings?.currencyEd?.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                onTextChange(s.toString())
            }

        })
        return bindings?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currencyData.observe(viewLifecycleOwner) {
            it?.let { data ->
                bindings?.adapter?.setDataList(data)
                bindings?.spinnerAdapter?.setDataList(data)
                bindings?.currencyListSpinner?.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View,
                            pos: Int,
                            id: Long
                        ) {
                            bindings?.adapter?.updateSelectedCurrencyRate(data[pos].rate)
                        }
                        override fun onNothingSelected(parent: AdapterView<*>?) {}
                    }
            }
        }
    }

    private fun onTextChange(amount:String){
        var parsed = 1.0
        if (amount.isNotEmpty()) {
            parsed = amount.toDouble()
        }
        bindings?.adapter?.convert(parsed)
    }
}