import java.util.ArrayList;

// IMPORTANT: Il ne faut pas changer la signature des méthodes
// de cette classe, ni le nom de la classe.
// Vous pouvez par contre ajouter d'autres méthodes (ça devrait 
// être le cas)
class CPUPlayer
{
    private Mark maxPlayer;
    private Mark minPlayer;

    // Contient le nombre de noeuds visités (le nombre
    // d'appel à la fonction MinMax ou Alpha Beta)
    // Normalement, la variable devrait être incrémentée
    // au début de votre MinMax ou Alpha Beta.
    private int numExploredNodes;

    // Le constructeur reçoit en paramètre le
    // joueur MAX (X ou O)
    public CPUPlayer(Mark cpu){
        maxPlayer = cpu;
        minPlayer = (cpu == Mark.X) ? Mark.O : Mark.X;
    }

    private int minMax(Board board, boolean isMaxTurn) {

        numExploredNodes++;

        if (board.isTerminal()) {
            return board.evaluate(maxPlayer);
        }
        if (isMaxTurn) {
            int bestValue = Integer.MIN_VALUE;
            for(Move move : board.getPossibleMoves()) {
                Board next = new Board(board);
                next.play(move, maxPlayer);
                bestValue = Math.max(bestValue, minMax(next,false));
            }
            return bestValue;
        }
        else {
            int bestValue = Integer.MAX_VALUE;
            for(Move move : board.getPossibleMoves()) {
                Board next = new Board(board);
                next.play(move, minPlayer);
                bestValue = Math.min(bestValue, minMax(next, true));
            }
            return bestValue;
        }
    }
    // Ne pas changer cette méthode
    public int   getNumOfExploredNodes(){
        return numExploredNodes;
    }

    // Retourne la liste des coups possibles.  Cette liste contient
    // plusieurs coups possibles si et seuleument si plusieurs coups
    // ont le même score.
    public ArrayList<Move> getNextMoveMinMax(Board board)
    {
        numExploredNodes = 0;

        ArrayList<Move> bestMoves = new ArrayList<>();
        int bestValue = Integer.MIN_VALUE;

        for(Move move : board.getPossibleMoves()) {
            Board next = new Board(board);
            next.play(move, maxPlayer);

            int value = minMax(next, false);

            if(value > bestValue) {
                bestValue = value;
                bestMoves.clear();;
                bestMoves.add(move);
            }
        }
        return bestMoves;
    }

    private int alphaBeta(Board board, boolean isMaxTurn, int alpha, int beta) {
        numExploredNodes++;

        if (board.isTerminal()) {
            return board.evaluate(maxPlayer);
        }
        if (isMaxTurn) {
            int value = Integer.MIN_VALUE;

            for(Move move : board.getPossibleMoves()) {
                Board next = new Board(board);
                next.play(move, maxPlayer);

                value = Math.max(value, alphaBeta(next, false, alpha, beta));
                alpha = Math.max(alpha,value);
                if ( alpha >= beta)
                    break;
            }
            return value;
        }
        else {
            int value = Integer.MAX_VALUE;

            for(Move move : board.getPossibleMoves()) {
                Board next = new Board(board);
                next.play(move, minPlayer);

                value = Math.min(beta, value);
                alphaBeta(next,true, alpha, beta);

                beta = Math.min(beta, value);
                if (alpha >= beta)
                    break;
            }
            return value;
        }
    }

    // Retourne la liste des coups possibles.  Cette liste contient
    // plusieurs coups possibles si et seuleument si plusieurs coups
    // ont le même score.
    public ArrayList<Move> getNextMoveAB(Board board){
        numExploredNodes = 0;

        ArrayList<Move> bestMoves = new ArrayList<>();
        int bestValue = Integer.MIN_VALUE;

        for(Move move : board.getPossibleMoves()) {
            Board next = new Board(board);
            next.play(move, maxPlayer);
            int value = alphaBeta(next, false,Integer.MIN_VALUE, Integer.MAX_VALUE);

            if(value > bestValue) {
                bestValue = value;
                bestMoves.clear();;
                bestMoves.add(move);
            }
        }
        return bestMoves;
    }
}
