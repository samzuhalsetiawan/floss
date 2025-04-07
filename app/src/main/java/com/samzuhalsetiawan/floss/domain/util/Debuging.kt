package com.samzuhalsetiawan.floss.domain.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

private const val TAG = "DEBUG"

@Suppress("unused")
fun <T> Flow<T>.printlnOnEach(logMessage: (value: T) -> String): Flow<T> = this.onEach {
   println("[$TAG] ${logMessage(it)}")
}