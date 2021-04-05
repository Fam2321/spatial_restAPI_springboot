package edu.project.gis.model.entity

import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.vividsolutions.jts.geom.Geometry
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "world")
data class World (
    @Id
    @Column(name = "ID")
    var id: Int? = null,
    @Column(name = "NAME")
    var name: String? = null,
    @Column(name = "CAPITAL")
    var capital : String? = null,
    @Column(name = "geom")
    @JsonSerialize(using = GeometrySerializer::class)
    @JsonDeserialize(contentUsing = GeometryDeserializer::class)
    var geom: Geometry? = null
)