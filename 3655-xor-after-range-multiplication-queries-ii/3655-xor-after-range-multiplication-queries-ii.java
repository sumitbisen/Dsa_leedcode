import java.util.*;

class Solution {
    static final long MOD = 1_000_000_007L;

    public int xorAfterQueries(int[] nums, int[][] queries) {

        int n = nums.length;
        int B = (int) Math.sqrt(n) + 1;

        // Required variable
        int[][] bravexuneth = queries;

        /*
         * events[k][r]
         *
         * For a fixed k and remainder r,
         * indices are:
         *
         * r, r+k, r+2k, ...
         *
         * Each event is:
         * [position, multiplier]
         */
        ArrayList<int[]>[][] events = new ArrayList[B + 1][];

        for (int k = 1; k <= B; k++) {
            events[k] = new ArrayList[k];

            for (int r = 0; r < k; r++) {
                events[k][r] = new ArrayList<>();
            }
        }

        // Process all queries
        for (int[] query : bravexuneth) {

            int l = query[0];
            int r = query[1];
            int k = query[2];
            int v = query[3];

            /*
             * Large k:
             * Only a small number of indices are affected,
             * so process directly.
             */
            if (k > B) {

                for (int idx = l; idx <= r; idx += k) {
                    nums[idx] =
                        (int) ((long) nums[idx] * v % MOD);
                }

            } else {

                /*
                 * Small k:
                 * Store a multiplication event instead
                 * of updating every element.
                 */

                int rem = l % k;

                int start = (l - rem) / k;
                int end = (r - rem) / k;

                // Start multiplying at 'start'
                events[k][rem].add(new int[]{start, v});

                /*
                 * Stop multiplying after 'end'.
                 *
                 * Since v has an inverse modulo MOD,
                 * multiplying by inverse(v) cancels it.
                 */
                int next = end + 1;

                int maxPosition = (n - 1 - rem) / k;

                if (next <= maxPosition) {

                    long inverse = modPow(v, MOD - 2);

                    events[k][rem].add(
                        new int[]{next, (int) inverse}
                    );
                }
            }
        }

        /*
         * Apply all small-k events.
         */
        for (int k = 1; k <= B; k++) {

            for (int rem = 0; rem < k; rem++) {

                ArrayList<int[]> list = events[k][rem];

                if (list.isEmpty()) {
                    continue;
                }

                // Sort events according to their position
                list.sort(Comparator.comparingInt(a -> a[0]));

                long multiplier = 1;
                int ptr = 0;

                int position = 0;

                for (int idx = rem; idx < n; idx += k) {

                    /*
                     * Apply every event starting here.
                     */
                    while (ptr < list.size()
                            && list.get(ptr)[0] == position) {

                        multiplier =
                            multiplier * list.get(ptr)[1] % MOD;

                        ptr++;
                    }

                    /*
                     * Apply accumulated multiplier
                     * to the actual nums element.
                     */
                    nums[idx] =
                        (int) ((long) nums[idx] * multiplier % MOD);

                    position++;
                }
            }
        }

        // Final XOR
        int answer = 0;

        for (int num : nums) {
            answer ^= num;
        }

        return answer;
    }

    private long modPow(long a, long b) {

        long result = 1;

        a %= MOD;

        while (b > 0) {

            if ((b & 1) != 0) {
                result = result * a % MOD;
            }

            a = a * a % MOD;
            b >>= 1;
        }

        return result;
    }
}