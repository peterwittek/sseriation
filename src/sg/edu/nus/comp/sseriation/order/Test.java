package sg.edu.nus.comp.sseriation.order;

import java.util.ArrayList;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Integer> al=new ArrayList<Integer>();
		al.add(5);al.add(6);al.add(7);
		System.out.println(al.size());
		al.add(1,4);
		System.out.println(al.size());
		for (int i=0;i<al.size();i++){
			System.out.println(al.get(i));
		}
	}

}
