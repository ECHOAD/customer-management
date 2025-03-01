package mediator;


/**
 * This class is used to send commands and queries to the mediator
 */
public interface Mediator {
    <R> R send(Command<R> command);

    <R> R send(Query<R> query);
}
