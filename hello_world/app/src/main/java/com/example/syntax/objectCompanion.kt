package com.example.syntax

fun main(args: Array<String>){
//    println(MyFirstObject.number)
//    MyFirstObject.sayHello()

    // 접근이 가능한 이유는 Dinner class 내 companion object 내 선언되어 있어 가능 한 것이다.
    // 즉) java static과 유사하다고 보면 된다.
    println(Dinner.MENU)
    Dinner.eatDinner()

    // 단) Dinner.lunch 같은 경우엔 클래스가 생성되지 않았기 때문이다 접근이 불가능하다.
}

// object인경우 instance 생성시 전체 프로그램에서 하나만 만들어 질 것이다.
// 그래서 객체 생성 없이 바로 쓸 수 있다.

// singleton 패턴과 유사하다
object MyFirstObject {
    var number = 1
    fun sayHello(){
        println("Hello!")
    }
}

class Dinner {
    val lunch = "steak"

    //companion object 클래스 하나에 하나만 정의 할 수 있다.
    companion object {
        val MENU = "pasta"
        fun eatDinner() {
           println("Today's Menu is : $MENU")
        }
    }
}