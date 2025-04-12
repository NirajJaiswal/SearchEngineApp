package com.example.niraj.searchengineapp

import com.example.niraj.searchengineapp.presentation.util.toTimeDateString
import org.junit.Assert.assertEquals
import org.junit.Test

class UnitTest {
    @Test
    fun `toTimeDateString formats Unix timestamp correctly`() {
        val timestamp: Long = 1744401672
        val expectedFormattedDate = "12/04/2025"
        val actualFormattedDate = timestamp.toTimeDateString()
        assertEquals(expectedFormattedDate, actualFormattedDate)
    }

    @Test
    fun `toTimeDateString handles zero timestamp`() {
        val timestamp: Long = 0
        val expectedFormattedDate = "01/01/1970"
        val actualFormattedDate = timestamp.toTimeDateString()
        assertEquals(expectedFormattedDate, actualFormattedDate)
    }
}