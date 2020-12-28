package com.example.wp.utils.enum

enum class OrderTypeEnum (val type:Int){
    DINE_IN(1),
    PRE_ORDER(2),
    TAKE_AWAY(3)
}

enum class OrderNameTypeEnum (val type:String){
    DINE_IN("dine in"),
    PRE_ORDER("po"),
    TAKE_AWAY("take away"),
    TAKE_AWAY_GRABFOOD("grabfood"),
    TAKE_AWAY_GOFOOD("gofood")
}

enum class OrderStatusTypeEnum(val status:String){
    BILL("bill"),
    PAY("pay")
}