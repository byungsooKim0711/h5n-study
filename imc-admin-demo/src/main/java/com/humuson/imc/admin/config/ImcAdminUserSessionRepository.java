package com.humuson.imc.admin.config;

import org.springframework.session.MapSession;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;

import java.time.Duration;
import java.util.Map;

public class ImcAdminUserSessionRepository implements SessionRepository<Session> {

    private Integer defaultMaxInactiveInterval;

    public static final String FIND_BY_UNAME_ATTR = "FIND_BY_UNAME_ATTR";

    private final Map<String, Session> sessions;

    public Session findByUsername(String username) {
        for(Session session : sessions.values()) {
            String uname = session.getAttribute(FIND_BY_UNAME_ATTR);
            if(username.equals(uname)) {
                return session;
            }
        }
        return null;
    }

    public ImcAdminUserSessionRepository(Map<String, Session> sessions) {
        if (sessions == null) {
            throw new IllegalArgumentException("sessions cannot be null");
        }
        this.sessions = sessions;
    }

    public void setDefaultMaxInactiveInterval(int defaultMaxInactiveInterval) {
        this.defaultMaxInactiveInterval = defaultMaxInactiveInterval;
    }

    public void save(MapSession session) {
        if (!session.getId().equals(session.getOriginalId())) {
            this.sessions.remove(session.getOriginalId());
        }
        this.sessions.put(session.getId(), new MapSession(session));
    }

    @Override
    public void save(Session session) {
        this.save(new MapSession(session));
    }

    @Override
    public MapSession findById(String id) {
        Session saved = this.sessions.get(id);
        if (saved == null) {
            return null;
        }
        if (saved.isExpired()) {
            deleteById(saved.getId());
            return null;
        }
        return new MapSession(saved);
    }

    @Override
    public void deleteById(String id) {
        this.sessions.remove(id);
    }

    @Override
    public MapSession createSession() {
        MapSession result = new MapSession();
        if (this.defaultMaxInactiveInterval != null) {
            result.setMaxInactiveInterval(Duration.ofSeconds(this.defaultMaxInactiveInterval));
        }
        return result;
    }
}
