class Solution {
    public int distinctSubseqII(String s) {
        final int MOD = 1_000_000_007;

        // dp[i] = number of distinct subsequences using first i characters
        // dp[0] = 1 represents the empty subsequence
        long[] dp = new long[s.length() + 1];
        dp[0] = 1;

        // last[c] = number of distinct subsequences before the
        // previous occurrence of character c
        long[] last = new long[26];

        for (int i = 1; i <= s.length(); i++) {
            int c = s.charAt(i - 1) - 'a';

            // Every existing subsequence can either:
            // 1. not use current character
            // 2. append current character
            dp[i] = (2 * dp[i - 1]) % MOD;

            // Remove duplicates created by previous occurrence
            dp[i] = (dp[i] - last[c] + MOD) % MOD;

            // Store the number of subsequences before this occurrence
            last[c] = dp[i - 1];
        }

        // Remove the empty subsequence
        return (int) ((dp[s.length()] - 1 + MOD) % MOD);
    }
}