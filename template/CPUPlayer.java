import java.util.ArrayList;

// IMPORTANT: Il ne faut pas changer la signature des méthodes
// de cette classe, ni le nom de la classe.
// Vous pouvez par contre ajouter d'autres méthodes (ça devrait 
// être le cas)
class CPUPlayer
{

    // Contient le nombre de noeuds visités (le nombre
    // d'appel à la fonction MinMax ou Alpha Beta)
    // Normalement, la variable devrait être incrémentée
    // au début de votre MinMax ou Alpha Beta.
    private int numExploredNodes;

    // Le joueur MAX (X ou O)
    private Mark cpu;



    // Le constructeur reçoit en paramètre le
    // joueur MAX (X ou O)
    public CPUPlayer(Mark cpu){
        this.cpu = cpu;
        this.numExploredNodes = 0;
    }

    // Ne pas changer cette méthode
    public int  getNumOfExploredNodes(){
        return numExploredNodes;
    }

    // Retourne la liste des coups possibles.  Cette liste contient
    // plusieurs coups possibles si et seuleument si plusieurs coups
    // ont le même score.
    public ArrayList<Move> getNextMoveMinMax(Board board)
    {
        int maxScore = Integer.MIN_VALUE, score =0;
        ArrayList<Move> listOfPossibleMoves = board.getAllPossiblesMoves();
        ArrayList<Move> listOfBestNodes = new ArrayList<>();
        Mark opponent = this.getNextPlayer(this.cpu);
        numExploredNodes = 0;

        for(Move m: listOfPossibleMoves){

            //le cpu joue
            board.play(m,this.cpu);
            //on verifie le jeu de l'adversaire
            score = miniMax(board, opponent);
            if(score > maxScore){
                listOfBestNodes.clear();
                listOfBestNodes.add(m);
                maxScore = score;
            }else if(maxScore == score){
                listOfBestNodes.add(m);
            }
            numExploredNodes++;
            board.play(m, Mark.EMPTY);
        }
        return listOfBestNodes;

    }

    // Retourne la liste des coups possibles.  Cette liste contient
    // plusieurs coups possibles si et seuleument si plusieurs coups
    // ont le même score.
    public ArrayList<Move> getNextMoveAB(Board board){
        numExploredNodes = 0;
        ArrayList<Move> bestMoves = new ArrayList<>();
        int maxScore = Integer.MIN_VALUE;

        ArrayList<Move> possibleMoves = board.getAllPossiblesMoves();

        for (Move m : possibleMoves) {
            board.play(m, this.cpu);

            // On appel de miniMax avec Alpha et Béta
            int score = miniMax(board, getNextPlayer(this.cpu), Integer.MIN_VALUE, Integer.MAX_VALUE);
            board.play(m, Mark.EMPTY);

            if (score > maxScore) {
                maxScore = score;
                bestMoves.clear();
                bestMoves.add(m);
            } else if (score == maxScore) {
                bestMoves.add(m);
            }
        }
        return bestMoves;
    }

    // Retourne le joueur qui va joue maintenent
    public Mark getNextPlayer(Mark m){
        return (m == Mark.X) ? Mark.O : Mark.X;
    }

    // retourne le score minimal ou maximal : Minimax classique
    private int miniMax(Board board, Mark currentMark) {
        int maxScore,minScore, score;
        ArrayList<Move> listOfpossibleMoves;

        numExploredNodes ++;
        score = board.evaluate(this.cpu);

        if(score == 100 || score == -100){
            return score;  //gagne quelque soit le joueur
        }

        listOfpossibleMoves = board.getAllPossiblesMoves();
        if(listOfpossibleMoves.isEmpty()){
            return 0; // jeu termine
        }

        if(currentMark == this.cpu){ // ie == Mark.X
            maxScore = Integer.MIN_VALUE;
            for(Move m : listOfpossibleMoves){
                board.play(m,currentMark);
                score = miniMax(board,getNextPlayer(currentMark));
                maxScore = Math.max(score, maxScore);
                board.play(m,Mark.EMPTY);
            }
            return maxScore;
        }else{
            minScore = Integer.MAX_VALUE;
            for(Move m : listOfpossibleMoves){
                board.play(m,currentMark);
                score = miniMax(board,this.getNextPlayer(currentMark));
                minScore = Math.min(score,minScore);
                board.play(m,Mark.EMPTY);
            }
            return minScore;
        }
    }

    // Minimax avec elagage Alpha beta
    public int miniMax(Board board, Mark currentMark, int alpha, int beta) {
        numExploredNodes++;
        int score = board.evaluate(this.cpu);

        if (score == 100 || score == -100 || board.getAllPossiblesMoves().isEmpty()) {
            return score;
        }

        ArrayList<Move> moves = board.getAllPossiblesMoves();
        if (currentMark == this.cpu) {
            int maxScore = Integer.MIN_VALUE;
            for (Move m : moves) {
                board.play(m, currentMark);
                score = miniMax(board, getNextPlayer(currentMark), alpha, beta);
                board.play(m, Mark.EMPTY);

                maxScore = Math.max(maxScore, score);
                alpha = Math.max(alpha, score);
                if (beta <= alpha) break; // Élagage
            }
            return maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;
            for (Move m : moves) {
                board.play(m, currentMark);
                score = miniMax(board, getNextPlayer(currentMark), alpha, beta);
                board.play(m, Mark.EMPTY);

                minScore = Math.min(minScore, score);
                beta = Math.min(beta, score);
                if (beta <= alpha) break; // Élagage
            }
            return minScore;
        }
    }

}
