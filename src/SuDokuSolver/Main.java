/**
 * 
 */
package SuDokuSolver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;

/**
 * @author aashu
 *
 */
public class Main {

	public static String readFile(String filename){
		String everything="";
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			everything = sb.toString();
			br.close();
		} catch(Exception e){
			System.out.println(e);
		}	
		return everything;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filename = args[0];
		String string1 = readFile(filename);

		int[][] array = new  int[9][9];
		int n1 = (int) Math.sqrt(string1.length()/2);
		for(int i=0; i<n1 ; i++){
			for(int j=0; j<2*n1;j+=2){
				array[i][j/2] = string1.charAt(i*2*n1 + j) - 48;
			}
		}		
		SuDokuSolver sudoku = new SuDokuSolver(array);
		//long start = new Date().getTime();
		array = sudoku.getSolverSudoku();
		//long end = new Date().getTime();
		sudoku.printSuDoku();
		//SuDokuChecker checker = new SuDokuChecker(array);
		//System.out.println(checker.OverallChecker());
		//System.out.println(end-start);
	}

}
