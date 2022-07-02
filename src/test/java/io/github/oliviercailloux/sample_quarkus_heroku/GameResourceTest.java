package io.github.oliviercailloux.sample_quarkus_heroku;

import static io.restassured.RestAssured.given;

import io.github.oliviercailloux.abg.GameEntity;
import io.quarkus.runtime.ApplicationConfig;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import java.time.Duration;
import org.hamcrest.core.Is;
import org.hamcrest.text.MatchesPattern;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
public class GameResourceTest {
  Duration remainingTime = Duration.ofSeconds(1800);
  static GameEntity game;
  private static final Logger LOGGER = LoggerFactory.getLogger(GameEntityTest.class);

  @BeforeAll
  public static void instanciate() {
    game = new GameEntity();
  }

  @Test
  @Order(1)
  public void testPostcreateGame() {
    given().when().post("/v0/api/v1/game/new").then().statusCode(200).body(Is.is("1"));
  }

  @Test
  @Order(2)
  public void testGetgetGame() {
    given().when().get("/v0/api/v1/game/1").then().statusCode(200).body(
        MatchesPattern.matchesPattern("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"));
  }

  @Test
  @Order(3)
  public void testgetRemainingTime() {
    given().when().get("/v0/api/v1/game/1/clock/black").then().statusCode(200)
        .body(Is.is(remainingTime));
  }
}
