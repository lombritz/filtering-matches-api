package com.sparknetworks.exercise.filteringmatches;

import static com.sparknetworks.exercise.filteringmatches.TestUtil.FILTER_ALL_FIELDS;
import static com.sparknetworks.exercise.filteringmatches.TestUtil.HAS_NO_PHOTO;
import static com.sparknetworks.exercise.filteringmatches.TestUtil.HAS_PHOTO;
import static com.sparknetworks.exercise.filteringmatches.TestUtil.NEAR_LONDON;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.HttpStatus.OK;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Integration tests for FilteringMatches API, it runs against the actual services on docker containers.
 */
public class FilteringMatchesApplicationIT {
  private static Logger log = LoggerFactory.getLogger(FilteringMatchesApplicationIT.class);

  @BeforeAll
  public static void awaitReadiness() {
    log.warn("Waiting for containers to startup...");
    await().atMost(5, MINUTES)
        .pollDelay(10, SECONDS)
        .pollInterval(3, SECONDS)
        .ignoreExceptions()
        .until(() -> {
          log.warn("Trying to connect to container...");
          return given().when().get("/swagger-ui.html").getStatusCode() == OK.value();
        });
  }

  @Test
  public void givenNoFilter_thenMatchesShouldReturn25() {
    given().contentType(JSON).when().post("/api/matches")
        .then().assertThat().statusCode(OK.value()).and().body("$", hasSize(25));
  }

  @Test
  public void givenFilter_HasPhoto_thenMatchesShouldReturn22() {
    given().contentType(JSON).body(HAS_PHOTO).when().post("/api/matches")
        .then().assertThat().statusCode(OK.value()).and().body("$", hasSize(22));
  }

  @Test
  public void givenFilter_ByHasNoPhoto_thenMatchesShouldReturn3() {
    given().contentType(JSON).body(HAS_NO_PHOTO).when().post("/api/matches")
        .then().assertThat().statusCode(OK.value()).and().body("$", hasSize(3));
  }

  @Test
  public void givenFilter_ByLocation_thenMatchesShouldReturn13() {
    given().contentType(JSON).body(NEAR_LONDON).when().post("/api/matches")
        .then().assertThat().statusCode(OK.value()).and().body("$", hasSize(13));
  }

  @Test
  public void givenFilter_ByAllFields_thenMatchesShouldReturn1() {
    given().contentType(JSON).body(FILTER_ALL_FIELDS).when().post("/api/matches")
        .then().assertThat().statusCode(OK.value()).and().body("$", hasSize(1));
  }

}
