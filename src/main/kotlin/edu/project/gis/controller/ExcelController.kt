package edu.project.gis.controller

import edu.project.gis.helper.ExcelHelper
import edu.project.gis.model.entity.AirPollutionPM25
import edu.project.gis.model.response.ResponseMessage
import edu.project.gis.service.ExcelService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
@CrossOrigin(origins = ["http://localhost:3030"])
@RequestMapping("/api/excel")
class ExcelController(
    private val excelService: ExcelService
) {
    @PostMapping("/upload")
    fun uploadFile(@RequestParam("file") file: MultipartFile): ResponseEntity<ResponseMessage?>? {
        var message = ""
        if (ExcelHelper.hasExcelFormat(file)) {
            return try {
                excelService.save(file)
                message = "Uploaded the file successfully: " + file.originalFilename
                ResponseEntity.status(HttpStatus.OK).body(ResponseMessage(message))
            } catch (e: Exception) {
                message = "Could not upload the file: " + file.originalFilename + "!"
                ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(ResponseMessage(message + " clause:" + e.message.toString()))
            }
        }
        message = "Please upload an excel file!"
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMessage(message))
    }

    @GetMapping("/findAll")
    fun getAllTutorials(): ResponseEntity<List<AirPollutionPM25>> {
        return try {
            val tutorials: List<AirPollutionPM25> = excelService.getAllTutorials()
            if (tutorials.isEmpty()) {
                ResponseEntity<List<AirPollutionPM25>>(HttpStatus.NO_CONTENT)
            } else ResponseEntity<List<AirPollutionPM25>>(tutorials, HttpStatus.OK)
        } catch (e: java.lang.Exception) {
            ResponseEntity<List<AirPollutionPM25>>(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}