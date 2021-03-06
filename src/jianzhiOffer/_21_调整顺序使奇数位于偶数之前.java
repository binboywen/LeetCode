package jianzhiOffer;

/**
 * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，
 * 使得所有奇数位于数组的前半部分，所有偶数位于数组的后半部分。
 */
public class _21_调整顺序使奇数位于偶数之前 {
    /**
     * 快排思想：
     * 我们可以维护两个指针：第一个指针初始化时指向数组的第一个数字，
     * 它只向后移动：第二个指针初始化时指向数组的最后一个数字，它只向
     * 前移动。在两个指针相遇之前，第一个指针总是位于第二个指针的前面。
     * 如果第一个指针指向的数字是偶数，并且第二个指针指向的数字是奇数
     * 则交换这两个数字。
     */
    public static void reOrderArray(int [] array) {
        if ( array == null || array.length == 0 ) return;
        int left = 0;//从左向右最右边的奇数下标
        for (int i = 0; i < array.length; i++) {
            if (array[i] % 2 == 1) {
                int cur = i;//碰到的奇数
                while ( cur > left ) {
                    //逐个交换已经排序了的最右边的奇
                    // 数下标到当前奇数之间的所有数
                    swap(array, cur, --cur);
                }
                left++;//交换完奇数下标向右移动
            }
        }
    }
    //不能保证原序
    public static void ReorderOddEven(int [] array) {
        int length = array.length;
        if (array == null || length == 0)
            return;
        int begin = 0;
        int end = length - 1;
        while(begin < end){
            while(begin < end && array[begin]%2!=0){
                begin++;
            }
            while(begin<end && array[end] % 2 == 0){
                end--;
            }
            if(begin < end){
                swap(array,begin,end);
            }
        }
    }

    public static void swap(int [] array,int i, int j){
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * 如果考虑把题目改成数组中的数按照大小分为两部分，所有负数都在非负数的前面；
     * 或者考虑能被3整除的数都在不能被3整除的数的前面。虽然可以通过修改while里面的
     * 条件来实现，但是这样做还是不能扩展。
     * 可扩展的解法：
     * 利用函数指针来实现判断过程的抽象函数的调用
     */

    /**
     * 需要保证奇数和奇数，偶数和偶数之间的（前后）相对位置不变，这和书本不太一样
     * 方法一：创建一个新数组，时间复杂度O(N)空间复杂度O(N)
     */
    public static void reOrderArray_1(int [] nums){
        int oddCnt = 0;
        for(int x : nums){
            if(x%2==1){
                oddCnt++;
            }
        }
        int [] copy = nums.clone();
        int i = 0;
        int j = oddCnt;
        for(int num : copy){
            if(num%2==1){
                nums[i++] = num;
            }else
                nums[j++] = num;
        }
    }
    /**
     *方法二：使用冒泡思想，每次都当前偶数上浮到最右边，时间复杂度O(N^2)空间O(1)
     *       超出时间限制
     */
    public void reOrderArray_2(int [] nums){
        int N = nums.length;
        for(int i = N -1; i>0; i--){
            for(int j = 0; j < i; j++){
                if(nums[j]%2==0 && nums[j+1]%2!=0){
                    swap(nums,j,j+1);
                }
            }
        }
    }
    public static void main(String [] args){
        int [] array = new int[]{0,1,2,3,4,5,6,7,8,9};
        reOrderArray(array);
        for(int i = 0; i < array.length; i++){
            System.out.println(array[i]);
        }
    }
}
