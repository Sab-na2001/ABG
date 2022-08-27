package io.github.oliviercailloux.abg;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.move.MoveException;

import java.util.List;
import java.util.Scanner;

public class CheckersBoard2 extends MyBoard{

	//*Objectif : Représenter le jeux et toutes les positions dans une matrice 64 (8x8)
	//*Situation des coups des joueurs

	//Matrice de pièce
	int length = 8 ;
	
	int CurrentPlayer; // Joueur en cours de jeux 
	
	Player[] tabJ = new Player[2]; 
	
	
	Position [][]Board=new Position[8][8];
	// Pour déclarer une variable du type checkerBord automatiquement
	

	public int getLength() {
		return length;
	}

	public CheckersBoard2(int length, int currentPlayer, Player[] tabJ) {
		//super();
		this.length = length;
		CurrentPlayer = currentPlayer;
		this.tabJ = tabJ;
	}

	public CheckersBoard2(int length, int currentPlayer, Player[] tabJ, Position[][] board) {
		//super();
		this.length = length;
		CurrentPlayer = currentPlayer;
		this.tabJ = tabJ;
		Board = board;
	}

	public void setLength(int length) {
		this.length = length;
	}


	public int getCurrentPlayer() {
		return CurrentPlayer;
	}

	public void setCurrentPlayer(int currentPlayer) {
		CurrentPlayer = currentPlayer;
	}

	public Player[] getTabJ() {
		return tabJ;
	}

	public void setTabJ(Player[] tabJ) {
		this.tabJ = tabJ;
	}

	public Position[][] getBoard() {
		return Board;
	}

	public void setBoard(Position[][] board) {
		Board = board;
	}
//////
	
	public void Initialisation() {
		for(int i = 0; i<8;i++){
			for(int j=0; j<8; j++) {
				this.Board[i][j]= new Position(i,j, Piece.EMPTY);
			}
		}
	};
	
	public void InitialisationPlayer() {
		for(int i = 7; i>4;i--){
			
			for(int j=0; j<8; j++) {
				if ((i+j)%2 == 0 ) {
					this.Board[i][j].setPiece(Piece.BLACK_PAWN);				
				}
			}
		}
		for(int i = 0; i<3;i++){
					
			for(int j=0; j<8; j++) {
				if ((i+j)%2 == 0 ) {
					this.Board[i][j].setPiece(Piece.WHITE_PAWN);				
				}
			}
		}
	};
	
	public int Promotion() {
		int C = 0; 
		for (int j = 0; j < 8 ; j++){
			if (this.getTabJ()[0].getC() == Color.WHITE) {
				if (this.Board[7][j].getPiece() == Piece.WHITE_PAWN) {
					this.Board[7][j].setPiece(Piece.WHITE_QUEEN);
					C++ ; 
				}
				if (this.Board[0][j].getPiece() == Piece.BLACK_PAWN) {
					this.Board[0][j].setPiece(Piece.BLACK_QUEEN); 
					C++ ; 
				}
			}
		
		}
		return C;
	}

	public String Board_toString(){
        String s = "";
        for(int i = 0;i<8;i++){
            for(int j=0;j<8;j++){
                s += this.Board[i][j].getPiece();
            }
            s = s + "\n";
        }
        return s;
    }
    public  void print2(){
        String p = "__________________________________________________________";
        for (int i=7;i>=0 ;i-- ) {
            System.out.println("   ");
            System.out.println("  "+p+"\n");
            for(int j=0;j<8;j++){
                if(j==0){
                    System.out.print(i+" |");
                }
                System.out.print("  "+this.Board[i][j].convert()+"  |");
            }
        }
        System.out.println("\n  "+p);
        System.out.println("     0      1      2      3      4      5      6      7\n\n");
    }
    
    public void remove_captured_pieces(){
        for (int i=0;i<8 ;i++ ) {
            for(int j=0;j<8;j++){
                if(this.Board[i][j].getPiece().toInt() < 0 ){
                    this.Board[i][j].setPiece(Piece.EMPTY);
                }
            }
        }
    }

    public String Board_to_String(){
        String s = "";
        for(int i = 0;i<8;i++){
            for(int j=0;j<8;j++){
                s += this.Board[i][j].getPiece();
            }
            s = s+"\n";
        }
        return s;
    }
     
    public static void play (CheckersBoard2 C1 ) {
    	C1.print2();
    	System.out.println("C'est au joueur " + C1.tabJ [C1.CurrentPlayer].getC()+" de jouer ");
    	System.out.println("Entrez 4 entiers séparés par une virgule à chaque fois. Exemple : 1,2,3,4 tel que (1,2) position de départ et (3,4) la position d'arrivée.");
    
    	String regex = "(\\d),{1}(\\d),{1}(\\d),{1}(\\d)";
        Pattern p = Pattern.compile(regex);
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        Matcher m = p.matcher(s);
        Position P1 = null, P2 = null;
        Move move= null;
        int type_move ;
        if (m.find()) {
        	P1 = new Position(Integer.parseInt(m.group(1)) , Integer.parseInt(m.group(2)));  
        	P2 = new Position(Integer.parseInt(m.group(3)) , Integer.parseInt(m.group(4)));  
        	P1.add_Piece_from_Board(C1);
        	P2.add_Piece_from_Board(C1);
        	move = new Move(P1,P2);
        }
        else {
        	System.out.println("Vous n'avez pas entrer assez d'entiers veuillez réessayer. ");
        	play(C1);
        }
        
        System.out.println(P1.getCouleur());
        if(C1.tabJ[C1.CurrentPlayer].getC() == P1.getCouleur()) {
        	type_move = move.isMove_or_Eat(C1);
        	if (type_move == 1) {
        		if(move.doDeplace(C1)== true) {
        			C1.Promotion();
        			C1.print2();
        			C1.CurrentPlayer = 1 - C1.CurrentPlayer;
        			return;
        		}
        		else {
        			System.out.println("Le mouvement proposé est incorrect veuillez réesayer.1");
        			play(C1);
        		}      		
        	}
        	else if (type_move == 2) {
        		if (move.doEat(C1)== true ) {
        			C1.Promotion();
        			C1.remove_captured_pieces();
        			C1.print2();
        			C1.CurrentPlayer = 1 - C1.CurrentPlayer;
        			return; 
        		}
        		else {
        			System.out.println("Le mouvement proposé est incorrect veuillez réesayer.2");
        			play(C1);
        		}
        	}
        	else {
        		System.out.println("Le mouvement proposé est incorrect veuillez réesayer.");
    			play(C1);
        	}
        }
        else {
        	System.out.println("Vous essayez de déplacer un pion qui n'est pas de votre couleur ! Veuillez réessayer.");
        	play(C1);
        }
    }
    
    public boolean isfinish() {
    	int count_white = 0;
    	int count_black = 0;
    	for( int i = 0; i <8; i++) {
    		for (int j = 0; j<8 ; j++) {
    			if(this.Board[i][j].getCouleur() == Color.BLACK ) {
    				count_black++;
    			}
    			if(this.Board[i][j].getCouleur() == Color.WHITE ) {
        			count_white++;
    				
    			}
    		}
    	}
    	if(count_black == 0 || count_white == 0) {
    		return true;    		
    	}
    	return false; 
    }
    
    
    public static void launchgame (CheckersBoard2 C1){
    	Player P1 = new Player(Color.WHITE);
    	Player P2 = new Player(Color.BLACK);
    	Player[] Tab_Player = new Player[2];
    	Position[][] Pos = new Position[8][8]; 
    	Tab_Player[0] = P1 ; 
    	Tab_Player[1] = P2 ;    	
    	C1 = new CheckersBoard2(8,0,Tab_Player,Pos) ;
    	C1.Initialisation();
    	C1.InitialisationPlayer();
    	while (C1.isfinish() == false ) {
    		play(C1);
    	}
    }
    
    
    //mis automatiquement :
    public static void main(String[] args ) {
    	CheckersBoard2 C1 = null;
    	launchgame(C1);
    	
    }

	@Override
	public Side getSideToMove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyBoard doMoves(List<MoveEntity> moves) throws MoveException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFen() {
		// TODO Auto-generated method stub
		return null;
	}
    
}
    /*public static void main(String[] args ) {
    	Player P1 = new Player(Color.WHITE);
    	Player P2 = new Player(Color.BLACK);
    	Player[] Tab_Player = new Player[2];
    	Position[][] Pos = new Position[8][8]; 
    	Tab_Player[0] = P1 ; 
    	Tab_Player[1] = P2 ;    	
    	CheckersBoard2 C1 = new CheckersBoard2(8,0,Tab_Player,Pos) ;
    	C1.Initialisation();
    	C1.print2();
    	C1.InitialisationPlayer();
    	C1.print2(); 
    	/*Position P = new Position(7,7, Piece.WHITE_PAWN );
    	C1.Board[7][7]= P ;
    	C1.print2();
    	C1.Promotion();
    	C1.print2();
    	Position p1 = new Position(2,0);
    	Position p2 = new Position(3,1);
    	p1.add_Piece_from_Board(C1);
    	p2.add_Piece_from_Board(C1);
    	Move M1 = new Move(p1,p2);  
    	M1.doDeplace(C1);
    	C1.print2();
    	
    	Position p3 = new Position(5,3);
    	Position p4 = new Position(4,2);
    	p3.add_Piece_from_Board(C1);
    	p4.add_Piece_from_Board(C1);
    	Move M2 = new Move(p3,p4);  
    	M2.doDeplace(C1);
    	C1.print2();
    	
    	Position p5 = new Position(4,2);
    	Position p6 = new Position(2,0);
    	p5.add_Piece_from_Board(C1);
    	p6.add_Piece_from_Board(C1);
    	Move M3 = new Move(p5,p6);
    	M3.doEat(C1);
    	C1.print2();
    }    
*/


