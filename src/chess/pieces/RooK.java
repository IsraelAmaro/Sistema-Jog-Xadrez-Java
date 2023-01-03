package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class RooK extends ChessPiece {

	public RooK(Board board, Color color) {
		super(board, color);		
	}

	@Override
	public String toString() {
		return "R";
	}	

}
