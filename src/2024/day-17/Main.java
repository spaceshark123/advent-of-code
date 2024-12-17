import java.util.*;
import java.util.stream.LongStream;
import java.io.*;
import java.lang.*;

class Main {
	public static void main(String[] args) throws IOException {
		// read input from file
		Kattio io = new Kattio("day17", System.out);

		// input
		long A = Long.parseLong(io.getLine().split(" ")[2]);
		long B = Long.parseLong(io.getLine().split(" ")[2]);
		long C = Long.parseLong(io.getLine().split(" ")[2]);
		io.getLine(); // skip empty line
		String[] instructions = io.getLine().split(" ")[1].split(",");
		StringBuilder progNum = new StringBuilder();
		int[] program = new int[instructions.length];
		for (int i = 0; i < instructions.length; i++) {
			program[i] = Integer.parseInt(instructions[i]);
			progNum.append(instructions[i]);
		}

		// part 1
		String out = solve(program, A, B, C);
		for (int i = 0; i < out.length(); i++) {
			if (i == out.length() - 1) {
				io.print(out.charAt(i));
			} else {
				io.print(out.charAt(i) + ",");
			}
		}
		io.println();

		// part 2
		long A2 = reverseEngineer(progNum.toString()); // first try to reverse engineer A
		io.println("found possible A: " + A2);
		String out2 = solve2(A2);
		io.println("target: " + progNum);
		io.println("got   : " + out2);
		if(out2.equals(progNum.toString())) {
			io.println("Success!");
		} else { // if not, perform brute-force search around the possible A
			io.println("Failed! performing search around possible A...");
			io.flush();
			search(A2 - 1_000_000L, A2 + 1_000_000L, progNum.toString());
		}
		
		io.close();
	}

	static void search(long minA, long maxA, String progNumFinal) {
		LongStream.range(minA, maxA).parallel().forEach(g -> {
			String out = solve2(g);
			// check if output is same as program
			if (out.equals(progNumFinal)) {
				System.out.println("Found A: " + g);
				System.exit(0);
			}
		});
	}

	static String solve(int[] program, long A, long B, long C) {
		StringBuilder out = new StringBuilder();
		for (int i = 0; i < program.length; i += 2) {
			int instruction = program[i];
			long operand = (long) program[i + 1];
			if (instruction == 0) {
				A = A >> combo(operand, A, B, C);
			} else if (instruction == 1) {
				B = B ^ operand;
			} else if (instruction == 2) {
				B = combo(operand, A, B, C) % 8;
			} else if (instruction == 3 && A != 0) {
				i = (int) operand - 2;
			} else if (instruction == 4) {
				B = B ^ C;
			} else if (instruction == 5) {
				out.append(combo(operand, A, B, C) % 8);
			} else if (instruction == 6) {
				B = A >> combo(operand, A, B, C);
			} else if (instruction == 7) {
				C = A >> combo(operand, A, B, C);
			}
		}
		return out.toString();
	}

	static long reverseEngineer(String program) {
		// figure out octal A from program
		long A = 0;
		for (int i = program.length() - 1; i >= 0; i--) {
			// try all digits from 0 to 7 and see if it creates the same output
			long digit = Long.parseLong(program.charAt(i) + "");
			boolean found = false;
			for (int j = 0; j < 8; j++) {
				if (out(A * 8 + j) == digit) {
					// add digit to A
					A = A * 8 + j;
					found = true;
					break;
				}
			}
			if (!found) {
				A *= 8;
			}
		}
		return A;
	}

	// solve function specifically for part 2 (decompiled program)
	static String solve2(long A) {
		StringBuilder out = new StringBuilder();
		while (A != 0) {
			out.append(out(A));
			A = A >> 3;
		}
		return out.toString();
	}
	
	// decompiled out function (for part 2)
	static long out(long A) {
		//out((((A % 8) ^ 1) ^ 5) ^ (A >> ((A % 8) ^ 1)) % 8)
		return ((((A % 8) ^ 1) ^ 5) ^ (A >> ((A % 8) ^ 1))) % 8;
	}
	
	static long combo(long operand, long A, long B, long C) {
		if (operand <= 3) {
			return (long)operand;
		}
		if (operand == 4) {
			return A;
		} else if (operand == 5) {
			return B;
		} else if (operand == 6) {
			return C;
		}
		return 0;
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