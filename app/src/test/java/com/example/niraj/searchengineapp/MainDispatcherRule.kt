package com.example.niraj.searchengineapp

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class InstantExecutorRule : TestRule {
    val executor = object : TaskExecutor() {
        override fun executeOnDiskIO(runnable: Runnable) {
            runnable.run()
        }

        override fun postToMainThread(runnable: Runnable) {
            runnable.run()
        }

        override fun isMainThread(): Boolean {
            return true
        }
    }

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                ArchTaskExecutor.getInstance().setDelegate(executor)

                try {
                    base.evaluate()
                } finally {
                    ArchTaskExecutor.getInstance().setDelegate(null)
                }
            }
        }
    }
}