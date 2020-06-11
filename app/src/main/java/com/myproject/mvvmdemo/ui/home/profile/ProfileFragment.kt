package com.myproject.mvvmdemo.ui.home.profile

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.myproject.mvvmdemo.R
import com.myproject.mvvmdemo.databinding.ProfileFragmentBinding
import org.kodein.di.android.x.kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class ProfileFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val factory : ProfileViewModelFactory by instance<ProfileViewModelFactory>()

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: ProfileFragmentBinding = DataBindingUtil.inflate(
            inflater,R.layout.profile_fragment,container,false)


        val viewModel: ProfileViewModel = ViewModelProviders.of(this,factory).get(ProfileViewModel::class.java)
        binding.profileViewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }


}
