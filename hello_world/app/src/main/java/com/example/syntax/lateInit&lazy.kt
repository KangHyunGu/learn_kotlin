package com.example.syntax

fun main(args: Array<String>){

    val name = "Kang"

    // lateinit -> var 나중에 초기화 하고 싶은 변수가 있을때 사용
    // 주의 점
    // 1. lateinit var 변수 앞에서만 사용 할 수 있다.
    // 2. Nullable 타입과 함께 사용 할 수 없다.
    // 3. 실행하기 전(변수 사용하기전)에 반드시 초기화는 해줘야 한다.
    // 4. 원시가 아닌(String, Array, Class) 자료형만 사용 할 수 있다.
    // 5. 전역 변수에서 사용시에만 허용

    lateinit var lunch : String
    lunch = "waffle"

    println(lunch)

    // lazy -> val
    val lazrBear: String by lazy {
        println("Bear is comming!")
        println("Bear is Stop!")
        lunch = "test"
        println(lunch)
        "bear"
    }

    println("First try : : :")
    println(lazrBear)
    println("Second try : : :")
    println(lazrBear)
    println("third try : : :")
    println(lazrBear)

}