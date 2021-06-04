package model.monster;

import java.util.ArrayList;

import model.Creature;
import model.character.Character;

public class Onion extends Monster {
	//コンストラクタ ---------------------------------
	public Onion(char suffix) {
		setSuffix(suffix);
		setId(null);
		setName(null);
		setHp(0);
		setMp(0);
		setAttack(0);
		setDeffence(0);
	}
	//メソッド ----------------------------------------------
	public String magic(Monster m, ArrayList<Creature> characters ) {
		//メッセージ
		String msg = "";
		msg += "<p>" + m.getName() + "の攻撃！</p>";
		msg += "<p>" + m.getName() + "は"+ m.getM_name() +"を唱えた！</p>";
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
