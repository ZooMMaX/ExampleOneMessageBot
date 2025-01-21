package ru.zoommax.windows;

import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import ru.zoommax.BotApp;
import ru.zoommax.utils.CreateNotification;
import ru.zoommax.utils.ViewMessageImpl;
import ru.zoommax.utils.ViewMessageListener;
import ru.zoommax.utils.db.NotificationType;
import ru.zoommax.utils.keyboard.Keyboard;
import ru.zoommax.utils.lang.UserLanguage;
import ru.zoommax.view.PhotoMessage;
import ru.zoommax.view.TextMessage;
import ru.zoommax.view.ViewMessage;

@ViewMessageListener
public class MainWindow implements ViewMessageImpl {

    private Keyboard getKeyboard(long chatId) {
        String userLanguage = UserLanguage.getUserLanguage(chatId);
        String photo = BotApp.localizationManager.getTranslationForLanguage(userLanguage, "bot.main.keyboard.photo");
        String text = BotApp.localizationManager.getTranslationForLanguage(userLanguage, "bot.main.keyboard.text");
        String notification = BotApp.localizationManager.getTranslationForLanguage(userLanguage, "bot.main.keyboard.notification");
        String docs = BotApp.localizationManager.getTranslationForLanguage(userLanguage, "bot.main.keyboard.docs");

        return Keyboard.builder()
                .chatId(chatId)
                .code(".....{"+photo+";photo}{"+text+";text}\n" + //Any characters outside of {} except \n are ignored
                        "...{"+notification+";notification}\n" +
                        "{"+docs+";https://zoommax.github.io/OneMessageBot/}\n" +
                        "{\uD83C\uDDEC\uD83C\uDDE7\uD83C\uDDF7\uD83C\uDDFA\uD83C\uDDEB\uD83C\uDDF7\uD83C\uDDE8\uD83C\uDDF3;start}")
                .build();
    }

    @Override
    public ViewMessage onMessage(String message, int messageId, long chatId, String onMessageFlag, Update update) {
        if (onMessageFlag.equals("mainWindow")) {
            String userLanguage = UserLanguage.getUserLanguage(chatId);
            String windowText = BotApp.localizationManager.getTranslationForLanguage(userLanguage, "bot.main.echo");

            return TextMessage.builder()
                    .text(windowText + message)
                    .chatId(chatId)
                    .onMessageFlag("mainWindow")
                    .keyboard(getKeyboard(chatId))
                    .build();
        }
        return null;
    }

    @Override
    public ViewMessage onCommand(String command, int messageId, long chatId, Update update) {
        return null;
    }

    @Override
    public ViewMessage onPicture(PhotoSize[] photoSize, String caption, int messageId, long chatId, Update update) {
        return null;
    }

    @Override
    public ViewMessage onCallbackQuery(String data, int messageId, long chatId, Update update) {
        switch (data) {
            case "photo":
                return PhotoMessage.builder()
                        .chatId(chatId)
                        .keyboard(getKeyboard(chatId))
                        .caption(BotApp.localizationManager.getTranslationForLanguage(UserLanguage.getUserLanguage(chatId), "bot.main.photo"))
                        .photoAsUrl("https://media.istockphoto.com/id/1443562748/ru/%D1%84%D0%BE%D1%82%D0%BE/%D0%BC%D0%B8%D0%BB%D0%B0%D1%8F-%D1%80%D1%8B%D0%B6%D0%B0%D1%8F-%D0%BA%D0%BE%D1%88%D0%BA%D0%B0.jpg?s=612x612&w=0&k=20&c=k8RwP4usK_LCpQ1bPn3fNDLk3vtfptH7CEcEMZw_K1A=")
                        .onMessageFlag("mainWindow")
                        .build();
            case "text":
                return TextMessage.builder()
                        .text(BotApp.localizationManager.getTranslationForLanguage(UserLanguage.getUserLanguage(chatId), "bot.main.text"))
                        .chatId(chatId)
                        .onMessageFlag("mainWindow")
                        .keyboard(getKeyboard(chatId))
                        .build();
            case "mainWindow":
                return TextMessage.builder()
                        .text(BotApp.localizationManager.getTranslationForLanguage(UserLanguage.getUserLanguage(chatId), "bot.main.main"))
                        .chatId(chatId)
                        .onMessageFlag("mainWindow")
                        .keyboard(getKeyboard(chatId))
                        .build();
            case "notification":
                String userLanguage = UserLanguage.getUserLanguage(chatId);
                String notificationTextAlert = BotApp.localizationManager.getTranslationForLanguage(userLanguage, "bot.main.notify.alert");
                String notificationTextFull = BotApp.localizationManager.getTranslationForLanguage(userLanguage, "bot.main.notify.full");
                CreateNotification createNotificationFull = new CreateNotification(notificationTextFull, chatId+"", null, NotificationType.FULL, null);
                CreateNotification createNotificationAlert = new CreateNotification(notificationTextAlert, chatId+"", null, NotificationType.ALERT, null);
                createNotificationFull.run();
                createNotificationAlert.run();
                return TextMessage.builder()
                        .text(BotApp.localizationManager.getTranslationForLanguage(userLanguage, "bot.main.notification"))
                        .chatId(chatId)
                        .onMessageFlag("mainWindow")
                        .keyboard(getKeyboard(chatId))
                        .build();
        }
        return null;
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
