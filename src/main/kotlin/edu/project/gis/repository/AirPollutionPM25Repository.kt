package edu.project.gis.repository

import com.vividsolutions.jts.geom.Geometry
import edu.project.gis.model.entity.AirPollutionPM25
import edu.project.gis.model.response.AvgResponse
import edu.project.gis.model.response.PopResponse
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface AirPollutionPM25Repository : JpaRepository<AirPollutionPM25, String> {
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

    @Query(value = "SELECT DISTINCT concat(l.color_pm25, ' ',l.conc_pm25) FROM AirPollutionPM25 l ORDER BY 1")
    fun distinctColor(): MutableList<String>

    @Query(value = "SELECT new edu.project.gis.model.response.PopResponse(l.country,SUM(l.population)) FROM AirPollutionPM25 l WHERE l.color_pm25 = ?1 AND l.Year = ?2 GROUP BY l.country ORDER BY l.country, SUM(l.population)")
    fun findPopByColor(@Param("color") color: String, @Param("year") year: String): MutableList<PopResponse>

    @Query(value = "SELECT l FROM AirPollutionPM25 l WHERE l.Year = ?1")
    fun findCityByYear(@Param("year") year: String): MutableList<AirPollutionPM25>

    @Query(value = "select l from AirPollutionPM25 l WHERE l.city = ?1")
    fun findCityPoint(@Param("city") city: String): AirPollutionPM25

    @Query(value = "select * from AirPollutionPM25 air WHERE air.city != ?1 ORDER BY air.Geom.STDistance(?2)", nativeQuery = true)
    fun findCityDistance(@Param("city") city: String, @Param("geom") geom: Geometry): MutableList<AirPollutionPM25>

    @Query(value = "select l FROM AirPollutionPM25 l WHERE l.country in (?1) AND l.Year = ?2")
    fun findCitiesInCountiesOnYear(@Param("country") country: ArrayList<String>, @Param("year") year: String): MutableList<AirPollutionPM25>

    @Query(value = "select ?1.STUnion(air.Geom) from AirPollutionPM25 air where air.country = 'Thailand' and air.Year = ?2", nativeQuery = true)
    fun union(@Param(value = "geom") geom: Geometry, @Param("year") year: String): Geometry

    @Query(value = "select l.country FROM AirPollutionPM25 l where l.Year = ?1 group by l.country order by count(l.city) DESC")
    fun findCountriesHaveMostCitiesInYear(@Param("year") year: String): MutableList<String>

    @Query(value = "select l from AirPollutionPM25 l where l.wbinc16_text='low income' and l.Year = ?1")
    fun findCitiesHaveLowIncome(@Param("year") year: String): MutableList<AirPollutionPM25>
}