package ec.uce.lex.androidchatproject.lib;

/**
 * Created by Alexis on 01/09/2017.
 */

public class GreenRobotEventBus implements EventBus {
    org.greenrobot.eventbus.EventBus eventBus;

    private static class SingletonHolder{
        private  static  final GreenRobotEventBus INSTANCE= new GreenRobotEventBus();
    }


    public static  GreenRobotEventBus getInstance (){
        return SingletonHolder.INSTANCE;
    }

    public GreenRobotEventBus() {
        this.eventBus = org.greenrobot.eventbus.EventBus.getDefault();
    }

    @Override
    public void register(Object subscriber) {
        eventBus.register(subscriber);
    }

    @Override
    public void unregister(Object subscriber) {
        eventBus.unregister(subscriber);
    }

    @Override
    public void post(Object event) {
        eventBus.post(event);
    }
}
