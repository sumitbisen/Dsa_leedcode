
class Solution {
    static final int MOD = 1_000_000_007;

    public int sumCounts(int[] nums) {
        int n = nums.length;
        long ans = 0;

        int size = 1;
        while (size < n) size <<= 1;

        long[] sum = new long[2 * size];
        long[] lazy = new long[2 * size];
        long[] squares = new long[2 * size];

        java.util.HashMap<Integer, Integer> last =
            new java.util.HashMap<>();

        for (int i = 0; i < n; i++) {
            int left = last.getOrDefault(nums[i], -1) + 1;
            int right = i;

            update(1, 0, size - 1, left, right,
                   sum, squares, lazy);

            ans = (ans + squares[1]) % MOD;
            last.put(nums[i], i);
        }

        return (int) ans;
    }

    private void update(int node, int start, int end,
                        int l, int r, long[] sum,
                        long[] squares, long[] lazy) {
        if (l > end || r < start) return;

        if (l <= start && end <= r) {
            squares[node] = (squares[node]
                + 2 * sum[node] + (end - start + 1L)) % MOD;
            sum[node] = (sum[node] + end - start + 1L) % MOD;
            lazy[node]++;
            return;
        }

        push(node, start, end, sum, squares, lazy);

        int mid = (start + end) / 2;
        update(node * 2, start, mid, l, r,
               sum, squares, lazy);
        update(node * 2 + 1, mid + 1, end, l, r,
               sum, squares, lazy);

        sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;
        squares[node] = (squares[node * 2]
            + squares[node * 2 + 1]) % MOD;
    }

    private void push(int node, int start, int end,
                      long[] sum, long[] squares,
                      long[] lazy) {
        if (lazy[node] == 0 || start == end) return;

        int mid = (start + end) / 2;
        apply(node * 2, start, mid, lazy[node],
              sum, squares, lazy);
        apply(node * 2 + 1, mid + 1, end, lazy[node],
              sum, squares, lazy);

        lazy[node] = 0;
    }

    private void apply(int node, int start, int end,
                       long delta, long[] sum,
                       long[] squares, long[] lazy) {
        long len = end - start + 1L;
        long d = delta % MOD;

        squares[node] = (squares[node]
            + 2 * d % MOD * sum[node] % MOD
            + d * d % MOD * len % MOD) % MOD;

        sum[node] = (sum[node] + d * len) % MOD;
        lazy[node] = (lazy[node] + d) % MOD;
    }
}
