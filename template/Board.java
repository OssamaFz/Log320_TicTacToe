import java.util.ArrayList;

// IMPORTANT: Il ne faut pas changer la signature des méthodes
// de cette classe, ni le nom de la classe.
// Vous pouvez par contre ajouter d'autres méthodes (ça devrait 
// être le cas)
class Board
{
    // taille de la grille
    public static  int BOARD_LENGTH = 3;
    private Mark[][] board;

    // Ne pas changer la signature de cette méthode
    public Board() {
        this.board = new Mark[BOARD_LENGTH][BOARD_LENGTH];
        for(int i=0; i<BOARD_LENGTH;i++){
            for(int j=0; j<BOARD_LENGTH; j++){
                this.board[i][j] = Mark.EMPTY;
            }
        }
    }

    // Place la pièce 'mark' sur le plateau, à la
    // position spécifiée dans Move
    //
    // Ne pas changer la signature de cette méthode
    public void play(Move m, Mark mark){
        int col = m.getCol();
        int row = m.getRow();
        this.board[row][col] = mark;
    }


    // retourne  100 pour une victoire
    //          -100 pour une défaite
    //           0   pour un match nul
    // Ne pas changer la signature de cette méthode
    public int evaluate(Mark mark){
        Mark opponent = (mark == Mark.X) ? Mark.O : Mark.X;

        if(isWinner(mark)){
            return 100;
        }else if(isWinner(opponent)){
            return -100;
        }else{
            return 0;
        }
    }

    // Verifie s'il ya alignement sur ligne colonne ou diagonale
    public boolean isWinner(Mark m){
        int countRow, countCol, countDiagonal1 = 0, countDiagonal2 = 0;
        int n = board.length;

        // ✅ Vérification lignes
        for(int i = 0; i < n; i++) {
            countRow = 0;
            for (int j = 0; j < board[i].length; j++) {
                if (m ==board[i][j]) {
                    countRow++;
                }
            }
            if (countRow == board[i].length) {
                return true;
            }
        }

        // ✅ Vérification colonnes
        for (int j = 0; j < board[0].length; j++) {
            countCol = 0;
            for (int i = 0; i < n; i++) {
                if (m == board[i][j]) {
                    countCol++;
                }
            }
            if (countCol == n) {
                return true;
            }
        }

        // ✅ Diagonale principale
        for (int i = 0; i < n; i++) {
            if (m ==board[i][i]) {
                countDiagonal1++;
            }
        }
        if (countDiagonal1 == n) {
            return true;
        }

        // ✅ Diagonale secondaire
        for (int i = 0; i < n; i++) {
            if (m == board[i][n - 1 - i]) {
                countDiagonal2++;
            }
        }
        if (countDiagonal2 == n) {
            return true;
        }

        return false;
    }

    //recupere le liste de toutes les cases vides disponibles
    public ArrayList<Move> getAllPossiblesMoves(){
        ArrayList<Move> list = new ArrayList<>();
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[0].length; j++){
                if(board[i][j] == Mark.EMPTY){
                    list.add(new Move(i,j));
                }

            }
        }
        return list;
    }

    //afficher la grille
    public void display() {
        for (int i = 0; i< BOARD_LENGTH; i++) {
            for (int j = 0; j < BOARD_LENGTH; j++) {
                System.out.print("| " + (board[i][j] == Mark.EMPTY ? " " : board[i][j]) + " ");

            }
            System.out.println("|");
            System.out.println("-----------");
        }
    }

    public boolean isFull (){
        return getAllPossiblesMoves().isEmpty();
    }
    public Mark getMark(int row, int col) {
        return this.board[row][col];
    }

}
