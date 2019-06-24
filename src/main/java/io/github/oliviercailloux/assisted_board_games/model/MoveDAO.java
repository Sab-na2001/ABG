package io.github.oliviercailloux.assisted_board_games.model;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;
import com.google.common.base.Preconditions;

/**
 * The purpose of this class is to serve as middleware between the MoveEntity
 * representation used by JPA and Hibernate and the API endpoints that require
 * writeable objects to serialize / deserialize.
 * 
 * @author Theophile Dano
 *
 */
@JsonbPropertyOrder({ "from", "to", "promotion" })
public class MoveDAO implements Serializable {

    private Square from;
    private Square to;
    private Piece promotion;

    MoveDAO(Square from, Square to, Piece promotion) {
        this.from = from;
        this.to = to;
        this.promotion = promotion;
    }

    @JsonbCreator
    public static MoveDAO createMoveDAO(@JsonbProperty("from") Square from,
            @JsonbProperty("to") Square to,
            @JsonbProperty("promotion") Piece promotion) {
        Preconditions.checkArgument(from != null && from != Square.NONE);
        Preconditions.checkArgument(to != null && to != Square.NONE);
        return new MoveDAO(from, to, promotion);
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

    public static Move asMove(MoveDAO move) {
        return new Move(move.getFrom(), move.getTo(), move.getPromotion());
    }
}
