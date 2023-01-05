package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Pawn;
import chess.pieces.RooK;

public class ChessMatch {
	
	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkmate;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	public ChessMatch() {
	
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		check = false;
		initialSetup();
	}
		
	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}	

	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkmate;
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
		
		//Teste para saber se com a jogada fiquei em check
		if(testCheck(currentPlayer)) {
			
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself in check");
		}
		
		// Teste para saber se com a jogada o oponete está em check
		
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		if(testCheckMate(opponent(currentPlayer))) {
			checkmate = true;
		}
		else {
		nextTurn();
		}
		
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
		
		ChessPiece p =(ChessPiece)board.removePiece(source);
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		
		//teste se capturou alguma peça
		
		if(capturedPiece !=null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		return (ChessPiece) capturedPiece;		
	}
	
	// metodo para desfazer o movimento caso a jogada deixe o rei em cheque
	
	private void undoMove(Position source, Position target , Piece capturedPiece ) {
		
		ChessPiece p = (ChessPiece) board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);
		
		if(capturedPiece != null) {
			
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
	}
	
	//metodo para troca da turno 
	
	private void nextTurn() {
		turn ++;
		
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
		
	}
	
	// metodo para saber se a peça esta em check
	
	private Color opponent(Color color) {
		
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;		
	}
	
	//metodo que recebe as cordenadas do xadrez
	
	private void placeNewPiece(char column, int row, ChessPiece piece ) {
		board.placePiece(piece,new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}
	
	//metodo para localizar no tabuleiro a peça Rei de determinada cor
	
	private ChessPiece  king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		
		for(Piece p : list) {
			if(p instanceof King) {
				return (ChessPiece)p ;
			}
		}
		
		throw new IllegalStateException("There is no "+ color + "King on the board");
	}
	
	// metodo para farrer todas as peças adversarias ver as possiveis chgadas de cada peça e testar se alguma ameaça o Rei 
	
	private boolean testCheck(Color color) {
		
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		
		for(Piece p : opponentPieces) {
			
			boolean[][] mat = p.possibleMoves();
			if(mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}		
		return false;
	}
	
	//metodo para testar check Mate
		
	private boolean testCheckMate(Color color) {
		if(!testCheck(color)) {
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList()); 
		for (Piece p : list) {
			boolean[][] mat = p.possibleMoves();
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColumns(); j++) {
					if(mat[i][j]) {
						
						//fazendo um novo movimento para saber se exiset alguma possibilidade de sair do check
						Position source = ((ChessPiece)p).getChessPosition().toPosition();
						Position target = new Position(i,j);
						Piece capturedPiece = makeMove(source, target);
						
						//teste pra saber se ainda está em cheque 
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);
						
						if(!testCheck) {
							return false;
						}						
					}
				}
			}
		}
		return true;
	}	
	
	//metodo para colocar as peças no tabuleiro atravé do metodo PlaceNewPiece
	private void initialSetup() {
		placeNewPiece ( 'a' , 1 , new  RooK ( board , Color . WHITE ));	
		placeNewPiece ( 'c' , 1 , new  Bishop ( board , Color . WHITE ));
        placeNewPiece ( 'e' , 1 , new  King ( board , Color . WHITE )); 
        placeNewPiece ( 'f' , 1 , new  Bishop ( board , Color . WHITE ));
        placeNewPiece ( 'h' , 1 , new  RooK ( board , Color . WHITE ));

        placeNewPiece ( 'a' , 2 , new  Pawn ( board , Color . WHITE ));
        placeNewPiece ( 'b' , 2 , new  Pawn ( board , Color . WHITE ));
        placeNewPiece ( 'c' , 2 , new  Pawn ( board , Color . WHITE ));
        placeNewPiece ( 'd' , 2 , new  Pawn ( board , Color . WHITE ));
        placeNewPiece ( 'e' , 2 , new  Pawn ( board , Color . WHITE ));
        placeNewPiece ( 'f' , 2 , new  Pawn ( board , Color . WHITE ));
        placeNewPiece ( 'g' , 2 , new  Pawn ( board , Color . WHITE ));
        placeNewPiece ( 'h' , 2 , new  Pawn ( board , Color . WHITE ));
        
        placeNewPiece ( 'a' , 8 , new  RooK ( board , Color . BLACK ));
        placeNewPiece ( 'c' , 8 , new  Bishop ( board , Color . BLACK ));
        placeNewPiece ( 'd' , 8 , new  King ( board , Color . BLACK ));
        placeNewPiece ( 'f' , 8 , new  Bishop ( board , Color . BLACK ));
        
        placeNewPiece ( 'h' , 8 , new  RooK ( board , Color . BLACK ));
        placeNewPiece ( 'a' , 7 , new  Pawn ( board , Color . BLACK ));
        placeNewPiece ( 'b' , 7 , new  Pawn ( board , Color . BLACK ));
        placeNewPiece ( 'c' , 7 , new  Pawn ( board , Color . BLACK ));
        placeNewPiece ( 'd' , 7 , new  Pawn ( board , Color . BLACK ));
        placeNewPiece ( 'e' , 7 , new  Pawn ( board , Color . BLACK ));
        placeNewPiece ( 'f' , 7 , new  Pawn ( board , Color . BLACK ));
        placeNewPiece ( 'g' , 7 , new  Pawn ( board , Color . BLACK ));
        placeNewPiece ( 'h' , 7 , new  Pawn ( board , Color . BLACK ));        
    
       
	}

}
