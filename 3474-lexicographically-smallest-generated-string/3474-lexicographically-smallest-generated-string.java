class Solution {

    public String generateString(String str1, String str2) {

        int n = str1.length();
        int m = str2.length();
        int len = n + m - 1;

        char[] word = new char[len];
        boolean[] fixed = new boolean[len];

        // Initially all characters are 'a'
        for (int i = 0; i < len; i++) {
            word[i] = 'a';
        }

        // Step 1: Handle all 'T'
        for (int i = 0; i < n; i++) {

            if (str1.charAt(i) != 'T') {
                continue;
            }

            for (int j = 0; j < m; j++) {

                int pos = i + j;
                char ch = str2.charAt(j);

                // Conflict between two T constraints
                if (fixed[pos] && word[pos] != ch) {
                    return "";
                }

                word[pos] = ch;
                fixed[pos] = true;
            }
        }

        // Step 2: Handle all 'F'
        for (int i = 0; i < n; i++) {

            if (str1.charAt(i) != 'F') {
                continue;
            }

            // Check whether current substring == str2
            boolean same = true;

            for (int j = 0; j < m; j++) {

                if (word[i + j] != str2.charAt(j)) {
                    same = false;
                    break;
                }
            }

            // Already different -> F condition satisfied
            if (!same) {
                continue;
            }

            // Need to make this substring different.
            // Change the RIGHTMOST non-fixed position.
            boolean changed = false;

            for (int j = i + m - 1; j >= i; j--) {

                if (!fixed[j]) {

                    // 'a' -> 'b' is the smallest possible increase
                    word[j] = 'b';
                    changed = true;
                    break;
                }
            }

            // Every position is fixed by T
            if (!changed) {
                return "";
            }
        }

        return new String(word);
    }
}