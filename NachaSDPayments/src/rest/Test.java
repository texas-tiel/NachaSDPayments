package rest;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Test implements Serializable{

	private static final long serialVersionUID = 1L;
	private String textA, textB;

	public Test(){}
   
	public Test(String tA, String tB){
       textA = tA;
       textB = tB;
    }

    public String getTextA() { return textA; }
    public void setTextA(String t) { textA = t; }
    public String getTextB() { return textB; }
    public void setTextB(String t) { textB = t; }
}
