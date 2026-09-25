import java.util.*;

class Solution {

    public List<String> braceExpansionII(String expression) {
        Set<String> result = parse(expression, 0, expression.length() - 1);

        List<String> ans = new ArrayList<>(result);
        Collections.sort(ans);

        return ans;
    }

    // Parse expression from l to r
    private Set<String> parse(String s, int l, int r) {

        Set<String> result = new HashSet<>();
        Set<String> current = new HashSet<>();
        current.add("");

        int i = l;

        while (i <= r) {

            char ch = s.charAt(i);

            // Case 1: lowercase letter
            if (Character.isLowerCase(ch)) {

                Set<String> part = new HashSet<>();
                part.add(String.valueOf(ch));

                current = combine(current, part);
                i++;

            }

            // Case 2: opening brace
            else if (ch == '{') {

                int balance = 1;
                int j = i + 1;

                while (j <= r && balance > 0) {
                    if (s.charAt(j) == '{') {
                        balance++;
                    } else if (s.charAt(j) == '}') {
                        balance--;
                    }
                    j++;
                }

                // Parse inside { ... }
                Set<String> part = parse(s, i + 1, j - 2);

                current = combine(current, part);

                i = j;
            }

            // Case 3: comma
            else if (ch == ',') {

                result.addAll(current);

                current.clear();
                current.add("");

                i++;
            }

            else {
                i++;
            }
        }

        result.addAll(current);

        return result;
    }

    // Cartesian product + concatenation
    private Set<String> combine(Set<String> a, Set<String> b) {

        Set<String> result = new HashSet<>();

        for (String x : a) {
            for (String y : b) {
                result.add(x + y);
            }
        }

        return result;
    }
}