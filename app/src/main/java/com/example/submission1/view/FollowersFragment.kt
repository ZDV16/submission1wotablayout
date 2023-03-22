package com.example.submission1.view

import android.os.Bundle
import android.text.style.TtsSpan.ARG_USERNAME
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.submission1.R


class FollowersFragment : Fragment() {

    var position: Int = 0
    var username: String = "a"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // arguments?.let {
     //       position = it.getInt(ARG_POSITION)
     //        username = it.getString(ARG_USERNAME)
     //   }
    //    if (position == 1){
      //      binding.testUsername.text = "Get Follower $username"
    //    } else {
      //      binding.testUsername.text = "Get Following $username"
     //   }

    }

    companion object {
        var ARG_POSITION: String? = null
        var ARG_USERNAME: String? = null
        const val ARG_SECTION_NUMBER = "section_number"
    }
}
