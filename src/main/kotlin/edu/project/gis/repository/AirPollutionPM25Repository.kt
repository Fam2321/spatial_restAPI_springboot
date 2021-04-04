package edu.project.gis.repository

import edu.project.gis.model.entity.AirPollutionPM25
import edu.project.gis.model.response.AvgResponse
import edu.project.gis.model.response.PopResponse
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface AirPollutionPM25Repository : JpaRepository<AirPollutionPM25, Int> {
    @Query(value = "SELECT * FROM AirPollutionPM25 WHERE row_id = 1", nativeQuery = true)
    fun findpm25_1(): MutableList<AirPollutionPM25>

    @Query(value = "SELECT l FROM AirPollutionPM25 l WHERE l.pm25 > ?1 AND l.Year = ?2")
    fun findByPM25(@Param("pm25") pm: Double, @Param("year") year: String): MutableList<AirPollutionPM25>

    @Query(value = "SELECT new edu.project.gis.model.response.AvgResponse(l.country, AVG(l.pm25))  FROM AirPollutionPM25 l GROUP BY l.country ORDER BY Avg(l.pm25) DESC")
    fun findAVGGroupByCountry(): MutableList<AvgResponse>

    @Query(value = "SELECT l FROM AirPollutionPM25 l WHERE l.country = ?1  ORDER BY l.Year")
    fun findHistoryByCountry(@Param("country") country: String): MutableList<AirPollutionPM25>

    @Query(value = "SELECT DISTINCT l.country FROM AirPollutionPM25 l ORDER BY l.country")
    fun distinctCountry(): MutableList<String>

    @Query(value = "SELECT DISTINCT l.Year FROM AirPollutionPM25 l ORDER BY  l.Year")
    fun distinctYear(): MutableList<String>

    @Query(value = "SELECT DISTINCT l.color_pm25 FROM AirPollutionPM25 l ORDER BY  l.color_pm25")
    fun distinctColor(): MutableList<String>

    @Query(value = "SELECT new edu.project.gis.model.response.PopResponse(l.country,SUM(l.population)) FROM AirPollutionPM25 l WHERE l.color_pm25 = ?1 AND l.Year = ?2 GROUP BY l.country ORDER BY l.country, SUM(l.population)")
    fun findPopByColor(@Param("color") color: String, @Param("year") year: String): MutableList<PopResponse>
}