import java.util.*;
import java.io.*;
import java.lang.*;

class Main {
	//dp
	static HashMap<Long, HashMap<Long, Long>> dp;
	
	public static void main(String[] args) throws IOException {
		// read input from file
		Kattio io = new Kattio("day13", System.out);

		// input
		long part1Sum = 0;
		long part2Sum = 0;
		while (io.hasNextLine()) {
			String[] A = io.getLineArr(); // costs 3 tokens per move
			String[] B = io.getLineArr(); // costs 1 token per move
			String[] Goal = io.getLineArr();
			io.getLine(); // skip empty line
			long a_x = Long.parseLong(A[2].substring(1, A[2].length() - 1));
			long a_y = Long.parseLong(A[3].substring(1));
			long b_x = Long.parseLong(B[2].substring(1, B[2].length() - 1));
			long b_y = Long.parseLong(B[3].substring(1));
			long goal_x = Long.parseLong(Goal[1].substring(2, Goal[1].length() - 1));
			long goal_y = Long.parseLong(Goal[2].substring(2));

			// solve both parts using the efficient O(1) solution
			long min_tokens_1 = solve_v2(a_x, a_y, b_x, b_y, goal_x, goal_y);
			long min_tokens_2 = solve_v2(a_x, a_y, b_x, b_y, goal_x + 10000000000000L, goal_y + 10000000000000L);
			part1Sum += min_tokens_1 > 0 ? min_tokens_1 : 0;
			part2Sum += min_tokens_2 > 0 ? min_tokens_2 : 0;
		}

		// output
		io.println(part1Sum);
		io.println(part2Sum);

		io.close();
	}
	
	static long solve_v1(long a_x, long a_y, long b_x, long b_y, long goal_x, long goal_y) {
		// dynamic programming to solve in O(goal_x * goal_y) time
		// this solution is still too slow for the second part
		if (goal_x == 0 && 0 == goal_y) {
			return 0;
		}
		if (dp.get(goal_x).get(goal_y) != 0) {
			return dp.get(goal_x).get(goal_y); // memoization
		}
		// branch: last move was a
		long ans1 = goal_x < a_x || goal_y < a_y ? Long.MAX_VALUE : 3 + solve_v1(a_x, a_y, b_x, b_y, goal_x - a_x, goal_y - a_y);
		// branch: last move was b
		long ans2 = goal_x < b_x || goal_y < b_y ? Long.MAX_VALUE : 1 + solve_v1(a_x, a_y, b_x, b_y, goal_x - b_x, goal_y - b_y);
		ans1 = ans1 < 0 ? Long.MAX_VALUE : ans1; // if ans1 is negative, it means it's impossible to reach the goal
		ans2 = ans2 < 0 ? Long.MAX_VALUE : ans2; // if ans2 is negative, it means it's impossible to reach the goal
		long ans = Math.min(ans1, ans2);
		if (ans == Long.MAX_VALUE) {
			// if both branches are impossible, it's impossible to reach the goal
			dp.get(goal_x).put(goal_y, Long.MIN_VALUE);
			return Long.MIN_VALUE;
		}
		dp.get(goal_x).put(goal_y, ans);
		return ans;
	}

	static long solve_v2(long a_x, long a_y, long b_x, long b_y, long goal_x, long goal_y) {
		// cramers rule to solve in O(1) time
		long det = a_x * b_y - a_y * b_x;
		long num1 = (goal_x * b_y - goal_y * b_x);
		long num2 = (goal_y * a_x - goal_x * a_y);
		if (det == 0) {
			if (num1 == 0 || num2 == 0) {
				// if either of the numerators is 0, the system of equations has no solution
				return -1;
			} else {
				// infinite solutions, minimize tokens with nonnegative a and b
				// since tokens = 3a + b, we can minimize a to at most 0 (maximize b) to minimize tokens
				// since the line have a non-integer y-intercept, we have to round down y(0), then solve for x(floor(y(0)))
				long y = (goal_x - 0 * a_x) / b_x; // implicitly rounds down
				return 3 * ((b_x * y - goal_x) / a_x) + y;
			}
		}
		long a = num1 / det;
		long b = num2 / det;
		if (num1 % det != 0 || num2 % det != 0 || a < 0 || b < 0) {
			// no integer solution or has negative button presses (which is impossible)
			return -1;
		}
		return 3*a + b;
	}

	static class Kattio extends PrintWriter {
		public Kattio(InputStream i) {
			super(new BufferedOutputStream(System.out));
			r = new BufferedReader(new InputStreamReader(i));
		}
		public Kattio(InputStream i, OutputStream o) {
			super(new BufferedOutputStream(o));
			r = new BufferedReader(new InputStreamReader(i));
		}
		public Kattio(String problemName) throws FileNotFoundException {
			super(new BufferedOutputStream(new FileOutputStream(problemName + ".out")));
			try {
				r = new BufferedReader(new FileReader(problemName + ".in"));
			} catch (FileNotFoundException e) {
				System.err.println("Input file not found: " + problemName + ".in");
				throw e; // Re-throw the exception to notify the caller
			}
		}
		public Kattio(String problemName, OutputStream o) throws FileNotFoundException {
			super(new BufferedOutputStream(o));
			try {
				r = new BufferedReader(new FileReader(problemName + ".in"));
			} catch (FileNotFoundException e) {
				System.err.println("Input file not found: " + problemName + ".in");
				throw e; // Re-throw the exception to notify the caller
			}
		}

		public boolean hasMoreTokens() {
			return peekToken() != null;
		}

		public boolean hasNextLine() {
			try {
				// Mark the current position in the stream
				r.mark(1000); // 1000 is the buffer size for mark/reset
				
				// Try to read the next line
				if (r.readLine() != null) {
					// If a line is available, reset the reader to the marked position
					r.reset();
					return true;
				} else {
					return false; // No more lines
				}
			} catch (IOException e) {
				return false; // In case of I/O error, assume no more lines
			}
		}

		public int getInt() {
			return Integer.parseInt(nextToken());
		}

		public double getDouble() { 
			return Double.parseDouble(nextToken());
		}

		public long getLong() {
			return Long.parseLong(nextToken());
		}

		public String getWord() {
			return nextToken();
		}

		private BufferedReader r;
		private String line;
		private StringTokenizer st;
		private String token;

		private String peekToken() {
			if (token == null) 
				try {
				while (st == null || !st.hasMoreTokens()) {
					line = r.readLine();
					if (line == null) return null;
					st = new StringTokenizer(line);
				}
				token = st.nextToken();
				} catch (IOException e) { }
			return token;
		}

		private String nextToken() {
			String ans = peekToken();
			token = null;
			return ans;
		}
		
		public String getLine() {
			try {
				return r.readLine();
			} catch (IOException e) {
				return null;
			}
		}

		public String getAll() {
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = getLine()) != null) {
				sb.append(line).append("\n");
			}
			return sb.toString();
		}
		
		public String[] getLineArr() {
			return getLine().split(" ");
		}
	}

	static class ArrayHelper {
		public static void printArr(int[] array) {
	        System.out.print("{");
	        for (int i = 0; i < array.length; i++) {
	            System.out.print(array[i]);
	            if (i != array.length - 1) {
	                System.out.print(", ");
	            }
	        }
	        System.out.print("}\n");
    	}

		public static void printArr(String[] array) {
	        System.out.print("{");
	        for (int i = 0; i < array.length; i++) {
	            System.out.print(array[i]);
	            if (i != array.length - 1) {
	                System.out.print(", ");
	            }
	        }
	        System.out.print("}\n");
    	}

		public static void printArr(char[] array) {
	        System.out.print("{");
	        for (int i = 0; i < array.length; i++) {
	            System.out.print(array[i]);
	            if (i != array.length - 1) {
	                System.out.print(", ");
	            }
	        }
	        System.out.print("}\n");
    	}

		public static int indexOf(int[] array, int element) {
	        for (int i = 0; i < array.length; i++) {
	            if (array[i] == element)
	                return i;
	        }
	        return -1;
    	}

		public static int indexOf(String[] array, String element) {
	        for (int i = 0; i < array.length; i++) {
	            if (array[i].equals(element))
	                return i;
	        }
	        return -1;
    	}

		public static int indexOf(char[] array, char element) {
	        for (int i = 0; i < array.length; i++) {
	            if (array[i] == element)
	                return i;
	        }
	        return -1;
    	}

		public static void swap(int[] array, int index1, int index2) {
	        int temp = array[index1];
	        array[index1] = array[index2];
	        array[index2] = temp;
    	}

		public static void swap(String[] array, int index1, int index2) {
	        String temp = array[index1];
	        array[index1] = array[index2];
	        array[index2] = temp;
    	}

		public static void swap(char[] array, int index1, int index2) {
	        char temp = array[index1];
	        array[index1] = array[index2];
	        array[index2] = temp;
    	}

		public static int min(int[] arr) {
			int min = Integer.MAX_VALUE;
			for(int i : arr) {
				if(i < min)
					min = i;
			}
			return min;
		}

		public static int max(int[] arr) {
			int max = Integer.MIN_VALUE;
			for(int i : arr) {
				if(i > max)
					max = i;
			}
			return max;
		}

		public static boolean contains(int[] arr, int value) {
			for(int i : arr) {
				if(i == value)
					return true;
			}
			return false;
		}

		public static boolean contains(String[] arr, String value) {
			for(String i : arr) {
				if(i.equals(value))
					return true;
			}
			return false;
		}

		public static boolean contains(char[] arr, char value) {
			for(char i : arr) {
				if(i == value)
					return true;
			}
			return false;
		}

		public static float clamp(float val, float min, float max) {
    		return Math.max(min, Math.min(max, val));
		}

		public static void reverse(int[] data) {
			for (int left = 0, right = data.length - 1; left < right; left++, right--) {
				// swap the values at the left and right indices
				swap(data, left, right);
			}
		}

		public static int randomInt(int minInclusive, int maxExclusive) {
			return (int)Math.floor(Math.random()*(maxExclusive-minInclusive+1)+minInclusive);
		}
	}

	public static class PermIterator implements Iterator<int[]> {
		//syntax for creating: PermIterator it = new PermIterator(length of array);
		//syntax for iterating: while(it.hasNext()) { int[] perm = it.next(); }
		//permutations can be used as indices for permuting other data types like strings in lexicographical order
		private final int[] array;
		private final int size;
		private boolean hasNext = true;

		public PermIterator(int size) {
			this.size = size;
			this.array = new int[size];
			for (int i = 0; i < size; i++) {
				array[i] = i;
			}
		}

		@Override
		public boolean hasNext() {
			return hasNext;
		}

		@Override
		public int[] next() {
			if (!hasNext) {
				return null;
			}
			int[] result = array.clone();
			// Find the first element from the right that can be incremented (start index of suffix)
			int i;
			for (i = size - 1; i > 0 && array[i - 1] >= array[i]; i--)
				;
			// If no such element exists, we have reached the last permutation
			if (i == 0) {
				hasNext = false;
				return result;
			}
			//pivot is index before suffix start
			//set pivot to smallest element in suffix bigger than pivot
			int minsuffix = Integer.MAX_VALUE;
			int minInd = 0;
			for (int j = i; j < size; j++) {
				if (array[j] < minsuffix && array[j] > array[i - 1]) {
					minsuffix = array[j];
					minInd = j;
				}
			}
			ArrayHelper.swap(array, i - 1, minInd);
			//reverse suffix
			int start = i;
			int end = size - 1;
			while (start < end) {
				ArrayHelper.swap(array, start++, end--);
			}
			return result;
		}
	}
	
	public static class TreeNode<T> {
		public T data;
		public TreeNode<T> left;
		public TreeNode<T> right;

		public TreeNode(T data) {
			this.data = data;
			this.left = null;
			this.right = null;
		}
	}

	public static class LinkedNode<T> {
		public T data;
		public LinkedNode<T> next;

		public LinkedNode(T data) {
			this.data = data;
			this.next = null;
		}
	}

	public static class GraphNode<T> {
		public T data;
		public ArrayList<GraphNode<T>> neighbors;

		public GraphNode(T data) {
			this.data = data;
			this.neighbors = new ArrayList<GraphNode<T>>();
		}
	}

	public static class BaseIterator {
		private int max;
		private int count = 0;
		private int base;
		private int size;

		public BaseIterator(int base, int size) {
			if (base < 2) {
				throw new IllegalArgumentException("Base must be at least 2");
			}
			this.base = base;
			this.size = size;
			max = (int) Math.pow(base, size);
		}

		public boolean hasNext() {
			return count < max;
		}

		public int[] next() {
			if (!hasNext()) {
				return null;
			}
			// Convert the current count to a base representation
			int[] result = new int[size];
			int temp = count;
			for (int i = 0; i < size; i++) {
				result[i] = temp % base;
				temp /= base;
			}
			count++;
			return result;
		}

		public void reset() {
			count = 0;
		}
	}
}