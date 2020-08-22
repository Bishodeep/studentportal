package Helper;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Filter extends HttpServlet {

    public static RequestDispatcher getPage(HttpServletRequest request, HttpServletResponse response, String PageName)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        String username = (String) session.getAttribute("username");
        if (username != null) {
            int userRole = (int) session.getAttribute("roleId");
            if (userRole == 1) {
                RequestDispatcher resDispatcher = request.getRequestDispatcher(PageName + ".html");
                return resDispatcher;
            } else if (userRole == 2) {
                RequestDispatcher resDispatcher = request.getRequestDispatcher(PageName + ".html");
                return resDispatcher;
            } else {
            }
            RequestDispatcher resDispatcher = request.getRequestDispatcher("unauthorizedpage.html");
            return resDispatcher;
        } else {
            RequestDispatcher resDispatcher = request.getRequestDispatcher("errorpage.html");
            return resDispatcher;
        }

    }
}
