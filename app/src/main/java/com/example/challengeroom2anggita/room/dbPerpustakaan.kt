package com.example.challengeroom2anggita.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.locks.Lock

@Database(entities = [tbbuku::class], version = 1)
abstract class dbPerpustakaan : RoomDatabase() {

    abstract fun tbbookDao() :tbBukuDAO

    companion object {

        @Volatile private var instance: dbPerpustakaan? = null
        private val Lock = Any()

        operator fun invoke(context: Context) = instance?: synchronized(Lock){
            instance?: buildDatabase(context).also{
                instance = it

            }
        }
        private fun buildDatabase(context: Context)= Room.databaseBuilder(
            context.applicationContext,
            dbPerpustakaan::class.java,
            "perpus.db"
        ).build()
    }
}