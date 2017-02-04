package edu.hannam.cismlab;

public class Statement {

	Property m_Property;
	Resource m_Subject, m_Object;
	
	public Statement() {
		// TODO Auto-generated constructor stub
	}

	public void addProperty(Property aProperty)
	{
		m_Property = aProperty;
	}
	
	public void addSubject(Resource aResource)
	{
		m_Subject = aResource;
	}
	public void addObject(Resource aResource)
	{
		m_Object = aResource;		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
