package flteam.flru4066992.core.bot;

import flteam.flru4066992.core.conditions.Expression;
import flteam.flru4066992.entity.User;
import flteam.flru4066992.model.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.*;

@Singleton
public class Notifier {
    private static final Logger logger = LoggerFactory.getLogger(Notifier.class);

    private final Bot bot;

    private final Map<LocalDateTime, Match> submittedMatches = new HashMap<>(1000);

    @Inject
    public Notifier(Bot bot) {
        this.bot = bot;
    }

    public void notify(Collection<User> users, Match match, Collection<Expression> expressions, String comment) {
        try {
            if (submittedMatches.containsValue(match)) {
                logger.info("Match already notified");
                return;
            }

            for (User user : users) {
                SendMessage m = new SendMessage(user.getChatId(), buildMessage(expressions, match, comment));
                bot.execute(m);
                submittedMatches.put(LocalDateTime.now(), match);
            }
        } catch (TelegramApiException e) {
            logger.error("Error while sending notify message", e);
        }
    }

    private static String buildMessage(Collection<Expression> expressions, Match match, String comment) {
        StringJoiner message = new StringJoiner("\n")
                .add("Для матча: ")
                .add("  " + match.getLeague())
                .add(String.format("    %s (кф %s) - %s (кф %s)",
                        match.getHomeTeam().getName(),
                        match.getHomeTeam().getCoefficient(),
                        match.getAwayTeam().getName(),
                        match.getAwayTeam().getCoefficient()))
                .add("Подошло условие:");
        expressions.forEach(e -> message.add("  " + e.toString()));

        if (comment != null && !comment.isEmpty()) {
            message.add("Комментарий: " + comment);
        }
        return message.toString();
    }
}
