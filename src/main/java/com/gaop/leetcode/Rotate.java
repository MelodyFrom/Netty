package com.gaop.leetcode;

/**
 * 旋转数组 <br/>
 * 给定一个数组，将数组中的元素向右移动 k 个位置，其中 k 是非负数。 <br/>
 * 输入: [1,2,3,4,5,6,7] 和 k = 3 <br/>
 * 输出: [5,6,7,1,2,3,4] <br/>
 * 解释: <br/>
 * 向右旋转 1 步: [7,1,2,3,4,5,6] <br/>
 * 向右旋转 2 步: [6,7,1,2,3,4,5] <br/>
 * 向右旋转 3 步: [5,6,7,1,2,3,4] <br/>
 * @author gaopeng@doctorwork.com
 * @date 2019-08-16 8:08
 **/
public class Rotate {

    public static void rotate(int[] nums, int k) {
        int rotate = k % nums.length;
        if (rotate == 0)
            return;
        // 每一个元素的位置： (index + rotate) % nums.length，其中 index 为每个元素的初始下标
        // 题目期望空间复杂度为 O(1)，但是我给出的解法空间复杂度为 O(n)
        int[] numsCopy = nums.clone();
        for (int i = 0; i < numsCopy.length; i++) {
            int newIdx = (i + rotate) % numsCopy.length;
            nums[newIdx] = numsCopy[i];
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3,4,5,6,7};
        int k = 3;
        print(nums);
        rotate(nums, k);
        print(nums);
    }

    public static void print(int[] nums) {
        for (int number : nums) {
            System.out.print(number + " ");
        }
        System.out.println();
    }
}
