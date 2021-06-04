package model.monster;

import java.util.ArrayList;

import model.Creature;
import model.character.Character;

public class Sennin extends Monster {
	//コンストラクタ ---------------------------------
	public Sennin() {
		setId(null);
		setName(null);
		setHp(0);
		setMp(0);
		setAttack(0);
		setDeffence(0);
	}
	//メソッド ----------------------------------------------
	//通常攻撃
	public String attack(Character c) {
		int damege = this.getAttack() - c.getDeffence();
		if (damege < 0) {
			damege = 0;
		}

		String msg = "";
		msg += "<p>" + c.getName() + "に" + damege + "ポイントのダメージ</p>";
		c.setHp(c.getHp() - damege);
		if(c.getHp() <=  0) {
			msg += "<p>" + c.getName() + "は死んでしまった</p>";
			c.setHp(0);
		}
		return msg;
	}
	public String magic(Monster m, ArrayList<Creature> characters ) {
		//メッセージ
		String msg = "";
		msg += "<p>" + m.getName() + "の攻撃！</p>";
		msg += "<p>" + m.getName() + "は"+ m.getM_name() +"をはいた！</p>";
		for(int i = 0; i < characters.size(); i++) {
			if(characters.get(i).getHp() > 0) {
				int avoid = (int)(Math.random() * 8); // 0=攻撃回避
				if(avoid != 0) {
					msg += "<p>" + ((Character)characters.get(i)).getName() + "に" + m.getM_atk() + "ポイントのダメージ</p>";
					characters.get(i).setHp(characters.get(i).getHp() - m.getM_atk());
					if(characters.get(i).getHp() <=  0) {
						msg += "<p>" + ((Character) characters.get(i)).getName() + "は死んでしまった</p>";
						characters.get(i).setHp(0);
					}
				}
			}
		}
		msg += "</p>";
		return msg;
	}
}
