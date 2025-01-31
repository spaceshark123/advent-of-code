import java.util.*;
import java.io.*;
import java.lang.*;

class Main {
	static int WIDTH = 101;
	static int HEIGHT = 103;

	// robot class
	static class Robot {
		int x;
		int y;
		int v_x;
		int v_y;

		void step(int n) {
			x = (x + v_x * n) % WIDTH;
			y = (y + v_y * n) % HEIGHT;
			while (x < 0) {
				x += WIDTH;
			}
			while (y < 0) {
				y += HEIGHT;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		// read input from file
		Kattio io = new Kattio("day14", System.out);

		// input
		String[] lines = io.getAll().split("\n");
		Robot[] robots = new Robot[lines.length];
		for (int i = 0; i < lines.length; i++) {
			String[] parts = lines[i].split(" ");
			robots[i] = new Robot();
			robots[i].x = Integer.parseInt(parts[0].substring(2, parts[0].indexOf(",")));
			robots[i].y = Integer.parseInt(parts[0].substring(parts[0].indexOf(",") + 1));
			robots[i].v_x = Integer.parseInt(parts[1].substring(2, parts[1].indexOf(",")));
			robots[i].v_y = Integer.parseInt(parts[1].substring(parts[1].indexOf(",") + 1));
		}

		// part 1
		long part1 = part1(robots);
		System.out.println(part1);

		// part 2 old vs new (manual) 
		// part2_old(robots); // much slower
		part2(robots); // optimized

		io.close();
	}

	static long part1(Robot[] robots) {
		// counts for each quadrant (not counting the middle of each axis)
		int middleX = WIDTH / 2;
		int middleY = HEIGHT / 2;
		long q1Count = 0;
		long q2Count = 0;
		long q3Count = 0;
		long q4Count = 0;
		for (int i = 0; i < robots.length; i++) {
			robots[i].step(100);
			// check which quadrant the robot is in
			if (robots[i].x < middleX && robots[i].y < middleY) {
				q1Count++;
			} else if (robots[i].x > middleX && robots[i].y < middleY) {
				q2Count++;
			} else if (robots[i].x < middleX && robots[i].y > middleY) {
				q3Count++;
			} else if (robots[i].x > middleX && robots[i].y > middleY) {
				q4Count++;
			}
		}
		return q1Count * q2Count * q3Count * q4Count;
	}

	static void part2_old(Robot[] robots) {
		long startTime = System.currentTimeMillis();
		// find some interesting timesteps and display to user
		Scanner scan = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		System.out.println("Finding interesting timesteps...\n\n\n\n");
		for (int i = 0; i < 10000; i++) {
			// move all robots one time step
			Arrays.stream(robots).parallel().forEach(r -> r.step(1));
			// calculate the max streak of robots in a row (this might indicate some clustering that could form the tree)
			int maxStreak = 0;
			for (int j = 0; j < HEIGHT; j++) {
				int streak = 0;
				for (int k = 0; k < WIDTH; k++) {
					int count = 0;
					for (int l = 0; l < robots.length; l++) {
						if (robots[l].x == k && robots[l].y == j) {
							count++;
						}
					}
					if (count != 0) {
						streak++;
					} else {
						if (streak > maxStreak) {
							maxStreak = streak;
						}
						streak = 0;
					}
				}
			}
			if (maxStreak > 10) {
				long endTime = System.currentTimeMillis();
				System.out.println("Time taken: " + (endTime - startTime) + "ms");
				// this might be important, show the grid to the user
				System.out.println("SECOND " + (i + 1));
				sb.setLength(0); // clear the stringbuilder
				for (int j = 0; j < HEIGHT; j++) {
					for (int k = 0; k < WIDTH; k++) {
						int count = 0;
						for (int l = 0; l < robots.length; l++) {
							if (robots[l].x == k && robots[l].y == j) {
								count++;
							}
						}
						sb.append(count == 0 ? "." : count);
					}
					sb.append("\n");
				}
				System.out.println(sb.toString());
				//wait for user input
				System.out.println("Press enter to continue");
				String line = scan.nextLine();
				if (line.contains("q")) {
					break;
				}
				startTime = System.currentTimeMillis();
				System.out.println("Finding interesting timesteps...\n\n\n\n");
			}
		}
		scan.close();
	}

	static void part2(Robot[] robots) {
		long startTime = System.currentTimeMillis();
		// find some interesting timesteps and display to user
		Scanner scan = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		System.out.println("Finding interesting timesteps...\n\n\n\n");
		for (int i = 0;; i++) {
			// move all robots one time step
			Arrays.stream(robots).forEach(r -> r.step(1));

			// count the number of robots at each position
			int[][] positionMap = new int[WIDTH][HEIGHT];
			for (int j = 0; j < robots.length; j++) {
				positionMap[robots[j].x][robots[j].y]++;
			}
			// Calculate the max streak of robots in a row
			int maxStreak = 0;
			for (int j = 0; j < HEIGHT; j++) {
				int streak = 0;
				for (int k = 0; k < WIDTH; k++) {
					if (positionMap[k][j] > 0) {
						streak++;
					} else {
						maxStreak = Math.max(maxStreak, streak);
						streak = 0;
					}
				}
				maxStreak = Math.max(maxStreak, streak);  // End-of-row streak
			}
			if (maxStreak > 10) {
				long endTime = System.currentTimeMillis();
				System.out.println("Time taken: " + (endTime - startTime) + "ms");
				// this might be important, show the grid to the user
				System.out.println("SECOND " + (i + 1));
				sb.setLength(0); // clear the stringbuilder
				for (int j = 0; j < HEIGHT; j++) {
					for (int k = 0; k < WIDTH; k++) {
						sb.append(positionMap[k][j] > 0 ? positionMap[k][j] : ".");
					}
					sb.append("\n");
				}
				System.out.println(sb.toString());
				//wait for user input
				System.out.println("Press enter to continue");
				String line = scan.nextLine();
				if (line.contains("q")) {
					break;
				}
				startTime = System.currentTimeMillis();
				System.out.println("Finding interesting timesteps...\n\n\n\n");
			}
		}
		scan.close();
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