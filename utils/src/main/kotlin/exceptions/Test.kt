package exceptions

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
    println("Exception caught")
}
private val dispatcher = Executors.newCachedThreadPool().asCoroutineDispatcher()
private val scope = CoroutineScope(dispatcher + CoroutineName("My coroutine") + Job() + exceptionHandler)

fun main() {
    scope.launch {
        method()
    }
    scope.launch {
        method2()
    }
}

suspend fun method() {
    Thread.sleep(3000)
    error("")
}

suspend fun method2() {
    delay(5000)
    error("Method2 is finished")
}