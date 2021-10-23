package com.web0zz.cache.converter

import com.web0zz.cache.base.BaseConverter
import com.web0zz.cache.model.LinksEntity

class LinksEntityConverter : BaseConverter<LinksEntityConverter>()

class PatchEntityConverter : BaseConverter<LinksEntity.Companion.PatchEntity>()
class RedditEntityConverter : BaseConverter<LinksEntity.Companion.RedditEntity>()
class FlickrEntityConverter : BaseConverter<LinksEntity.Companion.FlickrEntity>()