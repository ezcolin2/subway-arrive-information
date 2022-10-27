package com.example.xmlapi

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name="response")
data class BusData(
    @Element(name="msgBody")
    val msgBody:MsgBody
)
@Xml(name = "msgBody")
data class MsgBody(
    @Element(name="busArrivalList")
    val busArrivalList:List<BusArrivalList>
)

@Xml(name="busArrivalList")
data class BusArrivalList(
    @PropertyElement(name="plateNo1")
    val plateNo1:String
)