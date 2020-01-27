package kei.su.sales.network

import java.lang.Exception

/**
 * Created by  on 4/13/2019.
 */
sealed class Output<out T : Any>{
    data class Success<out T : Any>(val output : T) : Output<T>()
    data class Error(val exception: Exception)  : Output<Nothing>()
}