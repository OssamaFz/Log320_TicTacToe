import java.util.ArrayList;

// IMPORTANT: Il ne faut pas changer la signature des méthodes
// de cette classe, ni le nom de la classe.
// Vous pouvez par contre ajouter d'autres méthodes (ça devrait
// être le cas)
class Board {

    private Mark[][] board;

    // Ne pas changer la signature de cette méthode
    public Board() {
        board = new Mark[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = Mark.EMPTY;
            }
        }
    }

    // Place la pièce 'mark' sur le plateau, à la
    // position spécifiée dans Move
    //
    // Ne pas changer la signature de cette méthode
    public void play(Move m, Mark mark) {
        board[m.getRow()][m.getCol()] = mark;
    }

    // Annule un coup (remet la case à EMPTY)
    public void unplay(Move m) {
        board[m.getRow()][m.getCol()] = Mark.EMPTY;
    }

    // Retourne la liste des coups possibles (cases vides)
    // Parcours de gauche à droite, de haut en bas
    public ArrayList<Move> getPossibleMoves() {
        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == Mark.EMPTY) {
                    moves.add(new Move(i, j));
                }
            }
        }
        return moves;
    }

    // Vérifie si le joueur 'mark' a gagné
    private boolean hasWon(Mark mark) {
        // Vérifier les lignes
        for (int i = 0; i < 3; i++) {
            if (
                board[i][0] == mark &&
                board[i][1] == mark &&
                board[i][2] == mark
            ) {
                return true;
            }
        }
        // Vérifier les colonnes
        for (int j = 0; j < 3; j++) {
            if (
                board[0][j] == mark &&
                board[1][j] == mark &&
                board[2][j] == mark
            ) {
                return true;
            }
        }
        // Vérifier les diagonales
        if (board[0][0] == mark && board[1][1] == mark && board[2][2] == mark) {
            return true;
        }
        if (board[0][2] == mark && board[1][1] == mark && board[2][0] == mark) {
            return true;
        }
        return false;
    }

    // Vérifie si la partie est terminée (victoire ou plateau plein)
    public boolean isTerminal() {
        if (hasWon(Mark.X) || hasWon(Mark.O)) {
            return true;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == Mark.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    // retourne  100 pour une victoire
    //          -100 pour une défaite
    //           0   pour un match nul
    // Ne pas changer la signature de cette méthode
    public int evaluate(Mark mark) {
        Mark opponent = (mark == Mark.X) ? Mark.O : Mark.X;
        if (hasWon(mark)) {
            return 100;
        }
        if (hasWon(opponent)) {
            return -100;
        }
        return 0;
    }
}
