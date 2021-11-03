package com.tentwenty.assignment.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tentwenty.assignment.R
import com.tentwenty.assignment.databinding.ActivityMainBinding
import com.tentwenty.assignment.model.ApiEvent
import com.tentwenty.assignment.model.Movie
import com.tentwenty.assignment.utils.DialogUtil
import com.tentwenty.assignment.utils.Utility
import com.tentwenty.assignment.view.MovieDetailScreenActivity.Companion.MOVIE_ID
import com.tentwenty.assignment.view.adapter.MoviesAdapter
import com.tentwenty.assignment.view.videoPlayer.YoutubeActivity
import com.tentwenty.assignment.view.videoPlayer.YoutubeActivity.Companion.VIDEO_ID
import com.tentwenty.assignment.viewmodel.MoviesListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(), MoviesAdapter.MovieItemClickListener {

    private var loading: Boolean = false
    @Inject
    lateinit var dialogUtils: DialogUtil
    private val  viewModel : MoviesListViewModel by viewModels()

    @Inject
    lateinit var mMoviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView(R.layout.activity_main)

        viewModel.getMoviesList(Utility.isOnline(this@MainActivity))


        with(binding!!.recylerView){
            itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
            setHasFixedSize(true)
            adapter = mMoviesAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val totalItemCount = linearLayoutManager.itemCount
                    val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                    if (!loading
                        && totalItemCount <= (lastVisibleItem + 5)) {
                        loading = true
                        viewModel.getMoviesList(Utility.isOnline(this@MainActivity))
                    }

                }
            })
        }

        mMoviesAdapter.setOnClickListener(this)

        lifecycleScope.launchWhenStarted {
            viewModel.conversion.collect { value: ApiEvent ->
                when(value){
                    is ApiEvent.Success ->{
                        dialogUtils.hideDialog()
                        loading = false
                        mMoviesAdapter.setData(viewModel.moviesList)
                        binding!!.noDataFound.visibility = View.GONE
                        binding!!.recylerView.visibility = View.VISIBLE
                    }
                    is ApiEvent.Failure -> {
                        loading = false
                        dialogUtils.hideDialog()
                        binding!!.noDataFound.setText(value.errorText)
                        binding!!.noDataFound.visibility = View.VISIBLE
                        binding!!.recylerView.visibility = View.GONE
                    }
                    is ApiEvent.Loading -> {
                        if(!loading) {
                            dialogUtils.showDialog()
                        }
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onBookingClicked(movie: Movie) {
        val intent = Intent(this, BookMovieScreenActivity::class.java).apply {
            putExtra(MOVIE_ID, movie.id)
        }
        startActivity(intent)
    }
    override fun onMovieItemClicked(movie: Movie) {
        val intent = Intent(this, MovieDetailScreenActivity::class.java).apply {
            putExtra(MOVIE_ID, movie.id)
        }
        startActivity(intent)
    }
}