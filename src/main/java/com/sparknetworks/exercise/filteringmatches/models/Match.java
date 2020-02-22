package com.sparknetworks.exercise.filteringmatches.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@Document("matches")
@ApiModel(description = "Match: Object")
public class Match {
  @Id
  private String id;

  @Field("display_name")
  @ApiModelProperty(name = "Display Name", example = "Guido")
  private String displayName;

  @Field("age")
  @ApiModelProperty(name = "Age", example = "27")
  private Integer age;

  @Field("job_title")
  @ApiModelProperty(name = "Job Title", example = "Project Manager")
  private String jobTitle;

  @Field("height_in_cm")
  @ApiModelProperty(name = "Height (in cm)", example = "180")
  private Integer heightInCm;

  @Field("city")
  @ApiModelProperty(name = "City", example = "Miami")
  private City city;

  @Field("distance_in_km")
  @ApiModelProperty(name = "Distance in Km", example = "139.34")
  private Double distanceInKm;

  @Field("main_photo")
  @ApiModelProperty(name = "Photo URL", example = "http://thecatapi.com/api/images/get?format=src&type=gif")
  private String mainPhoto;

  @Field("compatibility_score")
  @ApiModelProperty(name = "Compatibility Score", example = "0.89")
  private double compatibilityScore;

  @Field("contacts_exchanged")
  @ApiModelProperty(name = "Contacts Exchanged", example = "3")
  private Integer contactsExchanged;

  @Field("favourite")
  @ApiModelProperty(name = "Favourite", example = "true")
  private Boolean favourite;

  @Field("religion")
  @ApiModelProperty(name = "Religion", example = "Catholic")
  private String religion;
}
