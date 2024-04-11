package session;

public class SessionManager {
    private static Session session;

    public static void setSession(Session newSession){
        session = newSession;
    }

    public static Session getSession(){
        if (session == null) {
            session = new Session();
            return session;
        }
        return session;
    }
}
