package com.personal.paginglibrary.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
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
import javax.inject.Inject

@AndroidEntryPoint
class SearchRepositoriesFragment : Fragment() {

    //val newsViewModel : NewsViewModel

    lateinit var viewModel: NewsViewModel

    @Inject
    lateinit var viewModelFactory: NewsViewModelFactory

    @Inject
    @ApplicationContext lateinit var appContext: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentSearchBookBinding.inflate(inflater, container, false)

        setupViewModel()
        setupList(binding.list, mutableListOf())

        viewModel.loader.observe(viewLifecycleOwner, { loading ->
            when(loading) {
                true -> binding.loader.visibility = View.VISIBLE
                else -> binding.loader.visibility = View.GONE
            }
        })

        setupList(binding.list, mutableListOf())

        viewModel.newsListLiveData.observe(viewLifecycleOwner, { newLiveData ->
            if(newLiveData !=null) {
                setupList(binding.list, newLiveData)
                Toast.makeText(appContext, "Success", Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(appContext,"Error", Toast.LENGTH_LONG).show()
            }
        })

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
            adapter=  adapterList
            adapterList.submitList(listArticle)
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]
    }


}
