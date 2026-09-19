class Solution {
    public boolean checkOverlap(int radius, int xCenter, int yCenter,
                                int x1, int y1, int x2, int y2) {

        // Find the closest point of rectangle to circle center
        int closestX = Math.max(x1, Math.min(xCenter, x2));
        int closestY = Math.max(y1, Math.min(yCenter, y2));

        // Distance between closest point and circle center
        int dx = closestX - xCenter;
        int dy = closestY - yCenter;

        // Check if distance <= radius
        return dx * dx + dy * dy <= radius * radius;
    }
}