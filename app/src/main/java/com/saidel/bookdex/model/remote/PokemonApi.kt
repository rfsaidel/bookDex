package com.saidel.bookdex.model.remote

import com.saidel.bookdex.model.remote.responses.PokemonList

interface PokemonApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonList

    @GET("pokemon/{name}") {
        @Path("name") name: String
    }: Pokemon

}