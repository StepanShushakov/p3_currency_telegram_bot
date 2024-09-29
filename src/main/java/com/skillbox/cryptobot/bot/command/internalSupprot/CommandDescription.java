package com.skillbox.cryptobot.bot.command.internalSupprot;


public class CommandDescription {

    public static String get() {
        return """
                Поддерживаемые команды:
                 /help - вывести сообщение со списком команд
                 /subscribe [число] - подписаться а стоимость биткоина в USD
                 /get_price - получить стоимость биткоина
                 /get_subscription - получить текущую подписку
                 /unsubscribe - отменить подписку на стоимость
                """;
    }
}
