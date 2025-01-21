package ru.zoommax.windows;

import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import ru.zoommax.BotApp;
import ru.zoommax.utils.ViewMessageImpl;
import ru.zoommax.utils.ViewMessageListener;
import ru.zoommax.utils.keyboard.Keyboard;
import ru.zoommax.utils.lang.UserLanguage;
import ru.zoommax.view.TextMessage;
import ru.zoommax.view.ViewMessage;

@ViewMessageListener
public class Start implements ViewMessageImpl {
    @Override
    public ViewMessage onMessage(String message, int messageId, long chatId, String onMessageFlag, Update update) {
        return null;
    }

    @Override
    public ViewMessage onCommand(String command, int messageId, long chatId, Update update) {
        if (command.equals("/start")) {
            return getMessage(chatId);
        }
        return null;
    }

    @Override
    public ViewMessage onPicture(PhotoSize[] photoSize, String caption, int messageId, long chatId, Update update) {
        return null;
    }

    @Override
    public ViewMessage onCallbackQuery(String data, int messageId, long chatId, Update update) {
        return switch (data) {
            case "langEN" -> {
                UserLanguage.setUserLanguage(chatId, "en");
                yield getMessage(chatId);
            }
            case "langRU" -> {
                UserLanguage.setUserLanguage(chatId, "ru");
                yield getMessage(chatId);
            }
            case "langFR" -> {
                UserLanguage.setUserLanguage(chatId, "fr");
                yield getMessage(chatId);
            }
            case "langCN" -> {
                UserLanguage.setUserLanguage(chatId, "cn");
                yield getMessage(chatId);
            }
            case "start" -> getMessage(chatId);
            default -> null;
        };
    }

    private ViewMessage getMessage(long chatId) {
        String userLanguage = UserLanguage.getUserLanguage(chatId);
        String startMessage = BotApp.localizationManager.getTranslationForLanguage(userLanguage, "bot.start.start_message");
        String continueButton = BotApp.localizationManager.getTranslationForLanguage(userLanguage, "bot.start.continue");

        return TextMessage.builder()
                .text(startMessage)
                .chatId(chatId)
                .keyboard(Keyboard.builder()
                        .chatId(chatId)
                        .code("{EN;langEN}{RU;langRU}{FR;langFR}{CN;langCN}\n" +
                                "{"+continueButton+";mainWindow}")
                        .build())
                .build();
    }

    @Override
    public ViewMessage onInlineQuery(String query, String queryId, long chatId, Update update) {
        return null;
    }

    @Override
    public ViewMessage onChosenInlineResult(String resultId, long queryId, String chatId, Update update) {
        return null;
    }
}
