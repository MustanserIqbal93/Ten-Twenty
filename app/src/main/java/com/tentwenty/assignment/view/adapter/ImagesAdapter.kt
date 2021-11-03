package com.tentwenty.assignment.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tentwenty.assignment.R
import com.tentwenty.assignment.databinding.MovieImageItemBinding
import com.tentwenty.assignment.model.MovieImage
import javax.inject.Inject

class ImagesAdapter  @Inject constructor():
    RecyclerView.Adapter<ImagesAdapter.MyViewHolder>() {
    private var listMovieImages: MutableList<MovieImage>? = null

    class MyViewHolder(val itemBinding: MovieImageItemBinding) : RecyclerView.ViewHolder(itemBinding.root){
 
        private var binding : MovieImageItemBinding? = null
 
        init {
            this.binding = itemBinding
        }
 
    }
 
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: MovieImageItemBinding = DataBindingUtil.inflate(inflater, R.layout.movie_image_item,parent,false)
        return MyViewHolder(binding)
    }
 
    override fun getItemCount(): Int {
        listMovieImages?.let {
            return if(it.size > 5){
                5
            }else {
                it.size
            }
        }
        return 0
    }
 
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.itemBinding.image = listMovieImages?.get(position)
    }

    fun setImages(imagesList: ArrayList<MovieImage>){
        this.listMovieImages = imagesList
        notifyDataSetChanged()
    }
}