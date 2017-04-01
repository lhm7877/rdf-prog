package ke;


import java.util.HashMap;

class RefSet_Style extends RefSet {
	String style;
}
//TODO : InfoExtract를 상속받는 이유? - > 관계성을 넣기 위해서
//Style 관점의 InfoExtract이다.

public class InfoExtract_Style extends InfoExtract {
	public InfoExtract_Style() {
	}	
	public static String classifier_by_style;
	// Do InfoExtract_Style with input types RefSet_Style and HashMap with names
	// and models
	public static void InfoExtract_Style(RefSet_Style aRefSet_Style, HashMap<String, CRFmodel> aCRFmodel) {
		InfoExtract.InfoExtract(aRefSet_Style, aCRFmodel.get(aRefSet_Style.style));
	}
	public static void InfoExtract_Style() {
		String trainData = "example4/train.dat"; // 분류하기 위한 학습데이터
		String modelName = "example4/model"; // 분류 모델
		Ref aRef = new Ref();
		// ConnectionAlgoDB.
		// 선결조건이 필요하다. 무엇을 위한 선결조건은 무엇인가? 아래 코드 부분는 설결조건을 표현한 것인데 그 선결조건을 그래프로
		// 만들어야 하나? 그렇다.
		// 만든 그래프, 즉, 선결조건으로부터 traverse하면서 아래 코드를 만들어야 한다. 아래 코드를 자동으로 생성하는 데에
		// 필요한 키워드들은 그래프에서 명시되든지
		// 혹은 지식베이스로부터 가져와야 한다.
		
		// 아래 코드는 svm 에 대한 실예(instance)이다.
		// svm_multiclass_learn -c 5000 example4/train.dat example4/model
		
		// train.dtat으로 model을 만든다.
		
		// svm_multiclass_classify example4/test.dat example4/model
		// example4/predictions
		// 여기서, 명령어 자체, 그리고, 옵션들의 값은 어디서 자동으로 생설할 수 있을까?
		// classifier_by_style은 무엇인가? 왜 필요한가? modelName과 같은 것 아닌가? modelName은
		// 1) 모델자체일 수도 있고 2) 모델을 지칭하는 이름일 수도 있지만, 3) 여기서는 모델이 위치한 경로일 수 있다. 모델의
		// 이름과 경로는 어떻게 다른가?
		// 경로와 이름은 같은 목적으로 사용되는 식별자이다. 즉, 그 목적은 구분을 위한 것이다. URL와 URN의 차이라고 할 수
		// 있다.
		// 이들 모두 URI와도 같은 의미를 지닐 것이다.
		// 1) 모델자체이면 DB에 blob형태로 저장되어 있어야 하고, 2) 모델을 지칭하는 이름이라면 그 이름을 통해서 그 모델이
		// 어디 있는지 다시 찾아야 하는 번거로움이 있고
		// 3) 만약 모델이 위치한 경로라면 server 내의 파일
		// URL일 것이다.
		if (InfoExtract_Style.classifier_by_style == null) {
				// learn을 할지 말지 결정하기 위한 조건은 style에 따라 분류할 model이 준비되어 있느냐 없느냐이다.
				// 만약 없다면 learn해서 그 모델을 만들고, 있다면 그 model을 가지고 입력자료를 분류한다. (사실 이
				// 부분은 새로운 문제이다. 즉,
				// 입력이 있어서 결과로 새로운 모델을 만들어야 하는 문제인 것이다.
				// 따라서, 그 모델은 learn에서의 모델이다.
				// 여기서 분류나 learn은 실제로 문제를 푸는 것이고, 구조적인(틀에 해당하는) 부분은 또 따로 그래프를
				// 형성해야
				// 한다.
			Classifier.learn(trainData, InfoExtract_Style.classifier_by_style); // 지식베이스로부터
																				// 가져온다.
																				// svm_multiclass_learn
																				// -c
																				// 1.0
																				// example_file
		}	// model_file
			// trainData는 입력인자에 해당하고, modelName은 출력인자에 해당한다.
			// trainData를 어떤 식으로 정의할 것인가? 데이터 자체라면 trainData에 대한 클래스가 정의되어 있어야
			// 하겠고
			// 그렇지 않고 경로라면 trainData가 정의된 파일 URL일 것이다. 이것은 어떻게 알 수 있나?
			// modelName은 어떤 식으로 처리할 지 고민해야 한다. 즉, 없었다가 만들어지기 때문에 서버나 로컬에
			// 만든 후에는 1) 만든 곳의 URL이나 혹은 2)그 자체로 blob형태로 DB에 저장해야 한다.
		Classifier.classify(aRef.text, InfoExtract_Style.classifier_by_style, aRef.refValue);
		// 지식베이스로부터가져온다. classify한 다음에는 그 style이 결정된다.
		HashMap<String, CRFmodel> aCRFmodelMap = new HashMap<String, CRFmodel>();
		aCRFmodelMap.put("IEEE", new CRFmodel("IEEEmodel"));
		InfoExtract_Style aThisClass = new InfoExtract_Style();
		
		//TODO : RefSet_Style , Ref 어떤것을 인자로 받아야 하나?
		
//		RefSet_Style aRefSet_Style1 = new RefSet_Style();
//		aThisClass.InfoExtract_Style(aRefSet_Style1, aCRFmodelMap);
		InfoExtract_Style(new RefSet_Style(), aCRFmodelMap);
	}
	public static void main(String[] args) {
		InfoExtract_Style.InfoExtract_Style();
	}
}