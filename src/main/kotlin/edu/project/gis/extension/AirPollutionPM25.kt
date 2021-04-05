package edu.project.gis.extension

import edu.project.gis.model.entity.AirPollutionPM25
import edu.project.gis.model.response.MarkerResponse
import edu.project.gis.model.response.Position

fun AirPollutionPM25.toMarker() = MarkerResponse(
    position = Position(
        latitude!!.toDouble(),
        longitude!!.toDouble()
    )
)