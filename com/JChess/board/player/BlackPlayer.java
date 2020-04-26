package com.JChess.board.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.JChess.board.Board;
import com.JChess.board.Move;
import com.JChess.board.Tile;
import com.JChess.board.Move.KingSideCastleMove;
import com.JChess.piece.Alliance;
import com.JChess.piece.Piece;
import com.JChess.piece.Rook;
import com.google.common.collect.ImmutableList;

public class BlackPlayer extends Player{
	
	public BlackPlayer(final Board board ,final Collection<Move> whiteStandardLegalMoves ,final  Collection<Move> blackStandardLegalMoves ) {
		super(board,blackStandardLegalMoves,whiteStandardLegalMoves);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getBlackPieces();
	}

	@Override
	public Alliance getAlliance() {
		return Alliance.BLACK;
	}
	
	public Player getOpponent() {
		return this.board.whitePlayer();
	}

	@Override
	final protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals,final Collection<Move> oppenentsLegals) {
		final List<Move> kingCastles = new ArrayList<>();
		//Black's king side castling move
		if(this.playerKing.isFirstMove() && !this.isInCheck()) {
			//white's King side castling move
			if(!this.board.getTile(5).isTileOccupied() && !this.board.getTile(6).isTileOccupied()) {
				final Tile rookTile =  this.board.getTile(7); 
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
					if(Player.calculateAttacksOnTile(5, oppenentsLegals.isEmpty() && Player.calculateAttacksOnTile(62, oppenentsLegals.isEmpty() && rookTile.getPiece().getPieceType().isRook()))) {
						kingCastles.add(new Move.KingSideCastleMove(this.board , this.playerKing , 6, (Rook)rookTile.getPiece() ,rookTile.getTileCoordinate(),5));
					}
				}
			}
			//Queen's side castling move
			if(!this.board.getTile(1).isTileOccupied() && !this.board.getTile(2).isTileOccupied() && !this.board.getTile(3).isTileOccupied()) {
				final Tile rookTile = this.board.getTile(0);
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
					kingCastles.add(new Move.QueenSideCastleMove(this.board , this.playerKing , 2, (Rook)rookTile.getPiece() ,rookTile.getTileCoordinate(),3));
				}
			}
		}
		
		return ImmutableList.copyOf(kingCastles);
	}
	
}
