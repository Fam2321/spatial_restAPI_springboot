package edu.project.gis.helper

import edu.project.gis.model.entity.AirPollutionPM25
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.io.InputStream
import java.util.ArrayList

@Component
class ExcelHelper {
    companion object {
        var TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        var HEADERs =
            arrayOf(
                "country", "city", "Year", "pm25", "latitude", "longitude",
                "population", "wbinc16_text", "Region", "conc_pm25", "color_pm25"
            )
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
                    airPollutionPM25.row_id = currentRow.rowNum
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
                    airPollutionPM25s.add(airPollutionPM25)
                }
                workbook.close()

                return airPollutionPM25s
            } catch (e: IOException) {
                throw RuntimeException("fail to parse Excel file: " + e.message)
            }
        }
    }
}