package de.keinesupportanfragen.spacenewsnetwork2.ui.blogs

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import de.keinesupportanfragen.spacenewsnetwork2.R
import de.keinesupportanfragen.spacenewsnetwork2.network.Article
import de.keinesupportanfragen.spacenewsnetwork2.network.BlogService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BlogEntryFragment : Fragment() {

    private var articles: List<Article> = listOf()
    private var columnCount = 1

    private lateinit var mView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private var sharedPreferences: SharedPreferences? = null

    private val blogViewModel: BlogViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        loadCachedContentOrCallAPI()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_blog_entry_list, container, false)
        swipeRefreshLayout = view as SwipeRefreshLayout

        sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)
        columnCount = sharedPreferences?.getInt(getString(R.string.preferences_key_layoutmgr), 1) ?: 1

        mView = view.findViewById(R.id.list) as RecyclerView
        // Set the adapter
        with(mView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL)
            }
            adapter = MyBlogEntryRecyclerViewAdapter(listOf(), this@BlogEntryFragment)
        }

        swipeRefreshLayout.setOnRefreshListener { fetchArticles() }

        loadCachedContentOrCallAPI()

        return view
    }

    private fun fetchArticles() {
        val fallbackArticle = Article(title = getString(R.string.error_news_title))

        swipeRefreshLayout.isRefreshing = true

        val rf = Retrofit.Builder()
            .baseUrl("https://api.spaceflightnewsapi.net")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = rf.create(BlogService::class.java)

        val callback: Callback<List<Article>> = object : Callback<List<Article>> {
            override fun onResponse(call: Call<List<Article>>, response: Response<List<Article>>) {
                updateRecyclerView(response.body() ?: listOf(fallbackArticle))
            }

            override fun onFailure(call: Call<List<Article>>, t: Throwable) {
                Log.e("Fetching: no data", t.toString())
                Snackbar.make(swipeRefreshLayout, R.string.error_snackbar_title, Snackbar.LENGTH_LONG).show()

                updateRecyclerView(listOf(fallbackArticle))
            }
        }
        service.getAllBlogItems().enqueue(callback)
    }

    private fun updateRecyclerView(data: List<Article>) {
        articles = data
        blogViewModel.cacheBlogs(data)

        val adapter = mView.adapter as MyBlogEntryRecyclerViewAdapter
        adapter.updateData(data)

        swipeRefreshLayout.isRefreshing = false
    }

    private fun loadCachedContentOrCallAPI() {
        if (blogViewModel.cachedBlogs.value != null)
            updateRecyclerView(blogViewModel.cachedBlogs.value!!)
        else fetchArticles()
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            BlogEntryFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}