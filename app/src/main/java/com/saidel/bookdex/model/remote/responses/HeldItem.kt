package com.saidel.bookdex.model.remote.responses

data class HeldItem(
    val item: Item,
    val version_details: List<VersionDetail>
)