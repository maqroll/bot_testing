import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultLocation;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LibraryBot extends TelegramLongPollingBot {
  private static Logger LOGGER = LoggerFactory.getLogger(LibraryBot.class);
  private final List<Library> libraries;
  private final String botToken;

  public LibraryBot(List<Library> libraries, String botToken) {
    this.libraries = libraries;
    this.botToken = botToken;
  }

  @Override
  public void onRegister() {
    // dummy (not implemented)
    SetMyCommands.SetMyCommandsBuilder builder = SetMyCommands.builder();
    SetMyCommands build = builder.command(BotCommand.builder().command("nearest").description("Get nearest library").build()).build();
    try {
      execute(build);
    } catch (TelegramApiException e) {
      LOGGER.error("Failed to init bot",e);
    }
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
    if (update.hasInlineQuery()) {
      InlineQuery inlineQuery = update.getInlineQuery();
      AnswerInlineQuery res = new AnswerInlineQuery();
      res.setInlineQueryId(inlineQuery.getId());
      res.setResults(Collections.emptyList());
      res.setIsPersonal(true); /* no cache */

      if (inlineQuery.getQuery() != null && !inlineQuery.getQuery().isEmpty()) {
        Location location = inlineQuery.getLocation();

        if (location != null) {
          LOGGER.debug("Request from {}", location);

          List<Library> nearLibrariesSorted = LibraryUtil.nearFrom(libraries, new BigDecimal(location.getLongitude()),
              new BigDecimal(location.getLatitude()),
              7);

          InlineQueryResultLocation.InlineQueryResultLocationBuilder builder = InlineQueryResultLocation.builder();

          List<InlineQueryResult> results = nearLibrariesSorted.stream().map(library -> builder.
              id(library.getName()).
              title(library.getName()).
              latitude(library.getLat().floatValue())
              .longitude(library.getLon().floatValue())
              .livePeriod(3600) /* 1 hour */
              .build()).collect(Collectors.toList());
          res.setResults(results);
        }
      }

      try {
        execute(res); // Call method to send the message
      } catch (TelegramApiException e) {
        LOGGER.error("Failed to answer inline query", e);
      }
    }
  }
}
