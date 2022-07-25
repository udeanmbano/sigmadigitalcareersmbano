package com.mbano.core.utils

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Util {

    fun formatToYesterdayOrToday(date: String?): String {
        val format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        val result = LocalDateTime.parse(date?.replace("T", " ")?.replace("Z", ""), format)
        val formatter = DateTimeFormatter.ofPattern("EEE hh:mma MMM d, yyyy")
        val output = formatter.format(result)
        val dateTime: DateTime =
            DateTimeFormat.forPattern("EEE hh:mma MMM d, yyyy").parseDateTime(output)
        val today = DateTime()
        val yesterday: DateTime = today.minusDays(1)
        val timeFormatter: org.joda.time.format.DateTimeFormatter? =
            DateTimeFormat.forPattern("hh:mma")
        val normalFormatter: org.joda.time.format.DateTimeFormatter? =
            DateTimeFormat.forPattern("dd.MM.YYYY")
        var dateRequired = ""
        if (timeFormatter != null) {
            if (normalFormatter != null) {
                dateRequired = if (dateTime.toLocalDate().equals(today.toLocalDate())) {
                    "Today , " + timeFormatter.print(dateTime)
                } else if (dateTime.toLocalDate().equals(yesterday.toLocalDate())) {
                    "Yesterday , " + timeFormatter.print(dateTime)
                } else {
                    normalFormatter.print(dateTime).toString()
                }
            }
        }
        return dateRequired
    }

    fun formatToTodayOrLater(date: String?): String {
        val format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        val result = LocalDateTime.parse(date?.replace("T", " ")?.replace("Z", ""), format)
        val formatter = DateTimeFormatter.ofPattern("EEE hh:mma MMM d, yyyy")
        val output = formatter.format(result)
        val dateTime: DateTime =
            DateTimeFormat.forPattern("EEE hh:mma MMM d, yyyy").parseDateTime(output)
        val today = DateTime()
        val tomorrow: DateTime = today.plusDays(1)
        val in_two_days: DateTime = today.plusDays(2)
        val in_three_days: DateTime = today.minusDays(3)

        val timeFormatter: org.joda.time.format.DateTimeFormatter? =
            DateTimeFormat.forPattern("hh:mma")
        val normalFormatter: org.joda.time.format.DateTimeFormatter? =
            DateTimeFormat.forPattern("dd.MM.YYYY")
        var dateRequired = ""
        if (timeFormatter != null) {
            if (normalFormatter != null) {
                dateRequired = if (dateTime.toLocalDate().equals(today.toLocalDate())) {
                    "Today , " + timeFormatter.print(dateTime)
                } else if (dateTime.toLocalDate().equals(tomorrow.toLocalDate())) {
                    "Tomorrow , " + timeFormatter.print(dateTime)
                } else if (dateTime.toLocalDate().equals(in_two_days.toLocalDate())) {
                    "In two days , " + timeFormatter.print(dateTime)
                } else if (dateTime.toLocalDate().equals(in_three_days.toLocalDate())) {
                    "In three days , " + timeFormatter.print(dateTime)
                } else {
                    normalFormatter.print(dateTime).toString()
                }
            }
        }
        return dateRequired
    }
}