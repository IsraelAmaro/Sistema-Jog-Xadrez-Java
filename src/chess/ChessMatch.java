package chess;

import boardgame.Board;
import boardgame.Position;
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
	
	
	//metodo para colocar as peças no tabuleiro
	private void initialSetup() {
		placeNewPiece('a' , 8 ,new RooK(board, Color.WHITE) );
		placeNewPiece('d' , 8 ,new RooK(board, Color.WHITE) );
		placeNewPiece('h' , 8 ,new King(board, Color.WHITE) );
		
		placeNewPiece('a', 1, new RooK(board, Color.BLACK));
		placeNewPiece('h', 1, new RooK(board, Color.BLACK));
		placeNewPiece('e', 3, new King(board, Color.BLACK));
	}

}
