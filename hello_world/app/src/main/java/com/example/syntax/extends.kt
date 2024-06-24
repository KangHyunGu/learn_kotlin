package com.example.syntax
fun main(args: Array<String>){
    val korea = Korea("Republic of Korean", "Seoul", "Korean")
    korea.printFullName()
    korea.printCapital()
    korea.printLanguage()
    korea.singNationalAnthem()

    val usa = USA("United States of America", "Washinton D.C", "English")
    usa.printFullName()
    usa.printCapital()
    usa.printLanguage()
    usa.singNationalAnthem()
}
// 상속을 받기 위해 부모 클래스에서는 class 키워드 앞에 open이라는 키워드를 정의해야한다.
open class Country(val fullName: String, val capital: String, val language: String){
    fun printFullName(){
        println("Full name : $fullName")
    }

    fun printCapital() {
        println("Capital : $capital")
    }

    fun printLanguage() {
        println("Language : $language")
    }

    open fun singNationalAnthem(){
        println("Start singing")
    }
}

// 상속 받는 자식 클래스 class 클래스명(param1:type, param2:type, param3:type) : 상속 클래스명(param1, param2, param3)
class Korea(fullName: String, capital: String, language: String) : Country(fullName, capital, language){
    override fun singNationalAnthem(){
        super.singNationalAnthem()
        println("Sing Korean national anthem")
    }
}

class USA(fullName: String, capital: String, language: String) : Country(fullName, capital, language){
    override fun singNationalAnthem(){
        super.singNationalAnthem()
        println("Sing USA national anthem")
    }
}