package mediator;

import java.util.function.Function;


public interface CommandHandler<C extends Command<R>, R> extends Function<C, R> {
    Class<C> getType();
}