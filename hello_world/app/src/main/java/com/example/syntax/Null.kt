package com.example.syntax

fun main(args: Array<String>) {

    //Nullable vs Non-Nullable
    //Nullable : Null이 될 수 있는 타입
    //Non-Nullable : Null이 될 수 없는 타입(코틀린에서는 default 이다)
    var myName: String = "HyunGu"
    println(myName.reversed())

    // Nullable ?
    var nullableMyName : String ? = null
    //println(nullableMyName.reversed())

    // 해당 nullableMyName 변수는 null 나올지 변수에 있는 존재하는 값이 나올진 프로그램 입장에선 알 수 없기 때문에
    // 에러 발생을 미리 방지 할 수 있다
    // 만약 Nullable을 허용한다면 해당 변수에 메서드를 호출 할 때 safe call 연산자를 사용하여 에러를 방지한다.
    // safe call 연산자는 변수명? 이다.
    println(nullableMyName?.reversed())

    // ?: 엘비스 연산자
    // 만약 해당 호출하는 함수나 값에 null이 나왔을경우에 "Anonymous"값으로 대체
    val joyce = nullableMyName?.reversed() ?: "Anonymous"
    println("joyce : $joyce")

    // !!: 확정 연산자
    // 확정 연산자는 해당 변수에 null이 아니라고 확신 할 때 사용한다.
    // 그래서 Nullable로 선언했을때 safe call 연산자가 없이도 함수를 호출하여 사용 할 수 있다
    // 단) 해당 변수가 null일경우 에러가 발생하니 주의 필요
    println(nullableMyName!!.reversed())
}