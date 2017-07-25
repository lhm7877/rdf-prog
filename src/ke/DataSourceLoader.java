package ke;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class DataSourceLoader {
	public static Instances DataSourceLoader(String path){
		System.out.println("Weka DataSourceLoader");
		try {
			DataSource source = new DataSource(path);
			Instances data = source.getDataSet();
			if(data.classIndex() == -1){
				data.setClassIndex(data.numAttributes()-1);
			}
			return data;
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}
		
	}
}
