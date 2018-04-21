package com.wrox.site;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import javax.websocket.ClientEndpoint;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.socket.server.standard.SpringConfigurator;

@ServerEndpoint(
        value = "/services/test",
        encoders = { TestEndpoint.Codec.class },
        decoders = { TestEndpoint.Codec.class },
        configurator = SpringConfigurator.class
)
@ClientEndpoint(
        encoders = { TestEndpoint.Codec.class },
        decoders = { TestEndpoint.Codec.class }
)
public class TestEndpoint {

    public static class Codec implements Encoder.BinaryStream<ClusterEvent>,
    Decoder.BinaryStream<ClusterEvent>
{
    private static final Logger log = LogManager.getLogger();
    
    @OnOpen
    public void onOpen(){
    	log.warn("*****onOpen in testEndpoint called");
    }
    	
	@Override
	public ClusterEvent decode(InputStream stream)
	        throws DecodeException, IOException
	{
	    try(ObjectInputStream input = new ObjectInputStream(stream))
	    {
	        return (ClusterEvent)input.readObject();
	    }
	    catch (ClassNotFoundException e)
	    {
	        throw new DecodeException((String)null, "Failed to decode.", e);
	    }
	}
	
	@Override
	public void encode(ClusterEvent event, OutputStream stream)
	        throws IOException
	{
	    try(ObjectOutputStream output = new ObjectOutputStream(stream))
	    {
	        output.writeObject(event);
	    }
	}
	
	@Override
	public void init(EndpointConfig endpointConfig) { 
	}
	
	@Override
	public void destroy() { }
	}
}
