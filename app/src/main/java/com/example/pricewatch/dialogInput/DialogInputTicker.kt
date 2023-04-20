package com.example.pricewatch.dialogInput

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.pricewatch.databinding.DialogInputTickerBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogInputTicker : BottomSheetDialogFragment() {
    private val viewModel: DialogInputViewModel by viewModels()
    private var _binding: DialogInputTickerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogInputTickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            binding.buttonInput.setOnClickListener {
                val ticker = binding.textInputTicker.text.toString()
                viewModel.insertTicker(ticker)
                dismiss()
            }
        }

    companion object {
        fun newInstance(): DialogInputTicker {
            return DialogInputTicker()
        }
    }
}