package flteam.flru4066992.core.bot;

import flteam.flru4066992.core.conditions.Expression;
import flteam.flru4066992.entity.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Set;
import java.util.StringJoiner;

public class Notifier {

    private final Bot bot;

    @Inject
    public Notifier(Bot bot) {
        this.bot = bot;
    }

    public void notify(Collection<User> users, Set<Expression> expression, String comment) {
        try {
            StringJoiner message = new StringJoiner("\n")
                    .add("Подошло условие:");
            expression.forEach(e -> message.add(e.toString()));
            message.add("Комментарий: " + comment);

            for (User user : users) {
                SendMessage m = new SendMessage(user.getChatId(), message.toString());
                bot.execute(m);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}