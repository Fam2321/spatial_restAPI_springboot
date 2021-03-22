package edu.project.gis.model.entity

import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import com.vividsolutions.jts.geom.Geometry

@Entity
@Table(name = "TOWNSSURVEY_POLY")
data class Townssurvey_poly (
    @Id
    @Column(name = "ID")
    var id: Int,
    @Column(name = "TOWN")
    var town: String,
    @Column(name = "TOWN_ID")
    var town_id: Long? = null,
    @Column(name = "POP1980")
    var pop1980: Long? = null,
    @Column(name = "POP1990")
    var pop1990: Long? = null,
    @Column(name = "POP2000")
    var pop2000: Long? = null,
    @Column(name = "POPCH80_90")
    var popch80_90: Long? = null,
    @Column(name = "POPCH90_00")
    var popch90_00: Long? = null,
    @Column(name = "TYPE")
    var type: String? = null,
    @Column(name = "ISLAND")
    var island: Long? = null,
    @Column(name = "COASTAL_PO")
    var coastal_po: String? = null,
    @Column(name = "FOURCOLOR")
    var fourcolor: Long? = null,
    @Column(name = "FIPS_STCO")
    var fips_stco: Long? = null,
    @Column(name = "CCD_MCD")
    var ccd_mcd: String? = null,
    @Column(name = "FIPS_PLACE")
    var fips_place: String? = null,
    @Column(name = "FIPS_MCD")
    var fips_mcd: Long? = null,
    @Column(name = "FIPS_COUNT")
    var fips_count: Long? = null,
    @Column(name = "ACRES")
    var acres: Double? = null,
    @Column(name = "SQUARE_MIL")
    var square_mil: Double? = null,
    @Column(name = "POP2010")
    var pop2010: Long? = null,
    @Column(name = "POPCH00_10")
    var popch00_10: Long? = null,
    @Column(name = "SHAPE_AREA")
    var shape_area: Double? = null,
    @Column(name = "SHAPE_LEN")
    var shape_len: Double? = null,
    @Column(name = "geom")
    @JsonSerialize(using = GeometrySerializer::class)
    @JsonDeserialize(contentUsing = GeometryDeserializer::class)
    var geom: Geometry
)