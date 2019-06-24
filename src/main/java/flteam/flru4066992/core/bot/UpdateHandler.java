package flteam.flru4066992.core.bot;

import flteam.flru4066992.core.Context;
import org.jetbrains.annotations.Nullable;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.inject.Inject;

public class UpdateHandler {

    private final Context context;

    @Inject
    public UpdateHandler(Context context) {
        this.context = context;
    }

    @Nullable
    SendMessage handle(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.getText().equals("/start")) {
                return handleStart(update);
            }
        }
        return null;
    }

    private SendMessage handleStart(Update update) {
        Long chatId = update.getMessage().getChatId();
        context.addNewUser(update.getMessage().getFrom(), chatId);
        SendMessage m = new SendMessage(chatId, "Вы зарегистрированы");
        return m;
    }
}
