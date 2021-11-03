package com.tentwenty.assignment.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import com.tentwenty.assignment.R
import com.tentwenty.assignment.databinding.ActivityMovieDetailScreenBinding
import com.tentwenty.assignment.model.ApiEvent
import com.tentwenty.assignment.model.movieDetail.MovieDetailResponse
import com.tentwenty.assignment.utils.DialogUtil
import com.tentwenty.assignment.utils.Utility
import com.tentwenty.assignment.view.adapter.ImagesAdapter
import com.tentwenty.assignment.view.videoPlayer.YoutubeActivity
import com.tentwenty.assignment.viewmodel.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.view.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


@AndroidEntryPoint
class MovieDetailScreenActivity : BaseActivity<ActivityMovieDetailScreenBinding>() {
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
        bindView(R.layout.activity_movie_detail_screen)

        intent.getStringExtra(MOVIE_ID)?.let {
            if (Utility.isOnline(this@MovieDetailScreenActivity)) {
                viewModel.getMovieImages(it)
            }
        }

        binding!!.movieCoverPager.adapter = imagesAdapter
        binding!!.dotsIndicator.setViewPager2(binding!!.movieCoverPager)
        binding!!.watchTrailer.setOnClickListener { watchTrailer(viewModel.trailerList[0].key!!) }
        lifecycleScope.launchWhenStarted {

            viewModel.imageLoader.collect { value: ApiEvent ->
                when (value) {
                    is ApiEvent.Success -> {
                        imagesAdapter.setImages(viewModel.imagesList)
                    }
                    is ApiEvent.Failure -> {
                        dialogUtils.hideDialog()
                        //viewModel.imagesList.add(Image(viewModel.movieDetails.backdrop_path))
                    }
                    else -> Unit
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.conversion.collect { value: ApiEvent ->
                when (value) {
                    is ApiEvent.Success -> {
                        dialogUtils.hideDialog()
                        updateUi(viewModel.movieDetails)
                    }
                    is ApiEvent.Failure -> {
                        dialogUtils.hideDialog()
                        no_data_found.setText(value.errorText)
                    }
                    is ApiEvent.Loading -> {
                        dialogUtils.showDialog()
                    }
                    else -> Unit
                }
            }
        }

        binding?.toolbarLayout?.rootView?.toolbar?.navigationIcon = ResourcesCompat.getDrawable(resources,R.drawable.ic_baseline_arrow_back_24,this.theme)
        binding?.toolbarLayout?.rootView?.toolbar?.setNavigationOnClickListener(View.OnClickListener {
            finish()
        })

    }

    fun updateUi(movieDetails: MovieDetailResponse) {

        binding?.toolbarLayout?.rootView?.toolbar?.title=movieDetails.original_title
        movieDetails.commaSeparatedGenre = movieDetails.genres.joinToString { it -> "${it.name}" }
        binding?.movieDetails = movieDetails

    }

    fun watchTrailer(videoId: String) {

        val intent = Intent(this, YoutubeActivity::class.java).apply {
            putExtra(YoutubeActivity.VIDEO_ID, videoId)
        }
        startActivity(intent)

    }

}