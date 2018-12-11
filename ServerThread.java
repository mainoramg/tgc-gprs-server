import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
 
/**
 * This thread is responsible to handle client connection.
 *
 * @author www.codejava.net
 */
public class ServerThread extends Thread
{
   private Socket socket;

   public ServerThread( Socket socket )
   {
      this.socket = socket;
   }

   public void run()
   {
      try
      {
         InputStream input = socket.getInputStream();
         byte[] buffer = new byte[1024];
         int read;
         while( (read = input.read(buffer)) != -1 )
         {
               String output = new String( buffer, 0, read );
               System.out.println( new Timestamp(System.currentTimeMillis()) + ": " + output );
               System.out.flush();
         }
         socket.close();
      }
      catch( IOException ex )
      {
         System.out.println( new Timestamp(System.currentTimeMillis()) + ": Server exception: " + ex.getMessage() );
         ex.printStackTrace();
      }
   }
}