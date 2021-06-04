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
 * Servlet implementation class SelectMob
 */
@WebServlet("/SelectTarget")
public class SelectTarget extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//sessionスコープ準備
		HttpSession session = request.getSession();
		ArrayList<Creature> partyList = (ArrayList<Creature>) session.getAttribute("partyList");
		ArrayList<Creature> mobList = (ArrayList<Creature>) session.getAttribute("mobList");
		CharacterDao dao = new CharacterDao();
		String cmd = (String) session.getAttribute("cmd");
		String idx = (String) session.getAttribute("idx");
		String targetIdx = request.getParameter("targetIdx");
		String msg = "";
		System.out.println("cmd= " + cmd);
		System.out.println("idx= " + idx);
		System.out.println("targetIdx= " + targetIdx);

		session.setAttribute("selectPartyFlg", false);
		session.setAttribute("selectMobFlg", false);
		session.setAttribute("msgFlg", true);
		if( cmd.equals("1")) {
			msg = dao.attack(partyList, mobList, idx, targetIdx);
			session.setAttribute("msg", msg);
			System.out.println(msg);
		}
		if(cmd.equals("2")) {
			msg = dao.magic(partyList, mobList, idx, targetIdx);
			session.setAttribute("msg", msg);
			System.out.println(msg);
		}

		getServletContext().getRequestDispatcher("/Game").forward(request, response);
	}
}
