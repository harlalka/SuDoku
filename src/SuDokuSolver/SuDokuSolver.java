package SuDokuSolver;
/**
 * Solves SuDoku.
 * Uses 2 logic :
 * 1. Only one number can come at one cell.
 * 2. A number can come at only one position in a row or column or box.
 * @author aashu
 *
 */
public class SuDokuSolver {
	protected int sqrootDimension = 3;
	protected int dimensions = sqrootDimension * sqrootDimension;
	protected int[][] array = null;
	protected boolean debug =false;
	int rownum, colnum;
	boolean firstNumFound = false;
	class PossibleList{
		public MyList<Integer> list = new MyList<Integer>();
	}

	protected PossibleList[][] possibleList;
	protected PossibleList[] confirmedRowList;
	protected PossibleList[] confirmedColumnList;
	protected PossibleList[] confirmedBoxList;
	protected PossibleList[] emptyRowIndexList;
	protected PossibleList[] emptyColumnIndexList;
	protected PossibleList[] emptyBoxIndexList;
	/**
	 * Constructor
	 * @param array SuDoku to be solved
	 */
	public SuDokuSolver(int[][] array){
		this.array = new int[dimensions][dimensions];
		this.array = array;
		initialise();		
	}	
	/**
	 * Initialises all lists
	 */
	protected void initialise(){
		possibleList = new PossibleList[dimensions][dimensions];
		confirmedRowList = new PossibleList[dimensions];
		confirmedColumnList = new PossibleList[dimensions];
		confirmedBoxList = new PossibleList[dimensions];
		emptyRowIndexList = new PossibleList[dimensions];
		emptyColumnIndexList = new PossibleList[dimensions];
		emptyBoxIndexList = new PossibleList[dimensions];

		for(int i=0;i<dimensions;i++){
			confirmedRowList[i] = new PossibleList();
			confirmedColumnList[i] = new PossibleList();
			confirmedBoxList[i] = new PossibleList();
			emptyRowIndexList[i] = new PossibleList();
			emptyColumnIndexList[i] = new PossibleList();
			emptyBoxIndexList[i] = new PossibleList();
			for(int j=0;j<dimensions;j++){
				possibleList[i][j] = new PossibleList();
			}
		}
		for(int i=0;i<dimensions;i++){
			for(int j=0;j<dimensions;j++){				
				int boxnum = boxNumber(i+1,j+1) -1;
				if(array[i][j]!=0){						
					confirmedRowList[i].list.add((Integer)array[i][j]);
					confirmedColumnList[j].list.add((Integer)array[i][j]);
					confirmedBoxList[boxnum].list.add((Integer)array[i][j]);
				}
				else{
					for(int k=1;k<=dimensions;k++){
						possibleList[i][j].list.add((Integer)(k));
					}
					emptyRowIndexList[i].list.add((Integer)(j));
					emptyColumnIndexList[j].list.add((Integer)(i));
					emptyBoxIndexList[boxnum].list.add((Integer)(boxNumberElement(i+1,j+1) - 1));
				}				
			}
		}
		for(int i=0;i<dimensions;i++){
			for(int j=0;j<dimensions;j++){
				int boxnum = boxNumber(i+1,j+1) -1;
				possibleList[i][j].list.minus(confirmedRowList[i].list);
				possibleList[i][j].list.minus(confirmedColumnList[j].list);
				possibleList[i][j].list.minus(confirmedBoxList[boxnum].list);
			}
		}
	}

	/**
	 * Solves the sudoku
	 */
	protected void solve(){
		while(!allPossibleListEmpty()){			
			for(int i=0;i<dimensions;i++){
				for(int j=0;j<dimensions;j++){				
					update(i+1, j+1);
					rowUpdate(i);
					columnUpdate(j);
					int boxnum = boxNumber(i+1,j+1) -1;
					boxUpdate(boxnum);			
				}
			}
		}
	}

	/**
	 * If only one number is possible at given cell then it update all lists. Logic 1
	 * @param rownum range 1-9
	 * @param colnum range 1-9
	 */
	protected void update(int rownum, int colnum){
		rownum--;
		colnum--;
		if(possibleList[rownum][colnum].list.size()==1){
			Integer elem = possibleList[rownum][colnum].list.get(0);
			if (debug){
				System.out.println("One Left Update at "+rownum + " " + colnum + " " + elem);
				possibleList[rownum][colnum].list.print();
			}
			updateOnNewNumberConfirm(rownum, colnum, elem);
		}
	}
	/**
	 * Logic 2 for rows.
	 * @param rownum row number 0-8
	 */
	protected void rowUpdate(int rownum){
			int i = rownum;
			for(int k=1;k<=dimensions;k++){
				int num =0;
				int index=-1;
				int size = emptyRowIndexList[i].list.size();
				for(int l=0;l<size;l++){
					if(possibleList[i][emptyRowIndexList[i].list.get(l)].list.contains(k)){
						num++;
						if(num==2){break;}
						index = emptyRowIndexList[i].list.get(l);
					}
				}
				if(num==1){
					if (debug){
						System.out.println("Row Update at "+i + " " + index + " " + k);
						possibleList[i][index].list.print();
					}
					updateOnNewNumberConfirm(i, index, k);						
				}
			}
	}
	/**
	 * Logic 2 for columns.
	 * @param colnum column number 0-8
	 */
	protected void columnUpdate(int colnum){
			int i = colnum;
			for(int k=1;k<=dimensions;k++){
				int num =0;
				int index=0;
				int size = emptyColumnIndexList[i].list.size();
				for(int l=0;l<size;l++){
					if(possibleList[emptyColumnIndexList[i].list.get(l)][i].list.contains(k)){
						num++;
						if(num==2){break;}
						index = emptyColumnIndexList[i].list.get(l);
					}
				}
				if(num==1){
					if (debug){
						System.out.println("Column Update at " + index + " " + i + " " + k);
						possibleList[index][i].list.print();
					}					
					updateOnNewNumberConfirm(index,i, k);						
				}
			}
	}
	/**
	 * Logic 2 for noxes
	 * @param boxnum box number 0-8
	 */
	protected void boxUpdate(int boxnum){
		int i = boxnum;
			for(int k=1;k<=dimensions;k++){
				int num =0;
				int index=0;
				int size = emptyBoxIndexList[i].list.size();
				for(int l=0;l<size;l++){
					if(possibleList[rowBoxNumber(i+1, emptyBoxIndexList[i].list.get(l)+1)-1]
							[columnBoxNumber(i+1, emptyBoxIndexList[i].list.get(l)+1)-1].list.contains(k)){
						num++;
						if(num==2){break;}
						index = emptyBoxIndexList[i].list.get(l);
					}
				}
				if(num==1){
					if(debug){
						System.out.println("Box Update at " + (rowBoxNumber(i+1,index+1)-1) + " " + (columnBoxNumber(i+1,index+1)-1) + " " + k);
						possibleList[rowBoxNumber(i+1,index+1)-1][columnBoxNumber(i+1,index+1)-1].list.print();
					}
					updateOnNewNumberConfirm(rowBoxNumber(i+1,index+1)-1, 
							columnBoxNumber(i+1,index+1)-1, k);						
				}			
			}
	}

	/**
	 * Updates all list on finding value for one cell.
	 * @param rownum row number (0-8)
	 * @param colnum column number (0-8)
	 * @param elem element to insert at that position
	 */
	protected void updateOnNewNumberConfirm(int rownum, int colnum, Integer elem){
		this.rownum = rownum;
		this.colnum = colnum;
		int boxnum = boxNumber(rownum+1, colnum+1)-1;
		possibleList[rownum][colnum].list.clear();
		confirmedRowList[rownum].list.add(elem);
		confirmedColumnList[colnum].list.add(elem);
		confirmedBoxList[boxnum].list.add(elem);
		emptyRowIndexList[rownum].list.remove((Integer)(colnum));
		emptyColumnIndexList[colnum].list.remove((Integer)(rownum));			
		emptyBoxIndexList[boxnum].list.remove((Integer)(boxNumberElement(rownum+1,colnum+1)-1));
		array[rownum][colnum] = (int) (elem);

		for(int i=0;i<dimensions;i++){
			possibleList[rownum][i].list.remove((Integer)(elem));
			possibleList[i][colnum].list.remove((Integer)(elem));
			possibleList[rowBoxNumber(boxnum+1, i+1)-1][columnBoxNumber(boxnum+1, i+1)-1].list.remove((Integer)(elem));
		}
		if (debug){
			printSuDoku();
			System.out.println();
		}		
	}

	/**
	 * checks whether all possible list are empty or not
	 * @return 
	 */
	protected boolean allPossibleListEmpty(){
		for(int i=0;i<dimensions;i++){
			for(int j=0;j<dimensions;j++){
				if(!possibleList[i][j].list.isEmpty()) {return false;}
			}
		}
		return true;
	}

	/**
	 * 
	 * @param boxnum range 1-9
	 * @param posnum range 1-9
	 * @return actual row (1-9)
	 */
	protected int rowBoxNumber(int boxnum, int posnum){
		return ((boxnum-1)/sqrootDimension)*sqrootDimension + (posnum+sqrootDimension-1)/sqrootDimension;
	}

	/**
	 * 
	 * @param boxnum range 1-9
	 * @param posnum range 1-9
	 * @return actual column (1-9)
	 */
	protected int columnBoxNumber(int boxnum, int posnum){
		return ((boxnum-1)%sqrootDimension)*sqrootDimension + ((posnum-1)%sqrootDimension) +1;
	}

	/**
	 * position of cell in the given box
	 * @param rownum range 1-9
	 * @param colnum range 1-9
	 * @return position of cell in the given box (range 1-9)
	 */
	protected int boxNumberElement(int rownum, int colnum){
		return (  ((rownum-1)%sqrootDimension)*sqrootDimension + ((colnum-1)%sqrootDimension)+1  );		
	}

	/**
	 * 
	 * @param rownum range from 1-9
	 * @param colnum range from 1-9
	 * @return box number(range 1-9) to which that cell i,j belong
	 */
	protected int boxNumber(int rownum, int colnum){
		int result = (rownum-1)/sqrootDimension;
		result*=sqrootDimension;
		result += ((colnum-1)/sqrootDimension);
		result++;
		return result;
	}

	/**
	 * prints the SuDoku
	 */
	public void printSuDoku(){
		for(int i=0;i<dimensions;i++){
			for(int j=0;j<dimensions;j++){
				System.out.print(array[i][j] + "\t");
			}
			System.out.println();
		}
	}

	/**
	 * Sovles and return the solved SuDoKu
	 * @return solved sudoku
	 */
	public int[][] getSolverSudoku(){
		solve();
		return this.array;
	}

}
