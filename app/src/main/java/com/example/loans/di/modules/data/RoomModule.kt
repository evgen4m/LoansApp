package com.example.loans.di.modules.data

import android.content.Context
import androidx.room.Room
import com.example.loans.data.dataSource.local.cache.dataBase.AppDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideDao(appDataBase: AppDataBase) = appDataBase.itemsListDao()

    @Singleton
    @Provides
    fun provideRoomDataBase(context: Context) =
        Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "dataBase"
        ).fallbackToDestructiveMigration()
            .build()

}