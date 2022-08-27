package io.github.oliviercailloux.abg;

public class Move { // Position de début et de fin
	private Position beg;
	private Position end;
	public Position getBeg() {
		return beg;
	}
	public void setBeg(Position beg) {
		this.beg = beg;
	}
	public Position getEnd() {
		return end;
	}
	public void setEnd(Position end) {
		this.end = end;
	}
	public Move(Position beg, Position end) {
		//super();
		this.beg = beg;
		this.end = end;
	}
	
	public boolean is_move_pawn_position_valid(CheckersBoard2 C1) {
		if(this.beg.isValid()!= 1 || this.end.isValid()!= 1) {
			return false;
		}
		return true;
	} 
	
	public boolean is_move_queen_position_valid(CheckersBoard2 C1) {
		if(this.beg.isValid()!= 1 || this.end.isValid()!= 1) {
			return false;
		}
		return true;
	} 
	
	
	public static int valabs(int a) {
		if (a<0) {
			return -a;
		}
		return a;
	}
	
	
	public boolean is_move_pawn_valid(CheckersBoard2 C1) {
		if (this.beg.isEMPTY() == 1 || this.end.isEMPTY()!= 1 || !this.is_move_pawn_position_valid(C1)) {
			return false; 
		}
		if (this.beg.getPiece()== Piece.BLACK_PAWN) {
			
			int dif_x = this.beg.getX()- this.end.getX();
			int dif_y = this.beg.getY()- this.end.getY();
			if (valabs(dif_y)!= 1 || dif_x != 1 ) {
				return false; 				
			}
			return true;
		}
		
		
		if (this.beg.getPiece()== Piece.WHITE_PAWN) {
				
			int dif_x = this.beg.getX()- this.end.getX();
			int dif_y = this.beg.getY()- this.end.getY();
			if (valabs(dif_y)!= 1 || dif_x != -1 ) {
				return false; 				
			}
			return true;	
		}
		return true;
	}
	
	public boolean is_EMPTY_diag (CheckersBoard2 C1) {
		int x = this.beg.getX();
		int y = this.beg.getY();
		int x_final = this.end.getX();
		int y_final = this.end.getY();
		for(int i = x ; i <= x_final ;  i ++) {
			for (int j = y ; j <= y_final ; j++) {
				if (C1.getBoard()[i][j].isEMPTY() != 1) {
					return false;
				}
			}
		}
		return true ;
	}
	
	public Position is_EMPTY_diag_except_1pose (CheckersBoard2 C1) {
		int count = 0;
		Position P1 = new Position (-1,-1);
		int x = this.beg.getX();
		int y = this.beg.getY();
		int x_final = this.end.getX();
		int y_final = this.end.getY();
		Color color = C1.getBoard()[x][y].getCouleur() ;
		for(int i = x ; i < x_final ;  i ++) {
			for (int j = y ; j < y_final ; j++) {
				if (C1.getBoard()[i][j].isEMPTY() != 1 && C1.getBoard()[i][j].getCouleur() != color ) {
					count++;
					P1 = new Position(i,j);
				}
				if (count>1) {
					return new Position (-1,-1); 
				}
			}
		}
		return P1  ;
	}
	
	public boolean is_move_queen_valid(CheckersBoard2 C1) {
		if (this.beg.isEMPTY() == 1 || this.end.isEMPTY()!= 1 || !this.is_move_queen_position_valid(C1) || this.is_EMPTY_diag(C1) == false ) {
			return false; 
		}
		int dif_x = this.beg.getX()- this.end.getX();
		int dif_y = this.beg.getY()- this.end.getY();
		if (valabs(dif_y)!= valabs(dif_x)) {
			return false; 
		}			
		return true;
	}
	
	public Position is_queen_eat_valid(CheckersBoard2 C1) {
		Position P1 = this.is_EMPTY_diag_except_1pose(C1);
		if (this.beg.isEMPTY() == 1 || this.end.isEMPTY()!= 1 || !this.is_move_queen_position_valid(C1) || P1.getX() == -1 ) {
			return new Position(-1,-1); 
		}
		int dif_x = this.beg.getX()- this.end.getX();
		int dif_y = this.beg.getY()- this.end.getY();
		if (valabs(dif_y)!= valabs(dif_x)) {
			return new Position(-1,-1); 
		}			
		return P1;
	}
	
	public boolean doDeplace(CheckersBoard2 C1) {
		if (this.beg.getPieceType()== PieceType.PAWN) {
			if(!this.is_move_pawn_valid(C1)) {
				return false; 
			}
			System.out.println("un pion c'est déplacer ");
			C1.getBoard()[this.end.getX()][this.end.getY()].setPiece(this.beg.getPiece());
			C1.getBoard()[this.beg.getX()][this.beg.getY()].setPiece(Piece.EMPTY);
			return true;
		}
		else if (this.beg.getPieceType()== PieceType.QUEEN) {
			if(!this.is_move_queen_valid(C1)) {
				return false; 
			}
			System.out.println("une dame c'est déplacer ");
			C1.getBoard()[this.end.getX()][this.end.getY()].setPiece(this.beg.getPiece());
			C1.getBoard()[this.beg.getX()][this.beg.getY()].setPiece(Piece.EMPTY);
			return true;
		}
		else {
			return false;
		}
	}
	
	
	public boolean is_pawn_eat_valid(CheckersBoard2 C1) {
		if (this.beg.isEMPTY() == 1 || this.end.isEMPTY()!= 1 || !this.is_move_pawn_position_valid(C1)) {
			return false; 
		}
		int x_inter = (this.end.getX()+this.beg.getX())	/2;	
		int y_inter = (this.end.getY()+this.beg.getY())	/2;	
		Position P1 = new Position(x_inter,y_inter);
		P1.add_Piece_from_Board(C1);
		
		
		if (this.beg.getPiece()== Piece.BLACK_PAWN) {
			if (P1.getPiece() == Piece.WHITE_QUEEN || P1.getPiece() == Piece.WHITE_PAWN) {
				int dif_x = this.beg.getX()- this.end.getX();
				int dif_y = this.beg.getY()- this.end.getY();
				if (valabs(dif_y)!= 2 || dif_x != 2 ) {
					return false; 				
				}
				return true;
			}
		}
		
		
		if (this.beg.getPiece()== Piece.WHITE_PAWN) { 
			if (P1.getPiece() == Piece.BLACK_QUEEN || P1.getPiece() == Piece.BLACK_PAWN) {
				int dif_x = this.beg.getX()- this.end.getX();
				int dif_y = this.beg.getY()- this.end.getY();
				if (valabs(dif_y)!= 2 || dif_x != -2 ) {
					return false; 				
				}
				return true;
			}
		}
		
		return true;
	}		 
	
	public boolean doEat(CheckersBoard2 C1) {
		if (this.beg.getPieceType()==PieceType.PAWN) {
			if (!this.is_pawn_eat_valid(C1)) {
				return false;
			}
			int x_inter = (this.end.getX()+this.beg.getX())	/2;	
			int y_inter = (this.end.getY()+this.beg.getY())	/2;	
			System.out.println("un pion à manger");		
			C1.getBoard()[this.end.getX()][this.end.getY()].setPiece(this.beg.getPiece());
			C1.getBoard()[this.beg.getX()][this.beg.getY()].setPiece(Piece.EMPTY);
			C1.getBoard()[x_inter][y_inter].switchCapture();
			return true ;
		}
		else if (this.beg.getPieceType()==PieceType.QUEEN) {
			Position P1 = this.is_queen_eat_valid(C1);
			if(P1.getX()== -1) {
				return false ; 
			}
			System.out.println("une dame à manger")	;
			C1.getBoard()[this.end.getX()][this.end.getY()].setPiece(this.beg.getPiece());
			C1.getBoard()[this.beg.getX()][this.beg.getY()].setPiece(Piece.EMPTY);
			C1.getBoard()[P1.getX()][P1.getY()].switchCapture();
			return true ;		
		}
		return false;
		
	}

	public int isMove_or_Eat (CheckersBoard2 C1) {
		if(this.beg.getPieceType()==PieceType.PAWN) {
			if (this.valabs(this.beg.getX() - this.end.getX())== 1) {
				return 1;
			}
			if (this.valabs(this.beg.getX() - this.end.getX())== 2) {
				return 2;
			}
			else {
				return -1;
			}
		}
		if (this.beg.getPieceType()==PieceType.QUEEN) {
			if (this.is_EMPTY_diag(C1)== true  ) {
				return 1;
			}
			if (this.is_EMPTY_diag_except_1pose(C1).getX()!= -1) {
				return 2;
			}
			else {
				return -1;
			}
		}
		else {
			return -1;
		}
		
	}
}


