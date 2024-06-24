package com.example.syntax

fun main(args: Array<String>){
    val car = Car()

    car.drive()
    car.stop()
    car.showMeColor()

    val bike = Bike()
    bike.drive()
    bike.stop()
    bike.showMeColor()
}

// 교통수단 interface
interface Vehicle{
    fun drive()
    fun stop()
    fun destory() {println("Vehicle is destroyed")}
}

interface Color{
    fun showMeColor()
}

// interface는 상속해서 정의 해놓은 함수를 구현해야 한다.
class Car : Vehicle, Color {
    override fun drive() {
        println("Car is moving")
    }

    override fun stop() {
        println("Car is stopping")
    }

    override fun destory() {
        super.destory()
        println("The Vehicle is car")
    }

    override fun showMeColor() {
        println("Red Color")
    }
}

class Bike : Vehicle, Color {
    override fun drive() {
        println("Bike is moving")
    }

    override fun stop() {
        println("Bike is stopping")
    }

    override fun destory() {
        super.destory()
        println("The Vehicle is Bike")
    }

    override fun showMeColor() {
        println("Blue Color")
    }

}