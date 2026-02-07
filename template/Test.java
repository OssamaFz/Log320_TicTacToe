import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        System.out.println("=== Tic Tac Toe Test ===\n");

        // Test 1: Board creation and play
        System.out.println("Test 1: Board creation and play");
        Board board = new Board();
        board.play(new Move(0, 0), Mark.X);
        board.play(new Move(1, 1), Mark.O);
        System.out.println("  Board created and moves played successfully.\n");

        // Test 2: Evaluate - no winner
        System.out.println("Test 2: Evaluate with no winner");
        int score = board.evaluate(Mark.X);
        assert score == 0 : "Expected 0, got " + score;
        System.out.println("  evaluate(X) = " + score + " (expected 0)\n");

        // Test 3: Evaluate - X wins
        System.out.println("Test 3: Evaluate with X winning");
        Board winBoard = new Board();
        winBoard.play(new Move(0, 0), Mark.X);
        winBoard.play(new Move(0, 1), Mark.X);
        winBoard.play(new Move(0, 2), Mark.X);
        int winScore = winBoard.evaluate(Mark.X);
        assert winScore == 100 : "Expected 100, got " + winScore;
        System.out.println("  evaluate(X) = " + winScore + " (expected 100)");
        int loseScore = winBoard.evaluate(Mark.O);
        assert loseScore == -100 : "Expected -100, got " + loseScore;
        System.out.println("  evaluate(O) = " + loseScore + " (expected -100)\n");

        // Test 4: Possible moves
        System.out.println("Test 4: Possible moves generation");
        Board moveBoard = new Board();
        moveBoard.play(new Move(0, 0), Mark.X);
        ArrayList<Move> moves = moveBoard.getPossibleMoves();
        assert moves.size() == 8 : "Expected 8 moves, got " + moves.size();
        System.out.println("  Possible moves after 1 play: " + moves.size() + " (expected 8)\n");

        // Test 5: Terminal state detection
        System.out.println("Test 5: Terminal state detection");
        assert !moveBoard.isTerminal() : "Board should not be terminal";
        System.out.println("  Non-terminal board detected correctly.");
        assert winBoard.isTerminal() : "Win board should be terminal";
        System.out.println("  Terminal (win) board detected correctly.\n");

        // Test 6: Unplay
        System.out.println("Test 6: Unplay");
        Board unplayBoard = new Board();
        Move m = new Move(1, 1);
        unplayBoard.play(m, Mark.X);
        unplayBoard.unplay(m);
        ArrayList<Move> allMoves = unplayBoard.getPossibleMoves();
        assert allMoves.size() == 9 : "Expected 9 moves after unplay, got " + allMoves.size();
        System.out.println("  Unplay works correctly. Moves available: " + allMoves.size() + " (expected 9)\n");

        // Test 7: MinMax from empty board (X plays first)
        System.out.println("Test 7: MinMax from empty board (X is MAX)");
        CPUPlayer cpuX = new CPUPlayer(Mark.X);
        Board emptyBoard = new Board();
        ArrayList<Move> bestMovesX = cpuX.getNextMoveMinMax(emptyBoard);
        System.out.println("  Number of best moves: " + bestMovesX.size());
        System.out.println("  Nodes explored (MinMax): " + cpuX.getNumOfExploredNodes());
        System.out.print("  Best moves: ");
        for (Move mv : bestMovesX) {
            System.out.print("(" + mv.getRow() + "," + mv.getCol() + ") ");
        }
        System.out.println("\n");

        // Test 8: Alpha-Beta from empty board (X plays first)
        System.out.println("Test 8: Alpha-Beta from empty board (X is MAX)");
        ArrayList<Move> bestMovesAB = cpuX.getNextMoveAB(emptyBoard);
        System.out.println("  Number of best moves: " + bestMovesAB.size());
        System.out.println("  Nodes explored (Alpha-Beta): " + cpuX.getNumOfExploredNodes());
        System.out.print("  Best moves: ");
        for (Move mv : bestMovesAB) {
            System.out.print("(" + mv.getRow() + "," + mv.getCol() + ") ");
        }
        System.out.println("\n");

        // Test 9: MinMax from a mid-game position
        System.out.println("Test 9: MinMax from mid-game position");
        Board midBoard = new Board();
        midBoard.play(new Move(0, 0), Mark.X);
        midBoard.play(new Move(1, 1), Mark.O);
        CPUPlayer cpuX2 = new CPUPlayer(Mark.X);
        ArrayList<Move> midMoves = cpuX2.getNextMoveMinMax(midBoard);
        System.out.println("  Nodes explored (MinMax): " + cpuX2.getNumOfExploredNodes());
        System.out.print("  Best moves: ");
        for (Move mv : midMoves) {
            System.out.print("(" + mv.getRow() + "," + mv.getCol() + ") ");
        }
        System.out.println("\n");

        // Test 10: Alpha-Beta from same mid-game position
        System.out.println("Test 10: Alpha-Beta from same mid-game position");
        CPUPlayer cpuX3 = new CPUPlayer(Mark.X);
        ArrayList<Move> midMovesAB = cpuX3.getNextMoveAB(midBoard);
        System.out.println("  Nodes explored (Alpha-Beta): " + cpuX3.getNumOfExploredNodes());
        System.out.print("  Best moves: ");
        for (Move mv : midMovesAB) {
            System.out.print("(" + mv.getRow() + "," + mv.getCol() + ") ");
        }
        System.out.println("\n");

        // Test 11: O as MAX player
        System.out.println("Test 11: O as MAX player from empty board");
        CPUPlayer cpuO = new CPUPlayer(Mark.O);
        Board emptyBoard2 = new Board();
        ArrayList<Move> bestMovesO = cpuO.getNextMoveMinMax(emptyBoard2);
        System.out.println("  Number of best moves: " + bestMovesO.size());
        System.out.println("  Nodes explored (MinMax): " + cpuO.getNumOfExploredNodes());
        System.out.print("  Best moves: ");
        for (Move mv : bestMovesO) {
            System.out.print("(" + mv.getRow() + "," + mv.getCol() + ") ");
        }
        System.out.println("\n");

        // Test 12: Verify MinMax and Alpha-Beta return same moves
        System.out.println("Test 12: Verify MinMax and Alpha-Beta return same moves");
        boolean sameResult = bestMovesX.size() == bestMovesAB.size();
        if (sameResult) {
            for (int i = 0; i < bestMovesX.size(); i++) {
                if (bestMovesX.get(i).getRow() != bestMovesAB.get(i).getRow() ||
                    bestMovesX.get(i).getCol() != bestMovesAB.get(i).getCol()) {
                    sameResult = false;
                    break;
                }
            }
        }
        System.out.println("  Same moves from both algorithms: " + sameResult + "\n");

        // Test 13: Block a winning move
        System.out.println("Test 13: CPU blocks opponent's winning move");
        Board blockBoard = new Board();
        blockBoard.play(new Move(0, 0), Mark.O);
        blockBoard.play(new Move(0, 1), Mark.O);
        // O has two in a row, X must block at (0,2)
        CPUPlayer cpuBlock = new CPUPlayer(Mark.X);
        ArrayList<Move> blockMoves = cpuBlock.getNextMoveMinMax(blockBoard);
        System.out.print("  Best moves for X: ");
        for (Move mv : blockMoves) {
            System.out.print("(" + mv.getRow() + "," + mv.getCol() + ") ");
        }
        System.out.println();
        System.out.println("  Nodes explored: " + cpuBlock.getNumOfExploredNodes() + "\n");

        System.out.println("=== All tests completed ===");
    }
}
