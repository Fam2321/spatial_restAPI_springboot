package edu.project.gis.model.response

import com.vividsolutions.jts.geom.Geometry
import edu.project.gis.model.entity.Townssurvey_poly

class GeoJson (
    var type: String = "Feature",
    var geometry: Geometry,
    var properties: Townssurvey_poly
)