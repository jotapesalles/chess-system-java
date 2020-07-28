package chess;
import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;
public class ChessMatch {
	private Board board;
	private int turn;
	private Color currentPlayer;
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public ChessMatch() {
		board = new Board(8,8);
		initialSetup();
		turn = 1;
		currentPlayer = Color.WHITE;
	}
	
	
	public ChessPiece[][] getPieces(){
		ChessPiece[][] chesspieces = new ChessPiece[board.getRows()][board.getColumns()];
		for(int i = 0;i<board.getRows();i++)
			for(int j = 0;j<board.getColumns();j++)
				chesspieces[i][j] = (ChessPiece) board.piece(i,j);
		return chesspieces;
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		ValidateSourcePosition(sourcePosition.toPosition());
		ValidateTargetPosition(sourcePosition.toPosition(), targetPosition.toPosition());
		Piece piece = board.RemovePiece(sourcePosition.toPosition());
		Piece capturedPiece = board.PlacePiece(piece, targetPosition.toPosition());
		nextTurn();
		return (ChessPiece) capturedPiece;
	}
	
	public boolean[][] PossibleMoves(ChessPosition sourcePosition){
		Position position = sourcePosition.toPosition();
		ValidateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private void ValidateSourcePosition(Position source) {
		Piece piece = board.piece(source);
		if(currentPlayer != ((ChessPiece) piece).getColor())
			throw new ChessException("The chosen piece is not yours.");
		if(!board.ThereIsAPiece(source))
			throw new ChessException("There is no piece on this source position.");
		if(!piece.isThereAnyPossibleMove())
			throw new ChessException("There is no any possible move.");
	}
	
	private void ValidateTargetPosition(Position source, Position target) {
		if(!this.board.piece(source).possibleMove(target))
			throw new ChessException("The chosen piece can't be moved to target.");
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.PlacePiece(piece, new ChessPosition(column,row).toPosition());
	}
	
	private void initialSetup() {
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}

}
