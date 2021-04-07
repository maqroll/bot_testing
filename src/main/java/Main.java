import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.util.List;

public class Main {
  public static void main(String[] args) throws TelegramApiException, IOException {
    List<Library> libraries = LoadLibrary.get();
    String bot_token = System.getenv().get("BOT_TOKEN");

    TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
    botsApi.registerBot(new LibraryBot(libraries,bot_token));
  }
}
