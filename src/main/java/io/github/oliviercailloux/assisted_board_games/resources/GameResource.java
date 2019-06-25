package io.github.oliviercailloux.assisted_board_games.resources;

import java.io.File;
import java.io.FileWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.game.Game;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveException;
import com.github.bhlangonijr.chesslib.pgn.PgnHolder;

import io.github.oliviercailloux.assisted_board_games.model.GameDAO;
import io.github.oliviercailloux.assisted_board_games.model.GameEntity;
import io.github.oliviercailloux.assisted_board_games.model.MoveDAO;
import io.github.oliviercailloux.assisted_board_games.model.MoveEntity;
import io.github.oliviercailloux.assisted_board_games.model.state.GameState;
import io.github.oliviercailloux.assisted_board_games.model.state.PlayerState;
import io.github.oliviercailloux.assisted_board_games.service.ChessService;
import io.github.oliviercailloux.assisted_board_games.service.MoveService;
import io.github.oliviercailloux.assisted_board_games.utils.GameHelper;

@Path("api/v1/game")
@RequestScoped
public class GameResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameResource.class);
    @Inject
    ChessService chessService;
    @Inject
    MoveService chessMove;

    @POST
    @Path("new")
    @Produces(MediaType.TEXT_PLAIN)
    public int createGame() {
        LOGGER.info("POST game/new");
        GameEntity game = new GameEntity();
        chessService.persist(game);
        return game.getId();
    }

    @POST
    @Path("import")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public int importGame(GameDAO gameDAO) {
        LOGGER.info("POST game/import");
        final GameEntity gameEntity = gameDAO.asGameEntity();
        chessService.persist(gameEntity);
        return gameEntity.getId();
    }

    @POST
    @Path("import/fen")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public int importFenGame(String fenPosition) {
        LOGGER.info("POST game/import/fen");
        final Board board = new Board();
        board.loadFromFen(fenPosition);
        final GameState gameState = GameState.of(board, PlayerState.of(Side.WHITE), PlayerState.of(Side.BLACK));
        final GameEntity gameEntity = new GameEntity(gameState);
        chessService.persist(gameEntity);
        return gameEntity.getId();
    }

    @POST
    @Path("import/pgn")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Integer> importPgnGame(String pgnString) throws Exception {
        LOGGER.info("POST game/import/pgn");
        // PgnHolder only accepts file inputs, so we create a temporary file
        File pgnFile = File.createTempFile("import", ".pgn");
        List<Integer> gameIds = new ArrayList<>();
        try (FileWriter fileWriter = new FileWriter(pgnFile)) {
            fileWriter.write(pgnString);
        }
        PgnHolder pgnHolder = new PgnHolder(pgnFile.getAbsolutePath());
        pgnHolder.loadPgn();
        // and then delete it
        pgnFile.delete();
        // a PGN file can hold one or more games
        for (Game game : pgnHolder.getGame()) {
            final GameState gameState = GameState.of(new Board(), PlayerState.of(Side.WHITE),
                    PlayerState.of(Side.BLACK));
            final GameEntity gameEntity = new GameEntity(gameState);
            game.loadMoveText();
            for (Move move : game.getHalfMoves()) {
                final MoveEntity moveEntity = MoveEntity.createMoveEntity(gameEntity, move);
                gameEntity.addMove(moveEntity);
            }
            chessService.persist(gameEntity);
            gameIds.add(gameEntity.getId());
        }
        return gameIds;
    }

    @GET
    @Path("{gameId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getGame(@PathParam("gameId") int gameId) throws MoveException {
        LOGGER.info("GET game/{}", gameId);
        GameEntity game = chessService.getGame(gameId);
        List<MoveEntity> moves = game.getMoves();
        Board b = GameHelper.playMoves(game.getStartPosition(), moves);
        return b.getFen(true);
    }

    @POST
    @Path("{gameId}/move")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addMove(@PathParam("gameId") int gameId, MoveDAO move) {
        LOGGER.info("POST game/{}/move", gameId);
        final GameEntity game = chessService.getGame(gameId);
        final Duration duration = game.getCurrentMoveDuration();
        final MoveEntity moveEntity = MoveEntity.createMoveEntity(game, move, duration);
        game.addMove(moveEntity);
        chessService.persist(moveEntity);
    }

    @GET
    @Path("{gameId}/moves")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Move> getMoves(@PathParam("gameId") int gameId) {
        LOGGER.info("GET game/{}/moves", gameId);
        return chessService.getGame(gameId)
                .getMoves()
                .stream()
                .map(MoveEntity::asMove)
                .collect(Collectors.toList());
    }

    @GET
    @Path("{gameId}/moves/last")
    @Produces(MediaType.APPLICATION_JSON)
    public Move getLastMove(@PathParam("gameId") int gameId) {
        LOGGER.info("GET game/{}/moves/last", gameId);
        int moveId = chessService.getLastMoveId(gameId);
        MoveEntity move = chessService.getMove(moveId);
        return MoveEntity.asMove(move);
    }

    @GET
    @Path("{gameId}/clock/black")
    public Duration getBlackRemainingTime(@PathParam("gameId") int gameId) {
        LOGGER.info("GET game/{}/clock/black", gameId);
        GameEntity game = chessService.getGame(gameId);
        GameState gameState = game.getGameState();
        PlayerState playerState = gameState.getPlayerState(Side.BLACK);
        if (gameState.isSideToMove(Side.BLACK)) {
            return playerState.getRemainingTimeAt(Instant.now());
        }
        return playerState.getRemainingTime();
    }

    @GET
    @Path("{gameId}/clock/white")
    public Duration getWhiteRemainingTime(@PathParam("gameId") int gameId) {
        LOGGER.info("GET game/{}/clock/white", gameId);
        GameEntity game = chessService.getGame(gameId);
        GameState gameState = game.getGameState();
        PlayerState playerState = gameState.getPlayerState(Side.WHITE);
        if (gameState.isSideToMove(Side.WHITE)) {
            return playerState.getRemainingTimeAt(Instant.now());
        }
        return playerState.getRemainingTime();
    }
}
