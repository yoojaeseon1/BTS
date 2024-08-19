package com.android.bts.presentation.my

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.bts.R
import com.android.bts.data.LoginInfo
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

        //지역 체크박스 옵저빙 부분
        viewModel.checkedText.observe(viewLifecycleOwner, Observer { checkedText ->
            binding.tvMyVideoUserRegion.text = checkedText.joinToString(", ")
        })

        //닉네임 텍스트 받아오기
        viewModel.text.observe(viewLifecycleOwner) { newText ->
            binding.tvMyVideoUserNickName.text = newText
            Log.d("TAG", "Received text: $newText")
        }

        //로그인 정보 받아오기
        viewModel.loginInfo.observe(viewLifecycleOwner) { newLoginInfo ->
            binding.tvMyVideoUserNickName.text = newLoginInfo.userNickName
            binding.tvMyVideoUserRegion.text = newLoginInfo.userRegion
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.tvMyVideoUserNickName.setOnClickListener {

            MyVideoModifyDialog().show(
                parentFragmentManager, "MyVideoModifyDialog"
            )

        }

        binding.icBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        //고객센터 url 이동
        binding.tvMyVideoHelp.setOnClickListener {
            val url = "https://support.google.com/youtube/?hl=ko#topic=9257498/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        //1대1문의 url 이동
        binding.tvMyVideoAsk.setOnClickListener {
            val url = "https://support.google.com/youtube/gethelp?sjid=12190161131484178952-AP"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        //이용약관 url 이동
        binding.tvMyVideoContract.setOnClickListener {
            val url = "https://www.youtube.com/t/terms?sjid=12190161131484178952-AP"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
