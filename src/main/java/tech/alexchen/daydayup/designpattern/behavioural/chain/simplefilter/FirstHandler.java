package tech.alexchen.daydayup.designpattern.behavioural.chain.simplefilter;

/**
 * @author alexchen
 * @date 2023/3/5
 */
public class FirstHandler extends Handler {

    public FirstHandler(Handler successor) {
        super(successor);
    }

    @Override
    protected void handleRequest(Request request) {
        System.out.println("FirstHandler");
        successorHandleRequest(request);
    }
}
