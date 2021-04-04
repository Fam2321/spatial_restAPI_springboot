package edu.project.gis.service

import edu.project.gis.model.entity.AirPollutionPM25
import edu.project.gis.model.response.AvgResponse
import edu.project.gis.model.response.PopResponse
import edu.project.gis.repository.AirPollutionPM25Repository
import org.springframework.stereotype.Service

@Service
class ReportService(
    private val airPollutionPM25Repository: AirPollutionPM25Repository
) {
    fun getDistinctYear(): List<String>{
        return airPollutionPM25Repository.distinctYear()
    }

    fun getListByPM25(pm25: Double, year: String): List<AirPollutionPM25> {
        return  airPollutionPM25Repository.findByPM25(pm25, year)
    }

    fun getListAvg(): List<AvgResponse> {
        return airPollutionPM25Repository.findAVGGroupByCountry()
    }

    fun getDistinctCountry(): List<String>{
        return airPollutionPM25Repository.distinctCountry()
    }

    fun getHistoryByCountry(country: String): List<AirPollutionPM25> {
        return airPollutionPM25Repository.findHistoryByCountry(country)
    }

    fun getDistinctColor(): List<String>{
        return airPollutionPM25Repository.distinctColor()
    }

    fun getPopByColorAndYear(color: String, year: String): List<PopResponse> {
        return  airPollutionPM25Repository.findPopByColor(color, year)
    }
}