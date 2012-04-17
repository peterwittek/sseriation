package sg.edu.nus.comp.sseriation.demo;

import java.io.IOException;
import java.util.ArrayList;

import sg.edu.nus.comp.sseriation.order.Distributional;
import sg.edu.nus.comp.sseriation.order.Euclidean;
import sg.edu.nus.comp.sseriation.util.SparseVector;
import sg.edu.nus.comp.sseriation.util.VectorNode;

public class RearrangeRowDemo {

	public static void main(String[] args) throws IOException {
		VectorNode[] vector=new VectorNode[4];
		ArrayList<Integer> order=new ArrayList<Integer>();
		order.add(2);
		order.add(0);
		vector[0]=new VectorNode(0,1);
		vector[1]=new VectorNode(1,2);
		vector[2]=new VectorNode(2,3);
		vector[3]=new VectorNode(5,3);
		updateOrderWithPotentialNewFeatures(vector, order);
		VectorNode[] rearrangedVector=SparseVector.rearrangeRowVectorWithFeatureSeriation(vector, order);
		for (int i=0;i<vector.length;i++){
			System.out.println(i+" "+rearrangedVector[i].index+":"+rearrangedVector[i].value);
		}
		for (int i=0;i<order.size();i++){
			System.out.println(order.get(i));
		}
	}
	
	
	private static void updateOrderWithPotentialNewFeatures(VectorNode[] vector, ArrayList<Integer> order){
		// Spill is the number of features not in the existing order
		for (int j = 0; j < vector.length; j++) {
			boolean found = false;
			for (int k = 0; k < order.size(); k++) {
				if (vector[j].index == order.get(k)) {
					found = true;
					break;
				}
			}
			if (!found) {
				order.add(vector[j].index);
			}
		}
	}

	
}
