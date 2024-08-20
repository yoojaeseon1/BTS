package com.android.bts.presentation.home

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.android.bts.BTSUtils
import com.android.bts.R
import com.android.bts.databinding.FragmentHomeBinding
import com.android.bts.presentation.MainActivity
import com.android.bts.presentation.detail.VideoDetailFragment
import com.android.bts.presentation.my.MyVideoFragment
import com.android.bts.presentation.my.MyVideoViewModel
import com.android.bts.presentation.search.ItemsEntity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private val _binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }
    val binding get() = _binding

    private val interestedClick: InterestedClickListenerImpl by lazy {
        InterestedClickListenerImpl(requireActivity())
    }

    private val hotSpotClick: HotClickListenerImpl by lazy {
        HotClickListenerImpl(requireActivity())
    }

    private val interestedAdapter: InterestedAdapter by lazy {
        InterestedAdapter(interestedClick)
    }

    private val hotSpotAdapter: HotSpotAdapter by lazy{
        HotSpotAdapter(hotSpotClick)
    }

    private val newSpotAdapter: HotSpotAdapter by lazy{
        HotSpotAdapter(hotSpotClick)
    }

    val homeViewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory()
    }

    private val myVideoViewModel: MyVideoViewModel by activityViewModels()

    val recyclerViewListener = object : OnItemTouchListener{
        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            rv.parent.requestDisallowInterceptTouchEvent(true)
            return false
        }
        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
    }

    private lateinit var parentActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.recyclerViewInterested.adapter = interestedAdapter
        binding.recyclerViewHot.adapter = hotSpotAdapter
        binding.recyclerViewNew.adapter = newSpotAdapter

        parentActivity = requireActivity() as MainActivity

        homeViewModel.initViewModel()

//        binding.recyclerViewInterested.layoutManager = LinearLayoutManager(requireActivity())
        homeViewModel.getInterestedVideoList(requireActivity())
        homeViewModel.getHotVideoList(requireActivity())
        homeViewModel.getNewVideoList(requireActivity())

//        Log.d("HomeFragment", "${viewModel.interestedVideos.value?.size}")

//        interestedAdapter.submitList(viewModel.interestedVideos.value)

        observeModel()

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.recyclerViewInterested.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
//
//            Log.d("HomeFragment", "interestedScroll = (${v.scrollX},${v.scrollY}), (${scrollX}, ${scrollY})")
//            Log.d("HomeFragment", "interestedScroll =  (${oldScrollX}, ${oldScrollY})")
//
//        }

        binding.recyclerViewInterested.addOnItemTouchListener(recyclerViewListener)
        binding.recyclerViewHot.addOnItemTouchListener(recyclerViewListener)
        binding.recyclerViewNew.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if(!recyclerView.canScrollVertically(1)) {
                    homeViewModel.getNewVideoList(requireActivity())
//                    viewModel.newSpotVideos.notifyObserver()
//                    newSpotAdapter.submitList(viewModel.newSpotVideos.value)
//                    newSpotAdapter.submitList(viewModel.newSpotVideos.value?.toMutableList())
                }
            }
        })

//        binding.recyclerViewInterested.addOnScrollListener(object : RecyclerView.OnScrollListener(){
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                Log.d("HomeFragment", "scrollListener (${dx}, ${dy})")
//
//                if(!recyclerView.canScrollHorizontally(1)) {
////                    isScrollable = false
//                    parentActivity.binding.pager.isUserInputEnabled = true
//                    Log.d("HomeFragment", "scrollListener end canScroll true (${dx}, ${dy})")
//                } else {
////                    isEndScroll = true
//                    Log.d("HomeFragment", "scrollListener canScroll false (${dx}, ${dy})")
//
//                    parentActivity.binding.pager.isUserInputEnabled = false
//
//                }
//
//                if(!recyclerView.canScrollHorizontally(-1)) {
////                    isScrollable = false
//                    parentActivity.binding.pager.isUserInputEnabled = true
//                    Log.d("HomeFragment", "scrollListener start canScroll true (${dx}, ${dy})")
//                }
//            }
//        })



        binding.profileIcon.setOnClickListener {
            // MyVideoFragment로 전환
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frame, MyVideoFragment())
                .addToBackStack(null)
                .commit()

        }

    }

    private fun observeModel(){

        homeViewModel.interestedVideos.observe(viewLifecycleOwner){
            interestedAdapter.submitList(homeViewModel.interestedVideos.value)
        }

        homeViewModel.interestedSpots.observe(viewLifecycleOwner){
            Log.d("HomeFragment", "observe interestedSpots checkedText = ${homeViewModel.interestedSpots.value?.size}")
            homeViewModel.getInterestedVideoList(requireActivity())
        }

        homeViewModel.hotSpotVideos.observe(viewLifecycleOwner) {
            hotSpotAdapter.submitList(homeViewModel.hotSpotVideos.value)
        }

        homeViewModel.newSpotVideos.observe(viewLifecycleOwner){
            Log.d("HomeFragment", "observe newSpotVideos size = ${homeViewModel.newSpotVideos.value?.size}")
//            newSpotAdapter.submitList(homeViewModel.newSpotResults.toMutableList())
            newSpotAdapter.submitList(homeViewModel.newSpotVideos.value?.toMutableList())
        }

        myVideoViewModel.checkedText.observe(viewLifecycleOwner){
            Log.d("HomeFragment", "observe mypage checkedText = ${myVideoViewModel.checkedText.value?.size}")
            homeViewModel.interestedSpots.value = myVideoViewModel.checkedText.value
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun submitAllVideos(){
        Log.d("HomeFragment", "execute submitAllVideos()")
        Log.d("HomeFragment", "homeViewModel.interestedVideos.value? = ${homeViewModel.interestedVideos.value?.hashCode()}")
        Log.d("HomeFragment", "homeViewModel.interestedVideos.value?.toMutableList() = ${homeViewModel.interestedVideos.value?.toMutableList().hashCode()}")

        interestedAdapter.submitList(homeViewModel.interestedVideos.value?.toMutableList())
        hotSpotAdapter.submitList(homeViewModel.hotSpotVideos.value?.toMutableList())
        newSpotAdapter.submitList(homeViewModel.newSpotVideos.value?.toMutableList())
    }


    class HotClickListenerImpl(val context: Activity) : HotClickListener {

        override fun onClickLike(itemsEntity: ItemsEntity, holder: HotSpotAdapter.HotSpotHolder) {
            if(itemsEntity.snippet.isLike) {
                BTSUtils.deleteLike(context, itemsEntity.id.videoId)
                itemsEntity.snippet.isLike = false
                holder.like.isVisible = false
            } else {
                BTSUtils.addLike(context, itemsEntity.id.videoId)
                itemsEntity.snippet.isLike = true
                holder.like.isVisible = true
            }
        }

        override fun onClickDetail(
            itemsEntity: ItemsEntity,
            holder: HotSpotAdapter.HotSpotHolder
        ) {
            Log.d("HomeFragment", "video id = ${itemsEntity.id.videoId}")
            Log.d("HomeFragment", "title = ${itemsEntity.snippet.title}")
            // VideoDetailFragment로 전환
//            val videoDetailFragment = VideoDetailFragment.newInstance(
//                itemsEntity.id.videoId,
//                itemsEntity.snippet.title
//            )

            val videoDetailFragment = VideoDetailFragment.newInstance(
                itemsEntity
            )
//            (context as FragmentActivity).supportFragmentManager.beginTransaction()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frame, videoDetailFragment)
                .addToBackStack(null)
                .commit()
        }
    }


    override fun onResume() {
        super.onResume()
        Log.d("HomeFragment", "onResume")
    }

    override fun onStart() {
        super.onStart()
        Log.d("HomeFragment", "onStart")
    }

    override fun onPause() {
        super.onPause()
        Log.d("HomeFragment", "onPause")
    }

}