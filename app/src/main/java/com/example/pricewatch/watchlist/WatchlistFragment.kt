package com.example.pricewatch.watchlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pricewatch.dialogInput.DialogInputTicker
import com.example.pricewatch.R
import com.example.pricewatch.databinding.FragmentWatchlistBinding
import kotlinx.coroutines.channels.ticker

class WatchlistFragment : Fragment() {
    private var _binding: FragmentWatchlistBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<WatchlistViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWatchlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val watchlistAdapter = WatchlistAdapter {
            /*findNavController().navigate(
                PoolsFragmentDirections.actionFragmentPoolsToFragmentPoolDetail(
                    it.slug
                )
            )*/
        }

        binding.recyclerMyData.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = watchlistAdapter
        }

        binding.buttonDialogInput.setOnClickListener {
            val bottomSheet = DialogInputTicker.newInstance()

            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }

        super.onViewCreated(view, savedInstanceState)

        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is WatchlistScreenState.Error -> {
                    with(binding) {
                        progressWatchlist.visibility = View.GONE
                        recyclerMyData.visibility = View.GONE
                        textWatchlist.text = getString(R.string.error)
                        retryButton.visibility = View.VISIBLE
                        retryButton.setOnClickListener {
                            viewModel.retryLoadingData()
                        }
                    }
                    Log.e("PoolScreen", "Error occurred:", state.throwable)
                }
                is WatchlistScreenState.Loading -> {
                    with(binding) {
                        progressWatchlist.visibility = View.VISIBLE
                        retryButton.visibility = View.GONE
                    }
                }
                is WatchlistScreenState.Success -> {
                    with(binding) {
                        progressWatchlist.visibility = View.GONE
                        retryButton.visibility = View.GONE
                        recyclerMyData.visibility = View.VISIBLE
                        textWatchlist.text = getString(R.string.watchlist)
                        swipeRefresh.isRefreshing = false
                        swipeRefresh.setOnRefreshListener {
                            viewModel.retryLoadingData()
                        }

                    }
                    watchlistAdapter.submitList(state.data)
                    binding.recyclerMyData.post {
                        binding.recyclerMyData.layoutManager?.scrollToPosition(0)
                    }

                    binding.textTest.text = state.tickers.toString()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}