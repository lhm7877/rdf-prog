package ke;

public class InfoExtract_Style3 {
	
	// style 분류 모델 
	public static String classifier_by_style;

	
	// 규칙 : 함수 이름은 클래스 이름과 동일하게 짓는다.
	public static void InfoExtract_Style3(addition.Ref_Style ref_style) {
		if(ref_style.Style==null || ref_style.Style == ""){
			Graph2.makeProblemGraph();
		}
		String InfoExtract_Style_Train_Data = Classifier.getTrainData("InfoExtract_Style_Train_Data");
		String classifiedValue = Classifier.classifyWithWeka(InfoExtract_Style_Train_Data);
		String crfModelPath = Classifier.getCRFModelPath(classifiedValue); // ref에 style 이름
		
		InfoExtract.InfoExtract(refText,new CRFmodel());

	}
	
	//test용
	public static void main(String[] args) {
		InfoExtract_Style3.InfoExtract_Style3("박성희, (2016), \"KE\", 정보관리학회, 33, (3), pp. 22-40 ");
	}
}
