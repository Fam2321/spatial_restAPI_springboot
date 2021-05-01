package edu.project.gis.controller

import edu.project.gis.helper.ExcelHelper
import edu.project.gis.model.entity.AirPollutionPM25
import edu.project.gis.model.response.AvgResponse
import edu.project.gis.model.response.PopResponse
import edu.project.gis.model.response.ResponseMessage
import edu.project.gis.service.ExcelService
import edu.project.gis.service.ReportService
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@CrossOrigin(origins = ["https://goaldiggervue.azurewebsites.net"])
@RequestMapping("/api/excel")
class ExcelController(
    private val excelService: ExcelService,
    private val reportService: ReportService
) {
    @PostMapping("/upload")
    fun uploadFile(@RequestParam("file") file: MultipartFile): ResponseEntity<ResponseMessage> {
        var message = ""
        if (ExcelHelper.hasExcelFormat(file)) {
            return try {
                excelService.save(file)
                message = "Uploaded the file successfully: " + file.originalFilename
                ResponseEntity.status(HttpStatus.CREATED).body(ResponseMessage(message))
            } catch (e: Exception) {
                message = "Could not upload the file: " + file.originalFilename + "!"
                ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(ResponseMessage(message + " clause:" + e.message.toString()))
            }
        }
        message = "Please upload an excel file!"
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMessage(message))
    }

    @GetMapping("/distinctYear")
    fun getDistinctYear(): ResponseEntity<List<String>> {
        return try {
            val countryList: List<String> = reportService.getDistinctYear()
            if (countryList.isEmpty())
                ResponseEntity<List<String>>(HttpStatus.NO_CONTENT)
            else ResponseEntity<List<String>>(countryList, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity<List<String>>(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/findByPM")
    fun getListByPM25(
        @RequestParam value: String,
        @RequestParam year: String
    ): ResponseEntity<List<AirPollutionPM25>> {
        return try {
            val airPollutionPM25: List<AirPollutionPM25> = reportService.getListByPM25(value.toDouble(), year)
            if (airPollutionPM25.isEmpty())
                ResponseEntity<List<AirPollutionPM25>>(HttpStatus.NO_CONTENT)
            else ResponseEntity<List<AirPollutionPM25>>(airPollutionPM25, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity<List<AirPollutionPM25>>(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/loadByPM")
    fun getFileByPM(
        @RequestParam value: String,
        @RequestParam year: String
    ): ResponseEntity<Resource> {
        val filename = "ListOfCountry.xlsx"
        val file = InputStreamResource(excelService.loadListByPM25(value.toDouble(), year))
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=$filename")
            .contentType(MediaType.parseMediaType("application/vnd.ms-excel;charset=utf-8"))
            .body(file)
    }

    @GetMapping("/findAvg")
    fun getListAvg(): ResponseEntity<List<AvgResponse>> {
        return try {
            val airPollutionResponse25: List<AvgResponse> = reportService.getListAvg()
            if (airPollutionResponse25.isEmpty())
                ResponseEntity<List<AvgResponse>>(HttpStatus.NO_CONTENT)
            else ResponseEntity<List<AvgResponse>>(airPollutionResponse25, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity<List<AvgResponse>>(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/loadAvg")
    fun getFileByPM(): ResponseEntity<Resource> {
        val filename = "AveragePMByCountry.xlsx"
        val file = InputStreamResource(excelService.loadAVG())
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=$filename")
            .contentType(MediaType.parseMediaType("application/vnd.ms-excel;charset=utf-8"))
            .body(file)
    }

    @GetMapping("/distinctCountry")
    fun getDistinctCountry(): ResponseEntity<List<String>> {
        return try {
            val countryList: List<String> = reportService.getDistinctCountry()
            if (countryList.isEmpty())
                ResponseEntity<List<String>>(HttpStatus.NO_CONTENT)
            else ResponseEntity<List<String>>(countryList, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity<List<String>>(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/findHistory")
    fun getHistoryByCountry(
        @RequestParam country: String
    ): ResponseEntity<List<AirPollutionPM25>> {
        return try {
            val airPollutionPM25: List<AirPollutionPM25> = reportService.getHistoryByCountry(country)
            if (airPollutionPM25.isEmpty())
                ResponseEntity<List<AirPollutionPM25>>(HttpStatus.NO_CONTENT)
            else ResponseEntity<List<AirPollutionPM25>>(airPollutionPM25, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity<List<AirPollutionPM25>>(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/loadHis")
    fun getFileHisByCountry(
        @RequestParam country: String
    ): ResponseEntity<Resource> {
        val filename = "HistoryOfCountry.xlsx"
        val file = InputStreamResource(excelService.loadHisByCountry(country))
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=$filename")
            .contentType(MediaType.parseMediaType("application/vnd.ms-excel;charset=utf-8"))
            .body(file)
    }

    @GetMapping("/distinctColor")
    fun getDistinctColor(): ResponseEntity<List<String>> {
        return try {
            val colors: List<String> = reportService.getDistinctColor()
            if (colors.isEmpty())
                ResponseEntity<List<String>>(HttpStatus.NO_CONTENT)
            else ResponseEntity<List<String>>(colors, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity<List<String>>(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/findPop")
    fun getPopByColorAndYear(
        @RequestParam color: String,
        @RequestParam year: String
    ): ResponseEntity<List<PopResponse>> {
        return try {
            val airPollutionPM25: List<PopResponse> = reportService.getPopByColorAndYear(color.split(" ")[0], year)
            if (airPollutionPM25.isEmpty())
                ResponseEntity<List<PopResponse>>(HttpStatus.NO_CONTENT)
            else ResponseEntity<List<PopResponse>>(airPollutionPM25, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity<List<PopResponse>>(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/loadPop")
    fun getFilePopByColor(
        @RequestParam color: String,
        @RequestParam year: String
    ): ResponseEntity<Resource> {
        val filename = "PopulationInHealth.xlsx"
        val file = InputStreamResource(excelService.loadPop(color.split(" ")[0], year))
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=$filename")
            .contentType(MediaType.parseMediaType("application/vnd.ms-excel;charset=utf-8"))
            .body(file)
    }
}