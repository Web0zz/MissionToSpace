package com.web0zz.domain.usecase

import com.github.michaelbull.result.Result
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

abstract class UseCase<out Type, out Failure, in Params>(
    private val mainDispatcher: CoroutineDispatcher
) where Type : Any {

    abstract suspend fun run(params: Params): Flow<Result<Type, Failure>>

    @DelicateCoroutinesApi
    operator fun invoke(
        params: Params,
        scope: CoroutineScope = GlobalScope,
        onResult: (Flow<Result<Type, Failure>>) -> Unit = {}
    ) {
        scope.launch(mainDispatcher) {
            val deferred = async(Dispatchers.IO) {
                run(params)
            }
            onResult(deferred.await())
        }
    }

    class None
}