package com.tentwenty.assignment.model

/**
 *Created by Mustanser Iqbal
 */
sealed class Resource<T>(val data:T? , val message:String?) {
    class Error<T>(message: String?):Resource<T>(null ,message){}
    class Success<T>(data: T):Resource<T>(data,null){}
}