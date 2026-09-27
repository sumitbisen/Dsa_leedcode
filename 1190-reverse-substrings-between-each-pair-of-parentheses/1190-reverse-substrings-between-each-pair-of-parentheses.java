
class Solution {
    public String reverseParentheses(String s) {
        Deque<Character> stack = new ArrayDeque<>();

        for (char ch : s.toCharArray()) {

            if (ch == ')') {
                StringBuilder temp = new StringBuilder();

                // '(' tak characters nikalo
                while (stack.peek() != '(') {
                    temp.append(stack.pop());
                }

                // '(' remove karo
                stack.pop();

                // Reversed characters wapas stack me daalo
                for (int i = 0; i < temp.length(); i++) {
                    stack.push(temp.charAt(i));
                }

            } else {
                stack.push(ch);
            }
        }

        // Stack ko correct order me convert karo
        StringBuilder ans = new StringBuilder();

        while (!stack.isEmpty()) {
            ans.append(stack.removeLast());
        }

        return ans.toString();
    }
}