package model.monster;

public class Baron extends Monster {
	//コンストラクタ ---------------------------------
	public Baron(char suffix) {
		setSuffix(suffix);
		setId(null);
		setName(null);
		setHp(0);
		setMp(0);
		setAttack(0);
		setDeffence(0);
	}
}
