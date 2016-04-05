package servlets;


import accounts.AccountService;
import accounts.UserProfile;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;




public class SessionsServlet
        extends HttpServlet
{
    private final AccountService accountService;

    public SessionsServlet(AccountService accountService)
    {
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String sessionId = request.getSession().getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);
        if (profile == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(401);
        } else {
            Gson gson = new Gson();
            String json = gson.toJson(profile);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(json);
            response.setStatus(200);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String login = request.getParameter("login");
        String pass = request.getParameter("password");

        if ((login == null) || (pass == null)) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(400);
            return;
        }

        UserProfile profile = accountService.getUserByLogin(login);
        if ((profile == null) || (!profile.getPass().equals(pass))) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(401);
            return;
        }

        accountService.addSession(request.getSession().getId(), profile);
        Gson gson = new Gson();
        String json = gson.toJson(profile);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(json);
        response.setStatus(200);
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String sessionId = request.getSession().getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);
        if (profile == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(401);
        } else {
            accountService.deleteSession(sessionId);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("Goodbye!");
            response.setStatus(200);
        }
    }
}