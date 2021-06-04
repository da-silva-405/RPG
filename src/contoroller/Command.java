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
import model.Creature;

/**
 * Servlet implementation class Command
 */
@WebServlet("/Command")
public class Command extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//sessionスコープ準備
		HttpSession session = request.getSession();
		//partyListとmobListをsessionスコープから取得
		ArrayList<Creature> partyList = (ArrayList<Creature>) session.getAttribute("partyList");
		ArrayList<Creature> mobList = (ArrayList<Creature>) session.getAttribute("mobList");
		CharacterDao dao = new CharacterDao();

		String cmd = request.getParameter("cmd");
		String idx = request.getParameter("idx");
		session.setAttribute("cmd", cmd);
		session.setAttribute("idx", idx);
		String selectParty = "";
		String selectMob = "";
		String msg = "";
		System.out.println("cmd=" + cmd);
		System.out.println("idx=" + idx);

		if(cmd.equals("1")) {
			selectMob = dao.selectMob(mobList);
			session.setAttribute("selectMob", selectMob);
			session.setAttribute("firstFlg", false);
			session.setAttribute("selectPartyFlg", false);
			session.setAttribute("selectMobFlg", true);
			session.setAttribute("msgFlg", false);
		}
		if(cmd.equals("2")) {
			if(idx.equals("0") || idx.equals("2")) {
				selectParty = dao.selectParty(partyList);
				session.setAttribute("selectParty", selectParty);
				session.setAttribute("selectPartyFlg", true);
				session.setAttribute("msgFlg", false);
			}
			if(idx.equals("1")) {
				msg = dao.allTargetMagic(partyList, mobList, idx);
				session.setAttribute("msg", msg);
				session.setAttribute("msgFlg", true);
				System.out.println(msg);
			}
			session.setAttribute("selectMobFlg", false);
		}
		if( cmd.equals("3")) {
			msg = dao.def(partyList, idx);
			session.setAttribute("msg", msg);
			session.setAttribute("selectPartyFlg", false);
			session.setAttribute("selectMobFlg", false);
			session.setAttribute("msgFlg", true);
			System.out.println(msg);
		}
		if( cmd.equals("4")) {
			msg = dao.run(partyList, idx);
			session.setAttribute("msg", msg);
			session.setAttribute("selectPartyFlg", false);
			session.setAttribute("selectMobFlg", false);
			session.setAttribute("msgFlg", true);
			System.out.println(msg);
		}
		getServletContext().getRequestDispatcher("/Game").forward(request, response);
	}
}
