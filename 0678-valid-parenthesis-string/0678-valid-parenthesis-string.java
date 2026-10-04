class Solution {
    public boolean checkValidString(String s) {
        int min = 0;
        int max = 0;

        for (char ch : s.toCharArray()) {

            if (ch == '(') {
                min++;
                max++;
            } 
            else if (ch == ')') {
                min--;
                max--;
            } 
            else { // '*'
                min--;  // '*' as ')'
                max++;  // '*' as '('
            }

            // Too many closing brackets
            if (max < 0) {
                return false;
            }

            // Minimum balance cannot be negative
            min = Math.max(min, 0);
        }

        return min == 0;
    }
}