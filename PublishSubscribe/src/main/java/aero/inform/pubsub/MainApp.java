package aero.inform.pubsub;

import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.apache.camel.component.netty4.ChannelHandlerFactories;
import org.apache.camel.main.Main;

/**
 * A Camel Application
 */
public class MainApp {

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {
        Main main = new Main();
        
        main.bind("length-encoder", new LengthFieldPrepender(4));
        main.bind("length-decoder", ChannelHandlerFactories.newLengthFieldBasedFrameDecoder(2505529, 0, 4, 0, 4));
        main.bind("string-encoder", new StringEncoder());
        main.bind("string-decoder", new StringDecoder());
        
        String netty4Target = "netty4:tcp://localhost:30600?clientMode=true&decoders=#length-decoder,#string-decoder&encoders=#length-encoder,#string-encoder";
        
        main.addRouteBuilder(new RegistrationRoute(netty4Target));
        main.addRouteBuilder(new ListenerRoute(netty4Target));
        main.run(args);
    }

}

