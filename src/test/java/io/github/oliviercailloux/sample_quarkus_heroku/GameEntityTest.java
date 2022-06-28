package io.github.oliviercailloux.sample_quarkus_heroku;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.github.oliviercailloux.abg.ChessBoard;
import io.github.oliviercailloux.abg.GameEntity;
import io.quarkus.test.junit.QuarkusTest;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import org.hamcrest.core.Is;
import org.hamcrest.text.MatchesPattern;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@QuarkusTest
public class GameEntityTest {

  static GameEntity game;
  private static final Logger LOGGER = LoggerFactory.getLogger(GameEntityTest.class);

  @BeforeAll
  public static void instanciate() {
    game = new GameEntity();
  }

  @Test
  public void testEntity() { // Test du constructeur de GameEntity
    assertEquals(Duration.ofSeconds(1800), game.getClockDuration());
    assertEquals(Duration.ofSeconds(10), game.getClockIncrement());
    ArrayList moves = new ArrayList<>(); // avoid NPE in tests
    assertEquals(moves, game.getMoves());
    assertEquals(GameEntity.STARTING_FEN_POSITION, game.getStartBoard().getFen());
    LOGGER.info("The game entity has been successfully instantiated");
  }

  @Test
  public void testSetStartTime() {
    final Instant fixedInstant =
        Instant.now(Clock.fixed(Instant.parse("2022-06-22T10:00:00Z"), ZoneOffset.UTC));
    game.setStartTime(fixedInstant);
    assertNotNull(game.getStartTime());
    assertEquals(fixedInstant, game.getStartTime());
    LOGGER.info("The start time has been successfully instantiated");

  }

  @Test
  public void testcomputeRemainingTime() {
    final Instant fixedInstant =
        Instant.now(Clock.fixed(Instant.parse("2022-06-22T10:00:00Z"), ZoneOffset.UTC));
    game.setStartTime(fixedInstant);
    assertNotNull(game.getStartTime());
    assertEquals(fixedInstant, game.getStartTime());

  }
}

// @Test
// public void testGetCurrentMoveDuration() {
// final Instant now = Instant.now();
//
// final Duration gameDuration = getGameDuration();
// final Instant lastMove = startTime.plus(gameDuration);
// }
// }
