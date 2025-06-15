package com.hmyh.moviecompose.ui.tutorial

fun main(){
    RepositoryS.startFetch()
    getResult(result = RepositoryS.getCurrentState())
    RepositoryS.finishedFetch()
    getResult(result = RepositoryS.getCurrentState())
    RepositoryS.error()
    getResult(result = RepositoryS.getCurrentState())
}

//step 3
object RepositoryS {
    private var loadState: ResultS = NotLoading
    private var fetchData: String? = null

    fun startFetch() {
        loadState = Loading
        fetchData = "data"
    }

    fun finishedFetch() {
        loadState = Success(data = fetchData)
        fetchData = null
    }

    fun error() {
        loadState = Error(error = Exception("Exception"))
    }

    fun getCurrentState(): ResultS {
        return loadState
    }

}

//step 2
fun getResult(result: ResultS) {
    return when (result) {
        is Success -> {
            println(result.data.toString() ?: "Data fetch")
        }
        is Error -> {
            println(result.error.toString())
        }
        is Loading-> {
            println("Loading...")
        }
        is NotLoading -> {
            println("Idle")
        }
    }
}

//step 1
sealed class ResultS

data class Success(val data: String?): ResultS()
data class Error(val error: Exception): ResultS()
object NotLoading: ResultS()
object Loading: ResultS()