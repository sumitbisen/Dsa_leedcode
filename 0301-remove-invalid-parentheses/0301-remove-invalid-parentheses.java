class Solution {
    public List<String> removeInvalidParentheses(String s) {
        
        List<String> ans = new ArrayList<>();
        Queue<String> q = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        q.add(s);
        visited.add(s);

        boolean found = false;

        while (!q.isEmpty()) {
            int size = q.size();

            for (int i = 0; i < size; i++) {
                String str = q.poll();

                if (isValid(str)) {
                    ans.add(str);
                    found = true;
                }

                if (found) {
                    continue;
                }

                for (int j = 0; j < str.length(); j++) {

                    if (str.charAt(j) != '(' && str.charAt(j) != ')') {
                        continue;
                    }

                    String next = str.substring(0, j) 
                                + str.substring(j + 1);

                    if (!visited.contains(next)) {
                        visited.add(next);
                        q.add(next);
                    }
                }
            }

            if (found) {
                break;
            }
        }

        return ans;
    }

    private boolean isValid(String s) {
        int balance = 0;

        for (char c : s.toCharArray()) {

            if (c == '(') {
                balance++;
            } 
            else if (c == ')') {
                balance--;

                if (balance < 0) {
                    return false;
                }
            }
        }

        return balance == 0;
    }
}