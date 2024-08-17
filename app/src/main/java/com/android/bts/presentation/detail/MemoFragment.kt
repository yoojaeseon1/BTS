package com.android.bts.presentation.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.android.bts.R
import com.android.bts.databinding.FragmentMemoBinding

class MemoFragment : Fragment() {

    private var _binding: FragmentMemoBinding? = null
    private val binding get() = _binding!!
    private val memoViewModel: MemoViewModel by activityViewModels() // ViewModel 초기화

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMemoBinding.inflate(inflater, container, false)

        Log.d("MemoFragment", "onCreateView called")

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
