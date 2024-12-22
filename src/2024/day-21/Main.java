import java.util.*;
import java.util.function.LongFunction;
import java.util.stream.Stream;
import java.io.*;
import java.lang.*;

class Main {
	static HashMap<String, Long> memo = new HashMap<>(); // store the min length of instructions for each code

	static class Keypad {
		char[][] keypad;
	}

	static class Robot {
		Keypad keypadToPress;
		Keypad thisKeypad;
		int x;
		int y;

		public void init() {
			// find A in keypadToPress
			for (int i = 0; i < keypadToPress.keypad.length; i++) {
				for (int j = 0; j < keypadToPress.keypad[i].length; j++) {
					if (keypadToPress.keypad[i][j] == 'A') {
						x = j;
						y = i;
						return;
					}
				}
			}
		}

		@SuppressWarnings("unchecked")
		public String type2(String code) {
			// find the coordinates of the '.'
			int xDot = -1;
			int yDot = -1;
			for (int i = 0; i < keypadToPress.keypad.length; i++) {
				for (int j = 0; j < keypadToPress.keypad[i].length; j++) {
					if (keypadToPress.keypad[i][j] == '.') {
						xDot = j;
						yDot = i;
						break;
					}
				}
			}
			StringBuilder instructions = new StringBuilder(code.length()*3);
			for (int i = 0; i < code.length(); i++) {
				instructions.append(type2char(code.charAt(i)));
			}
			// find all permutations of instructions and add to possibleInstructions

			return instructions.toString();
		}

		public String type2char(char c) {
			// figure out how to move from current position to c on keypadToPress
			int xToPress = -1;
			int yToPress = -1;
			for (int j = 0; j < keypadToPress.keypad.length; j++) {
				for (int k = 0; k < keypadToPress.keypad[j].length; k++) {
					if (keypadToPress.keypad[j][k] == c) {
						xToPress = k;
						yToPress = j;
						break;
					}
				}
			}
			if (this.x == xToPress && this.y == yToPress) {
				return "A";
			}
			// move from current position to xToPress, yToPress but you cant go over '.'
			StringBuilder thisInstructions = new StringBuilder(3);

			int x = this.x;
			int y = this.y;
			boolean xFirstValid = true;
			while (x != xToPress) {
				if (x < xToPress) {
					thisInstructions.append('>');
					x++;
				} else {
					thisInstructions.append('<');
					x--;
				}
				if (keypadToPress.keypad[y][x] == '.') {
					//System.out.println("Error: tried to move over '.'");
					xFirstValid = false;
					break;
				}
			}
			while (y != yToPress && xFirstValid) {
				if (y < yToPress) {
					thisInstructions.append('v');
					y++;
				} else {
					thisInstructions.append('^');
					y--;
				}
				if (keypadToPress.keypad[y][x] == '.') {
					//System.out.println("Error: tried to move over '.'");
					xFirstValid = false;
					break;
				}
			}
			thisInstructions.append('A'); // press the key
			String xFirst = thisInstructions.toString();

			// now, try doing y first and add to instructions
			//if (this.x != xToPress) {
			x = this.x;
			y = this.y;
			boolean yFirstValid = true;
			thisInstructions.setLength(0);
			while (y != yToPress) {
				if (y < yToPress) {
					thisInstructions.append('v');
					y++;
				} else {
					thisInstructions.append('^');
					y--;
				}
				if (keypadToPress.keypad[y][x] == '.') {
					//System.out.println("Error: tried to move over '.'");
					yFirstValid = false;
					break;
				}
			}
			while (x != xToPress && yFirstValid) {
				if (x < xToPress) {
					thisInstructions.append('>');
					x++;
				} else {
					thisInstructions.append('<');
					x--;
				}
				if (keypadToPress.keypad[y][x] == '.') {
					//System.out.println("Error: tried to move over '.'");
					yFirstValid = false;
					break;
				}
			}
			thisInstructions.append('A'); // press the key
			String yFirst = thisInstructions.toString();
			//}
			this.x = xToPress;
			this.y = yToPress;
			if (xFirstValid && yFirstValid) {
				String code1 = xFirst;
				String code2 = yFirst;
				long length1 = memo.get(code1);
				long length2 = memo.get(code2);
				if (length1 == Long.MAX_VALUE && length2 == Long.MAX_VALUE) {
					// both are new, no need to remove any
					return code1;
				} else {
					if (length1 < length2) {
						return code1;
					} else {
						return code2;
					}
				}
			} else if (xFirstValid) {
				return xFirst;
			} else if (yFirstValid) {
				return yFirst;
			} else {
				// both are invalid
				return xFirst;
			}
		}

		@SuppressWarnings("unchecked")
		public List<String> type(String code) {
			// find the coordinates of the '.'
			int xDot = -1;
			int yDot = -1;
			for (int i = 0; i < keypadToPress.keypad.length; i++) {
				for (int j = 0; j < keypadToPress.keypad[i].length; j++) {
					if (keypadToPress.keypad[i][j] == '.') {
						xDot = j;
						yDot = i;
						break;
					}
				}
			}
			ArrayList<String>[] instructions = new ArrayList[code.length()]; // stores all possible instructions for each character in code
			for (int i = 0; i < code.length(); i++) {
				char c = code.charAt(i);
				// figure out how to move from current position to c on keypadToPress
				int xToPress = -1;
				int yToPress = -1;
				for (int j = 0; j < keypadToPress.keypad.length; j++) {
					for (int k = 0; k < keypadToPress.keypad[j].length; k++) {
						if (keypadToPress.keypad[j][k] == c) {
							xToPress = k;
							yToPress = j;
							break;
						}
					}
				}
				// move from current position to xToPress, yToPress but you cant go over '.'
				StringBuilder thisInstructions = new StringBuilder();
				
				int x = this.x;
				int y = this.y;
				boolean xFirstValid = true;
				while (x != xToPress) {
					if (x < xToPress) {
						thisInstructions.append('>');
						x++;
					} else {
						thisInstructions.append('<');
						x--;
					}
					if (keypadToPress.keypad[y][x] == '.') {
						//System.out.println("Error: tried to move over '.'");
						xFirstValid = false;
						break;
					}
				}
				while (y != yToPress && xFirstValid) {
					if (y < yToPress) {
						thisInstructions.append('v');
						y++;
					} else {
						thisInstructions.append('^');
						y--;
					}
					if (keypadToPress.keypad[y][x] == '.') {
						//System.out.println("Error: tried to move over '.'");
						xFirstValid = false;
						break;
					}
				}
				thisInstructions.append('A'); // press the key
				instructions[i] = new ArrayList<>();
				if (xFirstValid) {
					instructions[i].add(thisInstructions.toString());
				}

				// now, try doing y first and add to instructions
				//if (this.x != xToPress) {
					x = this.x;
					y = this.y;
					boolean yFirstValid = true;
					thisInstructions = new StringBuilder();
					while (y != yToPress) {
						if (y < yToPress) {
							thisInstructions.append('v');
							y++;
						} else {
							thisInstructions.append('^');
							y--;
						}
						if (keypadToPress.keypad[y][x] == '.') {
							//System.out.println("Error: tried to move over '.'");
							yFirstValid = false;
							break;
						}
					}
					while (x != xToPress && yFirstValid) {
						if (x < xToPress) {
							thisInstructions.append('>');
							x++;
						} else {
							thisInstructions.append('<');
							x--;
						}
						if (keypadToPress.keypad[y][x] == '.') {
							//System.out.println("Error: tried to move over '.'");
							yFirstValid = false;
							break;
						}
					}
					thisInstructions.append('A'); // press the key
					if (yFirstValid) {
						instructions[i].add(thisInstructions.toString());
					}
				//}

				if (instructions[i].size() == 2 && instructions[i].get(0).equals(instructions[i].get(1))) {
					instructions[i].remove(1); // remove duplicate
				}
				// choose the one with the shortest length in memo
				if (instructions[i].size() == 2) {
					String code1 = instructions[i].get(0);
					String code2 = instructions[i].get(1);
					long length1 = memo.getOrDefault(code1, Long.MAX_VALUE);
					long length2 = memo.getOrDefault(code2, Long.MAX_VALUE);
					if(length1 == Long.MAX_VALUE && length2 == Long.MAX_VALUE) {
						// both are new, no need to remove any
					} else {
						if (length1 < length2) {
							instructions[i].remove(1);
						} else {
							instructions[i].remove(0);
						}
					}
				}
				this.x = xToPress;
				this.y = yToPress;
			}
			// find all permutations of instructions and add to possibleInstructions
			List<String> results = new ArrayList<>();
			generatePermutations(instructions, 0, "", results);

			return results;
		}

		// generate all permutations of the instructions
		public static void generatePermutations(List<String>[] lists, int index, String current, List<String> results) {
			if (index == lists.length) {
				results.add(current); // Base case: when all lists are processed, add the concatenated result
				return;
			}

			// Iterate through each string in the current list
			for (String str : lists[index]) {
				generatePermutations(lists, index + 1, current + str, results);
			}
		}

		public String getCode(String instructions) {
			// simulate typing the instructions and return the code 
			Keypad keypad = new Keypad();
			// copy keypadToPress to keypad
			keypad.keypad = new char[keypadToPress.keypad.length][keypadToPress.keypad[0].length];
			for (int i = 0; i < keypadToPress.keypad.length; i++) {
				for (int j = 0; j < keypadToPress.keypad[i].length; j++) {
					keypad.keypad[i][j] = keypadToPress.keypad[i][j];
				}
			}
			int x = this.x;
			int y = this.y;
			StringBuilder code = new StringBuilder();
			for (int i = 0; i < instructions.length(); i++) {
				char c = instructions.charAt(i);
				if (c == '^') {
					y--;
				} else if (c == 'v') {
					y++;
				} else if (c == '<') {
					x--;
				} else if (c == '>') {
					x++;
				} else {
					// press the key
					code.append(keypad.keypad[y][x]);
				}
			}
			return code.toString();
		}
	}

	public static void main(String[] args) throws IOException {
		// read input from file
		Kattio io = new Kattio("day21", System.out);

		// input
		String[] lines = io.getAll().split("\n");

		// fill memo
		fillMemo();

		// part 1
		long part1 = part1(lines);
		System.out.println("Part 1: " + part1);

		// part 2
		long part2 = part2(lines);
		System.out.println("Part 2: " + part2);

		io.close();
	}

	static void fillMemo() {
		// iterate through all permutations of ^v<> chars 3 long and find the min length of instructions
		// generate all permutations of ^v<> chars 3 long
		BaseIterator it = new BaseIterator(4, 3);
		int[] perm;
		while (it.hasNext()) {
			perm = it.next();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < perm.length; i++) {
				if (perm[i] == 0) {
					sb.append('^');
				} else if (perm[i] == 1) {
					sb.append('v');
				} else if (perm[i] == 2) {
					sb.append('<');
				} else {
					sb.append('>');
				}
			}
			sb.append('A');
			Keypad original = new Keypad();
			original.keypad = new char[][] {
					{ '.', '^', 'A' },
					{ '<', 'v', '>' }
			};
			String code = "A"+sb.toString();
			List<String> prevInstructions = List.of(code);
			Robot[] robots = new Robot[3];
			for (int j = 0; j < 3; j++) {
				//System.out.println("code " + code + " robot " + j + " parsing " + prevInstructions.size() + " instructions of length " + prevInstructions.get(0).length());
				robots[j] = new Robot();
				robots[j].keypadToPress = j == 0 ? original : robots[j - 1].thisKeypad;
				Keypad robotKeypad = new Keypad();
				robotKeypad.keypad = new char[][] {
						{ '.', '^', 'A' },
						{ '<', 'v', '>' }
				};
				robots[j].thisKeypad = robotKeypad;
				// make robot start at the A of the previous keypad
				robots[j].init();
				List<String> instructions = new ArrayList<>();
				for (String instruction : prevInstructions) {
					instructions.addAll(robots[j].type(instruction));
				}
				prevInstructions = instructions;
			}
			// get min length
			int length = Integer.MAX_VALUE;
			int minIndex = -1;
			for (int j = 0; j < prevInstructions.size(); j++) {
				if (prevInstructions.get(j).length() < length) {
					length = prevInstructions.get(j).length();
					minIndex = j;
				}
			}
			memo.put(code.substring(1), (long) length);
		}
		it = new BaseIterator(4, 2);
		while (it.hasNext()) {
			perm = it.next();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < perm.length; i++) {
				if (perm[i] == 0) {
					sb.append('^');
				} else if (perm[i] == 1) {
					sb.append('v');
				} else if (perm[i] == 2) {
					sb.append('<');
				} else {
					sb.append('>');
				}
			}
			sb.append('A');
			Keypad original = new Keypad();
			original.keypad = new char[][] {
					{ '.', '^', 'A' },
					{ '<', 'v', '>' }
			};
			String code = "A"+sb.toString();
			List<String> prevInstructions = List.of(code);
			Robot[] robots = new Robot[3];
			for (int j = 0; j < 3; j++) {
				//System.out.println("code " + code + " robot " + j + " parsing " + prevInstructions.size() + " instructions of length " + prevInstructions.get(0).length());
				robots[j] = new Robot();
				robots[j].keypadToPress = j == 0 ? original : robots[j - 1].thisKeypad;
				Keypad robotKeypad = new Keypad();
				robotKeypad.keypad = new char[][] {
						{ '.', '^', 'A' },
						{ '<', 'v', '>' }
				};
				robots[j].thisKeypad = robotKeypad;
				// make robot start at the A of the previous keypad
				robots[j].init();
				List<String> instructions = new ArrayList<>();
				for (String instruction : prevInstructions) {
					instructions.addAll(robots[j].type(instruction));
				}
				prevInstructions = instructions;
			}
			// get min length
			int length = Integer.MAX_VALUE;
			int minIndex = -1;
			for (int j = 0; j < prevInstructions.size(); j++) {
				if (prevInstructions.get(j).length() < length) {
					length = prevInstructions.get(j).length();
					minIndex = j;
				}
			}
			memo.put(code.substring(1), (long) length);
		}
		it = new BaseIterator(4, 1);
		while (it.hasNext()) {
			perm = it.next();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < perm.length; i++) {
				if (perm[i] == 0) {
					sb.append('^');
				} else if (perm[i] == 1) {
					sb.append('v');
				} else if (perm[i] == 2) {
					sb.append('<');
				} else {
					sb.append('>');
				}
			}
			sb.append("A");
			Keypad original = new Keypad();
			original.keypad = new char[][] {
					{ '.', '^', 'A' },
					{ '<', 'v', '>' }
			};
			String code = "A"+sb.toString();
			List<String> prevInstructions = List.of(code);
			Robot[] robots = new Robot[3];
			for (int j = 0; j < 3; j++) {
				//System.out.println("code " + code + " robot " + j + " parsing " + prevInstructions.size() + " instructions of length " + prevInstructions.get(0).length());
				robots[j] = new Robot();
				robots[j].keypadToPress = j == 0 ? original : robots[j - 1].thisKeypad;
				Keypad robotKeypad = new Keypad();
				robotKeypad.keypad = new char[][] {
						{ '.', '^', 'A' },
						{ '<', 'v', '>' }
				};
				robots[j].thisKeypad = robotKeypad;
				// make robot start at the A of the previous keypad
				robots[j].init();
				List<String> instructions = new ArrayList<>();
				for (String instruction : prevInstructions) {
					instructions.addAll(robots[j].type(instruction));
				}
				prevInstructions = instructions;
			}
			// get min length
			int length = prevInstructions.stream().mapToInt(String::length).min().getAsInt();
			memo.put(code.substring(1), (long) length);
		}
		//System.out.println(memo);
	}

	static long part1(String[] lines) {
		long sum = Stream.of(lines).mapToLong(code -> solve(code, 2)).sum();
		return sum;
	}

	static long part2(String[] lines) {
		long sum = Stream.of(lines).mapToLong(code -> solve(code, 25)).sum();
		return sum;
	}

	static long solve(String code, int numRobots) {
		Keypad original = new Keypad();
		original.keypad = new char[][] {
				{ '7', '8', '9' },
				{ '4', '5', '6' },
				{ '1', '2', '3' },
				{ '.', '0', 'A' }
		};

		Robot robot1 = new Robot();
		robot1.keypadToPress = original;
		Keypad robot1Keypad = new Keypad();
		robot1Keypad.keypad = new char[][] {
				{ '.', '^', 'A' },
				{ '<', 'v', '>' }
		};
		robot1.thisKeypad = robot1Keypad;
		// make robot1 start at the A of the previous keypad
		robot1.init();
		String instructions1 = robot1.type2(code);
		//System.out.println(instructions1);

		// repeat for numRobots - 1 more robots
		Robot[] robots = new Robot[25];
		String prevInstructions = instructions1;
		long startTime = System.currentTimeMillis();
		for (int j = 0; j < numRobots; j++) {
			System.out.println("code " + code + " robot " + j + " parsing length " + prevInstructions.length());
			robots[j] = new Robot();
			robots[j].keypadToPress = j == 0 ? robot1.thisKeypad : robots[j - 1].thisKeypad;
			Keypad robotKeypad = new Keypad();
			robotKeypad.keypad = new char[][] {
					{ '.', '^', 'A' },
					{ '<', 'v', '>' }
			};
			robots[j].thisKeypad = robotKeypad;
			// make robot start at the A of the previous keypad
			robots[j].init();
			prevInstructions = robots[j].type2(prevInstructions);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Time taken: " + (endTime - startTime) + "ms");

		// get min length
		System.out.println("length of instruction: " + prevInstructions.length());
		int length = prevInstructions.length();
		//System.out.println(prevInstructions.get(minIndex));

		// find numeric part of lines[i]
		String numeric = "";
		for (int j = 0; j < code.length(); j++) {
			if (Character.isDigit(code.charAt(j))) {
				numeric += code.charAt(j);
			}
		}
		long n = Long.parseLong(numeric);
		return (long)length * n;
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