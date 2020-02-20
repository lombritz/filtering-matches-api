package com.sparknetworks.exercise.filteringmatches;

import static com.sparknetworks.exercise.filteringmatches.TestUtils.CAT_GIF;
import static com.sparknetworks.exercise.filteringmatches.TestUtils.CityInfo.LONDON;
import static com.sparknetworks.exercise.filteringmatches.TestUtils.randomMatch;
import static org.junit.jupiter.api.Assertions.assertTrue;


import com.sparknetworks.exercise.filteringmatches.controllers.FilterMatchesRequest;
import com.sparknetworks.exercise.filteringmatches.models.Match;
import com.sparknetworks.exercise.filteringmatches.services.MatchesService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@DataMongoTest
@ExtendWith(SpringExtension.class)
public class MatchesServiceTests {
  private MongoTemplate mongoTemplate;
  private MatchesService matchesService;

  @Autowired
  public MatchesServiceTests(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  /**
   * Insert test records to mongo DB and initialize MatchesService
   */
  @BeforeEach
  public void setup() {
    mongoTemplate.getCollection("matches").drop();
    mongoTemplate.insert(randomMatch().build());
    mongoTemplate.insert(randomMatch().build());
    mongoTemplate.insert(randomMatch().build());
    mongoTemplate.insert(randomMatch().build());
    mongoTemplate.insert(randomMatch().build());
    mongoTemplate.insert(randomMatch().build());
    // at least 2 records must match the FindByAge test criteria
    mongoTemplate.insert(randomMatch().age(36).build());
    mongoTemplate.insert(randomMatch().age(40).build());
    // at least 2 records must match the FindByAllFilters test criteria
    mongoTemplate.insert(randomMatch(0)
        .mainPhoto(CAT_GIF).contactsExchanged(2).displayName("Robert").age(31).compatibilityScore(0.89)
        .build());
    mongoTemplate.insert(randomMatch(0)
        .mainPhoto(CAT_GIF).contactsExchanged(3).displayName("Rachel").age(40).compatibilityScore(0.83)
        .build());

    this.matchesService = new MatchesService(mongoTemplate);
  }

  void print_GeneratedRandomMatches() {
    matchesService.findAll().stream()
        .map(Match::toString)
        .forEach(log::info);
  }

  @Test
  void test_FindByAge() {
    print_GeneratedRandomMatches();
    List<Match> results = matchesService.findMatchesByAgeRange(35, 40);

    assertTrue(results.size() >= 2);
  }

  @Test
  void test_FindByAllFilters() {
    print_GeneratedRandomMatches();
    FilterMatchesRequest request = FilterMatchesRequest.builder()
        .hasPhoto(true)
        .inContact(true)
        .rangeAge(new Integer[]{ 30, 40 })
        .rangeCompatibilityScore(new Double[]{ 0.80, 0.89 })
        .distanceInKm(350).coordinates(LONDON.getCoordinates())
        .build();
    List<Match> results = matchesService.findAll(request);

    assertTrue(results.size() >= 2);
  }
}
