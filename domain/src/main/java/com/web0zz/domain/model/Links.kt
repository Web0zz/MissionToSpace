package com.web0zz.domain.model

data class Links(
    val patch: Patch,
    val reddit: Reddit,
    val flickr: Flickr,
    val presskit: String,
    val webcast: String,
    val youtubeId: String,
    val article: String,
    val wikipedia: String
) {
    companion object {

        data class Patch(
            val small: String,
            val large: String
        )

        data class Reddit(
            val campaign: String,
            val launch: String,
            val media: String,
            val recovery: String
        )

        data class Flickr(
            val small: List<String>,
            val original: List<String>
        )
    }
}
