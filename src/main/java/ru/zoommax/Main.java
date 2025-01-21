package ru.zoommax;

import ru.zoommax.utils.db.DbType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {

        String botToken = Files.readString(Path.of("token.txt")).trim();

        BotSettings botSettings = BotSettings.builder()
                .dbType(DbType.MONGODB)
                .dbName("ExampleOneMessageBot")
                .botToken(botToken)
                .languageDirPath("translations")
                .defaultLanguage("en")
                .buttonsRows(2)
                .build();

        new Thread(new BotApp(botSettings)).start();
    }
}