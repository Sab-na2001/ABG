package io.github.oliviercailloux.assisted_board_games.checkers;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Objects;

import com.google.common.base.MoreObjects;
import com.google.common.hash.HashCode;

/**
 * <p>
 * This class represents a square and all of it's related functionalities.
 * <p>
 * The square is defined using row and column index, e.g. : 11
 * <p>
 * Among available methods in this class, we have methods that allow us to know if a square is
 * white or black or if the square is empty or not.
 * 
 */
public class Square {
	private final int squareNumber;

	private Square(int squareNumber) {
		checkArgument(squareNumber > 0 && squareNumber < 51);
		this.squareNumber = squareNumber;
	}

	/**
	 * <p>
	 * The Board consists of 100 squares. Size is 10 x 10.
	 * The squares are represented by a number, all squares are black,
	 * since pieces can only appear and move on black squares, starting from
	 * upper-left side: (1), (2) ... (50)
	 * @param square number
	 * @return Creates a square using provided square number.
	 */
	public static Square given(int squareNumber) {
		return new Square(squareNumber);
	}

	/**
	 * @return The square number
	 */
	public int getSquareNumber() {
		return squareNumber;
	}
	
	/**
	 * @returns {@link Equals}
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Square)) {
			return false;
		}

		final Square other = (Square) obj;
		return squareNumber == other.getSquareNumber();
	}

	/**
	 *	@returns {@link HashCode}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(squareNumber);
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("square number", squareNumber).toString();
	}
}
