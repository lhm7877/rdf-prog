package ke;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.ClassAssigner;

public class ClassAssignerLoader {
	public static Instances ClassAssignerLoader(Instances data) {
		try {
			ClassAssigner classAssigner = new ClassAssigner();
			classAssigner.setInputFormat(data);
			classAssigner.setClassIndex("first");
			Instances classAssignerInst = Filter.useFilter(data, classAssigner);
			return classAssignerInst;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
