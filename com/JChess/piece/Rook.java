package com.JChess.piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.JChess.board.Board;
import com.JChess.board.BoardUtils;
import com.JChess.board.Move;
import com.JChess.board.Tile;
import com.JChess.piece.Piece.PieceType;
import com.google.common.collect.ImmutableList;

public class Rook extends Piece {
	

	private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = { -8 ,-1 ,1 ,8 };

	public Rook(int piecePosition, Alliance pieceAlliance) {
		super(PieceType.ROOK,piecePosition, pieceAlliance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {

		final List<Move> legalMoves = new ArrayList<>();

		for(final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES) {
			int candidateDestinationCoordinate = this.piecePosition;
			while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				if(isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset ) ||
						isEighthColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)) {
					break;
				}
				
				candidateDestinationCoordinate += candidateCoordinateOffset;
				
				if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
					final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
					if (!candidateDestinationTile.isTileOccupied()) {
						legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate)); // Non-attacking
																											// Legal
																											// Move
					} else {
						final Piece pieceAtDestination = candidateDestinationTile.getPiece();
						final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
						if (this.pieceAlliance != pieceAlliance) {
							legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate,
									pieceAtDestination)); // Attacking Legal move
						}
					}
				}
				break;
			}
		}
		
		return ImmutableList.copyOf(legalMoves);
	}
	
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -1);
	}
	
	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 1);
	}
	
	@Override 
	public String toString() {
		return PieceType.ROOK.toString();
	}

	@Override
	public Piece movePiece(final Move move) {
		return new Rook(move.getDestinationCoordinate(),move.getMovedPiece().getPieceAlliance());
	}
	
}
