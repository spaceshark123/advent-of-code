import java.util.*;
import java.util.stream.*;
import java.io.*;
import java.lang.*;

class Main {
	static int[] dx = { 0, 1, 0, -1 }; // down, right, up, left
	static int[] dy = { 1, 0, -1, 0 };
	static int[] dx8 = { 0, 1, 1, 1, 0, -1, -1, -1 }; // down, DR, right, UR, up, UL, left, DL
	static int[] dy8 = { 1, 1, 0, -1, -1, -1, 0, 1 };

	public static void main(String[] args) throws IOException {
		// read input from file
		Kattio io = new Kattio("day23", System.out);



		io.close();
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
						if (line == null)
							return null;
						st = new StringTokenizer(line);
					}
					token = st.nextToken();
				} catch (IOException e) {
				}
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

		public String[] getAllArr() {
			return getAll().split("\n");
		}

		public String[] getLineArr() {
			return getLine().split(" ");
		}

		public String[] getLineArr(String delimiter) {
			return getLine().split(delimiter);
		}

		public int[] getIntArr() {
			return Arrays.stream(getLineArr()).mapToInt(Integer::parseInt).toArray();
		}

		public int[] getIntArr(String delimiter) {
			return Arrays.stream(getLineArr(delimiter)).mapToInt(Integer::parseInt).toArray();
		}

		public long[] getLongArr() {
			return Arrays.stream(getLineArr()).mapToLong(Long::parseLong).toArray();
		}

		public long[] getLongArr(String delimiter) {
			return Arrays.stream(getLineArr(delimiter)).mapToLong(Long::parseLong).toArray();
		}

		public double[] getDoubleArr() {
			return Arrays.stream(getLineArr()).mapToDouble(Double::parseDouble).toArray();
		}

		public double[] getDoubleArr(String delimiter) {
			return Arrays.stream(getLineArr(delimiter)).mapToDouble(Double::parseDouble).toArray();
		}
	}
	
	static long timeIt(Runnable r) {
		long start = System.currentTimeMillis();
		r.run();
		long end = System.currentTimeMillis();
		long time = end - start;
		System.out.println("Time: " + time + " ms");
		return time;
	}

	static class ArrayHelper {
		public static void printArr(int[] array) {
			System.out.println(Arrays.toString(array));
		}

		public static void printArr(long[] array) {
			System.out.println(Arrays.toString(array));
		}

		public static void printArr(String[] array) {
			System.out.println(Arrays.toString(array));
		}

		public static void printArr(char[] array) {
			System.out.println(Arrays.toString(array));
		}

		public static int indexOf(int[] array, int element) {
			for (int i = 0; i < array.length; i++) {
				if (array[i] == element)
					return i;
			}
			return -1;
		}

		public static int indexOf(long[] array, long element) {
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
			for (int i : arr) {
				if (i < min)
					min = i;
			}
			return min;
		}

		public static int max(int[] arr) {
			int max = Integer.MIN_VALUE;
			for (int i : arr) {
				if (i > max)
					max = i;
			}
			return max;
		}

		public static boolean contains(int[] arr, int value) {
			for (int i : arr) {
				if (i == value)
					return true;
			}
			return false;
		}

		public static boolean contains(String[] arr, String value) {
			for (String i : arr) {
				if (i.equals(value))
					return true;
			}
			return false;
		}

		public static boolean contains(char[] arr, char value) {
			for (char i : arr) {
				if (i == value)
					return true;
			}
			return false;
		}

		public static void reverse(int[] data) {
			for (int left = 0, right = data.length - 1; left < right; left++, right--) {
				// swap the values at the left and right indices
				swap(data, left, right);
			}
		}

		public static boolean inBounds(int coord1, int coord2, int size1, int size2) {
			return coord1 >= 0 && coord1 < size1 && coord2 >= 0 && coord2 < size2;
		}
	}
	
	public static float clamp(float val, float min, float max) {
		return Math.max(min, Math.min(max, val));
	}

	public static int randomInt(int minInclusive, int maxExclusive) {
		return (int) (Math.random() * (maxExclusive - minInclusive) + minInclusive);
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

	// Wrapper class for primitive arrays to allow for hashmap storage
	static class ArrayWrapper<T> {
		private T[] array;

		public ArrayWrapper(T[] array) {
			this.array = array;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			ArrayWrapper<?> that = (ArrayWrapper<?>) o;
			return Arrays.equals(array, that.array);
		}

		@Override
		public int hashCode() {
			return Arrays.hashCode(array);
		}
	}
}