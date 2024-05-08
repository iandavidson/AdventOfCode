package org.example.kLowestOccurrences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KLowestOccurrences {

    public static void main(String [] args){
        KLowestOccurrences kLowestOccurrences = new KLowestOccurrences();
        int [] input = {3,3,3,2,2,1};
        int [] results = kLowestOccurrences.topKFrequent(input, 2);
        System.out.println(Arrays.toString(results));


        input = new int[]{1};
        System.out.println(Arrays.toString(kLowestOccurrences.topKFrequent(input, 1)));


        input = new int[]{1,1,1,2,2,2,3,3,3};
        System.out.println(Arrays.toString(kLowestOccurrences.topKFrequent(input, 3)));
    }

    public int[] topKFrequent(int[] nums, int k) {

        Map<Integer, Integer> map = new HashMap<>();
        for (int i : nums) {
            if (map.containsKey(i)) {
                map.put(i, map.get(i) + 1);
            } else {
                map.put(i, 1);
            }
        }

        List<List<Integer>> count2d = new ArrayList<>(nums.length + 1);
        for (int i = 0; i < nums.length + 1; i++) {
            count2d.add(new ArrayList<>());
        }

        for (Integer key : map.keySet()) {
            count2d.get(map.get(key)).add(key);
        }

        Collections.reverse(count2d);

        List<Integer> resultList = new ArrayList<>();

        for (List<Integer> inner : count2d) {
            if (inner.isEmpty()) {
                continue;
            }

            if(inner.size() <= k - resultList.size()){
                for (Integer i : inner) {
                    resultList.add(i);
                    if (resultList.size() >= k) {
                        break;
                    }
                }
            }
            if (resultList.size() >= k) {
                break;
            }
        }

        int [] result = new int [k];

        for(int i = 0; i < resultList.size() ; i++){
            result[i] = resultList.get(i);
        }

        return result;
    }


}
