package com.example.xmlapi

import java.util.*

data class SubwayApiData(
    val errorMessage: Objects,
    val realtimeArrivalList:Array<SubwayData>
)
