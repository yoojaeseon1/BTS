package com.android.bts.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.bts.databinding.FragmentMemoBinding

class MemoFragment : Fragment() {

    private var _binding: FragmentMemoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMemoBinding.inflate(inflater, container, false)

        // 기본 비활성화 상태로 설정
        binding.editTextMemo.isEnabled = false

        // "편집" 버튼 클릭 리스너
        binding.textEdit.setOnClickListener {
            if (binding.editTextMemo.isEnabled) {
                // 에딧텍스트가 활성화되어 있을 경우 비활성화하고 버튼 텍스트를 "편집"으로 변경
                binding.editTextMemo.isEnabled = false
                binding.textEdit.text = "편집"
            } else {
                // 에딧텍스트가 비활성화되어 있을 경우 활성화하고 버튼 텍스트를 "완료"로 변경
                binding.editTextMemo.isEnabled = true
                binding.textEdit.text = "완료"
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
