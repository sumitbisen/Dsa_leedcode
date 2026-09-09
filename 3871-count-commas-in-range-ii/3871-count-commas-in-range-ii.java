class Solution {
    public long countCommas(long n) {
        long ans = 0;
        long start = 1000;

        while (start <= n) {
            ans += n - start + 1;

            // Move to 1,000,000, 1,000,000,000, ...
            if (start > n / 1000) {
                break;
            }

            start *= 1000;
        }

        return ans;
    }
}