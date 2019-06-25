package flteam.flru4066992.core.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.inject.Inject;

public class Bot extends TelegramLongPollingBot {

    private final UpdateHandler updateHandler;

    @Inject
    public Bot(UpdateHandler updateHandler) {
        this.updateHandler = updateHandler;
    }

    public void onUpdateReceived(Update update) {
        SendMessage result = updateHandler.handle(update);
        if (result != null) {
            try {
                execute(result);
            } catch (TelegramApiException e) {
                //TODO correct handle here, mb queue
                e.printStackTrace();
            }
        }
    }

    public String getBotUsername() {
        return "MyScoreParser";
    }

    public String getBotToken() {
        return "843183515:AAHjumPWFpcSQvbCDLjF-4HymxLDlG9xaIw";
    }
}
