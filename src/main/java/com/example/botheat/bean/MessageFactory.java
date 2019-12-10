package com.example.botheat.bean;

/**
 * @author haya
 */
public class MessageFactory {
    public static <T> Message instance(T body) {
        return new Message() {{
            if (body != null) {
                setState( true );
            } else {
                setState( false );
            }
            setBody( body );
        }};
    }
    public static <T> Message instance(boolean state) {
        return new Message() {{
            setState( state );
            setBody( null );
        }};
    }
}
