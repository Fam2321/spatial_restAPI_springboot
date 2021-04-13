package edu.project.gis.service

import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.Geometry
import com.vividsolutions.jts.geom.GeometryFactory
import edu.project.gis.extension.toFormat
import edu.project.gis.extension.toMarker
import edu.project.gis.model.entity.AirPollutionPM25
import edu.project.gis.model.response.MarkerResponse
import edu.project.gis.model.response.Position
import edu.project.gis.repository.AirPollutionPM25Repository
import edu.project.gis.repository.WorldRepository
import org.springframework.stereotype.Service

@Service
class VisualService (
    private val airPollutionPM25Repository: AirPollutionPM25Repository,
    private val worldRepository: WorldRepository
) {
    fun findCityByYear(year: String): List<MarkerResponse> {
        return airPollutionPM25Repository
            .findCityByYear(year)
            .map { model -> model.toMarker() }
    }

    fun findClosetCity(city: String): List<MarkerResponse> {
        var result = airPollutionPM25Repository.findCityPoint(city)
        return airPollutionPM25Repository
            .findCityDistance(city ,result.Geom!!)
            .subList(0,50)
            .map{ model -> model.toMarker() }
    }

    fun thailandNeighbor(year: String): List<MarkerResponse> {
        var thaiGeom = worldRepository.findCountry("Thailand")
        var neighborName = arrayListOf<String>()
        worldRepository.findNeighbor(thaiGeom.geom!!).map { model -> neighborName.add(model.name!!) }
        return airPollutionPM25Repository
            .findCityNeighbor(neighborName, year)
            .map { model -> model.toMarker() }
    }

    fun findMBR(year: String): List<Position> {
        var thaiGeom =
            airPollutionPM25Repository.findCityNeighbor(arrayListOf("Thailand"),year)
        var all = arrayListOf<Geometry>()
        thaiGeom.map { model -> all.add(model.Geom!!) }
        var multiGeom = GeometryFactory().buildGeometry(all)
        return multiGeom.envelope.coordinates.map { model -> model.toFormat() }
    }
}