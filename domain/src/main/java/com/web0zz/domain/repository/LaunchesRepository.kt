package com.web0zz.domain.repository

import com.github.michaelbull.result.Result
import com.web0zz.domain.model.Failure
import com.web0zz.domain.model.Launches

interface LaunchesRepository {
    fun getLaunchesData(): Result<Failure, List<Launches>>
    fun getLaunchesById(): Result<Failure, Launches>
}