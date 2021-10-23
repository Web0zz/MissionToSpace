package com.web0zz.repository.mapper

interface Mapper<I, O> {
    fun map(input: I) : O
}