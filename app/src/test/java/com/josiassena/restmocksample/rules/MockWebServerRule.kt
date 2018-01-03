package com.josiassena.restmocksample.rules

import io.appflate.restmock.JVMFileParser
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.RESTMockServerStarter
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * Rule runs before and after each test. Required for [RESTMockServer] usage.
 *
 * Example usage:
 * At the top of your class add
 *
 * ```
 * class ExampleUnitTest {
 *      @Rule
 *      @JvmField
 *      var mockWebServerRule = MockWebServerRule()
 *
 *      // ...
 * }
 * ```
 *
 * @author Josias Sena
 */
class MockWebServerRule : TestRule {

    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {
                // Start the mock server before running a test
                RESTMockServerStarter.startSync(JVMFileParser())

                // Evaluate the current running test
                base?.evaluate()

                // After the test reset the mock server
                RESTMockServer.reset()
            }
        }
    }
}