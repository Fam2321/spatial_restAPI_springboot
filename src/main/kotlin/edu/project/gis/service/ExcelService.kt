package edu.project.gis.service

import edu.project.gis.helper.ExcelHelper
import edu.project.gis.model.entity.AirPollutionPM25
import edu.project.gis.repository.AirPollutionPM25Repository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
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

    fun getAllTutorials(): List<AirPollutionPM25> {
        return airPollutionPM25Repository.findAll()
    }
}