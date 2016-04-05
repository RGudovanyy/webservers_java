package servlets;


import accounts.AccountService;
import accounts.UserProfile;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;






public class SignUpServlet
        extends HttpServlet
{
    private final AccountService accountService;

    public SignUpServlet(AccountService accountService) { this.accountService = accountService; }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String pass = request.getParameter("password");

        accountService.addNewUser(new UserProfile(login, pass, null));

        UserProfile profile = accountService.getUserByLogin(login);

        accountService.addSession(request.getSession().getId(), profile);
        Gson gson = new Gson();
        String json = gson.toJson(profile);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(json);
        response.setStatus(200);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        UserProfile profile = accountService.getUserBySessionId(request.getSession().getId());

        Gson gson = new Gson();
        String json = gson.toJson(profile);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(json);
        response.setStatus(200);
    }
}