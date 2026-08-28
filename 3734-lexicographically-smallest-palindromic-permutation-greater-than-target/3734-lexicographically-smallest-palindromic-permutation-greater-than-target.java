class Solution {

    public String lexPalindromicPermutation(String s, String target) {

        int n = s.length();

        // --------------------------------
        // 1. Count characters
        // --------------------------------
        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        // --------------------------------
        // 2. Check palindrome possible
        // --------------------------------
        int odd = 0;
        char middle = 0;

        for (int i = 0; i < 26; i++) {
            if (freq[i] % 2 != 0) {
                odd++;
                middle = (char) ('a' + i);
            }
        }

        if (odd > 1) {
            return "";
        }

        // --------------------------------
        // 3. Frequency of first half
        // --------------------------------
        int halfLen = n / 2;
        int[] halfFreq = new int[26];

        for (int i = 0; i < 26; i++) {
            halfFreq[i] = freq[i] / 2;
        }

        // --------------------------------
        // 4. Try to match target's first half
        // --------------------------------
        int[] half = new int[halfLen];

        int matched = 0;

        while (matched < halfLen) {

            int c = target.charAt(matched) - 'a';

            if (halfFreq[c] == 0) {
                break;
            }

            half[matched] = c;
            halfFreq[c]--;
            matched++;
        }

        // --------------------------------
        // CASE 1:
        // Entire target first half matched
        // --------------------------------
        if (matched == halfLen) {

            String candidate = buildPalindrome(
                half,
                middle,
                n
            );

            // Exact first half, but palindrome itself
            // may be greater than target because of
            // the second half.
            if (candidate.compareTo(target) > 0) {
                return candidate;
            }

            // Need next bigger first half.
            for (int i = halfLen - 1; i >= 0; i--) {

                // Put back character at i
                halfFreq[half[i]]++;

                int current = half[i];

                // Find smallest character greater than current
                for (int c = current + 1; c < 26; c++) {

                    if (halfFreq[c] > 0) {

                        half[i] = c;
                        halfFreq[c]--;

                        // Fill remaining positions smallest first
                        int pos = i + 1;

                        for (int x = 0; x < 26; x++) {
                            while (halfFreq[x] > 0) {
                                half[pos++] = x;
                                halfFreq[x]--;
                            }
                        }

                        return buildPalindrome(
                            half,
                            middle,
                            n
                        );
                    }
                }
            }

            return "";
        }

        // --------------------------------
        // CASE 2:
        // Target first half could not be
        // completely matched.
        // --------------------------------

        /*
         * At 'matched' position, try a character
         * greater than target[matched].
         */

        int targetChar = target.charAt(matched) - 'a';

        for (int c = targetChar + 1; c < 26; c++) {

            if (halfFreq[c] > 0) {

                half[matched] = c;
                halfFreq[c]--;

                // Fill remaining characters smallest first
                int pos = matched + 1;

                for (int x = 0; x < 26; x++) {
                    while (halfFreq[x] > 0) {
                        half[pos++] = x;
                        halfFreq[x]--;
                    }
                }

                return buildPalindrome(
                    half,
                    middle,
                    n
                );
            }
        }

        // --------------------------------
        // No bigger character at 'matched'.
        // Backtrack to previous positions.
        // --------------------------------

        for (int i = matched - 1; i >= 0; i--) {

            // Restore the character used at i
            halfFreq[half[i]]++;

            int current = half[i];

            // Try next bigger character
            for (int c = current + 1; c < 26; c++) {

                if (halfFreq[c] > 0) {

                    half[i] = c;
                    halfFreq[c]--;

                    // Fill rest with smallest characters
                    int pos = i + 1;

                    for (int x = 0; x < 26; x++) {
                        while (halfFreq[x] > 0) {
                            half[pos++] = x;
                            halfFreq[x]--;
                        }
                    }

                    return buildPalindrome(
                        half,
                        middle,
                        n
                    );
                }
            }
        }

        return "";
    }


    // --------------------------------
    // Build complete palindrome
    // --------------------------------
    private String buildPalindrome(
        int[] half,
        char middle,
        int n
    ) {

        StringBuilder sb = new StringBuilder();

        // First half
        for (int c : half) {
            sb.append((char) ('a' + c));
        }

        // Middle
        if (n % 2 == 1) {
            sb.append(middle);
        }

        // Reverse first half
        for (int i = half.length - 1; i >= 0; i--) {
            sb.append((char) ('a' + half[i]));
        }

        return sb.toString();
    }
}