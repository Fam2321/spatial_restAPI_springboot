package edu.project.gis.service

import edu.project.gis.extension.toMarker
import edu.project.gis.model.entity.AirPollutionPM25
import edu.project.gis.model.response.MarkerResponse
import edu.project.gis.repository.AirPollutionPM25Repository
import edu.project.gis.repository.WorldRepository
import org.springframework.stereotype.Service

@Service
class VisualService (
    private val airPollutionPM25Repository: AirPollutionPM25Repository,
    private val worldRepository: WorldRepository
) {
    fun findCityByYear(year: String): List<MarkerResponse> {
        return airPollutionPM25Repository.findCityByYear(year).map { model -> model.toMarker() }
    }

    fun findClosetCity(city: String): List<AirPollutionPM25> {
        var result = airPollutionPM25Repository.findCityPoint(city)
        return airPollutionPM25Repository.findCityDistance(city ,result.Geom!!).subList(0,50)
    }
}