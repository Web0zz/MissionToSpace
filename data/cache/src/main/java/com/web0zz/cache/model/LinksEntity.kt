package com.web0zz.cache.model

data class LinksEntity(
    val patch: PatchEntity,
    val reddit: RedditEntity,
    val flickr: FlickrEntity,
    val presskit: String,
    val webcast: String,
    val youtubeId: String,
    val article: String,
    val wikipedia: String
) {
    companion object {

        data class PatchEntity(
            val small: String,
            val large: String
        )

        data class RedditEntity(
            val campaign: String,
            val launch: String,
            val media: String,
            val recovery: String
        )

        data class FlickrEntity(
            val small: List<String>,
            val original: List<String>
        )
    }
}
