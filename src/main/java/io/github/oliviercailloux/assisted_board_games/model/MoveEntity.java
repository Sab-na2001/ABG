package io.github.oliviercailloux.assisted_board_games.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

/***
 * 
 * @author Delmas Douo Bougna
 * @author Theophile Dano
 *
 */
@Entity
@Table(name = "moves")
@NamedQueries({
        @NamedQuery(name = "Move.find", query = "SELECT c FROM MoveEntity c WHERE c.id = :id"),
        @NamedQuery(name = "Move.getLastMoveId", query = "SELECT MAX(c.id) FROM MoveEntity c WHERE c.game.id = :id"),
        @NamedQuery(name = "Move.getLastMove", query = "SELECT MAX(c.id) FROM MoveEntity c ")
})
public class MoveEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    @Column(name = "square_from")
    Square from;
    @Column(name = "square_to")
    Square to;
    Piece promotion;
    @ManyToOne
    GameEntity game;

    public MoveEntity() {
        this(Square.NONE, Square.NONE, Piece.NONE);
    }

    public MoveEntity(String from, String to) {
        this(Square.valueOf(from), Square.valueOf(to));
    }

    public MoveEntity(String from, String to, String promotion) {
        this(Square.valueOf(from), Square.valueOf(to), Piece.valueOf(promotion));
    }

    public MoveEntity(Square from, Square to) {
        this(from, to, Piece.NONE);
    }

    public MoveEntity(Square from, Square to, Piece promotion) {
        this.from = from;
        this.to = to;
        this.promotion = promotion;
    }

    public int getId() {
        return id;
    }

    public Square getFrom() {
        return from;
    }

    public Square getTo() {
        return to;
    }

    public Piece getPromotion() {
        return promotion;
    }

    public GameEntity getGame() {
        return game;
    }

    /**
     * Setter package visibility enables to set game in class GameEntity.java, but
     * still allows user-level immutability.
     * 
     * @param game
     */
    void setGame(GameEntity game) {
        this.game = game;
    }

    // Factory
    public static MoveEntity fromMove(Move move) {
        return new MoveEntity(move.getFrom(), move.getTo(), move.getPromotion());
    }

    public static Move asMove(MoveEntity move) {
        return new Move(move.getFrom(), move.getTo(), move.getPromotion());
    }
}
