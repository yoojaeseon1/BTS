package com.android.bts.presentation.Login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.bts.R
import com.android.bts.data.LoginInfo
import com.android.bts.databinding.FragmentLoginBinding
import com.android.bts.presentation.MainActivity
import com.android.bts.presentation.my.MyVideoFragment

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginUser1.setOnClickListener {
            LoginInfo.userId = "user1"
            LoginInfo.userPassword = "1234"
            LoginInfo.userNickName = "유저1"
            LoginInfo.userRegion = "서울"

            loginSuccess()
        }

        binding.loginUser2.setOnClickListener {
            LoginInfo.userId = "user2"
            LoginInfo.userPassword = "1234"
            LoginInfo.userNickName = "유저2"
            LoginInfo.userRegion = "서울"

            loginSuccess()
        }

        binding.loginUser3.setOnClickListener {
            LoginInfo.userId = "user3"
            LoginInfo.userPassword = "1234"
            LoginInfo.userNickName = "유저3"
            LoginInfo.userRegion = "서울"

            loginSuccess()
        }

        binding.loginUser4.setOnClickListener {
            LoginInfo.userId = "user4"
            LoginInfo.userPassword = "1234"
            LoginInfo.userNickName = "유저4"
            LoginInfo.userRegion = "서울"

            loginSuccess()
        }
    }

    fun loginSuccess() {
        val loginSuccess = true
        if (loginSuccess) {
            (activity as? MainActivity)?.showViewPager()
        }
    }


}