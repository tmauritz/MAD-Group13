package com.example.mad_group13

import android.content.Context
import android.util.Log
import com.example.mad_group13.data.PetDB
import com.example.mad_group13.data.PetDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PetDB{
        Log.i("MAD_Hilt", "Providing Database")
        return PetDB.getDatabase(context)
    }

    @Provides
    fun provideQuestionDao(db: PetDB): PetDao{
        Log.i("MAD_Hilt", "Providing Dao")
        return db.petDao
    }

}