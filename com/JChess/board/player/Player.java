package com.JChess.board.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.JChess.board.Board;
import com.JChess.board.Move;
import com.JChess.piece.Alliance;
import com.JChess.piece.King;
import com.JChess.piece.Piece;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

public abstract class Player {
	
	protected final Board board;
	protected final King playerKing; //Keeping track of king
	protected final Collection<Move> legalMoves;
	private final boolean isInCheck;
	
	public Player(final Board board , final Collection<Move> legalMoves, Collection<Move> opponentMoves){
		this.board=board;
		this.playerKing=establishKing();
		this.legalMoves = ImmutableList.copyOf(Iterables.concat(legalMoves,calculateKingCastles(legalMoves,opponentMoves)));
		this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves.isEmpty());
	}
	
	public King getPlayerKing () {
		return this.getPlayerKing();	
	}
	
	public Collection<Move> getLegalMoves(){
		return this.legalMoves;
	};
	
	protected static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> moves) { 
		
		final List<Move> attackMoves = new ArrayList<>();
		
		for(final Move move : moves) {
			if(piecePosition == move.getDestinationCoordinate()) { //Checks if the destination coordinate overlaps with the king's position. If yes, then in check
				attackMoves.add(move);
			}
		}
		
		return ImmutableList.copyOf(attackMoves);
	}

	private King establishKing() { //	Ensures that there is a king for the player on the board thus a legal game state
		for(final Piece piece : getActivePieces()) {
			if(piece.getPieceType().isKing()) {
				return (King) piece;
			}
		}
		throw new RuntimeException("Should not reach here! Not a valid board.");
	}
	
	public boolean isMoveLegal(final Move move) {
		return this.legalMoves.contains(move);
	}
	
	public boolean isInCheck() {
		return this.isInCheck && !hasEscapedMoves();
	}
	
	protected boolean hasEscapedMoves() { //Calculate whether the king can escape from a check
		for(final Move move : this.legalMoves) {
			final MoveTransition transition = makeMove(move);
			if(transition.getMoveStatus().isDone()){
				return true;
			}
		}
		return false;
	}
	
	public boolean isInCheckMate() {
		return this.isInCheckMate();
	}
	
	public boolean inInStaleMate() {
		return !this.isInCheck && !hasEscapedMoves();
	}
	
	public boolean isCastled() {
		return false;
	}
	
	public MoveTransition makeMove(final Move move) { //Checks move status and board transition
		
		if(isMoveLegal(move)) {
			return new MoveTransition(this.board , move , MoveStatus.ILLEGAL_MOVE);
		}
		
		final Board transitionBoard = move.execute(); //Creates new board, alliance switches
		
		final Collection<Move> kingAttacks = Player.calculateAttacksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(), transitionBoard.currentPlayer().getLegalMoves());
		
		if(!kingAttacks.isEmpty()) {
			return new MoveTransition(this.board,move,MoveStatus.LEAVES_PLAYER_IN_CHECK);
		}
		
		return new MoveTransition(transitionBoard , move , MoveStatus.DONE);
	}

	public abstract Collection<Piece> getActivePieces();
	public abstract Alliance getAlliance();
	public abstract Player getOpponent();
	protected abstract Collection<Move> calculateKingCastles(Collection<Move> playerLegals,Collection<Move> oppenentsLegals);
	
	
}
