package com.example.presentation.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.example.presentation.R
import com.example.presentation.Room.NewsDB
import com.example.presentation.databinding.FragmentNewsDetailBinding

class NewsDetailFragment : Fragment(R.layout.fragment_news_detail), View.OnClickListener {

    private lateinit var mBinding : FragmentNewsDetailBinding
    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController
    private var ivSaved : Boolean = false
    private lateinit var newsDB : NewsDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        navHostFragment =requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        newsDB = context?.let { NewsDB.getInstance(it) }!!

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.tvTitle.text = arguments?.getString("title")
        mBinding.tvAuthor.text = arguments?.getString("author")
        mBinding.tvDetail.text = arguments?.getString("desc")
        Glide.with(this).load(arguments?.getString("image")).into(mBinding.ivPhoto)
        Toast.makeText(context, "click", Toast.LENGTH_SHORT).show()
        mBinding.ivSaved.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view){
            mBinding.ivSaved -> {
                if(ivSaved){
                    ivSaved = false
                    mBinding.ivSaved.setImageResource(R.drawable.star_no)
                }else{
                    ivSaved = true
                    mBinding.ivSaved.setImageResource(R.drawable.star_ok)

                }
            }
        }
    }
}