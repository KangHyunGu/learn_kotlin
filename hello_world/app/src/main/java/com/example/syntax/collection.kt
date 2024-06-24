package com.example.syntax

fun main(){
    /**
    *  [collection]
    *  1. List
    *  2. Set
    *  3. Map
    * */

    /* 1. List(immutableListOf ==> List 내 읽기만 가능
    val fruitList = listOf("Banana", "Apple", "Melon")
    println("First Fruit ${fruitList[0]}")

     1. List(mutableListOf ==> List 내 쓰기 가능
    val mutableFruitList = mutableListOf("Banana", "Apple", "Melon");
    println("First Fruit ${mutableFruitList[0]}")

    mutableFruitList[0] = "Strawberry"

    println("New First Fruit ${mutableFruitList[0]}")
    println(mutableFruitList)


    mutableFruitList.forEach{fruit ->
        println("My First Fruit $fruit")
    }*/

   /* 2. Set
   // Immutable Set
    val immutableNumSet = setOf(1,1,2,2,3,3,4)
    println("immutableNumSet $immutableNumSet")

    // mutable Set
    val mutableNumSet = mutableSetOf(1,1,2,2,3,3,4,5,6)
    mutableNumSet.add(100)
    mutableNumSet.remove(1)
    println("mutableNumSet $mutableNumSet")
    println(mutableNumSet.contains(1))*/

   //3. Map
    val immutableMap = mapOf("name" to "Kang", "age" to 33, "height" to 175)
//    println(immutableMap["name"])
//    println(immutableMap["age"])
//    println(immutableMap["height"])
//    println(immutableMap["no"])

    val mutableMap = mutableMapOf("name" to "Kang", "age" to 100, "height" to 175)
    println(mutableMap["age"])

    mutableMap["age"] = 33
    println(mutableMap)

    mutableMap.put("hobby", "singing")
    mutableMap.remove("name")
    mutableMap.replace("age", 80)
    println("change mutableMap $mutableMap")
}

