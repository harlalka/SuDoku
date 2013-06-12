package SuDokuSolver;
/**
 * Checks the SuDoku is correct or not.
 * @author aashu
 *
 */
public class SuDokuChecker {
	private int[][] SuDoku;
	int sqrootDimension = 3;
	int dimensions = sqrootDimension * sqrootDimension;
	
	public SuDokuChecker(int[][] Sudoku){
		SuDoku = new int[dimensions][dimensions];
		this.SuDoku = Sudoku;
	}
	/**
	 * 
	 * @param rowNumber row number starting from 1
	 * @return true if given row has all the numbers
	 */
	public boolean checkRow(int rowNumber){
		for(int i = 1; i<=dimensions; i++){
			for(int j=0;j<dimensions;j++){
				if(SuDoku[rowNumber-1][j]== i) break;
				else if(j == dimensions-1) return false;
			}
		}
		return true;
	}
	/**
	 * 
	 * @param columnNumber column number starting from 1
	 * @return true if given column has all the number
	 */
	public boolean checkColumn(int columnNumber){
		for(int i = 1; i<=dimensions; i++){
			for(int j=0;j<dimensions;j++){
				if(SuDoku[j][columnNumber-1]== i) break;
				else if(j == dimensions-1) return false;
			}
		}
		return true;
	}
	/**
	 * 
	 * @param boxNumber box number starting from 1. boxes are numbered (for 9x9) as follows :
	 * 1 2 3
	 * 4 5 6
	 * 7 8 9
	 * @return true if given box has all the numbers
	 */
	public boolean checkBox(int boxNumber){
		int row = (boxNumber - 1)/sqrootDimension;
		int startingRow = row*sqrootDimension;
		int startingColumn = (boxNumber - startingRow -1) * sqrootDimension;
		for(int i=1;i<dimensions;i++){
			for(int j = startingRow;j < startingRow+sqrootDimension;j++){
				int flag =0;
				for(int k= startingColumn;k<startingColumn+sqrootDimension;k++){
					if(SuDoku[j][k]==i){
						flag = 1;
						break;
					}
				}
				if(flag ==1) break;
				else if(j ==startingRow+sqrootDimension -1) return false;
			}
		}		
		return true;
	}
	/**
	 * 
	 * @return true id SuDOku is correct, otherwise false.
	 */
	public boolean OverallChecker(){
		for(int i=1;i<=dimensions;i++){
			if(!checkRow(i)) return false;
			if(!checkColumn(i)) return false;
			if(!checkBox(i)) return false;
		}
		return true;
	}
	
}
