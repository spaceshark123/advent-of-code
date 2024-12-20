import java.util.*;
import java.io.*;
import java.lang.*;

class Main {
	public static void main(String[] args) throws IOException {
		// read input from file
		Kattio io = new Kattio("day20", System.out);

		// input
		String[] lines = io.getAll().split("\n");
		char[][] grid = new char[lines.length][lines[0].length()];
		for (int i = 0; i < lines.length; i++) {
			grid[i] = lines[i].toCharArray();
		}

		// part 1
		long part1 = part1(grid);
		System.out.println(part1);

		// part 2
		long part2 = part2(grid);
		System.out.println(part2);

		io.close();
	}

	static long part1(char[][] grid) {
		// Find the starting point
		int startRow = -1;
		int startCol = -1;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == 'S') {
					startRow = i;
					startCol = j;
					break;
				}
			}
		}
		// bfs to find shortest path to E
		long shortestPath = bfs(grid, startRow, startCol);
		// find all deletions of a single # that will shorten the shortest path by >=100
		int count = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				// if it is a wall and it is next to 2 empty spaces, check if removing it will shorten the shortest path by >=100
				if (grid[i][j] == '#') {
					int countEmpty = 0;
					if (i - 1 >= 0 && grid[i - 1][j] == '.') {
						countEmpty++;
					}
					if (i + 1 < grid.length && grid[i + 1][j] == '.') {
						countEmpty++;
					}
					if (j - 1 >= 0 && grid[i][j - 1] == '.') {
						countEmpty++;
					}
					if (j + 1 < grid[0].length && grid[i][j + 1] == '.') {
						countEmpty++;
					}
					if (countEmpty < 2) {
						continue;
					}
					// remove the wall and check if the shortest path is >=100 shorter
					grid[i][j] = '.';
					long newshortestPath = bfs(grid, startRow, startCol);
					if (newshortestPath <= shortestPath - 100) {
						count++;
					}
					grid[i][j] = '#';
				}
			}
		}
		return count+1;
	}

	static long part2(char[][] grid) {
		// Find the starting point
		int startRow = -1;
		int startCol = -1;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == 'S') {
					startRow = i;
					startCol = j;
					break;
				}
			}
		}
		// bfs to find shortest path to all empty cells
		long shortestPath = -1;
		int count = 0;
		long[][] dp = new long[grid.length][grid[0].length];
		Queue<int[]> queue = new LinkedList<>();
		queue.add(new int[] { startRow, startCol, 0 });
		boolean[][] visited = new boolean[grid.length][grid[0].length];
		visited[startRow][startCol] = true;
		while (!queue.isEmpty()) {
			int[] curr = queue.poll();
			int row = curr[0];
			int col = curr[1];
			int dist = curr[2];
			dp[row][col] = dist;
			if (grid[row][col] == 'E') {
				shortestPath = dist;
				break;
			}
			// Try moving up
			if (row - 1 >= 0 && grid[row - 1][col] != '#' && !visited[row - 1][col]) {
				queue.add(new int[] { row - 1, col, dist + 1 });
				visited[row - 1][col] = true;
			}
			// Try moving down
			if (row + 1 < grid.length && grid[row + 1][col] != '#' && !visited[row + 1][col]) {
				queue.add(new int[] { row + 1, col, dist + 1 });
				visited[row + 1][col] = true;
			}
			// Try moving left
			if (col - 1 >= 0 && grid[row][col - 1] != '#' && !visited[row][col - 1]) {
				queue.add(new int[] { row, col - 1, dist + 1 });
				visited[row][col - 1] = true;
			}
			// Try moving right
			if (col + 1 < grid[0].length && grid[row][col + 1] != '#' && !visited[row][col + 1]) {
				queue.add(new int[] { row, col + 1, dist + 1 });
				visited[row][col + 1] = true;
			}
		}
		// for all pairs of empty cells less than 20 manhattan steps away, check if the traveled distance is <= shortestPath - 100
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				if(grid[i][j] != '#') {
					// find all other empty cells that are less than 20 manhattan steps away (dont have to be connected)
					List<int[]> possibleEnds = new ArrayList<>();
					queue = new LinkedList<>();
					queue.add(new int[] { i, j, 0 });
					visited = new boolean[grid.length][grid[0].length];
					visited[i][j] = true;
					while (!queue.isEmpty()) {
						int[] curr = queue.poll();
						int row = curr[0];
						int col = curr[1];
						int dist = curr[2];
						if (dist > 20) {
							continue;
						}
						if (grid[row][col] != '#') {
							possibleEnds.add(new int[] { row, col, dist });
						}
						// Try moving up
						if (row - 1 >= 0 && !visited[row - 1][col]) {
							queue.add(new int[] { row - 1, col, dist + 1 });
							visited[row - 1][col] = true;
						}
						// Try moving down
						if (row + 1 < grid.length && !visited[row + 1][col]) {
							queue.add(new int[] { row + 1, col, dist + 1 });
							visited[row + 1][col] = true;
						}
						// Try moving left
						if (col - 1 >= 0 && !visited[row][col - 1]) {
							queue.add(new int[] { row, col - 1, dist + 1 });
							visited[row][col - 1] = true;
						}
						// Try moving right
						if (col + 1 < grid[0].length && !visited[row][col + 1]) {
							queue.add(new int[] { row, col + 1, dist + 1 });
							visited[row][col + 1] = true;
						}
					}
					// check if diff <= 100
					for(int[] end : possibleEnds) {
						long diff = (dp[end[0]][end[1]] - dp[i][j]) - end[2];
						if (100 <= diff) {
							count++;
						}
					}
				}
			}
		}
		return count;
	}

	static long bfs(char[][] grid, int startRow, int startCol) {
		Queue<int[]> queue = new LinkedList<>();
		queue.add(new int[] { startRow, startCol, 0 });
		boolean[][] visited = new boolean[grid.length][grid[0].length];
		visited[startRow][startCol] = true;
		while (!queue.isEmpty()) {
			int[] curr = queue.poll();
			int row = curr[0];
			int col = curr[1];
			int dist = curr[2];
			if (grid[row][col] == 'E') {
				return dist;
			}
			// Try moving up
			if (row - 1 >= 0 && grid[row - 1][col] != '#' && !visited[row - 1][col]) {
				queue.add(new int[] { row - 1, col, dist + 1 });
				visited[row - 1][col] = true;
			}
			// Try moving down
			if (row + 1 < grid.length && grid[row + 1][col] != '#' && !visited[row + 1][col]) {
				queue.add(new int[] { row + 1, col, dist + 1 });
				visited[row + 1][col] = true;
			}
			// Try moving left
			if (col - 1 >= 0 && grid[row][col - 1] != '#' && !visited[row][col - 1]) {
				queue.add(new int[] { row, col - 1, dist + 1 });
				visited[row][col - 1] = true;
			}
			// Try moving right
			if (col + 1 < grid[0].length && grid[row][col + 1] != '#' && !visited[row][col + 1]) {
				queue.add(new int[] { row, col + 1, dist + 1 });
				visited[row][col + 1] = true;
			}
		}
		return -1;
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