package br.com.andreyneto.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.andreyneto.room.dao.WordDao
import br.com.andreyneto.room.entities.Word

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordRoomDatabase : RoomDatabase() {
    abstract fun wordDao() : WordDao

    companion object {
        private var instance: WordRoomDatabase? = null

        fun getDatabase(context: Context) : WordRoomDatabase {
            if (instance == null) {
                synchronized(WordRoomDatabase::class.java) {
                    // Criação do DB
                    instance = Room
                        .databaseBuilder(context, WordRoomDatabase::class.java, "word_database")
                        .build()
                }
            }
            return instance!!
        }
    }
}