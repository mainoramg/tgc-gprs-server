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
               System.out.println( new Timestamp( System.currentTimeMillis()) + ": " + output );
               try
               {
                  if( output != null )
                  {
                     if( output.length() < 2000 )
                     {
                        DB.executeSQL( "insert into gps_raw_data     (id, data, status) values ( uuid(), '" + output.replace( "'", "\\\'" ) + "', 'PENDING' );" );
                     }
                     else
                     {
                        DB.executeSQL( "insert into gps_raw_data_big (id, data, status) values ( uuid(), '" + output.replace( "'", "\\\'" ) + "', 'PENDING' );" );
                     }
                  }                 
               }
               catch ( Exception e )
               {
                  System.out.println( new Timestamp( System.currentTimeMillis()) + ": Exception inserting into DB" );
               }
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