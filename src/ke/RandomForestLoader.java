package ke;

import java.util.Random;

import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;

public class RandomForestLoader {
	public static void RandomForestLoader(Instances data) {
		try {
			RandomForest randomForest = new RandomForest();
			randomForest.setNumIterations(10);
			randomForest.setNumFeatures(0);
			randomForest.setSeed(1);
			randomForest.setNumExecutionSlots(2);
			randomForest.buildClassifier(data);
			Evaluation evaluation = new Evaluation(data);
			evaluation.crossValidateModel(randomForest, data, 10, new Random(1));
			System.out.println(evaluation.toSummaryString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}

}
