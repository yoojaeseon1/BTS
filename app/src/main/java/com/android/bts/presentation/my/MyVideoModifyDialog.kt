package com.android.bts.presentation.my

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.android.bts.R
import com.android.bts.databinding.DialogMyVideoModifyBinding
import com.android.bts.presentation.MainActivity

class MyVideoModifyDialog() : DialogFragment() {
    private var _binding: DialogMyVideoModifyBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyVideoViewModel by activityViewModels()
    private lateinit var adapter: ModifyRecyclerViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogMyVideoModifyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        val binding = DialogMyVideoModifyBinding.inflate(LayoutInflater.from(context))
        binding.rvModifyRegionLayout.layoutManager = GridLayoutManager(context, 4)
        adapter = ModifyRecyclerViewAdapter(viewModel)
        binding.rvModifyRegionLayout.adapter = adapter


        binding.btnConfirm.setOnClickListener {
            val selectedRegionItems = adapter.getSelectedRegionItems()

            Log.d("Dialog", "selectedRegionItems: ${selectedRegionItems.size}")
            viewModel.updateChecked(selectedRegionItems)
//            viewModel.updateText(binding.etMyModifyNickname.text.toString())
//            Log.d("TAG", "onCreateDialog: ${binding.etMyModifyNickname.text.toString()}")
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()
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