package com.example.layout.ui.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.layout.databinding.ActivityMapsBinding

class MapsFragmentLogged : Fragment() {

    private lateinit var mapsViewModelLogged: MapsViewModelLogged
    private var _binding: ActivityMapsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mapsViewModelLogged =
            ViewModelProvider(this).get(MapsViewModelLogged::class.java)

        _binding = ActivityMapsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
