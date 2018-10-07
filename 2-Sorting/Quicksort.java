import java.util.Arrays;

import static java.util.Arrays.sort;

public class Quicksort {
    public static void main(String[] args) {
        int[] nums = {5, 2, 1, 6, 9, 3};

        sort(nums, 0, nums.length - 1);
        System.out.println(Arrays.toString(nums));
    }

    private static void sort(int[] nums, int start, int end) {
        if (start>=end){
            return;
        }
        int p = end;
        p = pivot(nums, start, end, p);
        sort(nums,start,p-1);
        sort(nums,p+1,end);

    }

    private static int pivot(int[] nums, int start, int end, int p) {
        int j= start;
        for (int i = start; i <end ; i++) {
            if (nums[i]<nums[p]){
                swap(nums,i,j);
                j++;
            }
        }
        swap(nums,j,p);
        return j;

    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[j];
        nums[j]=nums[i];
        nums[i]=temp;
    }

}
