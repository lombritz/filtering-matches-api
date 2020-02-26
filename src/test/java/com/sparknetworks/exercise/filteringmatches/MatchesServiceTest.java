package com.sparknetworks.exercise.filteringmatches;

import static com.sparknetworks.exercise.filteringmatches.TestUtil.CAT_GIF;
import static com.sparknetworks.exercise.filteringmatches.TestUtil.CityInfo.LONDON;
import static com.sparknetworks.exercise.filteringmatches.TestUtil.randomMatch;
import static de.flapdoodle.embed.mongo.distribution.Version.Main.PRODUCTION;
import static org.junit.jupiter.api.Assertions.assertTrue;


import com.mongodb.MongoClient;
import com.sparknetworks.exercise.filteringmatches.models.Match;
import com.sparknetworks.exercise.filteringmatches.requests.FilterMatchesRequest;
import com.sparknetworks.exercise.filteringmatches.services.MatchesService;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.process.runtime.Network;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.MongoTemplate;


/**
 * Unit tests for MatchesService, it runs against an in-memory MongoDB with fake test data.
 */
@Slf4j
public class MatchesServiceTest {

  private MongodExecutable mongodExecutable;
  private MatchesService matchesService;

  @AfterEach
  void clean() {
    mongodExecutable.stop();
  }

  @BeforeEach
  void setup() throws Exception {
    String ip = "localhost";
    int port = 27017;

    IMongodConfig mongodConfig = new MongodConfigBuilder().version(PRODUCTION)
        .net(new Net(ip, port, Network.localhostIsIPv6()))
        .build();

    MongodStarter starter = MongodStarter.getDefaultInstance();
    mongodExecutable = starter.prepare(mongodConfig);
    mongodExecutable.start();
    MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient(ip, port), "test");

    mongoTemplate.insert(randomMatch().build());
    mongoTemplate.insert(randomMatch().build());
    mongoTemplate.insert(randomMatch().build());
    mongoTemplate.insert(randomMatch().build());
    mongoTemplate.insert(randomMatch().build());
    mongoTemplate.insert(randomMatch().build());
    mongoTemplate.insert(randomMatch().age(36).build());
    mongoTemplate.insert(randomMatch().age(40).build());
    mongoTemplate.insert(randomMatch(0)
        .mainPhoto(CAT_GIF).contactsExchanged(2).displayName("Robert").age(31).compatibilityScore(0.89)
        .build());
    mongoTemplate.insert(randomMatch(0)
        .mainPhoto(CAT_GIF).contactsExchanged(3).displayName("Rachel").age(40).compatibilityScore(0.83)
        .build());
    mongoTemplate.getCollection("matches")
        .createIndex(Document.parse("{ 'city.location' : '2dsphere' }"));
    this.matchesService = new MatchesService(mongoTemplate);
  }

  void print_GeneratedRandomMatches() {
    matchesService.findAll().stream()
        .map(Match::toString)
        .forEach(log::info);
  }

  @DisplayName("Test MatchesService.find(request)")
  @Test
  public void test_FilterMatches() {
    print_GeneratedRandomMatches();
    FilterMatchesRequest request = FilterMatchesRequest.builder()
        .hasPhoto(true)
        .inContact(true)
        .rangeAge(new Integer[]{ 30, 40 })
        .rangeCompatibilityScore(new Double[]{ 0.80, 0.89 })
        .distanceInKm(350).coordinates(LONDON.getCoordinates())
        .build();
    List<Match> results = matchesService.find(request);

    assertTrue(results.size() >= 2, "Failed to find at least 2 records!");
  }
}
