package de.keinesupportanfragen.spacenewsnetwork2

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import de.keinesupportanfragen.spacenewsnetwork2.databinding.ActivityMainBinding
import de.keinesupportanfragen.spacenewsnetwork2.network.Article

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var articleCache: List<Article>? = null
        get() = field ?: listOf()

    fun hasCachedArticles() : Boolean = (articleCache?.size ?: 0) > 0


    var blogCache: List<Article>? = null
        get() = field ?: listOf()

    fun hasCachedBlogs() : Boolean = (blogCache?.size ?: 0) > 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_start, R.id.navigation_blogs, R.id.navigation_notifications, R.id.navigation_options
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}