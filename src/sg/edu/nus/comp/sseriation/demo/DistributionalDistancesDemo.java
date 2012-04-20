package sg.edu.nus.comp.sseriation.demo;

import java.io.IOException;

import sg.edu.nus.comp.sseriation.order.Correlation;
import sg.edu.nus.comp.sseriation.order.Distributional;
import sg.edu.nus.comp.sseriation.order.Euclidean;
import sg.edu.nus.comp.sseriation.util.VectorNode;

public class DistributionalDistancesDemo {

	public static void main(String[] args) throws IOException {
		String trainingFile = "test_data/train.dat";
		boolean isTransposed=false;
		Distributional seriation = new Euclidean(trainingFile, isTransposed);
		seriation.generateOrderLeftRight();
		seriation.writeNewOrder();
		VectorNode[] newInstance=new VectorNode[1];
		newInstance[0]=new VectorNode(1,1);
		seriation.foldInNewInstance(newInstance);
		seriation.writeNewOrder("test_data/train_eucl_updated.dat");
		
		isTransposed=true;
		Distributional columnSeriation = new Euclidean(trainingFile, isTransposed);
		columnSeriation.generateOrderLeftRight();
		columnSeriation.writeNewOrder("test_data/train_transposed_eucl.dat");				
	}

}
