package contoroller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Turn
 */
@WebServlet("/Turn")
public class Turn extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//sessionスコープ準備
		HttpSession session = request.getSession();

		String mobTurnFlg = request.getParameter("mobTurnFlg");
		session.setAttribute("mobTurnFlg", mobTurnFlg);
		System.out.println("mobTurnFlg＝" + mobTurnFlg);

		getServletContext().getRequestDispatcher("/Game").forward(request, response);
	}
}
