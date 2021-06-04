package model;

import java.io.Serializable;

public abstract class Creature implements Serializable {
	private String id;
	private int hp;
	private int mp;
	private int attack;
	private int deffence;
	private String m_name;
	private int m_cost;
	private int m_atk;
	//getter/setter-------------------------------
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getHp() {
		return hp;
	}
	public int getHp(int num) {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getMp() {
		return mp;
	}
	public int getMp(int num) {
		return mp;
	}
	public void setMp(int mp) {
		this.mp = mp;
	}
	public int getAttack() {
		return attack;
	}
	public int getAttack(int num) {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	public int getDeffence() {
		return deffence;
	}
	public int getDeffence(int num) {
		return deffence;
	}
	public void setDeffence(int deffence) {
		this.deffence = deffence;
	}
	public String getM_name() {
		return m_name;
	}
	public void setM_name(String m_name) {
		this.m_name = m_name;
	}
	public int getM_cost() {
		return m_cost;
	}
	public void setM_cost(int m_cost) {
		this.m_cost = m_cost;
	}
	public int getM_atk() {
		return m_atk;
	}
	public void setM_atk(int m_atk) {
		this.m_atk = m_atk;
	}
}
