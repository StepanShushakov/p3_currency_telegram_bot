package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.service.CryptoCurrencyService;
import com.skillbox.cryptobot.service.SubscribersService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * Обработка команды подписки на курс валюты
 */
@Service
@Slf4j
@AllArgsConstructor
public class SubscribeCommand implements IBotCommand {

    private final SubscribersService subscribersService;
    private final CryptoCurrencyService service;

    @Override
    public String getCommandIdentifier() {
        return "subscribe";
    }

    @Override
    public String getDescription() {
        return "Подписывает пользователя на стоимость биткоина";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        Long chatId = message.getChatId();
        SendMessage answer = new SendMessage();
        answer.setChatId(chatId);
        answer.setParseMode(ParseMode.HTML);
        boolean formatIsCorrect = false;
        if (arguments.length == 0) {
            answer.setText("Не передана стоимость, на которую нужно установить подписку");
        } else {
            String input = arguments[0].replace(',', '.');
            String doubleRegex = "^-?\\d+(\\.\\d+)?$";
            formatIsCorrect = input.matches(doubleRegex);
            answer.setText((formatIsCorrect
                    ? subscribersService.getStringResultOfSubscribeInstallation(chatId, Double.parseDouble(input))
                    : "<b>Неправильно передана стоимость, для установки подписки!</b>\n" +
                    "<u>ожидается число</u>, а передано: <i>" + input + "</i>"));
        }
        try {
            if (formatIsCorrect) {
                absSender.execute((SendMessage.builder()
                        .chatId(chatId)
                        .text(service.getBitcoinPriceDescription())
                        .build()));
            }
            absSender.execute(answer);
        } catch (Exception e) {
            log.error("Error occurred in /subscribe command", e);
        }
    }
}