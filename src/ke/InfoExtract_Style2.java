package ke;

import addition.Ref_Style;

public class InfoExtract_Style2 {
	
	// style 분류 모델 
	public static String classifier_by_style;

	
	// 규칙 : 함수 이름은 클래스 이름과 동일하게 짓는다.
	public static void InfoExtract_Style2(String refText) { //refText를 인자로 받는다.
		//1. 스타일 분류할 model이 없다면 만들기
		//2. 스타일 분류할 model로 style 분류하기
		//3. 분류된 해당 style로 Ref_labeled_Styled를 만들기
		
		//1. 스타일 분류할 model이 없다면 만들기
		//TODO: model 파일의 유무로만 train을 할 지 정한다면 새로운 데이터로 model을 업그레이드 시킬 때 언제 model을 업그레이드 할 것인가?
		// -> 해결: model을 사용할 때마다 업그레이드 후 사용한다.
		
		// DB에 접속해서 클래스명과 동일한 train 데이터를 가져와 로컬에 저장 후 저장 위치를 가져온다.
		// 명명 규칙 : 어떤데이터인지_Train_Datas , 이름에 해당하는 정보를 DB에서 가져온다.
		String InfoExtract_Style_Train_Data = Classifier.getTrainData("InfoExtract_Style_Train_Data");
		
		// trainDatas위치에 데이터로 train 후 trainDatas + "model"이라는 이름으로 model을 만든다.
		// 현재는 로컬 위치에서 데이터를 가져온 후 로컬 위치에 저장하지만, 파일을 DB에서 가져온 후 model 파일 저장 역시 DB에 저장을 지향한다. 
		String resultModelPath = Classifier.learn(InfoExtract_Style_Train_Data); //결과로 model이 만들어진다.
		System.out.println(resultModelPath);
		//model을 가지고 Ref가 어떤 style인지 분류하는 작업이다.
		//modelName과
		
		//classify(trainData, modelPath, classify 결과물)
		//trainData : ?
		//modelPath : classify에 사용될 model 파일의 위치이다.
		//classify 결과물 : classify 후 분류한 파일의 이름이다.
		// 분류한 결과를 다시 train.dat에 추가시켜 train 데이터의 양을 늘리는것을 지향.
		String classifiedPath = Classifier.classify(refText, resultModelPath, "model_result");
		String crfModelPath = Classifier.getCRFModelPath(classifiedPath); // ref에 style 이름
		
		
		// 질문 :Ref_Style은 style을 가진 Ref 객체이다. 
		// 그렇다면 Classifier.classify()의 결과를 Ref_Style의 String style에 넣어야 할 것이다.
		// RefSet이 그 역할을 수행하고 있는 것인가?
		// DB에 있는  Ref_Style에 setText로 text에 ref_Style.text를 집어넣었었다. 그 RefStyle에 ref_style.style을 지정하고
		// InfoExtract.Infoextract()를 할 때에는 ref_Style을 넘겨주면 ref_Style.text와 ref_Style.style을 이용해 style에 맞게 InfoExtract가 가능할 것이다.
		// Ref_Style을 실행하는 부분은 아직 없다. pathFindingAStar과정에서 새로 생긴 InfoExtract_Style로 넘어간 뒤 InfoExtract에서 DFS 후 노드들을 stack에 쌓은 후 실행하여야 한다.
		// 이 때 Ref_Style의 setText를 실행(applyNode)할 때 (Ref, Ref_Style은 연산자인가?) ref text를 넣어주고, InfoExtract_Style 즉 이 클래스를 실행할 때 Ref_Style.style에 style을 세팅해준다.
		
		Ref_Style ref_Style = new Ref_Style();
		ref_Style.setText(refText);
		ref_Style.Style = crfModelPath;

//		TODO:여기서 ref_Style을 넘겨준다.
		
		//TODO : Ref를 넘겨줘야한다.
		InfoExtract.InfoExtract(ref_Style.getText(), ref_Style.Style);

	}
	
	//test용
	public static void main(String[] args) {
		InfoExtract_Style2.InfoExtract_Style2("박성희, (2016), \"KE\", 정보관리학회, 33, (3), pp. 22-40 ");
	}
}
