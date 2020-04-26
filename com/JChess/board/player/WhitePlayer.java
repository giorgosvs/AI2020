package com.JChess.board.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.JChess.board.Board;
import com.JChess.board.Move;
import com.JChess.board.Tile;
import com.JChess.piece.Alliance;
import com.JChess.piece.Piece;
import com.JChess.piece.Rook;
import com.google.common.collect.ImmutableList;

public class WhitePlayer extends Player {

	public WhitePlayer(final Board board ,final Collection<Move> whiteStandardLegalMoves ,final Collection<Move> blackStandardLegalMoves) {
		super(board,whiteStandardLegalMoves , blackStandardLegalMoves);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getWhitePieces();
	}

	@Override
	public Alliance getAlliance() {
		return Alliance.WHITE;
	}
	
	public Player getOpponent() {
		return this.board.blackPlayer();
	}

	@Override
	protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals,final Collection<Move> oppenentsLegals) {
		
		final List<Move> kingCastles = new ArrayList<>();
		
		if(this.playerKing.isFirstMove() && !this.isInCheck()) {
			//white's King side castling move
			if(!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied()) {
				final Tile rookTile =  this.board.getTile(63); 
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
					if(Player.calculateAttacksOnTile(61, oppenentsLegals.isEmpty() && Player.calculateAttacksOnTile(62, oppenentsLegals.isEmpty() && rookTile.getPiece().getPieceType().isRook()))) {
						kingCastles.add(new Move.KingSideCastleMove(this.board , this.playerKing , 62, (Rook)rookTile.getPiece() ,rookTile.getTileCoordinate(),59));
					}
				}
			}
			//Queen's side castling move
			if(!this.board.getTile(59).isTileOccupied() && !this.board.getTile(58).isTileOccupied() && !this.board.getTile(57).isTileOccupied()) {
				final Tile rookTile = this.board.getTile(56);
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
					kingCastles.add(new Move.QueenSideCastleMove(this.board,this.playerKing,58,(Rook)rookTile.getPiece(),rookTile.getTileCoordinate(),59));
				}
			}
		}
		
		return ImmutableList.copyOf(kingCastles);
	}


	
}
