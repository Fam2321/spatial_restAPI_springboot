package edu.project.gis.repository

import edu.project.gis.model.entity.AirPollutionPM25
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface AirPollutionPM25Repository : JpaRepository<AirPollutionPM25, Int> {
    @Query(value = "SELECT * FROM AirPollutionPM25 WHERE row_id = 1", nativeQuery = true)
    fun findpm25_1(): MutableList<AirPollutionPM25>
}