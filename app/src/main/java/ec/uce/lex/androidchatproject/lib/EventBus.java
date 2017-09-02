package ec.uce.lex.androidchatproject.lib;

/**
 * Es una interfaz que defina que nos interesa de eventBus
 * Created by Alexis on 01/09/2017.
 */

public interface EventBus {

    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);

}
