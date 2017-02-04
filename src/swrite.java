import java.lang.*; 
import java.io.*; 
import java.util.*; 
 
class swrite 
{ 
    public static void main(String args[]) 
    { 
       try{       
        FileOutputStream f = new FileOutputStream("tmp"); 
        ObjectOutput s = new ObjectOutputStream(f); 
        s.writeObject("Today"); 
        s.writeObject(new Date()); 
        s.flush(); 
       } 
         catch(IOException e) { }     
         System.out.println("Today"); 
         System.out.println(new Date()); 
    } 
} 