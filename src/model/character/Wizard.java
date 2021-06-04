package model.character;

import java.util.ArrayList;

import model.Creature;
import model.monster.Monster;

public class Wizard extends Character  {
	//コンストラクタ ---------------------------------------
	public Wizard() {
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
			msg += "<p>" + ((Monster) c).getName() + ((Monster) c).getSuffix()  + "を倒した</p>";
			c.setHp(0);
		}
		return msg;
	}
	//まほう
	public String magic(Creature c, ArrayList<Creature> monsters) {
		//メッセージ
		String msg = "<p>" + this.getName() + "は" + c.getM_name()  + "を唱えた</p>";
		if(c.getMp() < c.getM_cost()) {
			msg += "<p>MPが足りない</p>";
		} else {
			for(int i = 0; i < monsters.size(); i++) {
				if(monsters.get(i).getHp() > 0) {
					int avoid = (int)(Math.random() * 10); // 0=攻撃回避
					if(avoid == 0) {
						msg += "<p>" + ((Monster) monsters.get(i)).getName() + ((Monster) monsters.get(i)).getSuffix()  + "は攻撃をかわした</p>";
					} else {
						int magic = (int)(Math.random() * 10) + 15; 	// ランダムダメージ
						int damege = magic - monsters.get(i).getDeffence();
						if (damege < 0) {
							damege = 0;
						}
						msg += "<p>" + ((Monster) monsters.get(i)).getName() + ((Monster) monsters.get(i)).getSuffix() +  "に" + damege + "ポイントのダメージ</p>";
						monsters.get(i).setHp(monsters.get(i).getHp() - damege);
						if(monsters.get(i).getHp() <=  0) {
							msg += "<p>" + ((Monster) monsters.get(i)).getName() + ((Monster) monsters.get(i)).getSuffix() + "を倒した</p>";
							monsters.get(i).setHp(0);
						}
					}
				}
			}
			//自分のMPの計算
			c.setMp(c.getMp() - c.getM_cost());
		}
		return msg;

	}
}
