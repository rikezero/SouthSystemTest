package com.example.southsystemtest.ui.eventslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.southsystemtest.data.model.EventListResponseItem
import com.example.southsystemtest.databinding.EventListFragmentBinding
import com.example.southsystemtest.ui.base.BaseBindableFragment
import com.example.southsystemtest.utils.displayVeil
import com.example.southsystemtest.utils.setQtdItemsDefault
import java.util.*

class EventsListFragment :
    BaseBindableFragment<EventListFragmentBinding>(EventListFragmentBinding::inflate) {

    private val viewModel: EventListViewModel by viewModels()
    private val adapter by lazy { EventListAdapter() }
    private val layoutManagerVertical by lazy {
        LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false,
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        setViews()
        setListeners()
    }

    private fun setListeners() {
        binding.run {
            adapter.onItemClicked = {
                viewModel.handle(EventListIntent.FetchEventDetails(it.id))
            }
            eventsArrowBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun setViews() {
        binding.run {
            eventsRv.apply {
                setAdapter(adapter)
                setLayoutManager(layoutManagerVertical)
                setQtdItemsDefault(SKELETON_DEFAULT)
            }
            viewModel.handle(EventListIntent.FetchEventList)
        }
    }

    private fun setObserver() = viewModel.uiState.observe(viewLifecycleOwner) { state ->
        when (state) {
            is EventListState.Loading -> displayVeil(state.loading)
            is EventListState.Error -> {
            }
            is EventListState.LoadEventList -> loadListOnView(state.eventList)
            is EventListState.LoadEventDetailScreen -> {
                if (state.eventDetail != null){
                    findNavController().navigate(EventsListFragmentDirections.actionListToDetail(state.eventDetail))
                }

            }
            is EventListState.EventIdError -> {}
        }
    }

    private fun loadListOnView(eventList: ArrayList<EventListResponseItem>?) {
        adapter.submitList(eventList)
    }

    private fun displayVeil(isVisible: Boolean) = binding.run {
        eventsRv.displayVeil(isVisible = isVisible)
    }

    companion object {
        const val SKELETON_DEFAULT = 10
    }
}