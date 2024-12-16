import java.util.*;
import java.io.*;
import java.lang.*;

class Main {
	static final int[] dxs = new int[] { -1, 1, 0, 0 };
	static final int[] dys = new int[] { 0, 0, -1, 1 };
	static HashMap<Character, Integer> dirMap = new HashMap<>(4);

	static int HEIGHT;
	static int WIDTH;

	public static void main(String[] args) throws IOException {
		// read input from file
		Kattio io = new Kattio("day15", System.out);

		// directions
		dirMap.put('<', 0);
		dirMap.put('>', 1);
		dirMap.put('^', 2);
		dirMap.put('v', 3);

		// input
		String[] lines = io.getAll().split("\n");
		int i;
		for (i = 0; i < lines.length; i++) {
			if (lines[i].equals("") && i++ < lines.length) {
				break;
			}
			HEIGHT++;
			WIDTH = lines[i].length();
		}
		char[][] grid = new char[HEIGHT][WIDTH];
		for (int j = 0; j < HEIGHT; j++) {
			grid[j] = lines[j].toCharArray();
		}
		String movesStr = "";
		String line;
		while (i < lines.length && (line = lines[i++]) != null) {
			//System.out.println("line: " + line);
			movesStr += line;
		}
		char[] moves = movesStr.toCharArray();
		char[][] gridCopy = new char[HEIGHT][WIDTH];
		for (int j = 0; j < HEIGHT; j++) {
			gridCopy[j] = grid[j].clone();
		}

		// part 1
		long part1 = part1(grid, moves);
		io.println(part1);

		// part 2
		long part2 = part2(gridCopy, moves);
		io.println(part2);

		io.close();
	}

	static long part1(char[][] grid, char[] moves) {
		// find the starting position (@)
		int x = 0;
		int y = 0;
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				if (grid[i][j] == '@') {
					x = j;
					y = i;
					break;
				}
			}
		}
		// simulate all moves
		for (char move : moves) {
			int dx = dxs[dirMap.get(move)];
			int dy = dys[dirMap.get(move)];
			int nx = x + dx;
			int ny = y + dy;
			if (nx < 0 || nx >= WIDTH || ny < 0 || ny >= HEIGHT || grid[ny][nx] == '#') {
				continue;
			}
			// while the next cell is pushable
			if (grid[ny][nx] == 'O') {
				boolean push = true;
				while (nx >= 0 && nx < WIDTH && ny >= 0 && ny < HEIGHT && grid[ny][nx] == 'O') {
					int nnx = nx + dx;
					int nny = ny + dy;
					if (nnx < 0 || nnx >= WIDTH || nny < 0 || nny >= HEIGHT || grid[nny][nnx] == '#') {
						push = false; // if the block can't be pushed, don't push it
						break;
					}
					if (grid[nny][nnx] == '.') {
						nx = nnx;
						ny = nny;
						break; // if theres an empty space, the block can be pushed
					}
					nx = nnx;
					ny = nny;
				}
				// nx, ny is the last position of everything that is moving (player and blocks)
				// push all objects from the current position to the next position
				if (push) {
					// start from nnx, nny and move back to x, y (shifting everything to the next position)
					while (nx != x || ny != y) {
						int nnx = nx - dx;
						int nny = ny - dy;
						grid[ny][nx] = grid[nny][nnx];
						nx = nnx;
						ny = nny;
					}
					// move the player
					grid[ny][nx] = '.';
					x = nx + dx;
					y = ny + dy;
				}
			} else {
				// if the next cell is empty, move the player
				if (grid[ny][nx] == '.') {
					grid[y][x] = '.';
					grid[ny][nx] = '@';
					x = nx;
					y = ny;
				}
			}
		}
		return sumGPS(grid);
	}

	static long part2(char[][] grid, char[] moves) {
		// scale up grid
		char[][] newGrid = new char[HEIGHT][WIDTH * 2];
		int x = 0;
		int y = 0;
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				char c = grid[i][j];
				if (c == '#') {
					newGrid[i][j * 2] = '#';
					newGrid[i][j * 2 + 1] = '#';
				} else if (c == 'O') {
					newGrid[i][j * 2] = '[';
					newGrid[i][j * 2 + 1] = ']';
				} else if (c == '.') {
					newGrid[i][j * 2] = '.';
					newGrid[i][j * 2 + 1] = '.';
				} else if (c == '@') {
					newGrid[i][j * 2] = '@';
					x = j * 2;
					y = i;
					newGrid[i][j * 2 + 1] = '.';
				}
			}
		}
		// simulate all moves
		for (char move : moves) {
			int dx = dxs[dirMap.get(move)];
			int dy = dys[dirMap.get(move)];
			int nx = x + dx;
			int ny = y + dy;
			if (nx < 0 || nx >= WIDTH * 2 || ny < 0 || ny >= HEIGHT || newGrid[ny][nx] == '#') {
				continue; // if the next cell is a wall, don't move
			}
			// if theres some pushing to be done
			if (newGrid[ny][nx] == '[' || newGrid[ny][nx] == ']') {
				boolean push = true;
				// since a box is 2 wide ([]), it can push multiple boxes at once
				HashSet<String> pushed = new HashSet<>(); // for finding all boxes to push
				HashSet<String> toPush = new HashSet<>(); // boxes to push
				if (newGrid[ny][nx] == '[') {
					toPush.add(ny + "," + nx);
					toPush.add(ny + "," + (nx + 1));
					pushed.add(ny + "," + nx);
					pushed.add(ny + "," + (nx + 1));
				} else if (newGrid[ny][nx] == ']') {
					toPush.add(ny + "," + nx);
					toPush.add(ny + "," + (nx - 1));
					pushed.add(ny + "," + nx);
					pushed.add(ny + "," + (nx - 1));
				}
				while (!pushed.isEmpty()) {
					// go in the direction of the push and check if the next cell is pushable
					// extract coordinates from the set
					String[] coord = pushed.iterator().next().split(",");
					pushed.remove(coord[0] + "," + coord[1]);
					int nnx = Integer.parseInt(coord[1]);
					int nny = Integer.parseInt(coord[0]);
					if (nnx < 0 || nnx >= WIDTH * 2 || nny < 0 || nny >= HEIGHT || newGrid[nny+dy][nnx+dx] == '#') {
						//System.out.println("Push: false triggered at " + nnx + ", " + nny);
						push = false; // if the block can't be pushed, don't push it
						break;
					}
					if(newGrid[nny][nnx] == '[') {
						if (!toPush.contains(nny + "," + (nnx + 1))) {
							toPush.add(nny + "," + (nnx + 1));
							pushed.add(nny + "," + (nnx + 1));
						}
					} else if(newGrid[nny][nnx] == ']') {
						if (!toPush.contains(nny + "," + (nnx - 1))) {
							toPush.add(nny + "," + (nnx - 1));
							pushed.add(nny + "," + (nnx - 1));
						}
					}
					if (newGrid[nny+dy][nnx+dx] == '.') {
						nx = nnx+dx;
						ny = nny+dy;
						continue; // if theres an empty space, the block can be pushed
					}
					if (newGrid[nny + dy][nnx + dx] == '[') {
						if (!toPush.contains((nny + dy) + "," + (nnx + dx))) {
							toPush.add((nny + dy) + "," + (nnx + dx));
							pushed.add((nny + dy) + "," + (nnx + dx));
						}
					} else if (newGrid[nny + dy][nnx + dx] == ']') {
						if (!toPush.contains((nny + dy) + "," + (nnx + dx))) {
							toPush.add((nny + dy) + "," + (nnx + dx));
							pushed.add((nny + dy) + "," + (nnx + dx));
						}
					}
				}
				// sort the boxes to push by dy, dx
				if (push) {
					ArrayList<String> sorted = new ArrayList<>(toPush);
					final int innerDx = dx;
					final int innerDy = dy;
					Collections.sort(sorted, (a, b) -> {
						String[] aCoord = a.split(",");
						String[] bCoord = b.split(",");
						int x1 = Integer.parseInt(aCoord[0]);
						int y1 = Integer.parseInt(aCoord[1]);
						int x2 = Integer.parseInt(bCoord[0]);
						int y2 = Integer.parseInt(bCoord[1]);

						// Sort logic based on dx, dy
						if (innerDy == 0) { // Horizontal sorting
							if (y1 == y2) {
								return (x1 - x2); 
							}
							return (y1 - y2) * -innerDx; // Sort by x depending on direction of dx
						} else if (innerDx == 0) { // Vertical sorting
							if (x1 == x2) {
								return (y1 - y2); // Sort by y depending on direction of dy
							}
							return (x1 - x2) * -innerDy; // Sort by y depending on direction of dy
						}
						return 0; // Fallback
					});
					// go through the boxes and shift them
					for (String coord : sorted) {
						String[] c = coord.split(",");
						nx = Integer.parseInt(c[1]);
						ny = Integer.parseInt(c[0]);
						int nnx = nx + dx;
						int nny = ny + dy;
						newGrid[nny][nnx] = newGrid[ny][nx];
						newGrid[ny][nx] = '.';
					}
					// move the player
					newGrid[y][x] = '.';
					newGrid[y + dy][x + dx] = '@';
					x = x + dx;
					y = y + dy;
				}
			} else {
				// if the next cell is empty, move the player
				if (newGrid[ny][nx] == '.') {
					newGrid[y][x] = '.';
					newGrid[ny][nx] = '@';
					x = nx;
					y = ny;
				}
			}
		}
		return sumGPS(newGrid);
	}

	// sum GPS coordinates of all blocks (100 * first coordinate + second coordinate)
	static long sumGPS(char[][] grid) {
		long sum = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == 'O' || grid[i][j] == '[') {
					sum += 100 * i + j;
				}
			}
		}
		return sum;
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