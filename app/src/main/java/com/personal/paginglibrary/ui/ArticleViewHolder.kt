package com.personal.paginglibrary.ui

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.personal.paginglibrary.R
import com.personal.paginglibrary.model.Article

/**
 * View Holder for a [Repo] RecyclerView list item.
 */
class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.repo_name)

    private var article: Article? = null

    fun bind(repo: Article?) {
        if (repo == null) {
            val resources = itemView.resources
            name.text = resources.getString(R.string.loading)
        } else {
            showRepoData(repo)
        }
    }

    private fun showRepoData(article: Article) {
        this.article = article
        name.text = article.title

    }

    companion object {
        fun create(parent: ViewGroup): ArticleViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.book_view_item, parent, false)
            return ArticleViewHolder(view)
        }
    }
}
