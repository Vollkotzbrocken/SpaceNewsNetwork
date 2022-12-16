package de.keinesupportanfragen.spacenewsnetwork2.network

import java.util.Calendar
import java.util.Date

data class Article(
    var title: String = "title",
    var url: String = "VeryError",
    var imageUrl: String = "",
    var newsSite: String = "",
    var summary: String? = "",
    var publishedAt: Date = Calendar.getInstance().time
)