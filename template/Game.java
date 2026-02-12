import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Game {

    public static void main(String[] args) {
        // Initialisation
        Mark cpuMark = Mark.X;
        Mark opponentMark = Mark.O;
        CPUPlayer cpuPlayer = new CPUPlayer(cpuMark);
        Board board = new Board();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== BIENVENUE AU TIC-TAC-TOE ===\n");

        // Boucle principale : tant qu'il n'y a pas de vainqueur et qu'il reste de la place
        while (!board.isFull() && board.evaluate(cpuMark) == 0) {

            // --- 1. TOUR DE L'HUMAIN ---
            board.display();
            int row, col;
            boolean moveValid = false;

            do {
                System.out.println("\nC'est ton tour (" + opponentMark + ")");
                System.out.print("Entrer ligne (0-2) : ");
                row = scanner.nextInt();
                System.out.print("Entrer colonne (0-2) : ");
                col = scanner.nextInt();

                // Vérification de la validité
                if (row >= 0 && row < 3 && col >= 0 && col < 3 && board.getMark(row, col) == Mark.EMPTY) {
                    moveValid = true;
                } else {
                    System.out.println("Coup invalide ! La case est occupée ou hors limites.");
                }
            } while (!moveValid);

            board.play(new Move(row, col), opponentMark);

            // Vérifier si l'humain a gagné ou si c'est la fin avant le tour du CPU
            if (board.isFull() || board.evaluate(cpuMark) != 0) {
                break;
            }

            // --- 2. TOUR DE CPU ---
             ArrayList<Move> choices = cpuPlayer.getNextMoveAB(board);

            if (!choices.isEmpty()) {
                // On mélange pour que CPU ne soit pas prévisible si plusieurs coups sont parfaits
                Collections.shuffle(choices);
                Move bestMove = choices.get(0);

                board.play(bestMove, cpuMark);
                System.out.println("Jeu CPU: " + bestMove.getRow() + "," + bestMove.getCol());
                System.out.println("Scénarios analysés : " + cpuPlayer.getNumOfExploredNodes());
            }
        }

        // --- 3. FIN DE PARTIE ---
        board.display();
        System.out.println("\n=== PARTIE TERMINÉE ===");

        int scoreFinal = board.evaluate(cpuMark);
        if (scoreFinal == 100) {
            System.out.println("CPU a gagné ");
        } else if (scoreFinal == -100) {
            System.out.println("tu as gagné !");
        } else {
            System.out.println("Match nul ");
        }

        scanner.close();
    }
}