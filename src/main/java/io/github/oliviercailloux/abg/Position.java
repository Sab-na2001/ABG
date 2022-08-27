package io.github.oliviercailloux.abg;

public class Position {
	private int x; //Ligne
	private int y; //Colonne
	private Piece piece;
	
	public Position(int x, int y, Piece piece) {
		//super();
		this.x = x;
		this.y = y;
		this.piece = piece;
	} 
	public Position(Piece piece) {
		//super();
		this.piece = piece;
	}
	// source --> Getter setter.pour utiliser les attributs privé sans etre dans la classe et avoir acces x et y 
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Piece getPiece() {
		return piece;
	}
	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public Position(int x, int y) {
		//super();
		this.x = x;
		this.y = y;
		this.piece = Piece.EMPTY;
	}
	
	public void add_Piece_from_Board(CheckersBoard2 C1) {
		if(this.isValid() == 0 ) {
			return ;
		}
		this.piece = C1.getBoard()[this.x][this.y].piece;
	}
	
	public int isValid () {
		int x_beg = this.x;	
		int y_beg = this.y;
			if (this.x < 0 || this.x > 7 || this.y < 0 || this.y > 7 ) {
				return 0;
			}
			return 1;
	}
	
	public int isEMPTY() {
		if(this.getPiece()== Piece.EMPTY) {
			return 1;
		}
		return 0;
	}
	
	public Color getCouleur(){
	    if((this.getPiece() == Piece.WHITE_PAWN) || (this.getPiece() == Piece.WHITE_PAWN_C) || (this.getPiece() == Piece.WHITE_QUEEN) || (this.getPiece() == Piece.WHITE_QUEEN_C)){
	        return Color.WHITE;
	    }
	    if((this.getPiece() == Piece.BLACK_PAWN) || (this.getPiece() == Piece.BLACK_PAWN_C) || (this.getPiece() == Piece.BLACK_QUEEN) || (this.getPiece() == Piece.BLACK_QUEEN_C)){
	        return Color.BLACK;
	    }
	    else if(this.getPiece() == Piece.EMPTY){
	        return Color.NOCOL;
	    }

	    return Color.NOCOL;
	}
	
	public PieceType getPieceType(){
	    if((this.getPiece() == Piece.WHITE_PAWN) || (this.getPiece() == Piece.WHITE_PAWN_C) || (this.getPiece() == Piece.BLACK_PAWN) || (this.getPiece() == Piece.BLACK_PAWN_C)){
	        return PieceType.PAWN;
	    }
	    if((this.getPiece() == Piece.WHITE_QUEEN) || (this.getPiece() == Piece.WHITE_QUEEN_C) || (this.getPiece() == Piece.BLACK_QUEEN) || (this.getPiece() == Piece.BLACK_QUEEN_C)){
	        return PieceType.QUEEN;
	    }
	    else if(this.getPiece() == Piece.EMPTY){
	        return PieceType.NOTYPE;
	    }

	    return PieceType.NOTYPE;
	}
	
	public int isCapture () {
		Piece P1 = this.getPiece();
		if (P1 == Piece.BLACK_PAWN_C || P1 == Piece.BLACK_QUEEN_C || P1 == Piece.WHITE_PAWN_C || P1 == Piece.WHITE_QUEEN_C ){
			return 1; 
		}
		return 0;
	}
	public void switchCapture() {
		Piece P1=this.getPiece(); 
		int v = -P1.toInt();
		this.setPiece(Piece.fromInt(v));
	}
	
	public String convert (){
        String m;
        //Piece p = this.board[l][c].getPiece();
        switch(this.getPiece()){
            case BLACK_PAWN : 
                m = "BP";
                break;

            case WHITE_PAWN : 
                m = "WP";
                break;

            case BLACK_QUEEN : 
                m = "BQ";
                break;

            case WHITE_QUEEN : 
                m = "WQ";
                break;

            case EMPTY :
                m = "..";
                break;

            case BLACK_PAWN_C : 
                m = "BPC";
                break;

            case WHITE_PAWN_C : 
                m = "WPC";
                break;

            case BLACK_QUEEN_C : 
                m = "BQC";
                break;

            case WHITE_QUEEN_C : 
                m = "WQC";
                break;

            default : 
                m = ".";
                break;
        }
        return m;
    }

	
	
	public static void main(String[] args ) {
		Position P1 = new Position(5, 6, Piece.BLACK_PAWN );
		Position P2 = new Position(1, 2, Piece.WHITE_PAWN );
		Position P3 = new Position(7, 3, Piece.BLACK_QUEEN);
		Position P4 = new Position(8, 1, Piece.EMPTY);
		/*System.out.println(P1.getPiece());
		System.out.println(P2.getPiece());
		System.out.println(P3.getPiece());
		System.out.println(P4.getPiece());
		P1.switchCapture();
		P2.switchCapture();
		P3.switchCapture();
		P4.switchCapture();
		System.out.println(P1.getPiece());
		System.out.println(P2.getPiece());
		System.out.println(P3.getPiece());
		System.out.println(P4.getPiece());*/	
		//System.out.println(P4.isEMPTY());
		//System.out.println(P3.isCapture());
		System.out.println(P4.isValid());
	
    }
	
	
	
}

