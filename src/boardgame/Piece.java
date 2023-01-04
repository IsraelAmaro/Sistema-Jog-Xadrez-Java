package boardgame;

public abstract class Piece {
	protected Position position;
	private Board board;
	
	public Piece(Board board) {
	
		this.board = board;
		position = null;
	}

	protected Board getBoard() {
		return board;
	}
	
	//metodo para saber quais os possiveis movimentos
	
	public abstract boolean[][] possibleMoves();
	
	//metodo para saber se a peça pode mover para uma dada posição
	
	public boolean possibleMove(Position position) {
		
		return possibleMoves()[position.getRow()][position.getColumn()];
	}
	
	//metodo para saber se há possibilidade de ao menos um movimento.
	public boolean isThereAnyPossibleMove() {
		boolean[][] mat = possibleMoves();
		
		for(int i = 0; i < mat.length; i++) {
			for(int j = 0; j < mat.length; j++) {
				if(mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
	
}
