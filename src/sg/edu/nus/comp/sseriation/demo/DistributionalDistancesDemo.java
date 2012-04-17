package sg.edu.nus.comp.sseriation.demo;

import java.io.IOException;

import javax.swing.plaf.metal.MetalIconFactory.FolderIcon16;

import sg.edu.nus.comp.sseriation.order.Correlation;
import sg.edu.nus.comp.sseriation.order.Distributional;
import sg.edu.nus.comp.sseriation.order.Euclidean;
import sg.edu.nus.comp.sseriation.order.Manhattan;
import sg.edu.nus.comp.sseriation.order.MutualInformation;
import sg.edu.nus.comp.sseriation.util.VectorNode;

public class DistributionalDistancesDemo {

	public static void main(String[] args) throws IOException {
		String trainingFile = "test_data/train.dat";
		boolean isClasses=false;
		boolean isTransposed=false;
		Distributional seriation = new Euclidean(trainingFile, isClasses, isTransposed);
		seriation.generateOrderLeftRight();
		seriation.writeNewOrder();
		VectorNode[] newInstance=new VectorNode[1];
		newInstance[0]=new VectorNode(1,1);
		seriation.foldInNewInstance(newInstance);
		seriation.writeNewOrder("test_data/train_eucl_updated.dat");
		
		isTransposed=true;
		seriation = new Correlation(trainingFile, isClasses, isTransposed);
		seriation.generateOrderLeftRight();
		seriation.writeNewOrder("test_data/train_transposed_corr.dat");

		seriation = new Correlation(trainingFile, isClasses, isTransposed);
		seriation.generateOrderInsert();
		seriation.writeNewOrder("test_data/train_transposed_corr_in.dat");
		
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
