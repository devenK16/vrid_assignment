package com.example.vrid_assignment.di

import com.example.vrid_assignment.data.remote.BlogApi
import com.example.vrid_assignment.data.repository.BlogRepository
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

    @Provides
    @Singleton
    fun provideBlogApi(): BlogApi {
        return Retrofit.Builder()
            .baseUrl("https://blog.vrid.in/wp-json/wp/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BlogApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBlogRepository(api: BlogApi): BlogRepository {
        return BlogRepository(api)
    }
}