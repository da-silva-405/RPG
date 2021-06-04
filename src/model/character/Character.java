package model.character;

import java.io.Serializable;

import model.Creature;

public abstract class Character extends Creature implements  Serializable {
	//フィールド--------------------------------------
	private String job;
	private String name;
	private int atk_corr = (int)(Math.random() * 10) + 1;
	private int def_corr = (int)(Math.random() * 10) + 1;
	//getter/setter-----------------------------------
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDef_corr() {
		return def_corr;
	}
	public void setDef_corr(int def_corr) {
		this.def_corr = def_corr + (int)(Math.random() * 5) + 1;
	}
	public int getAtk_corr() {
		return atk_corr;
	}
	public void setAtk_corr(int atk_corr) {
		this.atk_corr = atk_corr + (int)(Math.random() * 5) + 1;
	}
}
