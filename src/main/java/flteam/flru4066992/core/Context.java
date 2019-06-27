package flteam.flru4066992.core;

import com.j256.ormlite.dao.Dao;
import flteam.flru4066992.core.conditions.Expression;
import flteam.flru4066992.core.conditions.sportspecific.Conditions;
import flteam.flru4066992.db.DatabaseManager;
import flteam.flru4066992.entity.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class Context {
    private static final Logger logger = LoggerFactory.getLogger(Context.class);

    private final Map<BetType, Filter> filterExpressions = new HashMap<>();
    private final Map<Integer, User> users = new HashMap<>();

    private final Dao<User, Integer> usersDAO;

    @Inject
    public Context(DatabaseManager databaseManager) throws SQLException {
        this.usersDAO = databaseManager.getUsersDAO();
        this.usersDAO.queryForAll().forEach(u -> users.put(u.getId(), u));
        logger.info("Load {} users from database", users.size());
    }

    public void addNewUser(org.telegram.telegrambots.meta.api.objects.User telegramUser, Long chatId) {
        if (users.containsKey(telegramUser.getId())) {
            // user already saved
            return;
        }
        User dbUser = convertToDBUser(telegramUser, chatId);
        try {
            usersDAO.createIfNotExists(dbUser);
            users.put(dbUser.getId(), dbUser);
        } catch (SQLException e) {
            // TODO: send message about error, but where?
            logger.error("Error while insert user into DB", e);
        }
    }

    public void addFilter(BetType type, String tabId, Expression expression) {
        filterExpressions.computeIfAbsent(type, k -> new Filter())
                .addExpression(tabId, expression);
    }

    public void setFilterComment(BetType type, String comment) {
        filterExpressions.computeIfAbsent(type, k -> new Filter())
                .setComment(comment);
    }

    public void removeFilter(BetType type, String tabId, Conditions condition) {
        if (condition != null) {
            filterExpressions.get(type).removeExpressions(tabId, condition);
        }
    }

    @Nullable
    public Filter getFilters(BetType type) {
        return filterExpressions.get(type);
    }

    public Collection<User> getUsers() {
        return users.values();
    }

    @Nullable
    public User getUser(Integer id) {
        return users.get(id);
    }

    @NotNull
    private User convertToDBUser(org.telegram.telegrambots.meta.api.objects.User telegramUser, Long chatId) {
        User dbUser = new User();
        dbUser.setChatId(chatId.toString());
        dbUser.setFirstName(telegramUser.getFirstName());
        dbUser.setLastName(telegramUser.getFirstName());
        dbUser.setUsername(telegramUser.getUserName());
        dbUser.setId(telegramUser.getId());
        dbUser.setNotify(true);
        return dbUser;
    }
}
