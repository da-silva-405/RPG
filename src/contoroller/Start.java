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

@WebServlet("/Start")
public class Start extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//sessionスコープ準備
		HttpSession session = request.getSession();
		//初回呼び出し時にキャラクターとモンスターのJavaBeans生成
		CharacterDao dao = new CharacterDao();
		ArrayList<Creature> partyList = dao.characters();
		session.setAttribute("partyList", partyList);
		MonsterDao mobDao = new MonsterDao();
		ArrayList<Creature> mobList = mobDao.monsters();
		session.setAttribute("mobList", mobList);
		//初回モンスター出現表示
		String firstMsg = mobDao.firstMsg(mobList);
		session.setAttribute("firstMsg", firstMsg);
		//コマンドセット
		String cmdParty1 = dao.cmdParty(partyList, 0);
		String cmdParty2 = dao.cmdParty(partyList, 1);
		String cmdParty3 = dao.cmdParty(partyList, 2);
		session.setAttribute("cmdParty1", cmdParty1);
		session.setAttribute("cmdParty2", cmdParty2);
		session.setAttribute("cmdParty3", cmdParty3);
		//各フラグ設定
		session.setAttribute("firstFlg", true);//初回のみモンスター出現表示
		session.setAttribute("selectPartyFlg", false);
		session.setAttribute("selectMobFlg", false);
		session.setAttribute("msgFlg", false);
		session.setAttribute("party1Flg", true);
		session.setAttribute("party2Flg", false);
		session.setAttribute("party3Flg", false);
		session.setAttribute("party1DieFlg", false);
		session.setAttribute("party2DieFlg", false);
		session.setAttribute("party3DieFlg", false);
		session.setAttribute("mobTurnFlg", "0");
		session.setAttribute("clearFlg", false);//ゲームクリア判定
		session.setAttribute("overFlg", false);//ゲームオーバー判定
		//geme画面に遷移
		getServletContext().getRequestDispatcher("/view/game.jsp").forward(request, response);
	}
}
