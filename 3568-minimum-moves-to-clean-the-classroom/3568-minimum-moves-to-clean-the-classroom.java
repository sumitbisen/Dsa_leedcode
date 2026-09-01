import java.util.*;

class Solution {

    static class State {
        int r, c;
        int mask;
        int energy;
        int moves;

        State(int r, int c, int mask, int energy, int moves) {
            this.r = r;
            this.c = c;
            this.mask = mask;
            this.energy = energy;
            this.moves = moves;
        }
    }

    public int minMoves(String[] classroom, int energy) {

        int m = classroom.length;
        int n = classroom[0].length();

        int sr = 0, sc = 0;

        // Give every litter a bit number
        int litterCount = 0;

        int[][] litterId = new int[m][n];
        for (int[] row : litterId) {
            Arrays.fill(row, -1);
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                char ch = classroom[i].charAt(j);

                if (ch == 'S') {
                    sr = i;
                    sc = j;
                }

                if (ch == 'L') {
                    litterId[i][j] = litterCount++;
                }
            }
        }

        // No litter to collect
        if (litterCount == 0) {
            return 0;
        }

        int allCollected = (1 << litterCount) - 1;

        /*
         * visited[r][c][mask][energy]
         *
         * energy ranges from 0 to maximum energy.
         */
        boolean[][][][] visited =
                new boolean[m][n][1 << litterCount][energy + 1];

        Queue<State> queue = new LinkedList<>();

        // Starting state
        visited[sr][sc][0][energy] = true;
        queue.offer(new State(sr, sc, 0, energy, 0));

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!queue.isEmpty()) {

            State cur = queue.poll();

            // All litter collected
            if (cur.mask == allCollected) {
                return cur.moves;
            }

            // If energy is 0, we can only continue from R
            if (cur.energy == 0 &&
                classroom[cur.r].charAt(cur.c) != 'R') {
                continue;
            }

            for (int d = 0; d < 4; d++) {

                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];

                // Outside grid
                if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                    continue;
                }

                // Obstacle
                if (classroom[nr].charAt(nc) == 'X') {
                    continue;
                }

                // Need 1 energy to make a move
                if (cur.energy == 0) {
                    continue;
                }

                int newEnergy = cur.energy - 1;

                int newMask = cur.mask;

                // Collect litter
                if (classroom[nr].charAt(nc) == 'L') {
                    int id = litterId[nr][nc];
                    newMask |= (1 << id);
                }

                // Reset energy on R
                if (classroom[nr].charAt(nc) == 'R') {
                    newEnergy = energy;
                }

                if (!visited[nr][nc][newMask][newEnergy]) {

                    visited[nr][nc][newMask][newEnergy] = true;

                    queue.offer(
                        new State(
                            nr,
                            nc,
                            newMask,
                            newEnergy,
                            cur.moves + 1
                        )
                    );
                }
            }
        }

        return -1;
    }
}