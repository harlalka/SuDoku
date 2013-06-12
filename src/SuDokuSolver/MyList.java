/**
 * 
 */
package SuDokuSolver;

import java.util.ArrayList;

/**  
 * MyList extends ArrayList. Overrides add function so as to have distinct element in the list.
 * @author aashu
 *
 */

@SuppressWarnings("serial")
public class MyList<E> extends ArrayList<E>{
	
	/**
	 * Returns the union of this and myList
	 * @param myList
	 * @return union of 2 lists
	 */
	public MyList<E> union(MyList<E> myList){
		MyList<E> result = new MyList<E>();
		result = myList;
		while(!myList.isEmpty()){
			E elem = myList.remove(0);
			result.remove(elem);
			result.add(elem);
		}
		return result;
	}
	/**
	 * Returns the intersection of this and myList
	 * @param myList
	 * @return the intersection of 2 lists
	 */
	public MyList<E> intersection(MyList<E> myList){
		MyList<E> result = new MyList<E>();
		while(!myList.isEmpty()){
			E elem = myList.remove(0);
			if(result.remove(elem)){
				result.add(elem);
			}			
		}
		return result;
	}
	/**
	 * update this to this - myList
	 * @param myList 
	 */
	public void minus(MyList<E> myList){
		int size = this.size();
		for(int i=size-1;i>=0;i--){
			if(myList.contains(this.get(i))){
				this.remove(i);
			}
		}
	}
	
	@Override
	/**
	 * Add elem to list if its not in the list and return true. Else doesn't do anything, return false
	 */
	public boolean add(E elem){
		if (!this.contains(elem)){
			super.add(elem);
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 * prints the elements of this list
	 */
	public void print(){
		for(int i=0;i<this.size();i++){
			System.out.print(this.get(i)+ " ");
		}
		System.out.println();
	}
}
