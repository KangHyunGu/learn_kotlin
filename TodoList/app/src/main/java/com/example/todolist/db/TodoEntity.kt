package com.example.todolist.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Annotation(어노테이션이란?)
//  - 자바(코틀린)코드에 추가할 수 있는 메타데이터의 한 형태
//  - 보일러 플레이트 코드를 줄여 간결한 코드 작성이 가능하다.
//  - Room, Retrofit 라이브러리에서 자주 사용된다.

// RoomDatabase Entity 구조
@Entity
data class TodoEntity (
    @PrimaryKey(autoGenerate = true) var id : Int? = null,
    @ColumnInfo(name="title") var title : String,
    @ColumnInfo(name="importance") var importance : Int
)