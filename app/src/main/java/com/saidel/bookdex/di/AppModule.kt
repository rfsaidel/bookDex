package com.saidel.bookdex.di

import com.saidel.bookdex.model.remote.PokemonApi
import com.saidel.bookdex.repository.PokemonRepository
import com.saidel.bookdex.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePokemonRepositoty(
        api: PokemonApi
    ) = PokemonRepository(api)

    fun providePokemonApi() : PokemonApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(PokemonApi::class.java)
    }

}