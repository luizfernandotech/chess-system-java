package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

    private ChessMatch chessMatch;

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    private boolean canMove(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().piece(position);
        return piece == null || piece.getColor() != getColor();
    }

    private boolean testRookCastling(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().piece(position);
        return piece != null && piece instanceof Rook && piece.getColor() == getColor() && piece.getMoveCount() == 0;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matrix = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position auxPosition = new Position(0, 0);

        // above
        auxPosition.setValues(position.getRow() - 1, position.getColumn());
        if (getBoard().positionExists(auxPosition) && canMove(auxPosition)) {
            matrix[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        // below
        auxPosition.setValues(position.getRow() + 1, position.getColumn());
        if (getBoard().positionExists(auxPosition) && canMove(auxPosition)) {
            matrix[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        // left
        auxPosition.setValues(position.getRow(), position.getColumn() - 1);
        if (getBoard().positionExists(auxPosition) && canMove(auxPosition)) {
            matrix[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        // right
        auxPosition.setValues(position.getRow(), position.getColumn() + 1);
        if (getBoard().positionExists(auxPosition) && canMove(auxPosition)) {
            matrix[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        // nw
        auxPosition.setValues(position.getRow() - 1, position.getColumn() - 1);
        if (getBoard().positionExists(auxPosition) && canMove(auxPosition)) {
            matrix[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        // ne
        auxPosition.setValues(position.getRow() - 1, position.getColumn() + 1);
        if (getBoard().positionExists(auxPosition) && canMove(auxPosition)) {
            matrix[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        // sw
        auxPosition.setValues(position.getRow() + 1, position.getColumn() - 1);
        if (getBoard().positionExists(auxPosition) && canMove(auxPosition)) {
            matrix[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        // se
        auxPosition.setValues(position.getRow() + 1, position.getColumn() + 1);
        if (getBoard().positionExists(auxPosition) && canMove(auxPosition)) {
            matrix[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        // special move castling
        if (getMoveCount() == 0 && !chessMatch.getCheck()) {
            // special move king side rook
            Position positionRook1 = new Position(position.getRow(), position.getColumn() + 3);
            if (testRookCastling(positionRook1)) {
                Position p1 = new Position(position.getRow(), position.getColumn() + 1);
                Position p2 = new Position(position.getRow(), position.getColumn() + 2);
                if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
                    matrix[position.getRow()][position.getColumn() + 2] = true;
                }
            }

            // special move queen side rook
            Position positionRook2 = new Position(position.getRow(), position.getColumn() - 4);
            if (testRookCastling(positionRook2)) {
                Position p1 = new Position(position.getRow(), position.getColumn() - 1);
                Position p2 = new Position(position.getRow(), position.getColumn() - 2);
                Position p3 = new Position(position.getRow(), position.getColumn() - 3);
                if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
                    matrix[position.getRow()][position.getColumn() - 2] = true;
                }
            }
        }

        return matrix;
    }

    @Override
    public String toString() {
        return "K";
    }
}
