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
@Table(name = "airpollutionpm25")
data class AirPollutionPM25 (
    @Id
    @Column(name = "row_id")
    var row_id: String? = null,
    @Column(name = "country")
    var country: String? = null,
    @Column(name = "city")
    var city : String? = null,
    @Column(name = "Year")
    var Year : String? = null,
    @Column(name = "pm25")
    var pm25 : Double? = null,
    @Column(name = "latitude")
    var latitude : Double? = null,
    @Column(name = "longitude")
    var longitude : Double? = null,
    @Column(name = "population")
    var population : Double? = null,
    @Column(name = "wbinc16_text")
    var wbinc16_text : String? = null,
    @Column(name = "Region")
    var Region : String? = null,
    @Column(name = "conc_pm25")
    var conc_pm25 : String? = null,
    @Column(name = "color_pm25")
    var color_pm25 : String? = null,
    @Column(name = "Geom")
    @JsonSerialize(using = GeometrySerializer::class)
    @JsonDeserialize(contentUsing = GeometryDeserializer::class)
    var Geom: Geometry? = null
)