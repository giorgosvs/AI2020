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

public class Knight extends Piece{
	
	private final static int[] CANDIDATE_MOVE_COORDINATES = { -17, -15, -10, -6, 6, 10, 15, 17}; //Possible Tile Offset
	
	public Knight(final int piecePosition,final Alliance pieceAlliance) {
		super(PieceType.KNIGHT,piecePosition, pieceAlliance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		
		final List<Move> legalMoves = new ArrayList<>();
		
		for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
			final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
				if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
					
					if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) 
							|| isSecondColumnExclusion(this.piecePosition, currentCandidateOffset) 
							|| isSeventhColumnExclusion(this.piecePosition, currentCandidateOffset) 
							|| isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
						continue;
					}
					
					final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
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
		return BoardUtils.FIRST_COLUMN[currentPosition] && ( candidateOffset == -17 || candidateOffset == -10 || candidateOffset == 6  || candidateOffset == 15 );
	}
	
	private static boolean isSecondColumnExclusion(final int currentPosition,final int candidateOffset) {
		return BoardUtils.SECOND_COLUMN[currentPosition] && ( candidateOffset == -10 || candidateOffset == 6 ); 
	}
	
	private static boolean isSeventhColumnExclusion(final int currentPosition,final int candidateOffset) {
		return BoardUtils.SEVENTH_COLUMN[currentPosition] && ( candidateOffset == -6 || candidateOffset == 10 );
	}
	
	private static boolean isEighthColumnExclusion(final int currentPosition,final int candidateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && ( candidateOffset == -15 || candidateOffset == -6  || candidateOffset == 10 || candidateOffset == 17 );
	}
	
	@Override 
	public String toString() {
		return PieceType.KNIGHT.toString();
	}

	@Override
	public Piece movePiece(Move move) {
		return new Knight(move.getDestinationCoordinate(),move.getMovedPiece().getPieceAlliance());
	}
	
}
