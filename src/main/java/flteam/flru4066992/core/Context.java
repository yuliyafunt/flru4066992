package flteam.flru4066992.core;

import flteam.flru4066992.core.conditions.Expression;
import flteam.flru4066992.entity.User;
import org.jetbrains.annotations.Nullable;

import javax.inject.Singleton;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class Context {

    private final Map<BetType, Filter> filterExpressions = new HashMap<>();
    private final Map<Long, User> users = new HashMap<>();


    public void addNewUser(org.telegram.telegrambots.meta.api.objects.User telegramUser, Long chatId) {
        if (users.containsKey(telegramUser.getId())) {
            // user already saved
            return;
        }

        User dbUser = new User();
        dbUser.setChatId(chatId.toString());
        dbUser.setFirstName(telegramUser.getFirstName());
        dbUser.setLastName(telegramUser.getFirstName());
        dbUser.setUsername(telegramUser.getUserName());
        dbUser.setId(telegramUser.getId());
        dbUser.setNotify(true);

        users.put(dbUser.getId(), dbUser);
    }

    public void addFilter(BetType type, Expression expression) {
        filterExpressions.computeIfAbsent(type, k -> new Filter())
                .addExpression(expression);
    }

    public void setFilterComment(BetType type, String comment) {
        filterExpressions.computeIfAbsent(type, k -> new Filter())
                .setComment(comment);
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
}
