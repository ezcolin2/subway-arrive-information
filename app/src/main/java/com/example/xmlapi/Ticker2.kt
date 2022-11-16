package com.example.xmlapi

import java.util.*

data class Ticker2(
    val errorMessage: ErrorMessage,
    val realtimePositionList:Array<Data2>
)
