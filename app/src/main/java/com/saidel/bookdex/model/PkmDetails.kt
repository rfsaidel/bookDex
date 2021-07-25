package com.saidel.bookdex.model

class PkmDetails(
        var number: String,
        var name: String,
        var image_url: String = "",
        var type: List<String> = emptyList(),
        var weight: String = "",
        var height: String = ""
)