package edu.project.gis.service

import edu.project.gis.helper.ExcelHelper
import edu.project.gis.model.entity.AirPollutionPM25
import edu.project.gis.model.response.AvgResponse
import edu.project.gis.model.response.PopResponse
import edu.project.gis.repository.AirPollutionPM25Repository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.io.IOException
import java.util.ArrayList

@Service
class ExcelService(
    private val airPollutionPM25Repository: AirPollutionPM25Repository
) {
    fun save(file: MultipartFile) {
        try {
            val airPollutionPM25s: ArrayList<AirPollutionPM25> = ExcelHelper.excelToAirPollutionPM25s(file.inputStream)
            airPollutionPM25Repository.saveAll(airPollutionPM25s)
        } catch (e: IOException) {
            throw RuntimeException("fail to store excel data: " + e.message)
        }
    }

    fun loadListByPM25(pm25: Double, year: String): ByteArrayInputStream {
        val airPollutionPM25: List<AirPollutionPM25> = airPollutionPM25Repository.findByPM25(pm25, year)
        return ExcelHelper.airPollutionPM25sToExcel(airPollutionPM25)
    }

    fun loadAVG(): ByteArrayInputStream {
        val avgResponses: List<AvgResponse> = airPollutionPM25Repository.findAVGGroupByCountry()
        return ExcelHelper.avgResponseToExcel(avgResponses)
    }

    fun loadHisByCountry(country: String): ByteArrayInputStream {
        val airPollutionPM25: List<AirPollutionPM25> = airPollutionPM25Repository.findHistoryByCountry(country)
        return ExcelHelper.airPollutionPM25sToExcel(airPollutionPM25)
    }

    fun loadPop(color: String, year: String): ByteArrayInputStream {
        val popResponses: List<PopResponse> = airPollutionPM25Repository.findPopByColor(color, year)
        return ExcelHelper.popResponseToExcel(popResponses)
    }
}