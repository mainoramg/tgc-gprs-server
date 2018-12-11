import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
 
/**
 * This program demonstrates a simple TCP/IP socket server that echoes every
 * message from the client in reversed form.
 * This server is multi-threaded.
 *
 * @author www.codejava.net
 */
public class GPRSServer
{
   public static void main( String[] args ) {
      if(args.length < 1)
         return;

      int port = Integer.parseInt(args[0]);

      try( ServerSocket serverSocket = new ServerSocket( port ) )
      {
         System.out.println( new Timestamp( System.currentTimeMillis() ) + ": Server is listening on port: " + port );
         while( true )
         {
            Socket socket = serverSocket.accept();
            System.out.println( new Timestamp( System.currentTimeMillis() ) + ": New client connected" );
            new ServerThread( socket ).start();
         }
      }
      catch( IOException ex )
      {
         System.out.println( new Timestamp( System.currentTimeMillis() ) + ": Server exception: " + ex.getMessage() );
         ex.printStackTrace();
      }
   }
}