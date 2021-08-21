package com.iot.server;
import java.net.*;
import java.io.*;
import java.util.*;
import com.iot.model.*;

public class IOTServer
{
int portNumber;
private ServerSocket serverSocket;
private States states;

public IOTServer(int portNumber)
{
this.portNumber=portNumber;
this.states=new States();
try
{
serverSocket=new ServerSocket(this.portNumber);
}catch(Exception exception)
{
System.out.println(exception);
}
}

public void start()
{
try
{
Socket socket;
RequestProcessor requestProcessor;
while(true)
{
System.out.println("IOT Server is ready and is listening on the port:"+this.portNumber);
socket=serverSocket.accept();
requestProcessor=new RequestProcessor(socket,this.states);
}
}catch(Exception exception)
{
System.out.println(exception);
}
}
}


