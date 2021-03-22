package edu.project.gis.controller

import edu.project.gis.extension.toGeoJson
import edu.project.gis.model.entity.Townssurvey_poly
import edu.project.gis.model.response.GeoJson
import edu.project.gis.repository.Townssurvey_polyRepository
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["http://localhost:3030"])
class Controller(
    private val townssurveyPolyrepository: Townssurvey_polyRepository
) {
    @GetMapping(value = ["/find"])
    fun query(): List<GeoJson> {
        return townssurveyPolyrepository.find1().map { result -> result.toGeoJson() }
    }
}