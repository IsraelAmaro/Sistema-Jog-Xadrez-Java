package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.RooK;

public class ChessMatch {
	
	private int turn;
	private Color currentPlayer;
	private Board board;
	
	public ChessMatch() {
	
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}
		
	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	public ChessPiece[][] getPieces(){
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
	
	//metodo para indicar quais as jogadas possiveis
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
				
	}
	
	//metodo para mover as peças
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source); //operção para validar essa posição
		validateTargetPosition(source,target);//operção para validar o destino dessa posição
		Piece capturedPiece = makeMove(source, target);//operação responsavel por realizar o movimento da peça
		
		nextTurn();
		
		return (ChessPiece)capturedPiece;
	}
	
	private void validateSourcePosition(Position position) {
		
		if(!board.thereIsAPiece(position)) {
			
			throw new ChessException("There is no piece on source position. ");
		}
		
		if(currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {
			throw new ChessException("The chosen piece is not yours");
		}
		
		if(!board.piece(position).isThereAnyPossibleMove()) {
			
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	
	private void validateTargetPosition(Position source, Position target) {
		
		if(!board.piece(source).possibleMove(target)) {
			
			throw new ChessException("The chose piece cant't move to target position");
		}
	}
	
	private Piece makeMove(Position source, Position target) {
		Piece p =board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		return (ChessPiece) capturedPiece;
	}
	//metodo para troca da turno 
	
	private void nextTurn() {
		turn ++;
		
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
		
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
