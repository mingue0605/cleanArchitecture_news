package com.example.presentation.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.presentation.Items
import com.example.presentation.R
import java.util.ArrayList

class TopNewsAdapter (private val context: Context)  : RecyclerView.Adapter<TopNewsAdapter.ViewHolder>() {
    private var newsData = ArrayList<Items>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.news_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(newsData[position])
    }

    override fun getItemCount(): Int = newsData.size

    fun setItems(@NonNull items: ArrayList<Items>) {
        newsData.clear()
        newsData.addAll(items)
//		mItems = items
    }


    inner class ViewHolder(view:View) : RecyclerView.ViewHolder(view){

        private val ivNews : AppCompatImageView = itemView.findViewById(R.id.iv_news)
        private val tvTitle : AppCompatTextView = itemView.findViewById(R.id.tv_title)
        private val tvAuthor : AppCompatTextView = itemView.findViewById(R.id.tv_author)
        private val ivSaved : AppCompatImageView = itemView.findViewById(R.id.iv_saved)

        fun bind(item : Items){

            Log.d("mingue ", "adapter")

            Glide.with(itemView).load(item.urlToImage).into(ivNews)
            tvTitle.text = item.title
            tvAuthor.text = item.author

//            bookTitle.text = item.bookTitle
//            bookDetail.text = item.bookDetail
        }
    }
}