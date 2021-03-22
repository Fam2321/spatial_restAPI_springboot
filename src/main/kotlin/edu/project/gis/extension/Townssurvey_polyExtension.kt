package edu.project.gis.extension

import edu.project.gis.model.entity.Townssurvey_poly
import edu.project.gis.model.response.GeoJson

fun Townssurvey_poly.toGeoJson() = GeoJson(
    type = "Feature",
    geometry = geom,
    properties = this
)