package com.example.presentation.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.presentation.*
import com.example.presentation.Adapter.NewsListAdapter
import com.example.presentation.databinding.FragmentCategoryNewsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CategoryNewsFragment : Fragment(R.layout.fragment_category_news), NewsListAdapter.OnClickListener{

    private lateinit var mBinding : FragmentCategoryNewsBinding
    private var categoryAdapter : NewsListAdapter? = null
    private lateinit var models : ArrayList<Items>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentCategoryNewsBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.rvCategory.layoutManager = LinearLayoutManagerWrapper(requireContext(), LinearLayoutManager.VERTICAL, false)
        categoryAdapter = context?.let { NewsListAdapter(it, this) }
        mBinding.rvCategory.adapter = categoryAdapter

        val category = arguments?.getString("category")
        if (category != null) {
            newsCategory(category)
        }
    }

    private fun newsCategory(category : String) {
        val retrofit = Retrofit.Builder().baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create()).build();
        val service = retrofit.create(ApiService::class.java)

        service.requestCategoryNews(category).enqueue(object : Callback<NewsData> {
            override fun onResponse(call: Call<NewsData>, response: Response<NewsData>) {
                if(response.isSuccessful){
                    // ??????????????? ????????? ????????? ??????
                    val result: NewsData? = response.body()
                    val model = result?.articles

                    models = ArrayList()
                    for(i in model!!.indices){
                        models.add(model[i])
                    }

                    categoryAdapter?.setItems(models)
                    categoryAdapter?.notifyDataSetChanged()
                }else{
                    // ????????? ????????? ??????(???????????? 3xx, 4xx ???)
                    Log.d("mingue ", "onResponse ??????$response")
                }
            }
            override fun onFailure(call: Call<NewsData>, t: Throwable) {
                // ?????? ?????? (????????? ??????, ?????? ?????? ??? ??????????????? ??????)
                Log.d("YMC", "onFailure ??????: " + t.message.toString());
            }
        })
    }

    override fun onItemClicked(item: Items, view: View) {
        when(view.id){
            R.id.tv_author, R.id.tv_title, R.id.iv_photo -> {
                val bundle = bundleOf("title" to item.title, "author" to item.author, "desc" to item.description , "image" to item.urlToImage)
                findNavController().navigate(R.id.newsDetailFragment, bundle)
            }
        }
    }
}