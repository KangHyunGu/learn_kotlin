package com.example.syntax

fun main(args: Array<String>) {
    val overwatch = Overwatch()

    overwatch.startGame()
    overwatch.printGameName()
}


abstract class Game {
    fun startGame(){
        println("Start Game")
    }

    // 추상 클래스 내 추상 메서드는 메서드만 정의 할 수 있고
    // 그 메서드는 상속 받은 자식 클래스에서 반드시 구현해야 한다.
    abstract fun printGameName()
}

class Overwatch : Game() {
    override fun printGameName() {
        println("this is Overwatch")
    }

}