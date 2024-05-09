package TaskScheduler;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class TaskScheduler {

    public static void main(String [] args){
        TaskScheduler taskScheduler = new TaskScheduler();
        System.out.println(taskScheduler.leastInterval(new char[]{'A','C','A','B','D','B'}, 1));
    }


    public int leastInterval(char[] tasks, int n) {
        // count occurrences
        int [] counts = new int [26];
        for(char task : tasks){
            counts[task - 'A']++;
        }

        Queue<Integer> heap = new PriorityQueue<>((a, b) -> b - a);
        Queue<List<Integer>> fifo = new LinkedList<>();

        for(int i = 0; i < counts.length; i++){
            if(counts[i] > 0){
                heap.add(counts[i]);
            }
        }

        int time = 0;
        while(!heap.isEmpty() || !fifo.isEmpty()){
            time++;

            //check if heap is not empty -> remove()
            if(!heap.isEmpty()){
                int tempCount = heap.remove();
                tempCount--;
                if(tempCount > 0){
                    fifo.add(List.of(tempCount, time + n));
                }
            }
            //otherwise idle

            if(!fifo.isEmpty() && time == fifo.peek().get(1)){
                //if(!fifo.isEmpty() && time >= fifo.peek().get(1)){
                heap.add(fifo.remove().get(0));
            }

        }

        return time;
    }
}
