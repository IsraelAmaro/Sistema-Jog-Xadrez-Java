package boardgame;

public class Board {
	 private int rows;
	 private int columns;
	 private Piece[][] pieces;
	 
	public Board(int rows, int columns) {
		
		if(rows <1 || columns <1) {
			throw new BoardException("Error creating board: there must be at least 1 row and column");
		}
		
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public Piece piece(int row , int column) {
		
		if(!positionExists(row, column)) {
			throw new BoardException("Position not on the board");
		}
		return pieces[row][column];
	}
	
	//criando sobre carga par aretornar a peça pela posição
	
	public Piece piece(Position position) {
		
		if(!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}//teste se a posição existe
		
		return pieces[position.getRow()][position.getColumn()];
	}
	
	//metodo responsavel por colocar uma determinada peça em um adeterminada posição do tabuleiro
	
	public void placePiece(Piece piece, Position position) {
		
		if(thereIsAPiece(position)) {
			throw new BoardException("There is already a piece position "+ position);
		}
		
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}
	
	//metodos para saber se a posição existe ou não 
	
	private boolean positionExists(int row, int column) {
		//metodo auxiliar para testar se a posição esta contida dentro do tabuleiro
		return row >= 0 && row < rows && column>=0 && column < columns;
	}	
		 
	public boolean positionExists(Position position) { 		
		return positionExists(position.getRow(), position.getColumn());
	}
	
	//metodos para saber se na posição ja existe uma peça  ou não 	
	
	public boolean thereIsAPiece(Position position) {
		
		if(!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}//teste se a posição existe
		
		return piece(position) != null; 
		//metodo piece() está dentro desta classe e retorna a peça que esta na matriz na posição informada aqui neste metodo 
		
	}
	
	
}
