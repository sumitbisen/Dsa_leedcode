class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] indegree = new int[numCourses];

        // Build indegree
        for (int[] p : prerequisites) {
            indegree[p[0]]++;
        }

        // Courses with no prerequisites
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        int completed = 0;

        while (!queue.isEmpty()) {
            int course = queue.poll();
            completed++;

            // Remove this course as a prerequisite
            for (int[] p : prerequisites) {
                if (p[1] == course) {
                    indegree[p[0]]--;

                    if (indegree[p[0]] == 0) {
                        queue.offer(p[0]);
                    }
                }
            }
        }

        return completed == numCourses;
    }
}