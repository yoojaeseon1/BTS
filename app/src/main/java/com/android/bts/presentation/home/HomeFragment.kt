package com.android.bts.presentation.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.android.bts.R
import com.android.bts.databinding.FragmentHomeBinding
import com.android.bts.presentation.MainActivity
import com.android.bts.presentation.my.MyVideoFragment

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


    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!

    private val interestedClick: InterestedClickListenerImpl by lazy {
        InterestedClickListenerImpl(requireActivity())
    }

    private val hotSpotClick: HotClickListenerImpl by lazy {
        HotClickListenerImpl(requireActivity())
    }

    private val interestedAdapter: InterestedAdapter by lazy {
        InterestedAdapter(interestedClick)
    }

    private val hotSpotAdapter: FavoriteAdapter by lazy{
        FavoriteAdapter(hotSpotClick)
    }

    private val newSpotAdapter: FavoriteAdapter by lazy{
        FavoriteAdapter(hotSpotClick)
    }

    private val viewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory()
    }

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

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.recyclerViewInterested.adapter = interestedAdapter
        binding.recyclerViewHot.adapter = hotSpotAdapter
        binding.recyclerViewNew.adapter = newSpotAdapter

        parentActivity = requireActivity() as MainActivity

        viewModel.initViewModel()

//        binding.recyclerViewInterested.layoutManager = LinearLayoutManager(requireActivity())
//        viewModel.getInterestedVideoList(requireActivity())
//        viewModel.getHotVideoList(requireActivity())
//        viewModel.getNewVideoList(requireActivity())

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
                    viewModel.getNewVideoList(requireActivity())
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

        viewModel.interestedVideos.observe(viewLifecycleOwner){
            interestedAdapter.submitList(viewModel.interestedVideos.value)
        }

        viewModel.hotSpotVideos.observe(viewLifecycleOwner) {
            hotSpotAdapter.submitList(viewModel.hotSpotVideos.value)
        }

        viewModel.newSpotVideos.observe(viewLifecycleOwner){
            Log.d("HomeFragment", "observe newSpotVideos size = ${viewModel.newSpotVideos.value?.size}")
            newSpotAdapter.submitList(viewModel.newSpotResults.toMutableList())
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






}