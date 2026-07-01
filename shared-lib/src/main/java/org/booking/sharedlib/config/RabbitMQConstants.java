package org.booking.sharedlib.config;

public final class RabbitMQConstants {
    public static final String BOOKING_EXCHANGE = "booking.exchange";
    public static final String DEAD_LETTER_EXCHANGE = "booking.dlx";

    public static final String PAYMENT_COMMAND_QUEUE = "payment.command.queue";
    public static final String FLIGHT_COMMAND_QUEUE = "flight.command.queue";
    public static final String HOTEL_COMMAND_QUEUE = "hotel.command.queue";
    public static final String USER_COMMAND_QUEUE = "user.command.queue";

    public static final String PAYMENT_REPLY_QUEUE = "payment.reply.queue";
    public static final String FLIGHT_REPLY_QUEUE = "flight.reply.queue";
    public static final String HOTEL_REPLY_QUEUE = "hotel.reply.queue";
    public static final String USER_REPLY_QUEUE = "user.reply.queue";

    public static final String FLIGHT_CANCEL_QUEUE = "flight.cancel.queue";
    public static final String HOTEL_CANCEL_QUEUE = "hotel.cancel.queue";
    public static final String PAYMENT_CANCEL_QUEUE = "payment.cancel.queue";
    public static final String USER_CANCEL_QUEUE = "user.cancel.queue";

    public static final String DEAD_LETTER_QUEUE = "booking.dead.queue";

    public static final String HOTEL_CANCEL_KEY = "command.hotel.cancel";
    public static final String FLIGHT_CANCEL_KEY = "command.flight.cancel";
    public static final String PAYMENT_CANCEL_KEY = "command.payment.cancel";
    public static final String USER_CANCEL_KEY = "command.user.cancel";

    public static final String USER_COMMAND_KEY = "command.user";
    public static final String PAYMENT_COMMAND_KEY = "command.payment";
    public static final String FLIGHT_COMMAND_KEY = "command.flight";
    public static final String HOTEL_COMMAND_KEY = "command.hotel";

    public static final String PAYMENT_REPLY_KEY = "reply.payment";
    public static final String FLIGHT_REPLY_KEY = "reply.flight";
    public static final String HOTEL_REPLY_KEY = "reply.hotel";
    public static final String USER_REPLY_KEY = "reply.user";

    public static final String DEAD_LETTER_KEY = "booking.dead";

}
