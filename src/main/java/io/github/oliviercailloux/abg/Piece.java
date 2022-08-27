package io.github.oliviercailloux.abg;

public enum Piece {
    WHITE_PAWN(1),
    BLACK_PAWN(2) ,
    WHITE_QUEEN(3),
    BLACK_QUEEN(4),
    EMPTY(0),
    WHITE_PAWN_C (-1),
    BLACK_PAWN_C(-2),
    WHITE_QUEEN_C(-3),
    BLACK_QUEEN_C(-4);
	
	private int value;

	private Piece(int value) {
		this.value = value;
	} 
	
	public int toInt() {
		return value ;
	}
	
	public static Piece fromInt(int value) {
		switch (value) {
			case 0: return EMPTY; 
			case 1: return WHITE_PAWN; 
			case 2: return BLACK_PAWN; 
			case 3: return WHITE_QUEEN;
			case 4: return BLACK_QUEEN; 
			case -1: return WHITE_PAWN_C; 
			case -2: return BLACK_PAWN_C; 
			case -3: return WHITE_QUEEN_C; 
			case -4: return BLACK_QUEEN_C; 
			default : return EMPTY;
		}
		
	}
}
