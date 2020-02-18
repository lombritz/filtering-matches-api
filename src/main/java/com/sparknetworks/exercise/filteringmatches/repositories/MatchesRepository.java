package com.sparknetworks.exercise.filteringmatches.repositories;

import com.sparknetworks.exercise.filteringmatches.models.Match;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchesRepository extends MongoRepository<Match, String> {
  GeoResults<Match> findByCity_LocationNear(Point location, Distance distance);
}
