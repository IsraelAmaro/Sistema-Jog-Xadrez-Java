package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class UI {
	
	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	//metodo para limpar tela 
	
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
	
	/*metodo para ler uma posição dada pelo usuário. Este metodo usará como argumento o Scanner 
	da classe principal para facilitar o processo*/
	
	public static ChessPosition readChessPosition(Scanner sc) {
		try {
		String s = sc.nextLine();
		char column = s.charAt(0);
		int row = Integer.parseInt(s.substring(1)); //recortando a string recebida
		return new ChessPosition(column, row);
		}
		catch(RuntimeException e) {			
			throw new InputMismatchException("ERROR readin ChessPosition. "
					+ "Valid values are from ai to h8");
		}		
	}
	// metodo para imprimir o chessMatch
	
	public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
		
		printBoard(chessMatch.getPieces());
		System.out.println();
		printcapturedPieces(captured);
		System.out.println();
		System.out.println("  turn: " + chessMatch.getTurn());
		
		if(!chessMatch.getCheckMate()) {			
		System.out.println("  Waiting player: " + chessMatch.getCurrentPlayer());	
		
			//testando se a partida entrou em estado de check
			if(!chessMatch.getCheck()) {
				System.out.println("   >>>> CHECK!!  <<<<");
			}
		}
		else {
			
			System.out.println("   >>>> #### CHECKMATE ####  <<<<");
			System.out.println("Winner: " + chessMatch.getCurrentPlayer());		
		}
				
	}
	
	// metodo para imprimir o tabuleiro
	public static void printBoard(ChessPiece[][] pieces) {
		for (int i = 0; i < pieces.length; i++) {
			System.out.print("  "+(8 - i) + " ");
			for (int j = 0; j < pieces.length; j++) {
				printPiece(pieces[i][j], false);
			}
			System.out.println();
		}

		System.out.println("    a b c d e f g h");
	}
	
	//sobrecarga do printBoard agora com as jogadaas possiveis coloridas
	
	public static void printBoard(ChessPiece[][] pieces, boolean [][] possibleMoves) {
		for (int i = 0; i < pieces.length; i++) {
			System.out.print("  "+(8 - i) + " ");
			for (int j = 0; j < pieces.length; j++) {
				printPiece(pieces[i][j], possibleMoves[i][j]);
			}
			System.out.println();
		}

		System.out.println("    a b c d e f g h");
	}
 
	// metodo auxiliar para imprimir uma peça
	private static void printPiece(ChessPiece piece, boolean background) {
		
		if(background) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		if ( piece == null ) {
			System.out.print ( "-" + ANSI_RESET);
        }
		else{
            if ( piece.getColor () == Color.WHITE ) {
            	System.out.print( ANSI_WHITE + piece + ANSI_RESET );
            }
            else {
                System.out.print( ANSI_YELLOW + piece + ANSI_RESET );
            }
        }
		System.out.print(" ");
	
	}
	
	//metodo para imprimir as peças capturadas
	
	private static void printcapturedPieces(List<ChessPiece> captured) {
		
		List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors
				.toList());
		
		List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors
				.toList());
		
		System.out.println("  Captured pieces: ");
		System.out.print("  White: ");
		System.out.print(ANSI_WHITE);
		System.out.println(Arrays.toString(white.toArray()));//Modelo padrão para imprimir um array no java 
		System.out.print(ANSI_RESET);
		System.out.print("  Black: ");
		System.out.print(ANSI_YELLOW);
		System.out.println(Arrays.toString(black.toArray()));//Modelo padrão para imprimir um array no java 
		System.out.print(ANSI_RESET);
		
	}
}
