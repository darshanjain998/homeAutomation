package com.iot.server;
import java.net.*;
import java.io.*;
import java.util.*;
import com.iot.client.*;
import com.iot.model.*;
import com.iot.utils.*;
import com.google.gson.*;

class RequestProcessor extends Thread
{
private Socket socket;
private States states;
RequestProcessor(Socket st,States states)
{
this.states=states;
this.socket=st;
start();
}

public String getHeader(long contentLength,String contentType)
{
String header;
header="";
header+="HTTP/1.1 200 OK\n";
header+="Content-Type: ";
header+=contentType+"\n";
header+="Content-Length: ";
header+=contentLength+"\n";

header+="\n";
return header;
}

public void run()
{
try
{
char c;
InputStream is=this.socket.getInputStream();
OutputStream os=this.socket.getOutputStream();
byte chunk[]=new byte[8192];
byte requestHeader[]=new byte[10];
byte req[]=new byte[10];
int rd=0;
int k=0;
int r=0;
int mrsz=8192;
mrsz=is.read(chunk);
if(mrsz!=-1)
{
int e;
StringBuffer stringBuffer=new StringBuffer();
for(e=0;e<mrsz;e++)
{
stringBuffer.append((char)chunk[e]);
}
String requestRawData=stringBuffer.toString();
System.out.println(requestRawData);
RequestParser requestParser=new RequestParser(requestRawData);
if(requestParser.isClientSideTechnology())
{
String fileName=requestParser.getFileName();
FileHandler fileHandler=new FileHandler(fileName,1024);
if(fileHandler.exists())
{
byte data[]=new byte[1024];
int bytesCount=0;
long lengthOfFile=fileHandler.size();
String header=getHeader(lengthOfFile,"text/html");
os.write(header.getBytes());
while(fileHandler.hasNext())
{
bytesCount=fileHandler.next(data);
os.write(data,0,bytesCount);
}
os.flush();
System.out.println("Contents of "+fileName+" has been sent successfully");
}
else System.out.println("File ---> "+fileName+" , do not exists");
}else
{
String servletName=requestParser.getServletName();
String requestData=requestParser.getRequestData();
if(requestData.length()!=0)
{
com.iot.utils.URLEncoder urlEncoder=new com.iot.utils.URLEncoder(requestData);
String device=urlEncoder.getAttribute("device");
String deviceState=urlEncoder.getAttribute("state");
String requestForNano=device+deviceState;
NanoRequestSender nanoRequestSender=new NanoRequestSender(requestForNano,this.states);
nanoRequestSender.sendRequest();
List<Integer> listOfStates=this.states.getStates();
Gson gson=new Gson();
String jsonString=gson.toJson(listOfStates);
String header=getHeader(jsonString.length(),"application/json");
os.write(header.getBytes());
os.write(jsonString.getBytes());
os.flush();
System.out.println("response has been send@@@@@@@@@\n"+jsonString);


}
}
}
is.close();
os.close();
socket.close();
}catch(Exception exception)
{
System.out.println(exception);
}
}
}
