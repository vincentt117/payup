package src.main;

public class Person {
	
	private String name;
	private float moneyDue = 0;
	
	public Person(String i){
		name = i;
	}
	
	public String getString(){
		return name;
	}
	
	public void changeDue(float n){
		moneyDue = moneyDue + n;
	}
	
	public float getDue(){
		return moneyDue;
	}
	
}
