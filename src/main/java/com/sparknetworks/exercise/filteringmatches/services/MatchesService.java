package com.sparknetworks.exercise.filteringmatches.services;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static org.springframework.data.geo.Metrics.KILOMETERS;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.geoNear;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.NearQuery.near;


import com.sparknetworks.exercise.filteringmatches.controllers.FilterMatchesRequest;
import com.sparknetworks.exercise.filteringmatches.models.Match;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class MatchesService {
  private MongoTemplate mongoTemplate;

  public MatchesService(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  public List<Match> findAll() {
    return mongoTemplate.findAll(Match.class);
  }

  public List<Match> findMatchesByAgeRange(double minAge, double maxAge) {
    Query query = new Query();
    query.addCriteria(where("age").lte(maxAge).gte(minAge));

    return mongoTemplate.find(query, Match.class);
  }

  public List<Match> findAll(FilterMatchesRequest request) {
    final List<AggregationOperation> aggs = new ArrayList<>();
    buildNearQuery(request)
        .map(nearQuery ->
            aggs.add(geoNear(nearQuery, "distance_in_km"))
        );
    aggs.add(match(buildCriteria(request)));

    return mongoTemplate.aggregate(newAggregation(Match.class, aggs), Match.class).getMappedResults();
  }

  private Optional<NearQuery> buildNearQuery(FilterMatchesRequest request) {
    return ofNullable(request.getCoordinates())
        .map(coords -> new Point(coords[0], coords[1]))
        .flatMap(point -> ofNullable(request.getDistanceInKm())
            .map(distanceInKm -> near(point, KILOMETERS).maxDistance(distanceInKm)));
  }

  private Criteria buildCriteria(FilterMatchesRequest request) {
    Criteria criteria = new Criteria();

    if (isNull(request)) {
      return criteria;
    }

    ofNullable(request.getHasPhoto())
        .ifPresent(hasPhoto -> criteria.and("main_photo").exists(hasPhoto));
    ofNullable(request.getInContact())
        .ifPresent(inContact -> {
          if (inContact) {
            criteria.and("contacts_exchanged").gt(0);
          } else {
            criteria.and("contacts_exchanged").is(0);
          }
        });
    ofNullable(request.getFavourite())
        .ifPresent(favourite -> criteria.and("favourite").is(favourite));
    ofNullable(request.getRangeCompatibilityScore())
        .ifPresent(score -> criteria.and("compatibility_score").gte(score[0]).lte(score[1]));
    ofNullable(request.getRangeAge())
        .ifPresent(age -> criteria.and("age").gte(age[0]).lte(age[1]));
    ofNullable(request.getRangeHeightInCm())
        .ifPresent(height -> criteria.and("height_in_cm").gte(height[0]).lte(height[1]));

    return criteria;
  }
}
