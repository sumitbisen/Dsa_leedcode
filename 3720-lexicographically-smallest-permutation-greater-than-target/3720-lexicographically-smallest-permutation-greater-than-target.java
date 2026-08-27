class Solution {

    public String lexGreaterPermutation(String s, String target) {

        int n = s.length();
        int[] freq = new int[26];

        // Frequency of characters in s
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        char[] ans = new char[n];

        int matched = 0;

        // Try to make target
        for (int i = 0; i < n; i++) {

            int t = target.charAt(i) - 'a';

            if (freq[t] > 0) {

                // Match target character
                ans[i] = target.charAt(i);
                freq[t]--;
                matched++;

            } else {

                // Current character cannot be matched.
                // Try smallest character greater than target[i].
                for (int j = t + 1; j < 26; j++) {

                    if (freq[j] > 0) {

                        ans[i] = (char) ('a' + j);
                        freq[j]--;

                        return buildResult(ans, i + 1, freq);
                    }
                }

                // No greater character here.
                break;
            }
        }

        // Backtrack only over matched positions
        for (int i = matched - 1; i >= 0; i--) {

            // Restore character used at this position
            int old = ans[i] - 'a';
            freq[old]++;

            int t = target.charAt(i) - 'a';

            // Find smallest character greater than target[i]
            for (int j = t + 1; j < 26; j++) {

                if (freq[j] > 0) {

                    ans[i] = (char) ('a' + j);
                    freq[j]--;

                    return buildResult(ans, i + 1, freq);
                }
            }
        }

        return "";
    }

    private String buildResult(char[] ans, int start, int[] freq) {

        int index = start;

        // Put remaining characters in sorted order
        for (int i = 0; i < 26; i++) {

            while (freq[i] > 0) {

                ans[index++] = (char) ('a' + i);
                freq[i]--;
            }
        }

        return new String(ans);
    }
}