package edu.project.gis.repository

import edu.project.gis.model.entity.World
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface WorldRepository : JpaRepository<World, Int> {
    @Query(value = "SELECT l FROM World l WHERE l.name = ?1")
    fun findCountry(@Param("country") country: String): World
}