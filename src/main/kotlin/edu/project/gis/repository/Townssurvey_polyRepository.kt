package edu.project.gis.repository

import edu.project.gis.model.entity.Townssurvey_poly
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface Townssurvey_polyRepository: JpaRepository<Townssurvey_poly, Int> {
    @Query(value = "SELECT * FROM TOWNSSURVEY_POLY WHERE ID = 1", nativeQuery = true)
    fun find1(): MutableList<Townssurvey_poly>
}