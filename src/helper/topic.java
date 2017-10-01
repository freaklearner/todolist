package helper;

import java.util.*;

public class topic{
	int id;
	ArrayList<topic> ar;
	String data;
	
	public topic(){
		ar = new ArrayList<topic>();
	}
	public int getId(){
		return id;
	}
	public void setId(int i){
		id = i;
	}
	public String getData(){
		return data;
	}
	public void setData(String str){
		data = str;
	}
	public ArrayList getSub(){
		return ar;
	}
	public void addTopic(topic t){
		ar.add(t);
	}
	public void setSub(ArrayList<topic> t){
		ar = t;
	}
	public void setSub(topic t){
		ar.add(t);
	}
}