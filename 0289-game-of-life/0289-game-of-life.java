class Solution {
    public void gameOfLife(int[][] board) {
        int m = board.length;
        int n = board[0].length;

        int[][] dirs = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1},  {1, 0},  {1, 1}
        };

        // 0 = dead -> dead
        // 1 = live -> live
        // 2 = live -> dead
        // 3 = dead -> live

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                int liveNeighbors = 0;

                for (int[] dir : dirs) {
                    int ni = i + dir[0];
                    int nj = j + dir[1];

                    if (ni >= 0 && ni < m &&
                        nj >= 0 && nj < n &&
                        (board[ni][nj] == 1 || board[ni][nj] == 2)) {
                        liveNeighbors++;
                    }
                }

                // Current cell is alive
                if (board[i][j] == 1) {
                    if (liveNeighbors < 2 || liveNeighbors > 3) {
                        board[i][j] = 2; // live -> dead
                    }
                }

                // Current cell is dead
                else {
                    if (liveNeighbors == 3) {
                        board[i][j] = 3; // dead -> live
                    }
                }
            }
        }

        // Convert encoded states to final states
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 2) {
                    board[i][j] = 0;
                } else if (board[i][j] == 3) {
                    board[i][j] = 1;
                }
            }
        }
    }
}