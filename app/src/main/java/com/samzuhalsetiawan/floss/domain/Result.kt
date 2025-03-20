package com.samzuhalsetiawan.floss.domain

sealed interface Result<out D, out E: Error> {
   data class Success<D>(val data: D): Result<D, Nothing>
   data class Failed<E: Error>(val error: E): Result<Nothing, E>
}
