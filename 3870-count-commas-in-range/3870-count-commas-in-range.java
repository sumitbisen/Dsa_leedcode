class Solution {
    public int countCommas(int n) {
        long total = 0;
        long threshold = 1000;

        while (threshold <= n) {
            total += n - threshold + 1;
            threshold *= 1000;
        }

        return (int) total;
    }
}