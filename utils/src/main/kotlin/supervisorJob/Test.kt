package supervisorJob

import kotlinx.coroutines.*
import java.util.concurrent.Executors

private val parentJob = SupervisorJob()
private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
    println("Exception caught")
}
private val dispatcher = Executors.newCachedThreadPool().asCoroutineDispatcher()
private val scope = CoroutineScope(parentJob + exceptionHandler + dispatcher)

fun main() {
    scope.launch {
        longOperation(3000, 1)
        error("")
    }
    scope.launch {
        longOperation(4000, 2)
    }
}

private suspend fun longOperation(timeMillis: Long, number: Int) {
    println("Started: $number")
    delay(timeMillis)
    println("Finished: $number")
}