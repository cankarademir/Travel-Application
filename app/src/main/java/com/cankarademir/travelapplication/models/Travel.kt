package com.cankarademir.travelapplication.models
data class Travel(
    var id :String,
    var title: String,
    var location: String,
    var note: String
)

data class TravelData(
    var title: String,
    var location: String,
    var note: String
)
