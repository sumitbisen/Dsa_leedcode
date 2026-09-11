class Solution {
    TreeSet<Integer> boundaries = new TreeSet<>();
    TreeMap<Integer, Integer> lengths = new TreeMap<>();

    // Add a boundary and update length frequencies
    private void addBoundary(int x) {
        Integer prev = boundaries.lower(x);
        Integer next = boundaries.higher(x);

        if (prev != null && next != null) {
            removeLength(next - prev);
        }

        if (prev != null) {
            addLength(x - prev);
        }

        if (next != null) {
            addLength(next - x);
        }

        boundaries.add(x);
    }

    // Remove a boundary and update length frequencies
    private void removeBoundary(int x) {
        Integer prev = boundaries.lower(x);
        Integer next = boundaries.higher(x);

        if (prev != null) {
            removeLength(x - prev);
        }

        if (next != null) {
            removeLength(next - x);
        }

        if (prev != null && next != null) {
            addLength(next - prev);
        }

        boundaries.remove(x);
    }

    private void addLength(int len) {
        lengths.put(len, lengths.getOrDefault(len, 0) + 1);
    }

    private void removeLength(int len) {
        int count = lengths.get(len);

        if (count == 1) {
            lengths.remove(len);
        } else {
            lengths.put(len, count - 1);
        }
    }

    public int[] longestRepeating(
            String s,
            String queryCharacters,
            int[] queryIndices) {

        int n = s.length();
        int k = queryIndices.length;

        char[] arr = s.toCharArray();

        // 0 is always a boundary
        boundaries.add(0);

        // n is a sentinel boundary
        boundaries.add(n);

        // Initial boundaries
        for (int i = 1; i < n; i++) {
            if (arr[i] != arr[i - 1]) {
                boundaries.add(i);
            }
        }

        // Calculate initial substring lengths
        Integer prev = null;

        for (int b : boundaries) {
            if (prev != null) {
                addLength(b - prev);
            }
            prev = b;
        }

        int[] ans = new int[k];

        for (int q = 0; q < k; q++) {

            int index = queryIndices[q];
            char newChar = queryCharacters.charAt(q);

            // Remove old boundaries around index
            if (index > 0 &&
                    arr[index] != arr[index - 1]) {
                removeBoundary(index);
            }

            if (index + 1 < n &&
                    arr[index + 1] != arr[index]) {
                removeBoundary(index + 1);
            }

            // Update character
            arr[index] = newChar;

            // Add new boundaries around index
            if (index > 0 &&
                    arr[index] != arr[index - 1]) {
                addBoundary(index);
            }

            if (index + 1 < n &&
                    arr[index + 1] != arr[index]) {
                addBoundary(index + 1);
            }

            // Largest substring length
            ans[q] = lengths.lastKey();
        }

        return ans;
    }
}