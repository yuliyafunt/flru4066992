package flteam.flru4066992.core;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Bot extends TelegramLongPollingBot {


    public void onUpdateReceived(Update update) {

    }

    public String getBotUsername() {
        return "MyScoreParser";
    }

    public String getBotToken() {
        return "843183515:AAHjumPWFpcSQvbCDLjF-4HymxLDlG9xaIw";
    }
}
