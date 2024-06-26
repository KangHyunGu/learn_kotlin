package com.example.todolist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = arrayOf(TodoEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getTodoDao() : TodoDao


    companion object {
        val databaseName = "db_todo" // 데이터베이스 이름입니다.
        var appDatabase : AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase? {
            if(appDatabase == null){

                // fallbackToDestructiveMigration()
                // 마이그레이션을 실패하는 경우 db 테이블을 재생성할때 쓰인다.
                // 이때 영구적으로 모든 데이터가 사라질 수 있으니 잘 판단해서 쓸 것
                appDatabase = Room.databaseBuilder(context,
                    AppDatabase::class.java,
                    databaseName)
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return appDatabase
        }
    }
}