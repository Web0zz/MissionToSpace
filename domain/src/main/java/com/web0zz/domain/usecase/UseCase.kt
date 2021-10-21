package com.web0zz.domain.usecase

import com.github.michaelbull.result.Result
import kotlinx.coroutines.*

abstract class UseCase<out Type, out Failure, in Params> where Type : Any {

    abstract suspend fun run(params: Params) : Result<Failure, Type>

    @DelicateCoroutinesApi
    operator fun invoke(
        params: Params,
        scope: CoroutineScope = GlobalScope,
        onResult: (Result<Failure, Type>) -> Unit = {}
    ) {
        scope.launch(Dispatchers.Main) {
            val deferred = async(Dispatchers.IO) {
                run(params)
            }
            onResult(deferred.await())
        }
    }

    class None
}