package com.personal.paginglibrary.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.personal.paginglibrary.R
import com.personal.paginglibrary.databinding.FragmentSearchBookBinding
import com.personal.paginglibrary.model.Article
import com.personal.paginglibrary.viewmodel.NewsViewModel
import com.personal.paginglibrary.viewmodel.NewsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class SearchRepositoriesFragment : Fragment() {

    //val newsViewModel : NewsViewModel

    lateinit var viewModel: NewsViewModel

    @Inject
    lateinit var viewModelFactory: NewsViewModelFactory

    @Inject
    @ApplicationContext
    lateinit var appContext: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSearchBookBinding.inflate(inflater, container, false)

        setupViewModel()
        setupList(binding.list, mutableListOf())

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.newsListLiveData.collect {
                    val listArticle = it.getOrNull()
                    if (it.isSuccess && listArticle != null) {
                        setupList(binding.list, listArticle)
                    } else {
                        Toast.makeText(
                            appContext,
                            it.exceptionOrNull()?.message + "Error",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
            viewModel.loader.observe(viewLifecycleOwner, {
                when (it) {
                    true -> binding.loader.visibility = View.VISIBLE
                    else -> binding.loader.visibility = View.GONE
                }
            })

        }

        return binding.root

    }

    private fun setupList(
        view: View?,
        listArticle: List<Article>
    ) {
        with(view as RecyclerView) {
            layoutManager = LinearLayoutManager(context)
            // add dividers between RecyclerView's row items
            val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            this.addItemDecoration(decoration)
            val adapterList = ArticleAdapter()
            adapter = adapterList
            adapterList.submitList(listArticle)
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]
    }


}
