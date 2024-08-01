package com.jordon.airquality.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConnection {
    // Singleton Pattern
    // 소프트웨어 디자인 패턴 중 하나로,
    // 프로그램 내에서 객체 하나를
    // 공유하는 패턴을 말한다.

    // Companion Object
    // 어떤 클래스의 모든 인스턴스의 공유하는 객체를
    // 만들고 싶을 때 사용하며,
    // 클래스당 한 개만 가질 수 있다.

    companion object {
        private const val BASE_URL = "https://api.airvisual.com/v2/"
        private var INSTANCE: Retrofit? = null

        fun getInstance() : Retrofit {
            if(INSTANCE == null){
                INSTANCE = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return INSTANCE!!
        }
    }


}