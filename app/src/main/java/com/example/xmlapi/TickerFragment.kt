package com.example.xmlapi


import java.util.Objects

data class TickerFragment (
    val errorMessage:Objects,
    val realtimeArrivalList:Array<SubwayModel>
    )
