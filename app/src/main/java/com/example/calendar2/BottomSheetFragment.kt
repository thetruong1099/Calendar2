package com.example.calendar2

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.calendar2.viewmodel.ShareDataViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottom_sheet.*

class BottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var viewModel: ShareDataViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("myData", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        viewModel = ViewModelProvider(requireActivity()).get(ShareDataViewModel::class.java)

        tv_mon.setOnClickListener {
            saveData(tv_mon.text.toString())
        }
        tv_tue.setOnClickListener {
            saveData(tv_tue.text.toString())
        }
        tv_wed.setOnClickListener {
            saveData(tv_wed.text.toString())
        }
        tv_thur.setOnClickListener {
            saveData(tv_thur.text.toString())
        }
        tv_fri.setOnClickListener {
            saveData(tv_fri.text.toString())
        }
        tv_sat.setOnClickListener {
            saveData(tv_sat.text.toString())
        }
        tv_sun.setOnClickListener {
            saveData(tv_sun.text.toString())

        }
    }

    fun saveData(string: String) {
        editor.putString("dayStart", string)
        editor.commit()
        viewModel.sendText(string)
        dismiss()
    }
}