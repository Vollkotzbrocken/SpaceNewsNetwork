package de.keinesupportanfragen.spacenewsnetwork2.ui.news

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import de.keinesupportanfragen.spacenewsnetwork2.R
import de.keinesupportanfragen.spacenewsnetwork2.databinding.FragmentNewsEntryBinding
import de.keinesupportanfragen.spacenewsnetwork2.network.Article
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MyNewsEntryRecyclerViewAdapter(
    private var values: List<Article>, private var fragment: Fragment
) : RecyclerView.Adapter<MyNewsEntryRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentNewsEntryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dateFormat = SimpleDateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.getDefault())

        val item = values[position]
        holder.titleText.text = item.title
        holder.summaryText.text = item.summary
        holder.moreInfoText.text = fragment.getString(R.string.more_info_placeholder).format(item.newsSite, dateFormat.format(item.publishedAt))

        holder.card.setOnClickListener {
            val builder = CustomTabsIntent.Builder()
            val intent = builder.build()
            intent.launchUrl(fragment.requireContext(), Uri.parse(item.url))
        }

        holder.shareButton.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            val shareBody = fragment.getString(R.string.shared_article_text).format(item.title, item.summary, item.url)

            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, fragment.getString(R.string.shared_article_subject))
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)

            fragment.startActivity(Intent.createChooser(shareIntent, fragment.getString(R.string.shared_article_subject)))
        }

        Glide.with(fragment).load(item.imageUrl).into(holder.imageView)
    }

    override fun getItemCount(): Int = values.size

    fun updateData(data: List<Article>) {
        values = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(binding: FragmentNewsEntryBinding) : RecyclerView.ViewHolder(binding.root) {

        val card: MaterialCardView = binding.root

        val titleText: TextView = binding.newsArticleTitle
        val summaryText: TextView = binding.newsArticlePreview
        val imageView: ImageView = binding.imageView
        val moreInfoText: TextView = binding.newsArticleMoreInfo

        val shareButton: Button = binding.shareNewsButton

        override fun toString(): String {
            return super.toString() + " '" + summaryText.text + "'"
        }
    }

}