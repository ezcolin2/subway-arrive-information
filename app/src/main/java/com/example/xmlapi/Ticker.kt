package com.example.xmlapi


import java.util.Objects

data class Ticker (
    val errorMessage:Objects,
    val realtimeArrivalList:Array<Data>
    )
