import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle.InlineQueryResultArticleBuilder;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class LibraryBot extends TelegramLongPollingBot {
  private final List<Library> libraries;
  private final String botToken;

  public LibraryBot(List<Library> libraries, String botToken) {
    this.libraries = libraries;
    this.botToken = botToken;
  }

  @Override
  public String getBotUsername() {
    return "libraria_bot";
  }

  @Override
  public String getBotToken() {
    return botToken;
  }

  @Override
  public void onUpdateReceived(Update update) {
    // We check if the update has a message and the message has text
    if (update.hasMessage() && update.getMessage().hasText()) {
      SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
      message.setChatId(update.getMessage().getChatId().toString());
      message.setText(update.getMessage().getText());

      try {
        execute(message); // Call method to send the message
      } catch (TelegramApiException e) {
        e.printStackTrace();
      }
    }

    if (update.hasInlineQuery()) {
      AnswerInlineQuery res = new AnswerInlineQuery();
      res.setInlineQueryId(update.getInlineQuery().getId());

      Location location = update.getInlineQuery().getLocation();

      long init = System.currentTimeMillis();
      List<Library> nearLibrariesSorted = LibraryUtil.nearFrom(libraries, new BigDecimal(location.getLongitude()),
          new BigDecimal(location.getLatitude()),
          7);

      System.out.println(System.currentTimeMillis() - init);

      InlineQueryResultArticleBuilder builder = InlineQueryResultArticle.builder();

      List<InlineQueryResult> results = nearLibrariesSorted.stream().map(library -> builder.
          title(library.getName()).
          description(library.getCode()).
          id(library.getCode()).
          inputMessageContent(new InputTextMessageContent("hola")).build()).collect(Collectors.toList());

      res.setResults(results);

      try {
        execute(res); // Call method to send the message
      } catch (TelegramApiException e) {
        e.printStackTrace();
      }
    }
  }
}
