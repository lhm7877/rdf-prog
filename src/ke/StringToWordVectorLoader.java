package ke;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class StringToWordVectorLoader {
	public static Instances StringToWordVectorLoader(Instances data){
		StringToWordVector filter = new StringToWordVector();
		try {
			filter.setInputFormat(data);
			Instances stringToWordVectorInst = Filter.useFilter(data, filter);
			return stringToWordVectorInst;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

}
