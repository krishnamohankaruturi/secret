package edu.ku.cete.util.json;

public abstract class JavaScriptEngineAnonymousFunctionHelper {
	private String parameter;
	
	public abstract Object dummyMethod(Object function);
	
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
}
