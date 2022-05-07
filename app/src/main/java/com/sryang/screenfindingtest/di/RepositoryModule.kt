package com.sarang.torang.di

import com.example.torang_core.repository.*
import com.example.torangrepository.*
import com.sryang.screenfindingtest.TestFilterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MyReviewsRepositoryProvider() {
    @Binds
    abstract fun provideMyReviewsRepository(myReviewsRepositoryImpl: MyReviewsRepositoryImpl): MyReviewsRepository

    @Binds
    abstract fun provideLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

    @Binds
    abstract fun provideMyReviewRepository(myReviewRepositoryImpl: MyReviewRepositoryImpl): MyReviewRepository

    @Binds
    abstract fun provideNationRepository(nationRepositoryImpl: NationRepositoryImpl): NationRepository

    @Binds
    abstract fun provideFindRepository(findRepository: FindRepositoryImpl): FindRepository

    @Binds
    abstract fun provideFilterRepository(filterRepository: FilterRepositoryImpl): FilterRepository
}