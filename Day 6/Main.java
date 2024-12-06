import java.util.*;
import java.io.*;
import java.lang.*;

class Main {
	static final int[] dxs = { -1, 0, 1, 0 };
	static final int[] dys = { 0, 1, 0, -1 };

	public static void main(String[] args) throws IOException {
		// read input from file
		Kattio io = new Kattio("day6", System.out);

		// input
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
		// find the starting point (^)
		int[] start = findStart(grid);
		int x = start[0];
		int y = start[1];
		// store visited nodes
		boolean[][] visited = new boolean[grid.length][grid[0].length];
		int direction = 0; // 0 = up, 1 = right, 2 = down, 3 = left
		// mark the current node as visited
		visited[x][y] = true;
		// simulate the path
		while (true) {
			// find the next direction
			int dx = dxs[direction];
			int dy = dys[direction];
			while (0 <= x + dx && x + dx < grid[0].length && 0 <= y + dy && y + dy < grid.length
					&& grid[x + dx][y + dy] == '#') {
				direction = (direction + 1) % 4;
				dx = dxs[direction];
				dy = dys[direction];
			}
			// move in the current direction
			x += dx;
			y += dy;
			if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length) {
				break;
			}
			// mark the current node as visited
			visited[x][y] = true;
		}
		// count the number of visited nodes
		int count = 0;
		for (int i = 0; i < visited.length; i++) {
			for (int j = 0; j < visited[0].length; j++) {
				if (visited[i][j]) {
					count++;
				}
			}
		}
		return count;
	}

	static int part2(char[][] grid) {
		// find the starting point (^)
		int[] start = findStart(grid);
		int x = start[0];
		int y = start[1];
		// store visited nodes
		@SuppressWarnings("unchecked")
		HashSet<Integer>[][] visited = new HashSet[grid.length][grid[0].length];
		for (int i = 0; i < visited.length; i++) {
			for (int j = 0; j < visited[0].length; j++) {
				visited[i][j] = new HashSet<>();
			}
		}
		HashSet<String> possibleObstructions = new HashSet<>();
		// simulate the path until we leave the grid
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] != '.') {
					continue;
				}
				progressBar(20, "Part 2", i * grid[0].length + j, grid.length * grid[0].length, "");
				// store the current node
				char temp = grid[i][j];
				int direction = 0; // 0 = up, 1 = right, 2 = down, 3 = left
				int tempX = x;
				int tempY = y;
				grid[i][j] = '#';
				// array of hashsets to store the direction we came from
				for (int f = 0; f < visited.length; f++) {
					for (int g = 0; g < visited[0].length; g++) {
						visited[f][g].clear();
					}
				}
				// mark the current node as visited
				visited[x][y].add(direction);
				// simulate the path
				while (true) {
					// find the next direction
					int dx = dxs[direction];
					int dy = dys[direction];
					while (0 <= x + dx && x + dx < grid[0].length && 0 <= y + dy && y + dy < grid.length
							&& grid[x + dx][y + dy] == '#') {
						direction = (direction + 1) % 4;
						dx = dxs[direction];
						dy = dys[direction];
					}
					// move in the current direction
					x += dx;
					y += dy;
					if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length) {
						break;
					}
					// if we reach a node we have visited before, it is an infinite loop
					if (visited[x][y].contains(direction)) {
						possibleObstructions.add(i + " " + j);
						break;
					}
					// mark the current node as visited
					visited[x][y].add(direction);
				}
				// reset the grid
				grid[i][j] = temp;
				x = tempX;
				y = tempY;
			}
		}
		System.out.println();
		return possibleObstructions.size();
	}
	
	static int[] findStart(char[][] grid) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == '^') {
					return new int[] { i, j };
				}
			}
		}
		return new int[] { -1, -1 };
	}

	static void progressBar(int width, String title, int current, int total, String subtitle) {
		String filled = "█";
		String unfilled = "░";
		double fill = (double) current / total;
		if (fill >= 0 && fill <= 1) {
			//set progress bar
			int fillAmount = (int) Math.ceil(fill * width);
			StringBuilder bar = new StringBuilder();
			bar.append(title).append(": ").append(filled.repeat(fillAmount)).append(unfilled.repeat(width - fillAmount)).append(" ").append(current).append("/").append(total).append(" ").append(subtitle).append("      ").append("\r");
			System.out.print(bar.toString());
		}
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
}