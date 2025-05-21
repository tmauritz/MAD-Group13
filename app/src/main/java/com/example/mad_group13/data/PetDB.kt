package com.example.mad_group13.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Pet::class],
    version = 2,
    exportSchema = false
)
abstract class PetDB: RoomDatabase(){
    abstract val petDao: PetDao

    // declare as singleton - companion objects are like static variables in Java
    companion object {
        @Volatile // never cache the value of instance
        private var instance: PetDB? = null
        fun getDatabase(context: Context): PetDB {
            return instance
                ?: synchronized(this) { // wrap in synchronized block to prevent race conditions
                    Room.databaseBuilder(context, PetDB::class.java, "MAD_DB")
                        .fallbackToDestructiveMigration() // if schema changes wipe the whole db - TODO: there are better migration strategies for production usage
                        .build() // create an instance of the db
                        .also {
                            instance = it // override the instance with newly created db
                        }
                }
        }
    }

}