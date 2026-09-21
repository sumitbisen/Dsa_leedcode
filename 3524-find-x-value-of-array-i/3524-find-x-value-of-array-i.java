class Solution {
    public long[] resultArray(int[] nums, int k) {

        long[] ans = new long[k];

        // dp[r] = number of subarrays ending at previous index
        // whose product % k == r
        long[] dp = new long[k];

        for (int num : nums) {

            long[] next = new long[k];

            int value = num % k;

            // Start a new subarray with nums[i]
            next[value]++;

            // Extend previous subarrays
            for (int r = 0; r < k; r++) {

                if (dp[r] == 0)
                    continue;

                int newRemainder = (int) ((long) r * value % k);

                next[newRemainder] += dp[r];
            }

            // Add all subarrays ending at current index
            for (int r = 0; r < k; r++) {
                ans[r] += next[r];
            }

            dp = next;
        }

        return ans;
    }
}