import java.util.*;
import java.io.*;
import java.lang.*;

class Main {
	static final String XMAS = "XMAS";
	static final String SSMM = "SSMM";
	static final int[] dx = { 0, 1, 1, 1, 0, -1, -1, -1 }; // all 8 directions x
	static final int[] dy = { 1, 1, 0, -1, -1, -1, 0, 1 }; // all 8 directions y

	public static void main(String[] args) throws IOException {
		// read input from file
		Kattio io = new Kattio("day4", System.out);

		// take input
		String[] lines = io.getAll().split("\n");
		char[][] grid = new char[lines.length][lines[0].length()];
		for (int i = 0; i < lines.length; i++) {
			grid[i] = lines[i].toCharArray();
		}

		// part 1
		int part1 = part1(grid);
		io.println(part1);

		// part 2
		int part2 = part2(grid);
		io.println(part2);

		io.close();
	}

	static int part1(char[][] grid) {
		// do word search for 'XMAS' and count the number of times it appears
		int count = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == 'X') {
					count += xmasSearch(grid, i, j);
				}
			}
		}
		return count;
	}

	static int part2(char[][] grid) {
		// count crosses of 'MAS' with itself (A is the center)
		int count = 0;
		for (int i = 1; i < grid.length-1; i++) {
			for (int j = 1; j < grid[0].length-1; j++) {
				if (grid[i][j] == 'A') {
					if (xSearch(grid, i, j)) {
						count++;
					}
				}
			}
		}
		return count;
	}

	static boolean xSearch(char[][] grid, int x, int y) {
		// check if 'MAS' forms a cross with itself by checking diagonals
		String diag1 = "" + grid[clamp(x - 1, 0, grid.length - 1)][clamp(y - 1, 0, grid[0].length - 1)] + grid[clamp(x + 1, 0, grid.length - 1)][clamp(y + 1, 0, grid[0].length - 1)];
		String diag2 = "" + grid[clamp(x + 1, 0, grid.length - 1)][clamp(y - 1, 0, grid[0].length - 1)] + grid[clamp(x - 1, 0, grid.length - 1)][clamp(y + 1, 0, grid[0].length - 1)];
		return diag1.contains("M") && diag1.contains("S") && diag2.contains("M") && diag2.contains("S");
	}

	static int xmasSearch(char[][] grid, int x, int y) {
		// check if word can be found starting from x, y in all 8 directions
		int count = 0;
		for (int i = 0; i < 8; i++) {
			int nx = x;
			int ny = y;
			int j = 0;
			for (j = 0; j < 4; j++) {
				if (grid[nx][ny] != XMAS.charAt(j)) {
					break;
				}
				nx = clamp(nx + dx[i], 0, grid.length-1);
				ny = clamp(ny + dy[i], 0, grid[0].length-1);
			}
			if (j == 4) {
				count++;
			}
		}
		return count;
	}
	
	static int clamp(int val, int min, int max) {
		return Math.max(min, Math.min(max, val));
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
			return peekToken() != null;
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
}