package com.example.mvvm_basics.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.mvvm_basics.R

class DemoVMFragment : Fragment() {

    companion object {
        fun newInstance() = DemoVMFragment()
    }

    private lateinit var viewModel: DemoVmViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.demo_vm_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DemoVmViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
