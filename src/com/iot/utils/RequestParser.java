package com.iot.utils;
public class RequestParser
{
private String requestRawData;
private String fileName;
private String servletName;
private String request;
private String requestType;
private String extension;
private String requestData;
private boolean isClientSideTechnology;

public RequestParser(String requestRawData)
{
this.requestRawData=requestRawData;
this.request="";
this.requestType="";
this.extension="";
this.fileName="";
this.requestData="";
this.isClientSideTechnology=false;
doProcessing();
}
public void doProcessing()
{
int index,e;
index=this.requestRawData.indexOf(" ",0);
this.requestType=requestRawData.substring(0,index);
index++;
e=index;
index=requestRawData.indexOf(" ",index);
this.request=requestRawData.substring(e,index);
if(this.request.indexOf(".")==-1)
{ 
// The request is for servlet
this.isClientSideTechnology=false;
index=this.request.indexOf("?");
if(index!=-1)
{
this.servletName=this.request.substring(1,index);
this.requestData=this.request.substring(index+1,request.length());
}
else
{
this.servletName=this.request.substring(1,request.length());
}
}
else
{ 
// The request is for a file.
this.isClientSideTechnology=true;
this.fileName=this.request.substring(1,request.length());
index=this.request.indexOf(".");
this.extension=this.request.substring(index+1,request.length());
}
}

public String getRequest()
{
return this.request;
}

public String getServletName()
{
return this.servletName;
}

public String getRequestType()
{
return this.requestType;
}
public String getExtension()
{
return this.extension;
}
public String getFileName()
{
return this.fileName;
}
public boolean isClientSideTechnology()
{
return this.isClientSideTechnology;
}

public String getRequestData()
{
return this.requestData;
}

}