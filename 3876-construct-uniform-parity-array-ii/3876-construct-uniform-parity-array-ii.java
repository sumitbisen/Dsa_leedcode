class Solution {
    public boolean uniformArray(int[] nums1) {
        boolean hasOdd = false;
        boolean hasEven = false;

        for (int num : nums1){
            if (num % 2 == 0){
                hasEven = true;
            } else {
                hasOdd = true;         
           }
        }

        if (!hasOdd || !hasEven){
            return true;
        }

        int min = nums1[0];

        for (int num : nums1){
            min = Math.min(min,num);
        }

        return min % 2 == 1;


        
    }
}