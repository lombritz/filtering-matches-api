package com.sparknetworks.exercise.filteringmatches.models;

import static org.springframework.data.mongodb.core.index.GeoSpatialIndexType.GEO_2DSPHERE;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

@Builder
@Data
public class City {
  private String name;
  @GeoSpatialIndexed(type = GEO_2DSPHERE, name = "location") private double[] location;
}
