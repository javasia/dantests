package core;

import logic.Ent;
import logic.Persistence;
import model.dto.Group;
import model.dto.User;
import java.util.HashMap;

public class WholeProcess {
    private final HashMap<Integer, Process> storage = new HashMap<>();
    private final Persistence persistence;
    private final Authenticator authenticator;

    public WholeProcess(Persistence persistence) {
        this.persistence = persistence;
        this.authenticator = new Authenticator(persistence);
    }

    public Process getByStudent(int id) {
        if (!storage.containsKey(id)) {
            storage.put(id, new ProcessPersonalized(persistence, id));
        }
        return storage.get(id);
    }

    public void userLogout(int id) {
        storage.remove(id);
    }

    public User user(int id) {
        return (User) persistence.get(Ent.User).dao().get(id);
    }

    public Group group(int id) {
        return (Group) persistence.get(Ent.Group).dao().get(id);
    }

    @Override
    public String toString() {
        return String.format("Users in memory:%s", storage.keySet());
    }

    public Authenticator.Result auth(String login, String passwd) {
        return this.authenticator.auth(login, passwd);
    }
}
