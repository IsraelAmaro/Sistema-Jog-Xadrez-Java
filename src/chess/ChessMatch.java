package chess;

import boardgame.Board;
import chess.pieces.King;
import chess.pieces.RooK;

public class ChessMatch {
	
	private Board board;
	
	public ChessMatch() {
	
		board = new Board(8, 8);
		initialSetup();
	}
	
	public ChessPiece[][] getpieces(){
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for(int i =0; i< board.getRows(); i++) {
			for(int j = 0; j< board.getColumns();j++) {
				
				mat[i][j] = (ChessPiece) board.piece(i , j); 
				/* necessario  fazer o dowCasting (ChessPiece) para que o 
				 * programa entenda que quero usar um objeto tipo Peça de Xadre e nao
					como uma peça comum contoda na classe Board  */				
			}
		}
		
		return mat;
	}
	
	//metodo que recebe as cordenadas do xadrez
	
	private void placeNewPiece(char column, int row, ChessPiece piece ) {
		board.placePiece(piece,new ChessPosition(column, row).toPosition());
	}
	
	
	//metodo para colocar as peças no tabuleiro atravé do metodo PlaceNewPiece
	private void initialSetup() {
		placeNewPiece ( 'c' , 1 , new  RooK ( board , Color . WHITE ));
        placeNewPiece ( 'c' , 2 , new  RooK ( board , Color . WHITE ));
        placeNewPiece ( 'd' , 2 , new  RooK ( board , Color . WHITE ));
        placeNewPiece ( 'e' , 2 , new  RooK ( board , Color . WHITE ));
        placeNewPiece ( 'e' , 1 , new  RooK ( board , Color . WHITE ));
        placeNewPiece ( 'd' , 1 , new  King ( board , Color . WHITE ));

        placeNewPiece ( 'c' , 7 , new  RooK ( board , Color . BLACK ));
        placeNewPiece ( 'c' , 8 , new  RooK ( board , Color . BLACK ));
        placeNewPiece ( 'd' , 7 , new  RooK ( board , Color . BLACK ));
        placeNewPiece ( 'e' , 7 , new  RooK ( board , Color . BLACK ));
        placeNewPiece ( 'e' , 8 , new  RooK ( board , Color . BLACK ));
        placeNewPiece ( 'd' , 8 , new  King ( board , Color . BLACK ));
	}

}
