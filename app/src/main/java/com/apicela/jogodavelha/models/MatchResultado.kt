package com.apicela.jogodavelha.models

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class MatchResultado(
    val playerOne: String,
    val opponent: String,
    private val _date: LocalDateTime,
    val resultado: Int
) {
    val date: String
        get() {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
            return _date.format(formatter)
        }
}


