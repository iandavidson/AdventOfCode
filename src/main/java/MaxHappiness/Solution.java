package MaxHappiness;

import java.util.Arrays;

public class Solution {

    public static void main(String []  args){
        Solution solution = new Solution();
        System.out.println(solution.maximumHappinessSum(new int[]{2,3,4,5}, 1));

        System.out.println(solution.maximumHappinessSum(new int[]{12,1,42}, 3));
    }


    public long maximumHappinessSum(int[] happiness, int k) {
        Arrays.sort(happiness);

        long tally = 0L;
        for(int i = 0; i < k; i++){
            tally += (long) Math.max(0L, (happiness[happiness.length - 1 - i] - i));
        }

        return tally;
    }
}
