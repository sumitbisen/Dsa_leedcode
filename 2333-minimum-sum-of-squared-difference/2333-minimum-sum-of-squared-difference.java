class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        long operations = (long) k1 + k2;
        int n = nums1.length;
        int[] diff = new int[n];

        int maxDiff = 0;
        long total = 0;

        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            maxDiff = Math.max(maxDiff, diff[i]);
            total += diff[i];
        }

        if (operations >= total) {
            return 0;
        }

        int left = 0, right = maxDiff;

        while (left < right) {
            int mid = left + (right - left) / 2;
            long needed = 0;

            for (int d : diff) {
                if (d > mid) {
                    needed += d - mid;
                }
            }

            if (needed <= operations) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        int target = left;
        long result = 0;
        long used = 0;

        for (int d : diff) {
            if (d > target) {
                used += d - target;
                d = target;
            }
            result += (long) d * d;
        }

        long remaining = operations - used;

        for (int i = 0; i < n && remaining > 0; i++) {
            int original = Math.abs(nums1[i] - nums2[i]);

            if (original >= target && original > 0) {
                result -= (long) target * target;
                result += (long) (target - 1) * (target - 1);
                remaining--;
            }
        }

        return result;
    }
}