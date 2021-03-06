package edu.project.gis.controller

import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.Geometry
import edu.project.gis.model.response.MarkerResponse
import edu.project.gis.model.response.Position
import edu.project.gis.service.VisualService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["https://goaldiggervue.azurewebsites.net"])
@RequestMapping("/api/visual")
class Controller(
    private val visualService: VisualService
) {
    @GetMapping(value = ["/findCityByYear"])
    fun findCityPoint(
        @RequestParam year: String
    ): List<MarkerResponse> {
        return visualService.findCityByYear(year)
    }

    @GetMapping(value = ["/findClosetCity"])
    fun findClosetCity(): List<MarkerResponse> {
        return visualService.findClosetCity("Bangkok")
    }

    @GetMapping(value = ["/findNeighbor"])
    fun thailandNeighbor(
        @RequestParam year: String
    ): List<MarkerResponse> {
        return visualService.thailandNeighbor(year)
    }

    @GetMapping(value = ["/findMBR"])
    fun findMBR(
        @RequestParam year: String
    ): List<Position> {
        return visualService.findMBR(year)
    }

    @GetMapping(value = ["/findMostCity"])
    fun findMostCity(
        @RequestParam year: String
    ): List<MarkerResponse> {
        return visualService.findCitiesOfCountryHaveHighCity(year)
    }

    @GetMapping(value = ["/findLowIncome"])
    fun findCitiesHaveLowIncome(
        @RequestParam year: String
    ): List<MarkerResponse> {
        return visualService.findCitiesHaveLowIncome(year)
    }
}