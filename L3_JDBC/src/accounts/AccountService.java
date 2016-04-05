package accounts;

import dbService.DBService;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

    public class AccountService {
        DBService dbService = new DBService();
        private final Map<String, UserProfile> sessionIdToProfile = new HashMap<String, UserProfile>();

        public void addNewUser(UserProfile userProfile) {
            try {
                this.dbService.addUser(userProfile.getLogin(), userProfile.getPass());
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public UserProfile getUserByLogin(String login) {
            try {
                return this.dbService.getUser(login);
            }
            catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        public UserProfile getUserBySessionId(String sessionId) {
            return this.sessionIdToProfile.get(sessionId);
        }

        public void addSession(String sessionId, UserProfile userProfile) {
            this.sessionIdToProfile.put(sessionId, userProfile);
        }

        public void deleteSession(String sessionId) {
            this.sessionIdToProfile.remove(sessionId);
        }
    }
