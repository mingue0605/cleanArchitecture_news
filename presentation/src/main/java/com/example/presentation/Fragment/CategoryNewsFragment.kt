package com.example.presentation.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.presentation.*
import com.example.presentation.Adapter.NewsListAdapter
import com.example.presentation.databinding.FragmentCategoryNewsBinding
import com.example.presentation.repository.NewsRepository
import com.example.presentation.repository.NewsRepositoryImpl
import com.example.presentation.source.local.SavedNewsLocalDataSource
import com.example.presentation.source.local.SavedNewsLocalDataSourceImpl
import com.example.presentation.source.remote.NewsRemoteDataSourceImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryNewsFragment : Fragment(R.layout.fragment_category_news), NewsListAdapter.OnClickListener{

    private lateinit var mBinding : FragmentCategoryNewsBinding
    private var categoryAdapter : NewsListAdapter? = null
    private lateinit var models : ArrayList<Articles>
    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController

    //reposotory 구성 해줌.
    private val topNewsRepository: NewsRepository by lazy {
        val topNewsRemoteDataSourceImpl = NewsRemoteDataSourceImpl()
        val saveNewsLocalDataSourceImpl = SavedNewsLocalDataSourceImpl(requireActivity())

        NewsRepositoryImpl(topNewsRemoteDataSourceImpl, saveNewsLocalDataSourceImpl)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentCategoryNewsBinding.inflate(inflater, container, false)
        navHostFragment =requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

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

        topNewsRepository.getNews(category).enqueue(object : Callback<NewsData> {
            override fun onResponse(call: Call<NewsData>, response: Response<NewsData>) {
                if(response.isSuccessful){
                    // 정상적으로 통신이 성공된 경우
                    val result: NewsData? = response.body()
                    val model = result?.articles

                    models = ArrayList()
                    for(i in model!!.indices){
                        models.add(model[i])
                    }

                    categoryAdapter?.setItems(models)
                }else{
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("mingue ", "onResponse 실패$response")
                }
            }
            override fun onFailure(call: Call<NewsData>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d("YMC", "onFailure 에러: " + t.message.toString());
            }
        })
    }

    override fun onItemClicked(articles: Articles, view: View) {
        when(view.id){
            R.id.cl_article -> {
                navController.navigate(R.id.newsDetailFragment, Bundle().apply {
                    putParcelable("items", articles)
                })
            }
        }
    }
}