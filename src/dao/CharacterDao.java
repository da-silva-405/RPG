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
import model.monster.Monster;
import model.monster.Sennin;

public class CharacterDao {
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
	//キャラクター配列
	public ArrayList<Creature> characters () {
		ArrayList<Creature> characters = new ArrayList<Creature>();
		Character info = null;
		try {
			this.connect();
			ps = db.prepareStatement("SELECT  id, job, name, hp, mp, attack, deffence, atk_corr, def_corr, \r\n"
					+ "(SELECT m.m_name FROM magic AS m WHERE c.id = m.m_id) AS m_name,\r\n"
					+ "(SELECT m.m_cost FROM magic AS m WHERE c.id = m.m_id) AS m_cost,\r\n"
					+ "(SELECT m.m_atk FROM magic AS m WHERE c.id = m.m_id) AS m_atk\r\n"
					+ "FROM characters AS c;");
			rs = ps.executeQuery();
			while(rs.next()) {
				if(rs.getString("id") .equals("C001")) {
					info = new model.character.Hero();
				}
				if(rs.getString("id") .equals("C002")) {
					info = new model.character.Wizard();
				}
				if(rs.getString("id") .equals("C003")) {
					info = new model.character.Priest();
				}
				info.setId(rs.getString("id"));
				info.setJob(rs.getString("job"));
				info.setName(rs.getString("name"));
				info.setHp(rs.getInt("hp"));
				info.setMp(rs.getInt("mp"));
				info.setAttack(rs.getInt("attack"));
				info.setDeffence(rs.getInt("deffence"));
				info.setAtk_corr(rs.getInt("atk_corr"));
				info.setDef_corr(rs.getInt("def_corr"));
				info.setM_name(rs.getString("m_name"));
				info.setM_cost(rs.getInt("m_cost"));
				info.setM_atk(rs.getInt("m_atk"));
				characters.add(info);
			}
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		} finally {
			this.discoonect();
		}
		return characters;
	}
	//コマンド
	public String cmdParty (ArrayList<Creature> characters, int idx) {
		String cmdParty = "";
		String strIdx = String.valueOf(idx);
		for (int i = 0; i < characters.size(); i++) {
			if (characters.get(idx) instanceof model.character.Hero) {
				model.character.Hero h = (model.character.Hero)characters.get(idx);
			} else if (characters.get(idx) instanceof model.character.Hero) {
				model.character.Wizard w = (model.character.Wizard)characters.get(idx);
			} else if (characters.get(idx) instanceof model.character.Hero) {
				model.character.Priest p = (model.character.Priest)characters.get(idx);
			}
		}
		cmdParty += "<div class=\"setName\">" + ((Character)characters.get(idx)).getName() + "</div>";
		cmdParty += "<ul>";
		cmdParty += "<li><a href=\"Command?cmd=1&idx=" + strIdx + "\">たたかう</a></li>";
		cmdParty += "<li><a href=\"Command?cmd=2&idx=" + strIdx + "\">じゅもん</a></li>";
		cmdParty += "<li><a href=\"Command?cmd=3&idx=" + strIdx + "\">ぼうぎょ</a></li>";
		cmdParty += "<li><a href=\"Command?cmd=4&idx=" + strIdx + "\">にげる</a></li>";
		cmdParty += "</ul>";
		return cmdParty;
	}
	//パーティーメンバーセレクト
	public String selectParty (ArrayList<Creature> characters) {
		String selectParty = "<div class=\"selectList\">";
		selectParty += "<p>	&nbsp;▼&nbsp;対象を選択</p>";
		selectParty += "<ul>";
		for (int i = 0; i < characters.size(); i++) {
			if( ((Character)characters.get(i)).getHp() > 0) {
				selectParty += "<li><a href=\"SelectTarget?targetIdx=" + String.valueOf(i) + "\">&nbsp;" + ((Character)characters.get(i)).getName() + "</a></li>";
			}
		}
		selectParty += "</ul>";
		selectParty += "</div>";
		return selectParty;
	}
	//モンスターセレクト
	public String selectMob (ArrayList<Creature> monsters) {
		String selectMob = "<div class=\"selectList\">";
		selectMob += "<p>	&nbsp;▼&nbsp;対象を選択</p>";
		selectMob += "<ul>";
		for (int i = 0; i < monsters.size(); i++) {
			if (monsters.get(i) instanceof model.monster.Sennin) {
				Sennin f = (model.monster.Sennin)monsters.get(i);
				if(f.getHp() > 0) {
					selectMob += "<li><a href=\"SelectTarget?targetIdx=" + String.valueOf(i) + "\">&nbsp;" + f.getName() +  "</a></li>";
				}
			} else  {
				if(((Monster)monsters.get(i)).getHp() > 0) {
					selectMob += "<li><a href=\"SelectTarget?targetIdx=" + String.valueOf(i) + "\">&nbsp;" + ((Monster)monsters.get(i)).getName() + ((Monster) monsters.get(i)).getSuffix() + "</a></li>";
				}
			}
		}
		selectMob += "</ul>";
		selectMob += "</div>";
		return selectMob;
	}
	//たたかう（1）
	public String attack(ArrayList<Creature> characters, ArrayList<Creature> monsters, String idx, String targetIdx) {
		int myIdx  = Integer.parseInt(idx);
		int tgtIdx  = Integer.parseInt(targetIdx);
		String msg = "<p>" + ((Character) characters.get(myIdx)).getName() + "の攻撃！</p>";
		int avoid = (int)(Math.random() * 10);	// 0=攻撃回避
		if(avoid == 0) {
			msg += "<p>" + ((Monster) monsters.get(tgtIdx)).getName() + ((Monster) monsters.get(tgtIdx)).getSuffix() + "は攻撃をかわした</p>";
		} else {
			for (int i = 0; i < characters.size(); i++) {
				if (characters.get(myIdx) instanceof model.character.Hero) {
					model.character.Hero h = (model.character.Hero)characters.get(myIdx);
					if (h.getHp() > 0) {
						msg += h.attack(monsters.get(tgtIdx));
					}
					break;
				} else if (characters.get(myIdx) instanceof model.character.Wizard) {
					model.character.Wizard w = (model.character.Wizard)characters.get(myIdx);
					if (w.getHp() > 0) {
						msg += w.attack(monsters.get(tgtIdx));
					}
					break;
				} else if (characters.get(myIdx) instanceof model.character.Priest) {
					model.character.Priest p = (model.character.Priest)characters.get(myIdx);
					if (p.getHp() > 0) {
						msg += p.attack(monsters.get(tgtIdx));
					}
					break;
				}
			}
		}
		return msg;
	}
	//まほう（2）単体魔法
	public String magic(ArrayList<Creature> characters, ArrayList<Creature> monsters, String idx, String targetIdx) {
		int myIdx  = Integer.parseInt(idx);
		int tgtIdx  = Integer.parseInt(targetIdx);
		String msg = "";
		for (int i = 0; i < characters.size(); i++) {
			if (characters.get(myIdx) instanceof model.character.Hero) {
				model.character.Hero h = (model.character.Hero)characters.get(myIdx);
				if (h.getHp() > 0) {
					msg = h.magic(characters.get(myIdx),characters, tgtIdx);
				}
				break;
			} else if (characters.get(myIdx) instanceof model.character.Wizard) {
				model.character.Wizard w = (model.character.Wizard)characters.get(myIdx);
				if (w.getHp() > 0) {
					msg = w.magic(characters.get(myIdx), monsters);
				}
				break;
			} else if (characters.get(myIdx) instanceof model.character.Priest) {
				model.character.Priest p = (model.character.Priest)characters.get(myIdx);
				if (p.getHp() > 0) {
					msg = p.magic(characters.get(myIdx), characters, tgtIdx);
				}
				break;
			}
		}
		return msg;
	}
	//まほう（2）全体魔法
	public String allTargetMagic(ArrayList<Creature> characters, ArrayList<Creature> monsters, String idx) {
		int myIdx  = Integer.parseInt(idx);
		String msg = "";
		for (int i = 0; i < characters.size(); i++) {
			if (characters.get(myIdx) instanceof model.character.Hero) {
				model.character.Hero h = (model.character.Hero)characters.get(myIdx);
				if (h.getHp() > 0) {
//					msg = h.magic(characters.get(myIdx),characters);
				}
				break;
			} else if (characters.get(myIdx) instanceof model.character.Wizard) {
				model.character.Wizard w = (model.character.Wizard)characters.get(myIdx);
				if (w.getHp() > 0) {
					msg = w.magic(characters.get(myIdx), monsters);
				}
				break;
			} else if (characters.get(myIdx) instanceof model.character.Priest) {
				model.character.Priest p = (model.character.Priest)characters.get(myIdx);
				if (p.getHp() > 0) {
//					msg = p.magic(characters.get(myIdx), characters);
				}
				break;
			}
		}
		return msg;
	}
	//ぼうぎょ（3）
	public String def(ArrayList<Creature> characters,  String idx) {
		int c_idx  = Integer.parseInt(idx);
		String msg = null;
		msg = "<p>" + ((Character)characters.get(c_idx)).getName() + "は、身を守った。</p>";
		return msg;
	}
	//逃げる（4）
	public String run(ArrayList<Creature> characters,  String idx) {
		int c_idx  = Integer.parseInt(idx);
		String msg = null;
		int run = (int)(Math.random() * 2);
		if(run == 1) {
			msg = "<p>" + ((Character)characters.get(c_idx)).getName() + "たちは、逃げ出した！<br />THE END</p>";
		} else {
			msg = "<p>" + ((Character)characters.get(c_idx)).getName() + "たちは、逃げ出した！<br />しかし、まわりこまれてしまった。</p>";
		}
		return msg;
	}
	//GAME CLEAR判定
	public boolean gameClear(ArrayList<Creature> monsters) {
		boolean clearFlg = false;
		int mobHpTotal = 0;
		for(int i = 0; i < monsters.size(); i++){
			mobHpTotal += monsters.get(i).getHp();
			if(mobHpTotal == 0){
				clearFlg = true;
			} else {
				clearFlg = false;
			}
		}
		return clearFlg;
	}
	//GAME OVER判定
	public boolean gameOver(ArrayList<Creature> characters) {
		boolean overFlg = false;
		int partyHpTotal = 0;
		for(int i = 0; i < characters.size(); i++){
			partyHpTotal += characters.get(i).getHp();
			if(partyHpTotal == 0){
				overFlg = true;
			} else {
				overFlg = false;
			}
		}
		System.out.println("Dao：overFlg=" + overFlg);
		return overFlg;
	}
}
