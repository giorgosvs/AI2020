package com.JChess.piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.JChess.board.Board;
import com.JChess.board.BoardUtils;
import com.JChess.board.Move;
import com.JChess.piece.Piece.PieceType;
import com.google.common.collect.ImmutableList;

public class Pawn extends Piece {

	private final static int[] CANDIDATE_MOVE_COORDINATE = {8 ,16 ,7 ,9};
	
	public Pawn(final int piecePosition,final Alliance pieceAlliance) {
		super(PieceType.PAWN,piecePosition, pieceAlliance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		
		final List<Move> legalMoves = new ArrayList<>();
		
		for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {
			
			final int candidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * currentCandidateOffset); //-8 for white +8 for black, This defines direction in board Offset.
			
			if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				continue;
			}
			
			if(currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) /*pawn moves one tile forward*/ {
				//TODO more work here
				legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
			} else if(currentCandidateOffset == 16 && this.isFirstMove() && 
					(BoardUtils.SECOND_RAW[this.piecePosition] && this.getPieceAlliance().isBlack()) 
					|| (BoardUtils.SEVENTH_RAW[this.piecePosition] && this.getPieceAlliance().isWhite())) /*pawn 2-tile jump at first move*/ {
				final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * 8);
				if(!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() && 
						!board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
						legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
				}
			} else if(currentCandidateOffset == 7 &&
					!((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite() ||
					(BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())))){
					
				if(board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
					if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
						//TODO
						legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
					}
				}
			} else if(currentCandidateOffset == 9 &&
					!((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack() ||
					(BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite())))) {
				if(board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
					if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
						//TODO
						legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
					}
				}
			}
		}
		
		return ImmutableList.copyOf(legalMoves);
	}
	
	@Override 
	public String toString() {
		return PieceType.PAWN.toString();
	}

	@Override
	public Piece movePiece(Move move) {
		return new Pawn(move.getDestinationCoordinate(),move.getMovedPiece().getPieceAlliance());
	}

}
