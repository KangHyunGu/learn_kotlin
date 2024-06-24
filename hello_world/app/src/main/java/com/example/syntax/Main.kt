package com.example.syntax

fun main(args: Array<String>){

    val price = 1000;
    val tax = 100;

    val originalPrice = "The original price is $price"
    val taxPrice = "The tax Price is ${price + tax}"

    println(originalPrice)
    println(taxPrice)


    val array:Array<String> = arrayOf("t1", "t2", "t3", "t4", "t5");

    for(i in 0..array.size - 1){
        println(array.get(i));
    }
}

fun printStudentInfo(name : String, age: Int){
    println("Student Name : " + name)
    println("Student Age : " + age)
    println("WelCome")
}

fun addNum(n1: Int, n2: Int) : Int {
    return n1 + n2
}