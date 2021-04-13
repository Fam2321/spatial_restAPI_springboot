package edu.project.gis.extension

import com.vividsolutions.jts.geom.Coordinate
import edu.project.gis.model.response.Position

fun Coordinate.toFormat() = Position(
    lat = y,
    lng = x
)