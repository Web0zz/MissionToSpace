package com.web0zz.network.model

import com.google.gson.annotations.SerializedName

data class LinksDto(
    @SerializedName("patch") val patch: PatchDto?,
    @SerializedName("reddit") val reddit: RedditDto?,
    @SerializedName("flickr") val flickr: FlickrDto?,
    @SerializedName("presskit") val presskit: String?,
    @SerializedName("webcast") val webcast: String?,
    @SerializedName("youtube_id") val youtubeId: String?,
    @SerializedName("article") val article: String?,
    @SerializedName("wikipedia") val wikipedia: String?
) {
    companion object {

        data class PatchDto(
            @SerializedName("small") val small: String?,
            @SerializedName("large") val large: String?
        )

        data class RedditDto(
            @SerializedName("campaign") val campaign: String?,
            @SerializedName("launch") val launch: String?,
            @SerializedName("media") val media: String?,
            @SerializedName("recovery") val recovery: String?
        )

        data class FlickrDto(
            @SerializedName("small") val small: List<String>?,
            @SerializedName("original") val original: List<String>?
        )
    }
}
