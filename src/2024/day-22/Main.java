import java.util.*;
import java.util.stream.IntStream;
import java.io.*;
import java.lang.*;

class Main {
	static class ArrayWrapper {
		long[] arr;

		public ArrayWrapper(long[] arr) {
			this.arr = arr;
		}
		
		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			ArrayWrapper that = (ArrayWrapper) o;
			return Arrays.equals(arr, that.arr);
		}

		@Override
		public int hashCode() {
			return Arrays.hashCode(arr);
		}
	}

	public static void main(String[] args) throws IOException {
		// read input from file
		Kattio io = new Kattio("day22", System.out);

		String[] lines = io.getAll().split("\n");
		long[] arr = new long[lines.length];
		long sum = 0;
		for (int i = 0; i < lines.length; i++) {
			long n = Long.parseLong(lines[i]);
			arr[i] = n;
			long ans = part1(n);
			sum += ans;
		}

		// part 1
		System.out.println(sum);

		// part 2
		long start = System.currentTimeMillis();
		io.println(part2(arr));
		long end = System.currentTimeMillis();
		System.out.println("Time: " + (end - start) + "ms");

		io.close();
	}

	static long part1(long n) {
		for (int i = 0; i < 2000; i++) {
			n ^= n << 6; // multiply by 64
			n %= 16777216;

			n ^= n >> 5; // divide by 32
			n %= 16777216;

			n ^= n << 11; // multiply by 2048
			n %= 16777216;
		}
		return n;
	}

	static long part2(long[] arr) {
		// 2000 steps
		long[][] diffs = new long[arr.length][2000];
		long[][] prices = new long[arr.length][2001];
		for (int i = 0; i < 2000; i++) {
			// step forward all buyers
			if (i == 0) {
				for(int j = 0; j < arr.length; j++) {
					prices[j][0] = arr[j] % 10;
				}
			}
			for(int j = 0; j < arr.length; j++) {
				long n = arr[j];
				long old = n % 10;
				n ^= n << 6; // multiply by 64
				n %= 16777216;

				n ^= n >> 5; // divide by 32
				n %= 16777216;

				n ^= n << 11; // multiply by 2048
				n %= 16777216;
				arr[j] = n;

				long price = n % 10;
				// find diff
				long diff = price - old;

				diffs[j][i] = diff;
				prices[j][i+1] = price;
			}
		}

		// find sequence of 4 diffs that come before the max price sum for all buyers
		HashSet<ArrayWrapper> seqs = new HashSet<>(); // store all distinct sequences of 4 diffs
		for (int k = 0; k < arr.length; k++) {
			for (int l = 0; l < 2000 - 4; l++) {
				long[] seq = new long[4];
				for (int m = 0; m < 4; m++) {
					seq[m] = diffs[k][l + m];
				}
				seqs.add(new ArrayWrapper(seq));
			}
		}
		System.out.println(seqs.size() + " sequences");

		return seqs.stream().parallel().mapToLong(s -> IntStream.range(0, arr.length)
				.mapToLong(j -> {
					// find the first occurence of the sequence in diffs, then add the price right after
					for (int k = 0; k < 2000 - 4; k++) {
						boolean found = true;
						for (int l = 0; l < 4; l++) {
							if (diffs[j][k + l] != s.arr[l]) {
								found = false;
								break;
							}
						}
						if (found) {
							return prices[j][k + 4];
						}
					}
					return 0;
				})
				.sum()).max().getAsLong();
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