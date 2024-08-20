package com.android.bts.presentation.Login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.android.bts.R
import com.android.bts.data.LoginInfo
import com.android.bts.databinding.FragmentLoginBinding
import com.android.bts.presentation.MainActivity
import com.android.bts.presentation.my.MyVideoFragment
import com.android.bts.presentation.my.MyVideoViewModel

class LoginFragment : Fragment() {

    private val myVideoViewModel: MyVideoViewModel by activityViewModels()
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
            val loginInfo = LoginInfo(
                userId = "user1",
                userPassword = "1234",
                userNickName = "유저1",
                userRegion = "서울",
                userProfile = R.drawable.profile4
            )
            myVideoViewModel.updateLoginInfo(loginInfo)

            loginSuccess()
        }

        binding.loginUser2.setOnClickListener {
            val loginInfo = LoginInfo(
                userId = "user2",
                userPassword = "1234",
                userNickName = "유저2",
                userRegion = "서울",
                userProfile = R.drawable.profile3
            )
            myVideoViewModel.updateLoginInfo(loginInfo)

            loginSuccess()
        }

        binding.loginUser3.setOnClickListener {
            val loginInfo = LoginInfo(
                userId = "user3",
                userPassword = "1234",
                userNickName = "유저3",
                userRegion = "서울",
                userProfile = R.drawable.profile2
            )
            myVideoViewModel.updateLoginInfo(loginInfo)

            loginSuccess()
        }

        binding.loginUser4.setOnClickListener {
            val loginInfo = LoginInfo(
                userId = "user4",
                userPassword = "1234",
                userNickName = "유저4",
                userRegion = "서울",
                userProfile = R.drawable.profile1
            )
            myVideoViewModel.updateLoginInfo(loginInfo)

            loginSuccess()
        }
    }

    private fun loginSuccess() {
        val loginSuccess = true
        if (loginSuccess) {
            (activity as? MainActivity)?.showViewPager()
        }
    }


}