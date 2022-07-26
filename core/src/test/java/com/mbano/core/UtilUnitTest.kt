package com.mbano.core

import com.mbano.core.utils.Util
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UtilUnitTest {
    var utilTest: Util = Util()

    @Test
    fun `test for today`() {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val currentDate: String = sdf.format(Date())
        val testDate = "${currentDate}T06:31:05.867Z"
        val time = "06:31AM"
        assertEquals("Today , $time", utilTest.formatToYesterdayOrToday(testDate))
    }

    @Test
    fun `test for yesterday`() {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -1);
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val currentDate: String = sdf.format(cal.time)
        val testDate = "${currentDate}T06:31:05.867Z"
        val time = "06:31AM"
        assertEquals("Yesterday , $time", utilTest.formatToYesterdayOrToday(testDate))
    }

    @Test
    fun `test for in one day`() {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, 1);
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val currentDate: String = sdf.format(cal.time)
        val testDate = "${currentDate}T06:31:05.867Z"
        val time = "06:31AM"
        assertEquals("Tomorrow , $time", utilTest.formatToTodayOrLater(testDate))
    }

    @Test
    fun `test for in two days`() {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, 2);
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val currentDate: String = sdf.format(cal.time)
        val testDate = "${currentDate}T06:31:05.867Z"
        val time = "06:31AM"
        assertEquals("In two days , $time", utilTest.formatToTodayOrLater(testDate))
    }

    @Test
    fun `test for in three days`() {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, 3);
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val currentDate: String = sdf.format(cal.time)
        val testDate = "${currentDate}T06:31:05.867Z"
        val time = "06:31AM"
        assertEquals("In three days , $time", utilTest.formatToTodayOrLater(testDate))
    }
}