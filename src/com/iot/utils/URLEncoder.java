package com.iot.utils;
import java.util.HashMap;
public class URLEncoder
{
    String data,value;
    HashMap<String,String>  dataMap=new HashMap<String,String>();
    public URLEncoder(String data)
    { 
        this.data=data;
        this.data=populateDS(this.data);
        mapping(this.data);
    }
     void mapping(String data)
     {
         this.value=data;
         String[] keyValuePairs = value.split("&");
         for(String pairs:keyValuePairs)
         {
             String[] entry =pairs.split("=");
             dataMap.put(entry[0].trim(),entry[1].trim());
         }
     }   
        
    public String getAttribute(String key)
    {
        String value="";
        value = (String)dataMap.get(key);
        return value;
    }
    
  
    
    String populateDS(String data)
    {
        return(data.replace("+"," "));
    }
}