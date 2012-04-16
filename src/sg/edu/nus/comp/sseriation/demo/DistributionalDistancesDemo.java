package sg.edu.nus.comp.sseriation.demo;

import java.io.IOException;

import sg.edu.nus.comp.sseriation.order.Correlation;
import sg.edu.nus.comp.sseriation.order.Distributional;
import sg.edu.nus.comp.sseriation.order.Euclidean;
import sg.edu.nus.comp.sseriation.order.Manhattan;
import sg.edu.nus.comp.sseriation.order.MutualInformation;

public class DistributionalDistancesDemo {

	public static void main(String[] args) throws IOException {
		String trainingFile = "test_data/train.dat";
		boolean isClasses=false;
		boolean isTransposed=false;
		Distributional seriation = new Euclidean(trainingFile, isClasses, isTransposed);
		seriation.generateOrderLeftRight();
		seriation.writeNewOrder();
		
		isTransposed=true;
		seriation = new Correlation(trainingFile, isClasses, isTransposed);
		seriation.generateOrderLeftRight();
		seriation.writeNewOrder("test_data/train_transposed_corr.dat");
		
		String trainingFileWithClasses = "test_data/train_with_classes.dat";
		isClasses=true;
		isTransposed=false;
		seriation = new Manhattan(trainingFileWithClasses, isClasses, isTransposed);
		seriation.generateOrderLeftRight();
		seriation.writeNewOrder();

		isTransposed=true;
		seriation = new MutualInformation(trainingFileWithClasses, isClasses, isTransposed);
		seriation.generateOrderLeftRight();
		seriation.writeNewOrder("test_data/train_with_classes_transposed_muti.dat");
		
	}

}
