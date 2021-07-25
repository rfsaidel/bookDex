package com.saidel.bookdex.utils

class Utils {
    companion object {
        fun generateImageURL(number: String): String {
            return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$number.png"
        }
    }
}