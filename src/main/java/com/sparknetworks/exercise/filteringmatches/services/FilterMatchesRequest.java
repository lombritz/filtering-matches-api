package com.sparknetworks.exercise.filteringmatches.services;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(description = "Filter Matches: Request")
public class FilterMatchesRequest {
  @ApiModelProperty(name = "Has photo?", example = "true")
  private Boolean hasPhoto;

  @ApiModelProperty(name = "In contact?", example = "false")
  private Boolean inContact;

  @ApiModelProperty(name = "Favourite?", example = "true")
  private Boolean favourite;

  @ApiModelProperty(name = "Range: Compatibility score", example = "[ 0.70, 0.79 ]")
  private Double[] rangeCompatibilityScore;

  @ApiModelProperty(name = "Range: Age", example = "[ 20, 30 ]")
  private Integer[] rangeAge;

  @ApiModelProperty(name = "Range: Height (in cm)", example = "[ 180, 200 ]")
  private Integer[] rangeHeightInCm;

  @ApiModelProperty(name = "Coordinates", example = "[ 55.006763, -7.318268 ]")
  private Double[] coordinates;

  @ApiModelProperty(name = "Distance (in km)", example = "25")
  private Integer distanceInKm;
}
