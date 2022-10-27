package com.example.xmlapi

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name="realtimePosition")
data class BusData2(
    @Element(name="row")
    val msgBody:List<MsgBody>
)
@Xml(name = "row")
data class MsgBody2(
    @Element(name="subwayNm")
    val busArrivalList:String
)
