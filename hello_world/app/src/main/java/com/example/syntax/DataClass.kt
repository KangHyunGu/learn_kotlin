package com.example.syntax

fun main(args: Array<String>){
    val memo = Memo("Go to grocery", "Eggs, Milk, Pork", false)

    println(memo.toString())
    println(memo.title)

    // data class 복사
    val memo2 = memo.copy("Go to home");
    println(memo2.toString())
}

// 데이터만 단순히 저장하고, 불러오는 용도만 사용했을 경우 data Class를 사용하는것이 용이하다.
data class Memo(val title: String, val content : String, var isDone : Boolean)