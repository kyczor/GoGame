package models;


import models.msgs.*;
import play.Logger;
import play.mvc.*;
import play.libs.*;
import play.libs.F.*;
import akka.actor.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;


public class Human extends UntypedActor
{
    public final String               name;
    public final ActorRef             table;

    protected WebSocket.In<JsonNode>  in;
    protected WebSocket.Out<JsonNode> out;


    public Human(String _name, WebSocket.In<JsonNode> _in,
            WebSocket.Out<JsonNode> _out, ActorRef _table)
    {
        name = _name;        
        table = _table;
        in = _in;
        out = _out;
        

        in.onMessage(new Callback<JsonNode>()
        {
            @Override
            public void invoke(JsonNode event)
            {
                try
                {
                	int nr = event.get("nr").asInt();                
                	getSelf().tell(new Move(nr,name), getSelf() );                	
                }
                catch (Exception e)
                {
                    Logger.error("invokeError");
                }
                
            }
        });

        in.onClose(new Callback0()
        {
            @Override
            public void invoke()
            {
                table.tell(new Quit(name), getSelf() );
            }
        });
    }

    @Override
    public void preStart()
    {        
        String text = "my Actor is now running!";
    	table.tell(new Info(text, name), getSelf()); 
    }

    @Override
    public void postStop()
    {
        String text = "I've been killed";
    	table.tell(new Info(text, name), getSelf()); 
    }

    @Override
    public void onReceive(Object msg) throws Exception
    {
 
            if (msg instanceof Move)
            {   
            	int position = ((Move) msg).getPosition();            	           			
            	String text = "I've got the position " + position + " from WebSocket and send it to Table";
            	table.tell(new Info(text, name), getSelf()); 
            	table.tell(new Move(position, name), getSelf());
            }  
            
            else if(msg instanceof Info)
            {        
            	Info info = (Info) msg;             	
	            ObjectNode event = Json.newObject();
	            event.put("message", "[ "+ info.getName()+ " ] : " + info.getText()); 
	            
	            out.write(event);
            }
            
            else if(msg instanceof Ack)
            {       
            	             	
	            ObjectNode event = Json.newObject();
	            event.put("message", "[ Ack from Table: my move has been accepted ] "); 
	            
	            out.write(event);
            }
            else {
                unhandled(msg);
            }
            
        }  
            
}

