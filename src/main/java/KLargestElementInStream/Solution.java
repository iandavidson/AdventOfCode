package KLargestElementInStream;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class Solution {

    public static void main(String[] arg) {
        KthLargest kthLargest = new KthLargest(3, new int[]{
                4, 5, 8, 2});
        kthLargest.add(3);   // return 4
        kthLargest.add(5);   // return 5
        kthLargest.add(10);  // return 5
        kthLargest.add(9);   // return 8
        kthLargest.add(4);   // return 8
    }

    static class KthLargest {

        private final int k;
        private final Queue<Integer> heap;

        public KthLargest(int k, int[] nums) {
            this.k = k;
            this.heap = new PriorityQueue<>();
            Arrays.stream(nums).forEach(heap::add);

            while (heap.size() > k) {
                heap.remove();
            }
        }

        public int add(int val) {
            heap.add(val);

            while (heap.size() > k) {
                heap.remove();
            }

            return heap.peek();
        }
    }

}
