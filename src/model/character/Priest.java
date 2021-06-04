package model.character;

import java.util.ArrayList;

import model.Creature;
import model.monster.Monster;

public class Priest extends Character  {
	//コンストラクタ ---------------------------------------
	public Priest() {
		setId(null);
		setJob(null);
		setName(null);
		setHp(0);
		setMp(0);
		setAttack(0);
		setDeffence(0);
		setAtk_corr(getAtk_corr());
		setDef_corr(getDef_corr());
	}
	//メソッド ----------------------------------------------
	//たたかう
	public String attack(Creature c) {
		//ダメージの計算（攻撃力＋攻撃補正値ーモンスター防御力）
		int damege = this.getAttack() + this.getAtk_corr() - c.getDeffence();
		if (damege < 0) {
			damege = 0;
		}
		//メッセージ
		String msg = "";
		msg += "<p>" + ((Monster) c).getName() + ((Monster) c).getSuffix()  + "に" + damege + "ポイントのダメージ</p>";
		//HPの計算
		c.setHp(c.getHp() - damege);
		if(c.getHp() <=  0) {
			msg +=  "<p>" + ((Monster) c).getName() + ((Monster) c).getSuffix()  + "を倒した</p>";
			c.setHp(0);
		}
		return msg;
	}
	//まほう
	public String magic(Creature c, ArrayList<Creature> characters,int tgtIdx) {
		//メッセージ
		String msg = "<p>" + this.getName() + "は" + c.getM_name()  + "を唱えた</p>";
		msg += "<p>" + ((Character)characters.get(tgtIdx)).getName() + "のHPが" + c.getM_atk() + "ポイント回復した！</p>";
		//対象のHPの計算
		((Character)characters.get(tgtIdx)).setHp(((Character)characters.get(tgtIdx)).getHp() + c.getM_atk());
		//自分のMPの計算
		c.setMp(c.getMp() - c.getM_cost());
		return msg;
	}
}