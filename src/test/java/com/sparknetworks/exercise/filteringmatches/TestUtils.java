package com.sparknetworks.exercise.filteringmatches;

import com.sparknetworks.exercise.filteringmatches.models.City;
import com.sparknetworks.exercise.filteringmatches.models.Match;
import java.util.Random;

public class TestUtils {
  public static final String CAT_GIF = "http://thecatapi.com/api/images/get?format=src&type=gif";
  public static final String[] NAMES = new String[]{ "Lucia", "Jaime", "Luisana", "Michael", "Norma", "Lara", "Mary" };
  public static final String[] RELIGIONS = new String[]{ "Protestant", "Agnostic", "Catholic", "Christian", "Orthodox", "Islam", "Jewish", "Atheist" };
  public static final String[] JOB_TITLES = new String[]{ "Project Manager", "Developer", "Product Owner", "Analyst", "Supervisor", "Team Lead" };

  public enum CityInfo {
    CARDIFF("Cardiff", new Double[] { 51.484198, -3.179248 }),
    HARLOW("Harlow", new Double[]{ 51.772938, 0.102310 }),
    LONDON("London", new Double[]{ 51.508569, -0.127733 }),
    ABERDEEN("Aberdeen", new Double[]{ 57.149651, -2.099075 }),
    LEEDS("Leeds", new Double[]{ 53.801277, -1.548567 }),
    BELFAST("Belfast", new Double[]{ 54.607868, -5.926437 });

    private String name;
    private Double[] coordinates;

    CityInfo(String name, Double[] coordinates) {
      this.name = name;
      this.coordinates = coordinates;
    }

    public String getName() {
      return name;
    }

    public Double[] getCoordinates() {
      return coordinates;
    }
  }

  public static Match.MatchBuilder randomMatch(int... cityIdx) {
    return Match.builder()
        .mainPhoto(randomPhoto())
        .displayName(randomDisplayName())
        .favourite(randomFavourite())
        .heightInCm(randomHeight())
        .age(randomAge())
        .religion(randomReligion())
        .jobTitle(randomJobTitle())
        .contactsExchanged(randomContactsExchanged())
        .compatibilityScore(randomCompatibilityScore())
        .city(cityIdx.length > 0 ? randomCity(cityIdx[0]) : randomCity());
  }

  private static City randomCity(int... idx) {
    int index = idx.length > 0 ? idx[0] : new Random().nextInt(CityInfo.values().length);
    return City.builder()
        .name(cityName(index))
        .location(cityCoordinates(index))
        .build();
  }

  private static String cityName(int index) {
    return CityInfo.values()[index].name;
  }

  private static Double[] cityCoordinates(int index) {
    return CityInfo.values()[index].coordinates;
  }

  private static double randomCompatibilityScore() {
    return new Random().nextDouble();
  }

  private static Integer randomContactsExchanged() {
    return new Random().nextInt(12);
  }

  private static String randomDisplayName() {
    return NAMES[new Random().nextInt(NAMES.length)];
  }

  private static String randomJobTitle() {
    return JOB_TITLES[new Random().nextInt(JOB_TITLES.length)];
  }

  private static String randomReligion() {
    return RELIGIONS[new Random().nextInt(RELIGIONS.length)];
  }

  private static Integer randomAge() {
    return 18 + new Random().nextInt(70);
  }

  private static Integer randomHeight() {
    return 100 + (new Random().nextInt(15) * 10);
  }

  private static String randomPhoto() {
    return new Random().nextBoolean() ? CAT_GIF : null;
  }

  private static boolean randomFavourite() {
    return new Random().nextBoolean();
  }

}
