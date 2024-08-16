package com.android.bts.presentation.my

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.bts.R
import com.android.bts.databinding.FragmentMyVideoBinding

class MyVideoFragment : Fragment() {
    private lateinit var viewModel: MyVideoViewModel

    private lateinit var binding: FragmentMyVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMyVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.tvMyVideoUserId.setOnClickListener {

            MyVideoModifyDialog().show(
                parentFragmentManager, "MyVideoModifyDialog"
            )

        }

        binding.icBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        viewModel = ViewModelProvider(this).get(MyVideoViewModel::class.java)
        // 체크된 지역텍스트 적용
        viewModel.checkedText.observe(viewLifecycleOwner) { checkedText ->
            binding.tvMyVideoUserRegion.text = checkedText.joinToString(", ")
        }
    }
}
