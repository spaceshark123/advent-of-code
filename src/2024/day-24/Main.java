import java.util.*;
import java.util.stream.*;
import java.io.*;
import java.lang.*;

class Main {
	static int[] dx = { 0, 1, 0, -1 }; // down, right, up, left
	static int[] dy = { 1, 0, -1, 0 };
	static int[] dx8 = { 0, 1, 1, 1, 0, -1, -1, -1 }; // down, DR, right, UR, up, UL, left, DL
	static int[] dy8 = { 1, 1, 0, -1, -1, -1, 0, 1 };

	static class Gate {
		String wire1;
		String wire2;
		String op;
		String wire3;

		public Gate(String wire1, String wire2, String op, String wire3) {
			this.wire1 = wire1;
			this.wire2 = wire2;
			this.op = op;
			this.wire3 = wire3;
		}

		@Override
		public String toString() {
			return wire1 + " " + op + " " + wire2 + " -> " + wire3;
		}

		public int eval(Map<String, Integer> wires) {
			int val1 = wires.getOrDefault(wire1, -1);
			int val2 = wires.getOrDefault(wire2, -1);
			if (val1 == -1 || val2 == -1) {
				return -1;
			}
			int val3 = -1;
			if (op.equals("AND")) {
				val3 = val1 & val2;
			} else if (op.equals("OR")) {
				val3 = val1 | val2;
			} else if (op.equals("XOR")) {
				val3 = val1 ^ val2;
			} else {
				System.out.println("Unknown operation: " + op);
			}
			wires.put(wire3, val3);
			return val3;
		}
	}

	public static void main(String[] args) throws IOException {
		// read input from file
		Kattio io = new Kattio("day24", System.out);

		Map<String, Integer> wires = new HashMap<>();

		io.println("hi");

		// read input until empty line
		while (io.hasNextLine()) {
			String line = io.getLine();
			if (line.isEmpty())
				break;

			String[] parts = line.split(": ");
			String wire = parts[0];
			String value = parts[1];

			wires.put(wire, Integer.parseInt(value));
		}

		String[] gatesStr = io.getAllArr();
		Gate[] gates = new Gate[gatesStr.length];
		for (int i = 0; i < gatesStr.length; i++) {
			String[] parts = gatesStr[i].split(" ");
			String wire1 = parts[0];
			String wire2 = parts[2];
			String op = parts[1]; // AND, OR, XOR
			String wire3 = parts[4];
			gates[i] = new Gate(wire1, wire2, op, wire3);
		}

		// // write to graphviz txt file the gates
		// PrintWriter writer = new PrintWriter("gates.txt", "UTF-8");
		// writer.println("digraph G {");
		// for (Gate gate : gates) {
		// 	writer.println(gate.wire1 + " -> " + gate.wire3 + " [label=\"" + gate.op + "\"];");
		// 	writer.println(gate.wire2 + " -> " + gate.wire3 + " [label=\"" + gate.op + "\"];");
		// }
		// writer.println("}");
		// writer.close();

		long part1 = part1(wires, gates);
		io.println(part1);

		// // part 2
		// long x = getNum(wires, "x");
		// long y = getNum(wires, "y");
		// long zExpected = x + y; // expected value of z (x + y)
		// long z = part1(wires, gates); // actual value of z from the gates

		// io.println("x: " + x + " (" + Long.toBinaryString(x) + ")");
		// io.println("y: " + y + " (" + Long.toBinaryString(y) + ")");
		// io.println("z: " + z + " (" + Long.toBinaryString(z) + ")");
		// io.println("zExpected: " + zExpected + " (" + Long.toBinaryString(zExpected) + ")");

		// // compare the bits of z and zExpected, if they dont match, then the wire z(bit
		// // # from right) is incorrect
		// List<Gate> incorrectGates = new ArrayList<>();
		// for (int i = 0; i < 64; i++) {
		// 	long mask = 1L << i;
		// 	if ((z & mask) != (zExpected & mask)) {
		// 		io.println("Incorrect bit: " + i);
		// 		String gateName = "z" + (i < 10 ? "0" + i : i);
		// 		for (Gate gate : gates) {
		// 			if (gate.wire3.equals(gateName)) {
		// 				io.println(gate);
		// 				incorrectGates.add(gate);
		// 				break;
		// 			}
		// 		}
		// 	}
		// }
		// for (Gate gate : gates) {
		// 	// if the gate is anding 2 corresponding bits of x and y, and the output is not
		// 	// ther corresponding bit of z, then the gate is incorrect
		// 	String num = gate.wire1.substring(1);
		// 	String x1 = "x" + num;
		// 	String y1 = "y" + num;
		// 	String z1 = "z" + num;
		// 	if (gate.op.equals("AND") && gate.wire1.equals(x1) && gate.wire2.equals(y1) && !gate.wire3.equals(z1)
		// 			&& gate.wire3.startsWith("z") && !incorrectGates.contains(gate)) {
		// 		io.println("Incorrect gate: " + gate);
		// 		incorrectGates.add(gate);
		// 	}
		// }
		// // try to swap outputs of all pairs of incorrect gates until the number of
		// // incorrect bits less than original
		// int numIncorrect = incorrectGates.size();
		// int origNumIncorrect = numIncorrect;
		// io.println("Num incorrect: " + numIncorrect);
		// for (int j = 0; j < origNumIncorrect; j++) {
		// 	for (int k = j + 1; k < origNumIncorrect; k++) {
		// 		Gate gate1 = incorrectGates.get(j);
		// 		Gate gate2 = incorrectGates.get(k);
		// 		String temp = gate1.wire3;
		// 		gate1.wire3 = gate2.wire3;
		// 		gate2.wire3 = temp;
		// 		long newZ = part1(wires, gates);
		// 		// compare the bits of z and zExpected, if they dont match, then the wire z(bit
		// 		// # from right) is incorrect
		// 		List<Gate> incorrectGates2 = new ArrayList<>();
		// 		for (int i = 0; i < 64; i++) {
		// 			long mask = 1L << i;
		// 			if ((newZ & mask) != (zExpected & mask)) {
		// 				String gateName = "z" + (i < 10 ? "0" + i : i);
		// 				for (Gate gate : gates) {
		// 					if (gate.wire3.equals(gateName)) {
		// 						incorrectGates2.add(gate);
		// 						break;
		// 					}
		// 				}
		// 			}
		// 		}
		// 		for (Gate gate : gates) {
		// 			// if the gate is anding 2 corresponding bits of x and y, and the output is not
		// 			// ther corresponding bit of z, then the gate is incorrect
		// 			String num = gate.wire1.substring(1);
		// 			String x1 = "x" + num;
		// 			String y1 = "y" + num;
		// 			String z1 = "z" + num;
		// 			if (gate.op.equals("AND") && ((gate.wire1.equals(x1) && gate.wire2.equals(y1)) || (gate.wire2.equals(x1) && gate.wire1.equals(y1)))
		// 					&& !gate.wire3.equals(z1) && gate.wire3.startsWith("z") && !incorrectGates2.contains(gate)) {
		// 				incorrectGates2.add(gate);
		// 			}
		// 		}
		// 		if (incorrectGates2.size() < numIncorrect) {
		// 			io.println("Swapped " + gate1 + " and " + gate2);
		// 			io.println("New z: " + newZ + " (" + Long.toBinaryString(newZ) + ")");
		// 			numIncorrect = incorrectGates2.size();
		// 		} else {
		// 			temp = gate1.wire3;
		// 			gate1.wire3 = gate2.wire3;
		// 			gate2.wire3 = temp;
		// 		}
		// 	}
		// }



		// io.println("hi");
		// ArrayList<Gate> badgatesList = new ArrayList<>();
		// for (Gate g : gates) {
		// 	if (g.wire3.startsWith("z") && !g.op.equals("XOR")) {
		// 		badgatesList.add(g);
		// 	}
		// }

		// Gate[] badgates = badgatesList.toArray(new Gate[0]);
		// io.println(badgatesList);
		// // brute force 4 pairs of bad gates whose outputs to swap to make the z value correct
		// for (int i = 0; i < badgates.length; i++) {
		// 	Gate gate1 = badgates[i];
		// 	for (int j = i + 1; j < badgates.length; j++) {
		// 		// one pair of gates
		// 		Gate gate2 = badgates[j];
		// 		for (int k = 0; k < badgates.length; k++) {
		// 			Gate gate3 = badgates[k];
		// 			// prune if gate3 is from the previous pair
		// 			if (gate3 == gate1 || gate3 == gate2) {
		// 				continue;
		// 			}
		// 			for (int l = k + 1; l < badgates.length; l++) {
		// 				// 2nd pair of gates
		// 				Gate gate4 = badgates[l];
		// 				// prune if gate4 is from the previous pair
		// 				if (gate4 == gate1 || gate4 == gate2) {
		// 					continue;
		// 				}
		// 				for (int m = 0; m < badgates.length; m++) {
		// 					Gate gate5 = badgates[m];
		// 					// prune if the gates are from the previous pairs
		// 					if (gate5 == gate1 || gate5 == gate2 || gate5 == gate3 || gate5 == gate4) {
		// 						continue;
		// 					}
		// 					for (int n = m + 1; n < badgates.length; n++) {
		// 						// 3rd pair of gates
		// 						Gate gate6 = badgates[n];
		// 						// prune if the gates are from the previous pairs
		// 						if (gate6 == gate1 || gate6 == gate2 || gate6 == gate3 || gate6 == gate4) {
		// 							continue;
		// 						}
		// 						for (int o = 0; o < badgates.length; o++) {
		// 							Gate gate7 = badgates[o];
		// 							// prune if the gates are from the previous pairs
		// 							if (gate7 == gate1 || gate7 == gate2 || gate7 == gate3 || gate7 == gate4
		// 									|| gate7 == gate5 || gate7 == gate6) {
		// 								continue;
		// 							}
		// 							for (int p = o + 1; p < badgates.length; p++) {
		// 								// 4th pair of gates
		// 								Gate gate8 = badgates[p];
		// 								// prune if the gates are from the previous pairs
		// 								if (gate8 == gate1 || gate8 == gate2 || gate8 == gate3 || gate8 == gate4
		// 										|| gate8 == gate5 || gate8 == gate6) {
		// 									continue;
		// 								}

		// 								String temp1 = gate1.wire3;
		// 								String temp2 = gate2.wire3;
		// 								String temp3 = gate3.wire3;
		// 								String temp4 = gate4.wire3;
		// 								String temp5 = gate5.wire3;
		// 								String temp6 = gate6.wire3;
		// 								String temp7 = gate7.wire3;
		// 								String temp8 = gate8.wire3;
		// 								gate1.wire3 = temp2;
		// 								gate2.wire3 = temp1;
		// 								gate3.wire3 = temp4;
		// 								gate4.wire3 = temp3;
		// 								gate5.wire3 = temp6;
		// 								gate6.wire3 = temp5;
		// 								gate7.wire3 = temp8;
		// 								gate8.wire3 = temp7;
		// 								long newZ = part1(wires, gates);
		// 								if (newZ == zExpected) {
		// 									io.println("Swapped " + gate1 + " and " + gate2);
		// 									io.println("Swapped " + gate3 + " and " + gate4);
		// 									io.println("Swapped " + gate5 + " and " + gate6);
		// 									io.println("Swapped " + gate7 + " and " + gate8);
		// 									io.println("New z: " + newZ + " (" + Long.toBinaryString(newZ) + ")");
		// 									break;
		// 								}
		// 								gate1.wire3 = temp1;
		// 								gate2.wire3 = temp2;
		// 								gate3.wire3 = temp3;
		// 								gate4.wire3 = temp4;
		// 								gate5.wire3 = temp5;
		// 								gate6.wire3 = temp6;
		// 								gate7.wire3 = temp7;
		// 								gate8.wire3 = temp8;
		// 							}
		// 						}
		// 					}
		// 				}
		// 			}
		// 		}
		// 	}
		// }
		// io.println("done");

		//io.println("Num incorrect: " + numIncorrect);
		//io.println(Arrays.toString(gates));

		io.close();
	}

	static long part1(Map<String, Integer> wires, Gate[] gates) {
		// read rest of input line by line
		for (int j = 0; j < gates.length; j++) {
			for (int i = 0; i < gates.length; i++) {
				gates[i].eval(wires);
			}
		}

		return getNum(wires, "z");
	}

	static long getNum(Map<String, Integer> wires, String prefix) {
		wires = wires.entrySet().stream()
				.filter(e -> e.getKey().startsWith(prefix))
				.sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		// create number where each successive bit is the value of the wire
		long num = 0;
		for (Map.Entry<String, Integer> wire : wires.entrySet()) {
			num <<= 1;
			num |= wire.getValue();
		}
		return num;
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
		// syntax for creating: PermIterator it = new PermIterator(length of array);
		// syntax for iterating: while(it.hasNext()) { int[] perm = it.next(); }
		// permutations can be used as indices for permuting other data types like
		// strings in lexicographical order
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
			// Find the first element from the right that can be incremented (start index of
			// suffix)
			int i;
			for (i = size - 1; i > 0 && array[i - 1] >= array[i]; i--)
				;
			// If no such element exists, we have reached the last permutation
			if (i == 0) {
				hasNext = false;
				return result;
			}
			// pivot is index before suffix start
			// set pivot to smallest element in suffix bigger than pivot
			int minsuffix = Integer.MAX_VALUE;
			int minInd = 0;
			for (int j = i; j < size; j++) {
				if (array[j] < minsuffix && array[j] > array[i - 1]) {
					minsuffix = array[j];
					minInd = j;
				}
			}
			ArrayHelper.swap(array, i - 1, minInd);
			// reverse suffix
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

		public TreeNode(T data, TreeNode<T> left, TreeNode<T> right) {
			this.data = data;
			this.left = left;
			this.right = right;
		}

		@Override
		public String toString() {
			return data.toString();
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			TreeNode<?> other = (TreeNode<?>) obj;
			if (data == null) {
				if (other.data != null) {
					return false;
				}
			} else if (!data.equals(other.data)) {
				return false;
			}
			return true;
		}

		@Override
		public int hashCode() {
			return data == null ? 0 : data.hashCode();
		}
	}

	public static class LinkedNode<T> {
		public T data;
		public LinkedNode<T> next;

		public LinkedNode(T data) {
			this.data = data;
			this.next = null;
		}

		public LinkedNode(T data, LinkedNode<T> next) {
			this.data = data;
			this.next = next;
		}

		@Override
		public String toString() {
			return data.toString();
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			LinkedNode<?> other = (LinkedNode<?>) obj;
			if (data == null) {
				if (other.data != null) {
					return false;
				}
			} else if (!data.equals(other.data)) {
				return false;
			}
			return true;
		}

		@Override
		public int hashCode() {
			return data == null ? 0 : data.hashCode();
		}

		public static <T> LinkedNode<T> fromArray(T[] arr) {
			LinkedNode<T> head = new LinkedNode<>(arr[0]);
			LinkedNode<T> temp = head;
			for (int i = 1; i < arr.length; i++) {
				temp.next = new LinkedNode<>(arr[i]);
				temp = temp.next;
			}
			return head;
		}
	}

	public static class GraphNode<T> implements Comparable<GraphNode<T>> {
		public T data;
		public ArrayList<GraphNode<T>> neighbors;
		boolean useRef; // whether to use reference equality for equals and hashCode

		public GraphNode(T data, boolean useRef) {
			this.data = data;
			this.neighbors = new ArrayList<GraphNode<T>>();
			this.useRef = useRef;
		}

		public GraphNode(T data) {
			this(data, false);
		}

		@Override
		public String toString() {
			return data.toString();
		}

		@Override
		public boolean equals(Object obj) {
			if (useRef) {
				return this == obj;
			}
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			GraphNode<?> other = (GraphNode<?>) obj;
			if (data == null) {
				if (other.data != null) {
					return false;
				}
			} else if (!data.equals(other.data)) {
				return false;
			}
			return true;
		}

		@Override
		public int hashCode() {
			if (useRef) {
				return System.identityHashCode(this);
			}
			return data == null ? 0 : data.hashCode();
		}

		@Override
		@SuppressWarnings("unchecked")
		public int compareTo(GraphNode<T> o) {
			if (data instanceof Comparable) {
				return ((Comparable<T>) data).compareTo(o.data);
			}
			return 0;
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