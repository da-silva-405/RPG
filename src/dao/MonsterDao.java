package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.Creature;
import model.character.Character;
import model.monster.Baron;
import model.monster.Fairy;
import model.monster.Hakase;
import model.monster.Monster;
import model.monster.Onion;
import model.monster.Sennin;

public class MonsterDao {
	Connection db = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	//接続共通処理
	private void connect() throws NamingException, SQLException {
		Context context = new InitialContext();
		DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/Draque");
		this.db = ds.getConnection();
	}
	//切断共通処理
	private void discoonect() {
		try {
			if(rs != null)  {
				rs.close();
			}
			if(ps != null)  {
				ps.close();
			}
			if(db != null)  {
				db.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//モンスター配列
	public ArrayList<Creature> monsters() {
		ArrayList<Creature> monsters = new ArrayList<Creature>();
		Monster info = null;
		try {
			this.connect();
			int gridCnt = 0;
			int mob1Cnt = 0;
			int mob2Cnt = 0;
			int mob3Cnt = 0;
			int mob4Cnt = 0;
			int bossCnt = 0;
			while(gridCnt < 10) {
				int mon = (int)(Math.random() * 5 ) + 1;
				if(mon == 1) {
					ps = db.prepareStatement("SELECT id, name, hp, mp, attack, deffence,\r\n"
							+ "(SELECT m.m_name FROM magic AS m WHERE mob.id = m.m_id) AS m_name,\r\n"
							+ "(SELECT m.m_cost FROM magic AS m WHERE mob.id = m.m_id) AS m_cost,\r\n"
							+ "(SELECT m.m_atk FROM magic AS m WHERE mob.id = m.m_id) AS m_atk\r\n"
							+ "FROM monsters AS mob WHERE mob.id = 'M001';");
					rs = ps.executeQuery();
					while(rs.next()) {
						info = new Fairy((char)('Ａ' + mob1Cnt));
						info.setId(rs.getString("id"));
						info.setName(rs.getString("name"));
						info.setHp(rs.getInt("hp"));
						info.setMp(rs.getInt("mp"));
						info.setAttack(rs.getInt("attack"));
						info.setDeffence(rs.getInt("deffence"));
						info.setM_name(rs.getString("m_name"));
						info.setM_cost(rs.getInt("m_cost"));
						info.setM_atk(rs.getInt("m_atk"));
						monsters.add(info);
					}
					mob1Cnt++;
					gridCnt += 1;
				} else if (mon == 2) {
					ps = db.prepareStatement("SELECT id, name, hp, mp, attack, deffence,\r\n"
							+ "(SELECT m.m_name FROM magic AS m WHERE mob.id = m.m_id) AS m_name,\r\n"
							+ "(SELECT m.m_cost FROM magic AS m WHERE mob.id = m.m_id) AS m_cost,\r\n"
							+ "(SELECT m.m_atk FROM magic AS m WHERE mob.id = m.m_id) AS m_atk\r\n"
							+ "FROM monsters AS mob WHERE mob.id = 'M002';");
					rs = ps.executeQuery();
					while(rs.next()) {
						info = new Baron((char)('Ａ' + mob2Cnt));
						info.setId(rs.getString("id"));
						info.setName(rs.getString("name"));
						info.setHp(rs.getInt("hp"));
						info.setMp(rs.getInt("mp"));
						info.setAttack(rs.getInt("attack"));
						info.setDeffence(rs.getInt("deffence"));
						info.setM_name(rs.getString("m_name"));
						info.setM_cost(rs.getInt("m_cost"));
						info.setM_atk(rs.getInt("m_atk"));
						monsters.add(info);
					}
					mob2Cnt++;
					gridCnt += 1;
				} else if (mon == 3 && gridCnt <= 8) {
					ps = db.prepareStatement("SELECT id, name, hp, mp, attack, deffence,\r\n"
							+ "(SELECT m.m_name FROM magic AS m WHERE mob.id = m.m_id) AS m_name,\r\n"
							+ "(SELECT m.m_cost FROM magic AS m WHERE mob.id = m.m_id) AS m_cost,\r\n"
							+ "(SELECT m.m_atk FROM magic AS m WHERE mob.id = m.m_id) AS m_atk\r\n"
							+ "FROM monsters AS mob WHERE mob.id = 'M003';");
					rs = ps.executeQuery();
					while(rs.next()) {
						info = new Onion((char)('Ａ' + mob3Cnt));
						info.setId(rs.getString("id"));
						info.setName(rs.getString("name"));
						info.setHp(rs.getInt("hp"));
						info.setMp(rs.getInt("mp"));
						info.setAttack(rs.getInt("attack"));
						info.setDeffence(rs.getInt("deffence"));
						info.setM_name(rs.getString("m_name"));
						info.setM_cost(rs.getInt("m_cost"));
						info.setM_atk(rs.getInt("m_atk"));
						monsters.add(info);
					}
					mob3Cnt++;
					gridCnt += 2;
				} else if (mon == 4 && gridCnt <= 8) {
					ps = db.prepareStatement("SELECT id, name, hp, mp, attack, deffence,\r\n"
							+ "(SELECT m.m_name FROM magic AS m WHERE mob.id = m.m_id) AS m_name,\r\n"
							+ "(SELECT m.m_cost FROM magic AS m WHERE mob.id = m.m_id) AS m_cost,\r\n"
							+ "(SELECT m.m_atk FROM magic AS m WHERE mob.id = m.m_id) AS m_atk\r\n"
							+ "FROM monsters AS mob WHERE mob.id = 'M004';");
					rs = ps.executeQuery();
					while(rs.next()) {
						info = new Hakase((char)('Ａ' + mob4Cnt));
						info.setId(rs.getString("id"));
						info.setName(rs.getString("name"));
						info.setHp(rs.getInt("hp"));
						info.setMp(rs.getInt("mp"));
						info.setAttack(rs.getInt("attack"));
						info.setDeffence(rs.getInt("deffence"));
						info.setM_name(rs.getString("m_name"));
						info.setM_cost(rs.getInt("m_cost"));
						info.setM_atk(rs.getInt("m_atk"));
						monsters.add(info);
					}
					mob4Cnt++;
					gridCnt += 2;
					if (gridCnt > 10) {
						mob4Cnt--;
						gridCnt -= 2;
						continue;
					}
				} else if (mon == 5 && gridCnt <= 6) {
					if (bossCnt == 0) {
						ps = db.prepareStatement("SELECT id, name, hp, mp, attack, deffence,\r\n"
								+ "(SELECT m.m_name FROM magic AS m WHERE mob.id = m.m_id) AS m_name,\r\n"
								+ "(SELECT m.m_cost FROM magic AS m WHERE mob.id = m.m_id) AS m_cost,\r\n"
								+ "(SELECT m.m_atk FROM magic AS m WHERE mob.id = m.m_id) AS m_atk\r\n"
								+ "FROM monsters AS mob WHERE mob.id = 'M005';");
						rs = ps.executeQuery();
						while(rs.next()) {
							info = new Sennin();
							info.setId(rs.getString("id"));
							info.setName(rs.getString("name"));
							info.setHp(rs.getInt("hp"));
							info.setMp(rs.getInt("mp"));
							info.setAttack(rs.getInt("attack"));
							info.setDeffence(rs.getInt("deffence"));
							info.setM_name(rs.getString("m_name"));
							info.setM_cost(rs.getInt("m_cost"));
							info.setM_atk(rs.getInt("m_atk"));
							monsters.add(info);
						}
						bossCnt ++;
						gridCnt += 4;
					} else {
						continue;
					}
				}
			}
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		} finally {
			this.discoonect();
		}
		return monsters;
	}
	//初回モンスター出現表示
	public String firstMsg (ArrayList<Creature> monsters) {
		String msg = "<div id=\"firstMsg\">";
		msg += "<p>";
		for (int i = 0; i < monsters.size(); i++) {
			if (monsters.get(i) instanceof model.monster.Sennin) {
				Sennin s = (model.monster.Sennin)monsters.get(i);
				msg += s.getName() + "があらわれた！</br />";
			} else {
				msg += ((Monster) monsters.get(i)).getName() + ((Monster) monsters.get(i)).getSuffix() + "があらわれた！</br />";
			}
		}
		msg += "</p>";
		msg += "</div>";
		return msg;
	}
	//攻撃対象の選択
	public static  int targetCharacter(ArrayList<Creature> characters) {
		while(true) {
			int index = (int)(Math.random() * characters.size());
			if(characters.get(index).getHp() <= 0) {
				continue;
			} else {
				return index;
			}
		}
	}
	//通常攻撃
	public String attack(Monster m, Character c) {
		String msg = "";
		int damege = m.getAttack() - c.getDeffence();
		if (damege < 0) {
			damege = 0;
		}
		msg += "<p>" + c.getName() + "に" + damege + "ポイントのダメージ</p>";
		c.setHp(c.getHp() - damege);
		if(c.getHp() <=  0) {
			msg += "<p>" +c.getName() + "は死んでしまった</p>";
			c.setHp(0);
		}
		return msg;
	}
	//モンスターの攻撃
	public String monsutarAttack(ArrayList<Creature> characters, ArrayList<Creature> monsters) {
		String msg = "";
		for (int i = 0; i < monsters.size(); i++) {
			if(monsters.get(i).getHp() > 0) {
				msg += "<p>" + ((Monster) monsters.get(i)).getName() + ((Monster) monsters.get(i)).getSuffix() + "の攻撃</p>";
				if (monsters.get(i) instanceof model.monster.Sennin) {
					Sennin s = (Sennin)monsters.get(i);
					int skill = (int)(Math.random() * 10); // 0=特技
					int tgtIdx = targetCharacter(characters); //ターゲット選択
					int avoid = (int)(Math.random() * 8); // 0=攻撃回避
					if (skill == 0) {
						s.magic((Monster) monsters.get(i), characters);
					} else {
						if(avoid != 0) {
							msg += attack((Monster) monsters.get(i), (Character) characters.get(tgtIdx));
						} else {
							msg += "<p>" + ((Character) characters.get(tgtIdx)).getName() + "は攻撃をかわした</p>";
						}
					}
				} else if (monsters.get(i) instanceof model.monster.Hakase) {
					Hakase h = (Hakase)monsters.get(i);
					int skill = (int)(Math.random() * 10); // 0=特技
					int tgtIdx = targetCharacter(characters); //ターゲット選択
					int avoid = (int)(Math.random() * 8); // 0=攻撃回避
					if (skill == 0) {
						h.magic((Monster) monsters.get(i), characters);
					} else {
						if(avoid != 0) {
							msg += attack((Monster) monsters.get(i), (Character) characters.get(tgtIdx));
						} else {
							 msg += "<p>" + ((Character) characters.get(tgtIdx)).getName() + "は攻撃をかわした</p>";
						}
					}
				} else if (monsters.get(i) instanceof model.monster.Onion) {
					Onion o = (Onion)monsters.get(i);
					int skill = (int)(Math.random() * 10); // 0=特技
					int tgtIdx = targetCharacter(characters); //ターゲット選択
					int avoid = (int)(Math.random() * 8); // 0=攻撃回避
					if (skill == 0) {
						o.magic((Monster) monsters.get(i), characters);
					} else {
						if(avoid != 0) {
							msg += attack((Monster) monsters.get(i), (Character) characters.get(tgtIdx));
						} else {
							msg += "<p>" + ((Character) characters.get(tgtIdx)).getName() + "は攻撃をかわした</p>";
						}
					}
				} else if (monsters.get(i) instanceof model.monster.Baron) {
					Baron b = (Baron)monsters.get(i);
					int tgtIdx = targetCharacter(characters); //ターゲット選択
					int avoid = (int)(Math.random() * 8); // 0=攻撃回避
					if(avoid != 0) {
						msg += attack((Monster) monsters.get(i), (Character) characters.get(tgtIdx));
					} else {
						msg += "<p>" + ((Character) characters.get(tgtIdx)).getName() + "は攻撃をかわした</p>";
					}
				} else if (monsters.get(i) instanceof model.monster.Fairy) {
					Fairy f = (Fairy)monsters.get(i);
					int tgtIdx = targetCharacter(characters); //ターゲット選択
					int avoid = (int)(Math.random() * 8); // 0=攻撃回避
					if(avoid != 0) {
						msg += attack((Monster) monsters.get(i), (Character) characters.get(tgtIdx));
					} else {
						msg += "<p>" + ((Character) characters.get(tgtIdx)).getName() + "は攻撃をかわした</p>";
					}
				}
			}
		}
		return msg;
	}
	//モンスターのターン
	public String mobTurn() {
		String mobTurn =  "<p class=\"nextTurn\"><a href=\"Turn?mobTurnFlg=1\">次へ&nbsp;▼&emsp;</a></p>";
		return mobTurn;

	}
}
