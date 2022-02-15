package com.example.southsystemtest.ui.eventdetail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.southsystemtest.R
import com.example.southsystemtest.data.model.CheckInRequest
import com.example.southsystemtest.databinding.EventDetailsFragmentBinding
import com.example.southsystemtest.ui.base.BaseBindableFragment
import com.example.southsystemtest.ui.checkinbottomsheet.CheckInBottomSheet
import com.example.southsystemtest.utils.*
import com.example.southsystemtest.utils.helper.ShareHelper

class EventDetailFragment :
    BaseBindableFragment<EventDetailsFragmentBinding>(EventDetailsFragmentBinding::inflate) {

    private val viewModel: EventDetailViewModel by viewModels()
    private val args: EventDetailFragmentArgs by navArgs()
    private val shareHelper by lazy {
        ShareHelper(
            activity = requireActivity(),
            receiptContainer = binding.shareContainer,
            viewsToHide = listOf(binding.shareBtn)
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
            eventDetailArrowBack.setOnClickListener {
                findNavController().popBackStack()
            }
            shareBtn.setOnClickListener {
                viewModel.handle(EventDetailIntent.Share)
            }
            checkInBtn.setOnClickListener {
                CheckInBottomSheet(
                    onClickContinue = { name, email ->
                        viewModel.handle(
                            EventDetailIntent.CheckIn(
                                request = CheckInRequest(
                                    email = email,
                                    eventId = args.event.id.orEmpty(),
                                    name = name
                                )
                            )
                        )
                    }
                ).show(
                    requireActivity().supportFragmentManager,
                    BOTTOM_SHEET_TAG
                )
            }
        }
    }

    private fun setViews() {
        val event = args.event
        binding.run {
            eventDetailTitle.text = event.title
            eventDetailDate.text = event.date?.toSimpleDate(Constants.DATE_TIME_BR_FORMAT)
            eventDetailImage.loadImage(event.image?.formatToHTTPS())
            eventDetailPrice.text = event.price?.formatToAccurateCurrency()
            eventDetailDescription.text = event.description
        }
    }

    private fun setObserver() = viewModel.uiState.observe(viewLifecycleOwner) { state ->
        when (state) {
            is EventDetailState.Share -> share()
            is EventDetailState.Error -> errorMsg()
            is EventDetailState.CheckedIn -> checkedIn()
            is EventDetailState.Loading -> loading(state.loading)
        }
    }

    private fun errorMsg() {
        Toast.makeText(
            context,
            getText(R.string.checkedInFailed),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun loading(loading: Boolean) {
        binding.progressCheckIn.setVisible(loading)
    }

    private fun checkedIn() {
        Toast.makeText(
            context,
            getText(R.string.checkedIn),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun share() {
        shareHelper.shareContent()
    }

    companion object{
       const val BOTTOM_SHEET_TAG = "BOTTOM_SHEET_TAG"
    }
}