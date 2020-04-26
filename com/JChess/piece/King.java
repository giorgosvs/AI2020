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

public class King extends Piece{

	private final static int[] CANDIDATE_MOVE_COORDINATE = {-9 ,-8,-7 ,-1 ,1 ,7 ,8 ,9};
	
	public King(final int piecePosition,final Alliance pieceAlliance) {
		super(PieceType.KING,piecePosition, pieceAlliance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<Move> calculateLegalMoves(Board board) {
		
		final List<Move> legalMoves = new ArrayList<>();
		
		for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {
			final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
			
			if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				
				final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
				
				if(isFirstColumnExclusion(this.piecePosition,currentCandidateOffset) || isEighthColumnExclusion(this.piecePosition , currentCandidateOffset)){
					continue;
				}
				
				if(!candidateDestinationTile.isTileOccupied()) {
					legalMoves.add(new Move.MajorMove(board,this,candidateDestinationCoordinate)); //Non-attacking Legal Move
				} else {
					final Piece pieceAtDestination = candidateDestinationTile.getPiece();
					final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
					if(this.pieceAlliance != pieceAlliance) {
						legalMoves.add(new Move.AttackMove(board ,this ,candidateDestinationCoordinate ,pieceAtDestination)); //Attacking Legal move
					}
				}
			}
		}
		
		return ImmutableList.copyOf(legalMoves);
	}
	
	private static boolean isFirstColumnExclusion(final int currentPosition,final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && ( candidateOffset == -9 || candidateOffset == -1 || candidateOffset == 7 );
	}
	
	private static boolean isEighthColumnExclusion(final int currentPosition,final int candidateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && ( candidateOffset == -7 || candidateOffset == 1 || candidateOffset == 9 ); 
	}
	
	@Override 
	public String toString() {
		return PieceType.KING.toString();
	}

	@Override
	public Piece movePiece(final Move move) {
		return new King(move.getDestinationCoordinate(),move.getMovedPiece().getPieceAlliance());
	}
	
}
