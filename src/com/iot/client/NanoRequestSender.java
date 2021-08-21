package com.iot.client;
import java.util.*;
import java.net.*;
import java.io.*;
import com.iot.model.*;
public class NanoRequestSender
{
    private String datatosend;
    private States states;
    List<Integer> list;
    public NanoRequestSender(String str,States states)
    {
        this.datatosend=str;
        this.list=new ArrayList<>();
        this.states=states;
    } 

    public void sendRequest()
    {
        try
        {
            char endchar='#';
            String datatosend2= datatosend+endchar;            
            Socket socket = new Socket("192.168.4.1",80);
            OutputStream os=socket.getOutputStream();
            //datatosend2="41#";
            OutputStreamWriter osw=new OutputStreamWriter(os);
            osw.write(datatosend2);
            osw.flush();
            InputStream is =socket.getInputStream();
            InputStreamReader isr=new InputStreamReader(is);
            StringBuffer sb=new StringBuffer();
            int x;
            while(true)
            {
                x=isr.read();
                if(x=='#') break;
                sb.append((char)x);
            }
            String datareceived=sb.toString();
            socket.close(); 
            int i=0;    
            while(i<datareceived.length())
            {    
            char y=datareceived.charAt(i);
            Integer datatoadd=Integer.parseInt(String.valueOf(y));
            list.add(datatoadd);
            i++;
            }
            this.states.setStates(list);
       }
        catch(Exception e)
        {
            System.out.println(e);
        }

               this.states.setStates(list);
    }
}