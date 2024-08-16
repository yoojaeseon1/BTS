package com.android.bts.presentation.my

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.android.bts.R
import com.android.bts.databinding.DialogMyVideoModifyBinding

class MyVideoModifyDialog() : DialogFragment() {
    private var _binding: DialogMyVideoModifyBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MyVideoViewModel
    private lateinit var adapter: ModifyRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogMyVideoModifyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MyVideoViewModel::class.java)

        binding.rvModifyRegionLayout.layoutManager = GridLayoutManager(context, 4)
        adapter = ModifyRecyclerViewAdapter(viewModel)
        binding.rvModifyRegionLayout.adapter = adapter

        binding.btnConfirm.setOnClickListener{
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }


    }

    override fun onResume() {
        super.onResume()
        context?.dialogFragmentResize(this@MyVideoModifyDialog, 0.9f, 0.8f)
    }

    // dialog 크기 조절
    fun Context.dialogFragmentResize(
        dialogFragment: DialogFragment,
        width: Float,
        height: Float
    ) {
        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val rect = windowManager.currentWindowMetrics.bounds

        val window = dialogFragment.dialog?.window
        val x = (rect.width() * width).toInt()
        val y = (rect.height() * height).toInt()

        window?.setLayout(x, y)
    }
}