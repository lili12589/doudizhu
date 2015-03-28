/*
 * 包含三个类class Sort，class Pack，class Mycomparator 
输入一个arraylist经过特殊处理，返回arraylist。。。get。。
*/
package android.game.logic;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
public class Sort {
	private ArrayList<Integer> f_list=null;
	private ArrayList<Pack> pack_list=null;
	public Sort(ArrayList<Integer> temp){
		f_list=new ArrayList<Integer>();
		//遍历给pack_list赋值
		pack_list=new ArrayList<Pack>();	
		Iterator<Integer> it = temp.iterator();
		for (int i = 0; i < (temp.size()) && (it.hasNext()); i++) {
			int t=it.next();
			pack_list.add(new Pack(t));
		}
		//排序
		Collections.sort(pack_list, new Mycomparator());
		//对f_list赋值
		Iterator<Pack> it1 =pack_list.iterator();
		for (int i = 0; i < (temp.size()) && (it1.hasNext()); i++) {
			Pack t=it1.next();
			f_list.add(t.getFirst());
		}
		
	}
	/**
	 * @return the f_list
	 */
	public ArrayList<Integer> getF_list() {
		return f_list;
	}
	
}
//封装数据为了便于排序
class Pack{
	int first,second;

	Pack(int t){
		first=t;
		if(t==52){
			second=13;
		}else if(t==53){
			second=14;
		}else{			
			second=first%13;
		}
	}

	/**
	 * @return the first
	 */
	public int getFirst() {
		return first;
	}

	/**
	 * @return the second
	 */
	public int getSecond() {
		return second;
	}	
}

//排序类
class Mycomparator implements Comparator<Object> {
	
	public int compare(Object o1, Object o2) {
		Pack d1= (Pack) o1;
		Pack d2= (Pack) o2;
		if(d1.getSecond()>d2.getSecond()){
			return 1;
		}else if(d1.getSecond()<d2.getSecond()){
			return -1;
		}else{
			return 0;
		}
	}

}