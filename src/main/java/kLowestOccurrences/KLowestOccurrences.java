package kLowestOccurrences;

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

        //build occurrence map {value} -> {# of value}
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : nums) {
            if (map.containsKey(i)) {
                map.put(i, map.get(i) + 1);
            } else {
                map.put(i, 1);
            }
        }

        //build 2d array: 1st dimension = occurrence of value, 2nd dimension = list of values with that # of occurrences
        List<List<Integer>> count2d = new ArrayList<>(nums.length + 1);
        for (int i = 0; i < nums.length + 1; i++) {
            count2d.add(new ArrayList<>());
        }

        for (Integer key : map.keySet()) {
            count2d.get(map.get(key)).add(key);
        }

        //reverse list so we are able to traverse start to finish collecting the highest occurring values
        Collections.reverse(count2d);

        //collect results with this list
        List<Integer> resultList = new ArrayList<>();

        for (List<Integer> inner : count2d) {
            if (inner.isEmpty()) {
                continue;
            }
            //e.g. if there are 2 spaces left but more than 2 element of the same occurrence, forgo adding any of them
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
