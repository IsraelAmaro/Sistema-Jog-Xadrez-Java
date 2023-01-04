package chess;

import boardgame.Position;

public class ChessPosition {
	
	private char column;
	private int row;
	public ChessPosition(char column, int row) {
		
		if(column< 'a' || column>'h' || row < 1 || row>8) {
			
			throw new ChessException("Erros instantianting ChessPosition. Valid values arefrom a1 to h8");
		}
		this.column = column;
		this.row = row;
	}
	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}
	
	//metodo que converte uma chessPosition para uma posição normal do tabuleiro (matriz)
	
	protected Position toPosition() {
		return new Position(8 - row, column - 'a');
	}
	
	//metodo inverso ao anterior; Agora convertemos uma posição normal do tabuleiro (matriz) para uma ChessPosition 
	
	protected static ChessPosition fromPosition(Position position){
		return new ChessPosition((char)('a' - position.getColumn()), 8 - position.getRow())	;	
	}
	@Override
	public String toString() {
		return "" + column + row ; 
		/* a String vazia no inicio da declaração de retorno, força o compilador 
		a entender que é uma concatenação de Strings */
	}	
	
}
