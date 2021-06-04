package contoroller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CharacterDao;
import dao.MonsterDao;
import model.Creature;

@WebServlet("/Game")
public class Game extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//sessionスコープ準備
		HttpSession session = request.getSession();
		//partyListとmobListをsessionスコープから取得
		ArrayList<Creature> partyList = (ArrayList<Creature>) session.getAttribute("partyList");
		ArrayList<Creature> mobList = (ArrayList<Creature>) session.getAttribute("mobList");
		CharacterDao dao = new CharacterDao();
		MonsterDao mobDao = new MonsterDao();
		String cmd = (String) session.getAttribute("cmd");
		String idx = (String) session.getAttribute("idx");
		String targetIdx = request.getParameter("targetIdx");
		String msg = "";
		boolean party1Flg = (boolean) session.getAttribute("party1Flg");
		boolean party2Flg = (boolean) session.getAttribute("party2Flg");
		boolean party3Flg = (boolean) session.getAttribute("party3Flg");
		boolean party1DieFlg = (boolean) session.getAttribute("party1DieFlg");
		boolean party2DieFlg = (boolean) session.getAttribute("party2DieFlg");
		boolean party3DieFlg = (boolean) session.getAttribute("party3DieFlg");
		boolean clearFlg = (boolean) session.getAttribute("clearFlg");
		boolean overFlg = (boolean) session.getAttribute("overFlg");
		String mobTurnFlg = (String) session.getAttribute("mobTurnFlg");

		//コマンド入れ替え
		if(cmd.equals("1") && targetIdx != null ||
				cmd.equals("2")  && idx.equals("1") && targetIdx == null ||
				cmd.equals("2")  && idx.equals("0") && targetIdx != null ||
				cmd.equals("2")  && idx.equals("2") && targetIdx != null ||
				cmd.equals("3") || cmd.equals("4")) {
			if(party1Flg) {
				if(partyList.get(0).getHp() == 0) {
					session.setAttribute("party1DieFlg", true);
					party2Flg = true;
				}
				session.setAttribute("party1Flg", false);
				session.setAttribute("party2Flg", true);
			}
			if(party2Flg) {
				if(partyList.get(1).getHp() == 0) {
					session.setAttribute("party2DieFlg", true);
					party3Flg = true;
				}
				session.setAttribute("party2Flg", false);
				session.setAttribute("party3Flg", true);
			}
			if(party3Flg) {
				if(partyList.get(2).getHp() == 0) {
					session.setAttribute("party3DieFlg", true);
					party3Flg = false;
				}
				session.setAttribute("party3Flg", false);
				party3Flg = false;
			}
		}
		//確認用
		System.out.println("////////////////////////////////////////////////////////////////////");
		System.out.println("party1Flg : " + session.getAttribute("party1Flg"));
		System.out.println("party2Flg : " + session.getAttribute("party2Flg"));
		System.out.println("party3Flg : " + session.getAttribute("party3Flg"));
		System.out.println("////////////////////////////////////////////////////////////////////");
		//クリア判定
		clearFlg = dao.gameClear(mobList);
		session.setAttribute("clearFlg", clearFlg);
		//次へ表示
		if(!party1Flg && !party2Flg && !party3Flg ) {
			String mobTurn = mobDao.mobTurn();
			session.setAttribute("mobTurn", mobTurn);
		} else {
			String mobTurn = "";
			session.setAttribute("mobTurn", mobTurn);
		}
		//モンスター攻撃へ
		if(mobTurnFlg.equals("1")) {
			msg = mobDao.monsutarAttack(partyList, mobList);
			overFlg = dao.gameOver(partyList);
			session.setAttribute("overFlg", overFlg);
			session.setAttribute("msg", msg);
			session.setAttribute("msgFlg", true);
			//始めるキャラクターの生死判定
			if(partyList.get(0).getHp() > 0) {
				session.setAttribute("party1Flg", true);
			} else if (partyList.get(1).getHp() > 0) {
				session.setAttribute("party2Flg", true);
			} else if (partyList.get(2).getHp() > 0) {
				session.setAttribute("party3Flg", true);
			}
			session.setAttribute("mobTurnFlg", "0");
			System.out.println(msg);
		}

		//生死判定
		party1DieFlg = (boolean)session.getAttribute("party1DieFlg");
		party2DieFlg = (boolean)session.getAttribute("party2DieFlg");
		party3DieFlg = (boolean)session.getAttribute("party3DieFlg");
		if(party1DieFlg) {
			session.setAttribute("party1Flg", false);
		}
		if(party2DieFlg) {
			session.setAttribute("party2Flg", false);
		}
		if(party3DieFlg) {
			session.setAttribute("party3Flg", false);
		}
		//ゲームオーバーの判定
		clearFlg = dao.gameClear(mobList);
		session.setAttribute("mobList", mobList);

		getServletContext().getRequestDispatcher("/view/game.jsp").forward(request, response);

	}
}
