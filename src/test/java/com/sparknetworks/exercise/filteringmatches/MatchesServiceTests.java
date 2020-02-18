package com.sparknetworks.exercise.filteringmatches;

import static org.junit.jupiter.api.Assertions.assertEquals;


import com.sparknetworks.exercise.filteringmatches.models.Match;
import com.sparknetworks.exercise.filteringmatches.services.FilterMatchesRequest;
import com.sparknetworks.exercise.filteringmatches.services.MatchesService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class MatchesServiceTests {

  @Autowired
  private MatchesService matchesService;

  @Test
  void test_FindByAge() {
    List<Match> results = matchesService.findMatchesByAgeRange(35, 40);

    assertEquals(2, results.size());
  }

  @Test
  void test_findByAllFilters() {
    FilterMatchesRequest request = FilterMatchesRequest.builder()
        .hasPhoto(true)
        .inContact(false)
        .rangeAge(new Integer[]{30, 40})
        .rangeCompatibilityScore(new Double[]{0.80, 0.89})
        .coordinates(new Double[]{55.006763, -7.318268})
        .distanceInKm(300)
        .build();
    List<Match> results = matchesService.findAll(request);

    assertEquals(1, results.size());
  }
}
