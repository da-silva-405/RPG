package model.monster;

import java.io.Serializable;

import model.Creature;

public class Monster extends Creature implements  Serializable {
	//フィールド--------------------------------------
	private String name;
	private char suffix;
	//getter/setter-----------------------------------
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public char getSuffix() {
		return suffix;
	}
	public void setSuffix(char suffix) {
		this.suffix = suffix;
	}

}
