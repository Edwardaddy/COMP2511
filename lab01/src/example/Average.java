package src.example;


public class Average {

        /**
         * Returns the average of an array of numbers
         * @param the array of integer numbers
         * @return the average of the numbers
         */
        public float average(int[] nums) {
            float result = 0;
            // Add your code
            for (int i = 0; i < nums.length; i++) {
            	result += nums[i];
            }
            return result/nums.length;
        }

        public static void main(String[] args) {
            // Add your code
        	int[] nums= {1,2,3,4,5,6};
        	Average test = new Average();
			System.out.println("The average is = " + test.average(nums));
        	
        }
}