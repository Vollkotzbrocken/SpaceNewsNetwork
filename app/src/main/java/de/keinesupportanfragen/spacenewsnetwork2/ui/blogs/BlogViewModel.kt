package de.keinesupportanfragen.spacenewsnetwork2.ui.blogs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.keinesupportanfragen.spacenewsnetwork2.network.Article

class BlogViewModel : ViewModel() {
    private val mutableBlogs = MutableLiveData<List<Article>>()
    val cachedBlogs: LiveData<List<Article>> get() = mutableBlogs

    fun cacheBlogs(data: List<Article>) {
        mutableBlogs.value = data
    }
}