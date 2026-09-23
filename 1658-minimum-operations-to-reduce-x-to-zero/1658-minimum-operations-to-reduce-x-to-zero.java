class Solution {
    public int minOperations(int[] nums, int x) {
        int total = 0;

        for (int num : nums) {
            total += num;
        }

        int target = total - x;

        // If target < 0, x is greater than total sum
        if (target < 0) {
            return -1;
        }

        // If target == 0, remove the entire array
        if (target == 0) {
            return nums.length;
        }

        int left = 0;
        int sum = 0;
        int maxLen = -1;

        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];

            while (sum > target && left <= right) {
                sum -= nums[left];
                left++;
            }

            if (sum == target) {
                maxLen = Math.max(maxLen, right - left + 1);
            }
        }

        // No subarray with sum target
        if (maxLen == -1) {
            return -1;
        }

        return nums.length - maxLen;
    }
}