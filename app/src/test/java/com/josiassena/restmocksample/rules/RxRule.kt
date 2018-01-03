package com.josiassena.restmocksample.rules

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * Runs before a test class, and after the last test in the test class. Required for RxJava usage
 * during tests.
 *
 * Example usage: At the top of your class (in Java), if in Kotlin inside a companion object
 *
 * ```
 * class ExampleUnitTest {
 *
 *  companion object {
 *
 *      @ClassRule
 *      @JvmField
 *      var rxRule = RxRule()
 *
 *  }
 *
 *  // ...
 *
 * }
 * ```
 * @author Josias Sena
 */
class RxRule : TestRule {

    val testScheduler = TestScheduler()

    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {
                // During a test use the Schedulers.trampoline() instead of the AndroidSchedulers.mainThread()
                // The reason for this is that AndroidSchedulers.mainThread() is not allowed during tests
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

                // During any computation use a test scheduler instead. Prevents errors
                // thrown when using schedulers like toe Scheduler.io() scheduler
                RxJavaPlugins.setComputationSchedulerHandler { _ -> testScheduler }

                try {
                    // Run/evaluate the current test
                    base?.evaluate()
                } finally {
                    // After the test, reset all schedulers.
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }
}