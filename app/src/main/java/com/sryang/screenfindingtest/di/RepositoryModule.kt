package com.sarang.torang.di

import android.content.Context
import com.example.torang_core.data.AppDatabase
import com.example.torang_core.repository.LoginRepository
import com.example.torang_core.repository.MyReviewRepository
import com.example.torang_core.repository.MyReviewsRepository
import com.example.torangrepository.LoginRepositoryImpl
import com.example.torangrepository.MyReviewRepositoryImpl
import com.example.torangrepository.MyReviewsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    /** 로컬 데이터베이스 제공 */
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }
}


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideMyReviewRepository(myReviewRepositoryImpl: MyReviewRepositoryImpl): MyReviewRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class MyReviewsRepositoryProvider() {
    @Binds
    abstract fun provideMyReviewsRepository(myReviewsRepositoryImpl: MyReviewsRepositoryImpl): MyReviewsRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class LoginRepositoryModule {
    @Binds
    abstract fun provideLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository
}