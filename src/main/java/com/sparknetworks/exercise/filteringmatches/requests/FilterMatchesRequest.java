package com.sparknetworks.exercise.filteringmatches.requests;

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

  @ApiModelProperty(name = "Favourite?", example = "false")
  private Boolean favourite;

  @ApiModelProperty(name = "Range: Compatibility score", example = "[ 0.90, 0.99 ]")
  private Double[] rangeCompatibilityScore;

  @ApiModelProperty(name = "Range: Age", example = "[ 40, 50 ]")
  private Integer[] rangeAge;

  @ApiModelProperty(name = "Range: Height (in cm)", example = "[ 150, 190 ]")
  private Integer[] rangeHeightInCm;

  @ApiModelProperty(name = "Coordinates", example = "[ 51.509865, -0.118092 ]")
  private Double[] coordinates;

  @ApiModelProperty(name = "Distance (in km)", example = "215")
  private Integer distanceInKm;
}
