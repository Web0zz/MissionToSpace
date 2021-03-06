package com.web0zz.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<B : ViewBinding, V : BaseViewModel>(
    private val inflateLayout: (LayoutInflater, ViewGroup?, Boolean) -> B
) : Fragment() {
    private var _binding: B? = null
    protected val binding get() = _binding!!

    protected abstract val mViewModel: V

    open fun onBackPressed() {}

    internal fun firstTimeCreated(savedInstanceState: Bundle?) = savedInstanceState == null

    open fun onCreateInvoke() {}
    open fun onCreateViewInvoke() {}
    open fun onViewCreatedInvoke() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateInvoke()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateLayout(layoutInflater, container, false)
        onCreateViewInvoke()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreatedInvoke()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}