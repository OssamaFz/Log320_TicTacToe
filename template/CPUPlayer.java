import java.util.ArrayList;

// IMPORTANT: Il ne faut pas changer la signature des méthodes
// de cette classe, ni le nom de la classe.
// Vous pouvez par contre ajouter d'autres méthodes (ça devrait
// être le cas)
class CPUPlayer {

    // Contient le nombre de noeuds visités (le nombre
    // d'appel à la fonction MinMax ou Alpha Beta)
    // Normalement, la variable devrait être incrémentée
    // au début de votre MinMax ou Alpha Beta.
    private int numExploredNodes;

    // Le joueur MAX (X ou O)
    private Mark cpuMark;
    // Le joueur MIN (l'adversaire)
    private Mark opponentMark;

    // Le constructeur reçoit en paramètre le
    // joueur MAX (X ou O)
    public CPUPlayer(Mark cpu) {
        this.cpuMark = cpu;
        this.opponentMark = (cpu == Mark.X) ? Mark.O : Mark.X;
    }

    // Ne pas changer cette méthode
    public int getNumOfExploredNodes() {
        return numExploredNodes;
    }

    // Retourne la liste des coups possibles.  Cette liste contient
    // plusieurs coups possibles si et seuleument si plusieurs coups
    // ont le même score.
    public ArrayList<Move> getNextMoveMinMax(Board board) {
        numExploredNodes = 0;

        ArrayList<Move> bestMoves = new ArrayList<Move>();
        int bestScore = Integer.MIN_VALUE;

        ArrayList<Move> possibleMoves = board.getPossibleMoves();

        for (Move m : possibleMoves) {
            board.play(m, cpuMark);
            int score = minimax(board, false);
            board.unplay(m);

            if (score > bestScore) {
                bestScore = score;
                bestMoves.clear();
                bestMoves.add(m);
            } else if (score == bestScore) {
                bestMoves.add(m);
            }
        }

        return bestMoves;
    }

    // Algorithme Minimax récursif
    private int minimax(Board board, boolean isMaximizing) {
        numExploredNodes++;

        // Si la position est terminale, retourner l'évaluation
        if (board.isTerminal()) {
            return board.evaluate(cpuMark);
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            ArrayList<Move> possibleMoves = board.getPossibleMoves();
            for (Move m : possibleMoves) {
                board.play(m, cpuMark);
                int score = minimax(board, false);
                board.unplay(m);
                if (score > bestScore) {
                    bestScore = score;
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            ArrayList<Move> possibleMoves = board.getPossibleMoves();
            for (Move m : possibleMoves) {
                board.play(m, opponentMark);
                int score = minimax(board, true);
                board.unplay(m);
                if (score < bestScore) {
                    bestScore = score;
                }
            }
            return bestScore;
        }
    }

    // Retourne la liste des coups possibles.  Cette liste contient
    // plusieurs coups possibles si et seuleument si plusieurs coups
    // ont le même score.
    public ArrayList<Move> getNextMoveAB(Board board) {
        numExploredNodes = 0;

        ArrayList<Move> bestMoves = new ArrayList<Move>();
        int bestScore = Integer.MIN_VALUE;

        ArrayList<Move> possibleMoves = board.getPossibleMoves();

        for (Move m : possibleMoves) {
            board.play(m, cpuMark);
            int score = alphaBeta(
                board,
                false,
                Integer.MIN_VALUE,
                Integer.MAX_VALUE
            );
            board.unplay(m);

            if (score > bestScore) {
                bestScore = score;
                bestMoves.clear();
                bestMoves.add(m);
            } else if (score == bestScore) {
                bestMoves.add(m);
            }
        }

        return bestMoves;
    }

    // Algorithme Alpha-Beta récursif
    private int alphaBeta(
        Board board,
        boolean isMaximizing,
        int alpha,
        int beta
    ) {
        numExploredNodes++;

        // Si la position est terminale, retourner l'évaluation
        if (board.isTerminal()) {
            return board.evaluate(cpuMark);
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            ArrayList<Move> possibleMoves = board.getPossibleMoves();
            for (Move m : possibleMoves) {
                board.play(m, cpuMark);
                int score = alphaBeta(board, false, alpha, beta);
                board.unplay(m);
                if (score > bestScore) {
                    bestScore = score;
                }
                if (bestScore > alpha) {
                    alpha = bestScore;
                }
                if (beta <= alpha) {
                    break;
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            ArrayList<Move> possibleMoves = board.getPossibleMoves();
            for (Move m : possibleMoves) {
                board.play(m, opponentMark);
                int score = alphaBeta(board, true, alpha, beta);
                board.unplay(m);
                if (score < bestScore) {
                    bestScore = score;
                }
                if (bestScore < beta) {
                    beta = bestScore;
                }
                if (beta <= alpha) {
                    break;
                }
            }
            return bestScore;
        }
    }
}
