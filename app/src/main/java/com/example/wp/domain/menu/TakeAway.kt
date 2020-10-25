package com.example.wp.domain.menu

import com.example.wp.utils.enum.TakeAwayTypeEnum

data class TakeAway(
    val name:String,
    val logoUrl:String
)

fun getTakeAwayTypes():MutableList<TakeAway>{
    return mutableListOf(
        TakeAway(
            TakeAwayTypeEnum.GOFOOD.type,
            "https://cdn2.tstatic.net/jakarta/foto/bank/images/logo-gojek-baru.jpg"
        ),
        TakeAway(
            TakeAwayTypeEnum.GRABFOOD.type,
            "https://media.suara.com/pictures/970x544/2016/08/22/o_1aqpc505a7vr17au5e0s3ifgka.jpg"
        ),
        TakeAway(
            TakeAwayTypeEnum.PERSONAL.type,
            "https://icon-library.com/images/personal-icon/personal-icon-6.jpg"
        )
    )
}