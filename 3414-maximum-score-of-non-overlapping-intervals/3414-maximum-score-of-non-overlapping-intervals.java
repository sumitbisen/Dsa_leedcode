class Solution {
    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();

        // [left, right, weight, originalIndex]
        int[][] a = new int[n][4];

        for (int i = 0; i < n; i++) {
            a[i][0] = intervals.get(i).get(0);
            a[i][1] = intervals.get(i).get(1);
            a[i][2] = intervals.get(i).get(2);
            a[i][3] = i;
        }

        // Sort by right endpoint
        Arrays.sort(a, (x, y) -> {
            if (x[1] != y[1])
                return Integer.compare(x[1], y[1]);
            return Integer.compare(x[3], y[3]);
        });

        // Right endpoints
        int[] ends = new int[n];
        for (int i = 0; i < n; i++) {
            ends[i] = a[i][1];
        }

        // prev[i] = number of intervals before i
        // whose right < current interval's left
        int[] prev = new int[n];

        for (int i = 0; i < n; i++) {
            prev[i] = lowerBound(ends, 0, i, a[i][0]);
        }

        // dp[k][i] = best answer using first i intervals
        // with at most k intervals
        State[][] dp = new State[5][n + 1];

        for (int k = 0; k <= 4; k++) {
            for (int i = 0; i <= n; i++) {
                dp[k][i] = new State(0, new ArrayList<>());
            }
        }

        for (int k = 1; k <= 4; k++) {
            for (int i = 1; i <= n; i++) {

                // Don't take current interval
                State skip = dp[k][i - 1];

                // Take current interval
                State take = dp[k - 1][prev[i - 1]];

                ArrayList<Integer> list = new ArrayList<>(take.indices);
                list.add(a[i - 1][3]);

                // Sort indices for lexicographical comparison
                Collections.sort(list);

                take = new State(
                    take.score + a[i - 1][2],
                    list
                );

                dp[k][i] = better(take, skip) ? take : skip;
            }
        }

        List<Integer> ans = dp[4][n].indices;

        int[] result = new int[ans.size()];
        for (int i = 0; i < ans.size(); i++) {
            result[i] = ans.get(i);
        }

        return result;
    }

    // First position in [left, right)
    // where arr[pos] >= target
    private int lowerBound(int[] arr, int left, int right, int target) {
        while (left < right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] >= target)
                right = mid;
            else
                left = mid + 1;
        }

        return left;
    }

    // Returns true if a is better than b
    private boolean better(State a, State b) {
        if (a.score != b.score) {
            return a.score > b.score;
        }

        // Same score -> lexicographically smaller
        return lexicographicallySmaller(a.indices, b.indices);
    }

    private boolean lexicographicallySmaller(
            List<Integer> a,
            List<Integer> b) {

        int n = Math.min(a.size(), b.size());

        for (int i = 0; i < n; i++) {
            if (!a.get(i).equals(b.get(i))) {
                return a.get(i) < b.get(i);
            }
        }

        return a.size() < b.size();
    }

    static class State {
        long score;
        ArrayList<Integer> indices;

        State(long score, ArrayList<Integer> indices) {
            this.score = score;
            this.indices = indices;
        }
    }
}