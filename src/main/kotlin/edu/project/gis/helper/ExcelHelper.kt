package edu.project.gis.helper

import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.GeometryFactory
import edu.project.gis.model.entity.AirPollutionPM25
import edu.project.gis.model.response.AvgResponse
import edu.project.gis.model.response.PopResponse
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.ArrayList

@Component
class ExcelHelper {
    companion object {
        var TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        var SHEET = "WHO_AirQuality_Database_2018"

        fun hasExcelFormat(file: MultipartFile): Boolean {
            return TYPE == file.contentType
        }

        fun excelToAirPollutionPM25s(input: InputStream): ArrayList<AirPollutionPM25> {
            try {
                val workbook: Workbook = XSSFWorkbook(input)

                val sheet: Sheet = workbook.getSheet(SHEET)
                val rows: Iterator<Row> = sheet.iterator()

                val airPollutionPM25s: ArrayList<AirPollutionPM25> = arrayListOf()
                var rowNumber = 0
                while (rows.hasNext()) {
                    val currentRow = rows.next()

                    // skip header
                    if (rowNumber == 0) {
                        rowNumber++
                        continue
                    }

                    val cellsInRow: Iterator<Cell> = currentRow.iterator()
                    val airPollutionPM25 = AirPollutionPM25()
                    var cellIdx = 0
                    while (cellsInRow.hasNext()) {
                        val currentCell = cellsInRow.next()
                        when (cellIdx) {
                            0 -> airPollutionPM25.country = currentCell.stringCellValue
                            1 -> airPollutionPM25.city = currentCell.stringCellValue
                            2 -> airPollutionPM25.Year = currentCell.numericCellValue.toInt().toString()
                            3 -> airPollutionPM25.pm25 = currentCell.numericCellValue
                            4 -> airPollutionPM25.latitude = currentCell.numericCellValue
                            5 -> airPollutionPM25.longitude = currentCell.numericCellValue
                            6 -> airPollutionPM25.population = currentCell.numericCellValue
                            7 -> airPollutionPM25.wbinc16_text = currentCell.stringCellValue
                            8 -> airPollutionPM25.Region = currentCell.stringCellValue
                            9 -> airPollutionPM25.conc_pm25 = currentCell.stringCellValue
                            10 -> airPollutionPM25.color_pm25 = currentCell.stringCellValue
                            else -> {
                            }
                        }
                        cellIdx++
                    }
                    var city = airPollutionPM25.city.toString()
                    var year = airPollutionPM25.Year.toString()
                    airPollutionPM25.row_id = currentRow.rowNum.toString() + city + year
                    airPollutionPM25.Geom = GeometryFactory().createPoint(
                        Coordinate(
                            airPollutionPM25.longitude!!.toDouble(),
                            airPollutionPM25.latitude!!.toDouble()
                        )
                    )
                    airPollutionPM25s.add(airPollutionPM25)
                }
                workbook.close()

                return airPollutionPM25s
            } catch (e: IOException) {
                throw RuntimeException("fail to parse Excel file: " + e.message)
            }
        }

        fun airPollutionPM25sToExcel(airPollutionPM25s: List<AirPollutionPM25>): ByteArrayInputStream {
            try {
                XSSFWorkbook().use { workbook ->
                    ByteArrayOutputStream().use { out ->
                        val sheet: Sheet = workbook.createSheet(SHEET)
                        var HEADERs =
                            arrayOf(
                                "country", "city", "Year", "pm25", "latitude", "longitude",
                                "population", "wbinc16_text", "Region", "conc_pm25", "color_pm25"
                            )

                        // Header
                        val headerRow = sheet.createRow(0)
                        for (col in HEADERs.indices) {
                            val cell = headerRow.createCell(col)
                            cell.setCellValue(HEADERs[col])
                        }
                        var rowIdx = 1
                        for (airPollutionPM25 in airPollutionPM25s) {
                            val row = sheet.createRow(rowIdx++)
                            row.createCell(0).setCellValue(airPollutionPM25.country)
                            row.createCell(1).setCellValue(airPollutionPM25.city)
                            row.createCell(2).setCellValue(airPollutionPM25.Year)
                            row.createCell(3).setCellValue(airPollutionPM25.pm25!!.toDouble())
                            row.createCell(4).setCellValue(airPollutionPM25.latitude!!.toDouble())
                            row.createCell(5).setCellValue(airPollutionPM25.longitude!!.toDouble())
                            row.createCell(6).setCellValue(airPollutionPM25.population!!.toDouble())
                            row.createCell(7).setCellValue(airPollutionPM25.wbinc16_text)
                            row.createCell(8).setCellValue(airPollutionPM25.Region)
                            row.createCell(9).setCellValue(airPollutionPM25.conc_pm25)
                            row.createCell(10).setCellValue(airPollutionPM25.color_pm25)
                        }
                        workbook.write(out)
                        return ByteArrayInputStream(out.toByteArray())
                    }
                }
            } catch (e: IOException) {
                throw RuntimeException("fail to import data to Excel file: " + e.message)
            }
        }

        fun avgResponseToExcel(avgResponses: List<AvgResponse>): ByteArrayInputStream {
            try {
                XSSFWorkbook().use { workbook ->
                    ByteArrayOutputStream().use { out ->
                        val sheet: Sheet = workbook.createSheet(SHEET)
                        var header =  arrayOf("country", "average")

                        // Header
                        val headerRow = sheet.createRow(0)
                        for (col in header.indices) {
                            val cell = headerRow.createCell(col)
                            cell.setCellValue(header[col])
                        }
                        var rowIdx = 1
                        for (avgResponse in avgResponses) {
                            val row = sheet.createRow(rowIdx++)
                            row.createCell(0).setCellValue(avgResponse.country)
                            row.createCell(1).setCellValue(avgResponse.Avg)
                        }
                        workbook.write(out)
                        return ByteArrayInputStream(out.toByteArray())
                    }
                }
            } catch (e: IOException) {
                throw RuntimeException("fail to import data to Excel file: " + e.message)
            }
        }

        fun popResponseToExcel(popResponses: List<PopResponse>): ByteArrayInputStream {
            try {
                XSSFWorkbook().use { workbook ->
                    ByteArrayOutputStream().use { out ->
                        val sheet: Sheet = workbook.createSheet(SHEET)
                        var header =  arrayOf("country", "population")

                        // Header
                        val headerRow = sheet.createRow(0)
                        for (col in header.indices) {
                            val cell = headerRow.createCell(col)
                            cell.setCellValue(header[col])
                        }
                        var rowIdx = 1
                        for (popResponse in popResponses) {
                            val row = sheet.createRow(rowIdx++)
                            row.createCell(0).setCellValue(popResponse.country)
                            row.createCell(1).setCellValue(popResponse.population)
                        }
                        workbook.write(out)
                        return ByteArrayInputStream(out.toByteArray())
                    }
                }
            } catch (e: IOException) {
                throw RuntimeException("fail to import data to Excel file: " + e.message)
            }
        }
    }
}