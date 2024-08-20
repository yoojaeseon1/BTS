package com.android.bts.presentation.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.android.bts.R
import com.android.bts.databinding.FragmentMemoBinding
import com.android.bts.presentation.home.HomeFragment.HotClickListenerImpl
import com.android.bts.presentation.home.HotSpotAdapter
import com.android.bts.presentation.home.HomeViewModel
import com.android.bts.presentation.home.HomeViewModelFactory


class MemoFragment : Fragment() {

    private var _binding: FragmentMemoBinding? = null
    private val binding get() = _binding!!
    private val memoViewModel: MemoViewModel by activityViewModels() // ViewModel 초기화
    private val homeViewModel: HomeViewModel by activityViewModels { HomeViewModelFactory() }

    private val hotSpotClick: HotClickListenerImpl by lazy {
//        HotClickListenerImpl(requireActivity())
        HotClickListenerImpl(this)
    }

    private val newSpotAdapter: HotSpotAdapter by lazy {
        HotSpotAdapter(hotSpotClick)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMemoBinding.inflate(inflater, container, false)


        Log.d("MemoFragment", "onCreateView called")
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.videoRecyclrview.layoutManager = gridLayoutManager
        binding.videoRecyclrview.adapter = newSpotAdapter

        // ViewModel의 상태 확인 및 관찰
        observeViewModel()

        // ViewModel의 상태를 관찰하여 EditText 상태와 텍스트 업데이트
        memoViewModel.isEditTextEnabled.observe(viewLifecycleOwner) { isEnabled ->
            updateEditTextState(isEnabled)
        }

        memoViewModel.memoText.observe(viewLifecycleOwner) { text ->
            binding.editTextMemo.setText(text)
        }

        // "편집" 버튼 클릭 리스너
        binding.textEdit.setOnClickListener {
            if (memoViewModel.isEditTextEnabled.value == true) {
                // 텍스트를 저장
                memoViewModel.setMemoText(binding.editTextMemo.text.toString())
            }
            memoViewModel.toggleEditTextState()  // ViewModel의 상태를 토글
        }

        return binding.root
    }

    private fun observeViewModel() {
        homeViewModel.newSpotVideos.observe(viewLifecycleOwner) { videoList ->
//            if (videoList.isNullOrEmpty()) {
//                // 데이터가 비어 있는 경우 로그 출력
//                Log.d("MemoFragment", "Video list is empty or null")
//            } else {
//                newSpotAdapter.submitList(videoList)
//                Log.d("MemoFragment", "Video list loaded with size: ${videoList.size}")
//            }
        }
    }

    private fun updateEditTextState(isEnabled: Boolean) {
        binding.editTextMemo.isEnabled = isEnabled
        binding.editTextMemo.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                if (isEnabled) R.color.gray else R.color.white
            )
        )
        binding.textEdit.text = if (isEnabled) "완료" else "편집"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
