import java.util.ArrayList;

// IMPORTANT: Il ne faut pas changer la signature des méthodes
// de cette classe, ni le nom de la classe.
// Vous pouvez par contre ajouter d'autres méthodes (ça devrait 
// être le cas)
class Board
{
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
    public void play(Move m, Mark mark){
        board[m.getRow()][m.getCol()] = mark;
    }
    public Board(Board other) {

        board = new Mark[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = other.board[i][j];
            }
        }
    }

    public ArrayList<Move> getPossibleMoves() {
        ArrayList<Move> moves = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == Mark.EMPTY) {
                    moves.add(new Move(i,j));
                }
            }
        }
        return moves;
    }

    public boolean isFull() {

        return getPossibleMoves().isEmpty();
    }

    public boolean hasWon(Mark mark) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == mark && board[i][1] == mark && board[i][2] == mark)
                return true;
        }
        for (int j = 0; j < 3; j++) {
            if (board[0][j] == mark && board[1][j] == mark && board[2][j] == mark)
                return true;
        }
        if (board[0][0] == mark && board[1][1] == mark && board[2][2] == mark)
            return true;

        return false;
    }

    public boolean isTerminal() {
        return hasWon(Mark.X) || hasWon(Mark.O) || isFull();
    }
    // retourne  100 pour une victoire
    //          -100 pour une défaite
    //           0   pour un match nul
    // Ne pas changer la signature de cette méthode
    public int evaluate(Mark mark){
        Mark min = (mark == Mark.X) ? Mark.O : Mark.X;

        if(hasWon(mark))
            return 100;

        if(hasWon(min))
            return -100;

        return 0;
    }
}
