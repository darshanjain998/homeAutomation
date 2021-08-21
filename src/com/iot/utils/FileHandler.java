package com.iot.utils;
import java.io.*;
public class FileHandler  
{
public int chunkSize;
public File f1;
public FileInputStream fis;
public int length;
public int read=0;
public boolean fileExists;
public int fileSize;


public FileHandler(String fileName,int chunkSize)
{
this.chunkSize=chunkSize;
try
{
this.f1=new File(fileName);
}
catch(Exception e)
{
System.out.println(e);
}
if(f1.exists()==false)
{
this.fileExists=false;
}
else 
{
this.fileExists=true;
this.length=(int)f1.length();
doProcessing();
}
}

public long size()
{
if(f1==null) return 0;
return f1.length(); 
}

public boolean exists()
{
return this.fileExists;
}

public void doProcessing()
{
int size=0;
try{
this.fis = new FileInputStream(this.f1);
}
catch(Exception e)
{
System.out.println(e);
}
}

public boolean hasNext()
{
if(this.fileExists==false) return false;
if(this.length-this.read<=0)
{
return false;
}
return true;
}

public int next(byte b[])
{
if(this.fileExists==false) return 0;
int dataChunk;
int m=0;
if(this.length-this.read<=0)
{
try{
this.fis.close();
}
catch(Exception exception)
{
System.out.println(exception);
}
return 0;
}
if(this.length-this.read>=1024)
{
dataChunk=1024;
}
else
{
dataChunk=this.length-this.read;
}
while(dataChunk>0)
{
try{
m=fis.read(b,0,dataChunk);
}
catch(Exception exception)
{
System.out.println(exception);
}
break;
}
this.read+=m;
return m;
}
}