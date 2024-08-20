package com.android.bts.presentation.Login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
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
                userId = "yoojaeseon1",
                userPassword = "1234",
                userNickName = "yoojaeseon1",
                userRegion = "서울",
                userProfile = R.drawable.profile4
            )
            myVideoViewModel.updateLoginInfo(loginInfo)

        }

        binding.loginUser2.setOnClickListener {
            val loginInfo = LoginInfo(
                userId = "hyehyunj",
                userPassword = "1234",
                userNickName = "hyehyunj",
                userRegion = "인천",
                userProfile = R.drawable.profile3
            )
            myVideoViewModel.updateLoginInfo(loginInfo)

//            loginSuccess()
        }

        binding.loginUser3.setOnClickListener {
            val loginInfo = LoginInfo(
                userId = "dacafo77",
                userPassword = "1234",
                userNickName = "dacafo77",
                userRegion = "경기",
                userProfile = R.drawable.profile2
            )
            myVideoViewModel.updateLoginInfo(loginInfo)

//            loginSuccess()
        }

        binding.loginUser4.setOnClickListener {
            val loginInfo = LoginInfo(
                userId = "Dyaoss",
                userPassword = "1234",
                userNickName = "Dyaoss",
                userRegion = "강원",
                userProfile = R.drawable.profile1
            )
            myVideoViewModel.updateLoginInfo(loginInfo)

//            loginSuccess()
        }
        initUserAnimation()




    }



    private fun initUserAnimation() {

        val imageView = arrayListOf(
            binding.loginUser1,
            binding.loginUser2,
            binding.loginUser3,
            binding.loginUser4
        )
        val animation = arrayOf(
            AnimationUtils.loadAnimation(requireContext(), R.anim.login_click),
            AnimationUtils.loadAnimation(requireContext(), R.anim.login_unclick),
        )

        imageView.forEach { cardView ->
            cardView.setOnClickListener { clickedCardView ->
                clickedCardView.startAnimation(animation[0])
                imageView.filter{ it != clickedCardView }.forEach{ it.startAnimation(animation[1])}
                (activity as MainActivity).supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.login_exit,R.anim.login_exit)
                    .remove(this).commit()
            }
            }
        }





//    private fun loginSuccess() {
//        val loginSuccess = true
//        if (loginSuccess) {
//            (activity as MainActivity).showViewPager()
//        }
//    }


}