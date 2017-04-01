package ke;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

// 원래는 DB로부터 가져와야 하는 것
public class Classifier {

	public static String modelname = "";

	public Classifier() {
		// TODO Auto-generated constructor stub
	}

	public static String getTrainData(String queryTrainData) {
		// DB에서 TrainData를 가져와 로컬에 생성 후 path를 가져온다.
		return "example4/train.dat";
	}

	public static String learn(String trainData) {
		// 결과로 생성될 model의 위치를 알기 위해 model의 위치를 반환한다.
		System.out.println("svm learn " + trainData + " " + "...");
		String path = "C:/svm_multiclass_windows/";
		String resultModelPath = path + trainData + ".model"; // trainData.dat가
																// 있는 위치에
																// trainData.dat_model이란
																// 이름으로 저장된다.
		String command = path + "svm_multiclass_learn -c 5000 " + path + trainData + " " + resultModelPath;
		System.out.println("learn command: "+command);
		executeSystemCommand(command);
		return resultModelPath;
	}

	public static void classify(String testData, String modelPath, String pred) {
		// model을 사용해 style을 분류하는 작업이다.
		// testData string으로 테스트 데이터를 로컬에 만든다.
		System.out.println("svm classify model 위치 : " + modelPath + "\n  testData String : " + testData + "...");
		String path = "C:/svm_multiclass_windows/";
		File file = new File(path+"/example4/testData.txt");
		FileWriter fw;
		try {
			fw = new FileWriter(file);
			fw.write(testData);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//현재는 refText로 테스트하지 않고 기본 textData로 한다
		String command = path + "svm_multiclass_classify.exe " + path+"example4/test.dat" + " " + modelPath + " " + path
				+ "example4/"+pred;
		System.out.println("learn command: "+command);

		 executeSystemCommand(command);
	}

	public static String getModelName() {
		//model을 사용해 분류한 결과에서 style 이름을 뽑기 위한 함수
		String modelName = "";
		return modelName;

	}

	public static void executeSystemCommand(String s) {
		// String s= null;

		try {

			// run the Unix "ps -ef" command
			// using the Runtime exec method:
			Process p = Runtime.getRuntime().exec(s);
			// +
			// "/Users/sunghee/Documents/sunghee-data/2016programs/mallet-2.0.7/bin/mallet
			// ");

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			// read the output from the command
			System.out.println("Here is the standard output of the command:\n");
			while ((s = stdInput.readLine()) != null) {
				System.out.println(s);
			}

			// read any errors from the attempted command
			if(stdError.readLine()!=null){
				System.out.println("Here is the standard error of the command (if any):\n");
				while ((s = stdError.readLine()) != null) {
					System.out.println(s);
				}
			}

//			System.exit(0);
		} catch (IOException e) {
			System.out.println("exception happened - here's what I know: ");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public static void main(String[] args) {
		Ref aRef = new Ref();
		CRFmodel aCRFmodel = new CRFmodel();
		Classifier aClassifier = new Classifier();
		String strTraining = "example4/train.dat"; // 입력자료
		String modelName = "example4/model"; // 모델명
		String testData = "example4/test.dat"; // 테스트 데이터
		String pred = "example4/predictions"; // 분류결과

//		aClassifier.learn(strTraining, modelName);
		aClassifier.classify(testData, modelName, pred);
	}
}