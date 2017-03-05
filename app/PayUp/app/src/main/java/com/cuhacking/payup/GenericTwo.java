package com.cuhacking.payup;

public class GenericTwo {
	
	private String stringParameter;
	private float floatParameter;
	
	public GenericTwo(String name1, float money1){
		setName(name1);
		setMoney(money1);
	}

	public String getName() {
		return stringParameter;
	}

	public void setName(String name) {
		this.stringParameter = name;
	}

	public float getMoney() {
		return floatParameter;
	}

	public void setMoney(float money) {
		this.floatParameter = money;
	}

}
