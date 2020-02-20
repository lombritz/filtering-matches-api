package com.sparknetworks.exercise.filteringmatches.controllers;

import com.sparknetworks.exercise.filteringmatches.models.Match;
import com.sparknetworks.exercise.filteringmatches.services.MatchesService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/matches")
public class MatchesController {
  private MatchesService matchesService;

  public MatchesController(MatchesService matchesService) {
    this.matchesService = matchesService;
  }

  @PostMapping
  public List<Match> getFilteredMatches(
      @RequestBody(required = false) FilterMatchesRequest request
  ) {
    return matchesService.findAll(request);
  }

}
