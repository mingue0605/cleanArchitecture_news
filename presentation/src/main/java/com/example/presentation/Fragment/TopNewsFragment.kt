package com.example.presentation.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.presentation.*
import com.example.presentation.Adapter.TopNewsAdapter
import com.example.presentation.databinding.ActivityMainBinding
import com.example.presentation.databinding.FragmentTopNewsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TopNewsFragment : Fragment(R.layout.fragment_top_news) {

    private lateinit var mBinding : FragmentTopNewsBinding
    private var topNewsAdapter : TopNewsAdapter? = null
    private lateinit var models : ArrayList<Items>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = FragmentTopNewsBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.rvTopNews.layoutManager = LinearLayoutManagerWrapper(requireContext(), LinearLayoutManager.VERTICAL, false)
        topNewsAdapter = context?.let { TopNewsAdapter(it) }
        mBinding.rvTopNews.adapter = topNewsAdapter
        topNews()
    }

    private fun topNews() {
        val retrofit = Retrofit.Builder().baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(ApiService::class.java);

        service.requestNews().enqueue(object : Callback<NewsData> {
            override fun onResponse(call: Call<NewsData>, response: Response<NewsData>) {
                if(response.isSuccessful){
                    // 정상적으로 통신이 성고된 경우
                    val result: NewsData? = response.body()

                    val model = result?.articles

                    models = ArrayList()
                    for(i in model!!.indices){
                        models.add(model[i])
                    }

                    topNewsAdapter?.setItems(models)
                    topNewsAdapter?.notifyDataSetChanged()
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

}