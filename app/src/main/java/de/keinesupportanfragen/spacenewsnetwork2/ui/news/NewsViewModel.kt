package de.keinesupportanfragen.spacenewsnetwork2.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.keinesupportanfragen.spacenewsnetwork2.network.Article

class NewsViewModel : ViewModel() {
    private val mutableArticles = MutableLiveData<List<Article>>()
    val cachedArticles: LiveData<List<Article>> get() = mutableArticles

    fun cacheArticles(data: List<Article>) {
        mutableArticles.value = data
    }
}