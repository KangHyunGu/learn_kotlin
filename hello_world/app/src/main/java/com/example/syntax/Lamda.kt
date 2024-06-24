package com.example.syntax

fun main(args: Array<String>) {

    //Lambda Expression
    val sayHello = fun() {println("Hello!")}
    sayHello()

    val squareNum : (Int) -> (Int) = {num -> num * num}

    val squareNum2 = {num : Int -> num * num}
    val squareNum3 : (Int) -> (Int) = {it * it} //it

    println(squareNum(10))
    println(squareNum2(10))
    println(squareNum3(10))

    fun invokeLambda(lambda : (Int) -> Boolean) : Boolean {
        return lambda(5)
    }

    val falseValue = invokeLambda({num -> num == 10})
    val trueValue = invokeLambda ({num -> num < 10})

    println(falseValue)
    println(trueValue)



}