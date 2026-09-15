class Solution {
    public int maxPalindromes(String s, int k) {
        int n = s.length();

        // dp[i] = maximum number of palindromes
        // that can be selected from s[0...i-1]
        int[] dp = new int[n + 1];

        for (int i = 0; i < n; i++) {

            // Don't start a palindrome at i
            dp[i + 1] = Math.max(dp[i + 1], dp[i]);

            // Check palindrome of length k
            if (i + k <= n && isPalindrome(s, i, i + k - 1)) {
                dp[i + k] = Math.max(dp[i + k], dp[i] + 1);
            }

            // Check palindrome of length k + 1
            if (i + k + 1 <= n && isPalindrome(s, i, i + k)) {
                dp[i + k + 1] = Math.max(dp[i + k + 1], dp[i] + 1);
            }
        }

        return dp[n];
    }

    private boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}