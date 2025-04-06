package com.samzuhalsetiawan.floss.domain

sealed interface Result<out D, out E: Error> {
   data class Success<out D>(val data: D): Result<D, Nothing>
   data class Failed<out E: Error>(val error: E): Result<Nothing, E>
}
