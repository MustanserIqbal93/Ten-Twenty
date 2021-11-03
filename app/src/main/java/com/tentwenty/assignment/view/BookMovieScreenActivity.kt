package com.tentwenty.assignment.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import com.tentwenty.assignment.R
import com.tentwenty.assignment.databinding.ActivityBookMovieScreenBinding
import com.tentwenty.assignment.utils.DialogUtil
import com.tentwenty.assignment.utils.Utility
import com.tentwenty.assignment.view.adapter.ImagesAdapter
import com.tentwenty.assignment.viewmodel.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.android.synthetic.main.toolbar.view.*


@AndroidEntryPoint
class BookMovieScreenActivity : BaseActivity<ActivityBookMovieScreenBinding>() {
    companion object {
        const val MOVIE_ID = "movie_id"
    }

    @Inject
    lateinit var dialogUtils: DialogUtil

    @Inject
    lateinit var imagesAdapter: ImagesAdapter

    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView(R.layout.activity_book_movie_screen)

        intent.getStringExtra(MOVIE_ID)?.let {
            if (Utility.isOnline(this@BookMovieScreenActivity)) {
                viewModel.getMovieImages(it)
            }
        }


        binding?.toolbarLayout?.rootView?.toolbar?.navigationIcon = ResourcesCompat.getDrawable(resources,R.drawable.ic_baseline_arrow_back_24,this.theme)
        binding?.toolbarLayout?.rootView?.toolbar?.title=getString(R.string.movie_booking)
        binding?.toolbarLayout?.rootView?.toolbar?.setNavigationOnClickListener(View.OnClickListener {
            finish()
        })

    }

}