import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * @author Alexander Steel
 * 
 * Circuit:
 * 
 * This program computes the specification of inputs to JK-flip-flops for the creation of a finite state circuit. 
 * Input: Reads input from file "state.txt", specifying the state table of a finite state machine with inputs. 
 * Output: Prints to standard output the tabulated input to JK flip flops. 
 * 
 * The output can be simplified using a K-map and characteristic equations built. 
 * 
 */

public class Circuit {

	final static int[][] jkEXCITATION = {

		// JK Excitation Table of the form: Current state, next state, J, K
		// Note: -1 represents X, the "don't care" state

		{0,0,0,-1},
		{0,1,1,-1},
		{1,0,-1,1},
		{1,1,-1,0}
	};

	public static void main(String[] args){

		// Input of the form: Current state A, Current State B, Input, Next State A, Next State B}

		/*		int[][] input = {
				{0,0,0,1,1},
				{0,0,1,0,1},
				{0,1,0,0,0},
				{0,1,1,1,0},
				{1,0,0,0,1},
				{1,0,1,1,1},
				{1,1,0,1,0},
				{1,1,1,0,0}
		};*/

		int[][] input = readInput();

		int[][] jkValues = init(input); // Initialize JK output array to zeros. 

		jkValues = computeValues(input,jkValues);

		output(jkValues);

	}


	private static void output(int[][] a) {

		System.out.println("A: J\t" + "A: K\t" + "B: J\t" + "B: K\t");

		for(int i = 0; i < a.length; i++){
			for(int j = 0; j < a[0].length; j++){
				if (a[i][j] != -1)
					System.out.print(a[i][j] + "\t");
				else
					System.out.print("X\t");
			}
			System.out.println();
		}

		System.out.println();

	}

	private static int[][] computeValues(int[][] input, int[][] jkValues) {

		String tmp = "";
		int rowInTable = 0;

		for(int i = 0; i < input.length; i++){

			tmp+= input[i][0];
			tmp+= input[i][3];

			rowInTable = findRow(tmp);
			jkValues = updateJK(i,0,rowInTable,jkValues);

			tmp = "";

			tmp+= input[i][1]; 
			tmp+= input[i][4];

			rowInTable = findRow(tmp);
			jkValues = updateJK(i,1,rowInTable,jkValues);

			tmp = "";

		}	
		return jkValues;
	}


	private static int[][] updateJK(int rowInInput, int k, int rowInEx, int[][] jkValues) {

		if (k == 0){

			jkValues[rowInInput][0] = jkEXCITATION[rowInEx][2];
			jkValues[rowInInput][1] = jkEXCITATION[rowInEx][3];

		}

		if (k == 1){

			jkValues[rowInInput][2] = jkEXCITATION[rowInEx][2];
			jkValues[rowInInput][3] = jkEXCITATION[rowInEx][3];

		}

		return jkValues;
	}

	private static int findRow(String tmp) {

		int a = tmp.charAt(0) - 48;
		int b = tmp.charAt(1) - 48;

		int row = 0;

		for(int i = 0; i < jkEXCITATION.length; i++){

			if (jkEXCITATION[i][0] == a && jkEXCITATION[i][1] == b){
				row = i;
				break;
			}
		}

		return row;
	}


	private static int[][] init(int[][] input) {

		int[][] tmp = new int[input.length][input[0].length-1];

		for(int i = 0; i < tmp.length; i++){
			for(int j = 0; j < tmp[0].length; j++){
				tmp[i][j] = 0;
			}
		}

		return tmp;

	}

	private static void printArray(int[][] a) {

		for(int i = 0; i < a.length; i++){
			for(int j = 0; j < a[0].length; j++){
				System.out.print(a[i][j] + " ");
			}
			System.out.println();
		}

		System.out.println();

	}

	private static int[][] readInput() {
		String matrix = "";

		try {
			Scanner s3 = new Scanner(new File("state.txt"));
			while (s3.hasNext()){
				matrix += s3.next();
				if (s3.hasNextLine())
					matrix+= "%";
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String[] tmpMatrix = matrix.split("%");

		String tmpCounter = tmpMatrix[0];

		int columns = 1;

		for(int i = 0; i < tmpCounter.length(); i++){
			if (tmpCounter.charAt(i) == ',')
				columns++;

		}

		String[][] m = new String[tmpMatrix.length][columns];

		for(int i = 0; i < m.length; i++){
			for(int j = 0; j < m[0].length; j++){
				m[i][j] = "0";
			}

		}

		for(int i = 0; i < m.length; i++){
			m[i] = tmpMatrix[i].split(",");
		}

		int[][] x = new int[tmpMatrix.length][columns];

		for(int i = 0; i < m.length; i++){
			for(int j = 0; j < m[0].length; j++){

				x[i][j] = Integer.parseInt(m[i][j]);
			}

		}
		return x;
	}


}
