package com.android.bts.presentation.my

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.bts.R
import com.android.bts.databinding.FragmentMyVideoBinding

class MyVideoFragment : Fragment() {
    private val viewModel: MyVideoViewModel by activityViewModels()
    private var _binding: FragmentMyVideoBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyVideoBinding.inflate(inflater, container, false)
//        viewModel = ViewModelProvider(this).get(MyVideoViewModel::class.java)

        viewModel.checkedText.observe(viewLifecycleOwner, Observer { checkedText ->
            binding.tvMyVideoUserRegion.text = checkedText.joinToString(", ")
        })

        //닉네임 텍스트 받아오기
        viewModel.text.observe(viewLifecycleOwner) { newText ->
            binding.tvMyVideoUserId.text = newText
            Log.d("TAG", "Received text: $newText")
        }
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
