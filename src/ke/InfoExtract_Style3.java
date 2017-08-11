package ke;

public class InfoExtract_Style3 {
	
	// style 분류 모델 
	public static String classifier_by_style;

	
	// 규칙 : 함수 이름은 클래스 이름과 동일하게 짓는다.
	public static void InfoExtract_Style3(addition.Ref_Style ref_style) {
		if(ref_style.Style==null || ref_style.Style == ""){
			String crfModelPath = Graph2.makeProblemGraph(ref_style,"classifier",ref_style);
		
//		String InfoExtract_Style_Train_Data = Classifier.getTrainData("InfoExtract_Style_Train_Data");
//		String classifiedValue = Classifier.classifyWithWeka(InfoExtract_Style_Train_Data);
//		String crfModelPath = Classifier.getCRFModelPath(classifiedValue); // ref에 style 이름
		
		//여기서 infoExtract함수를 실행할 때 파라미터로 ref 개체가 들어오게  되어있는데, ref_style은 어떤식으로 넘길 것인가?
		//상속하고 text는 static으로 사용한다.
		//아니면 ref를 바꿔준다. 기존 ref에서 변수 style을 추가하고 새로 컴파일
		InfoExtract.InfoExtract((addition.Ref)ref_style,crfModelPath);
		}

	}
	
	//test용
	public static void main(String[] args) {
		InfoExtract_Style3.InfoExtract_Style3("박성희, (2016), \"KE\", 정보관리학회, 33, (3), pp. 22-40 ");
	}
}
