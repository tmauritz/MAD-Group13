package com.example.mad_group13

import android.content.Context
import android.util.Log
import com.example.mad_group13.data.PetDB
import com.example.mad_group13.data.PetDao
import com.example.mad_group13.data.PetRepository
import com.example.mad_group13.data.TeslaStockDao
import com.example.mad_group13.data.TeslaStockRepository
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

    @Provides
    fun provideTeslaStockDao(db: PetDB): TeslaStockDao {
        Log.i("MAD_Hilt", "Providing TeslaStockDao")
        return db.teslaStockDao
    }

    @Provides
    @Singleton
    fun providePetRepository(petDao: PetDao): PetRepository {
        return PetRepository(petDao)
    }

    @Provides
    @Singleton
    fun provideTeslaStockRepository(
        teslaStockDao: TeslaStockDao,
        petRepository: PetRepository
    ): TeslaStockRepository {
        return TeslaStockRepository(teslaStockDao, petRepository)
    }
}