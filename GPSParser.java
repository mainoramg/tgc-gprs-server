import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.util.*;

class GPSParser
{
   public static void main( String args[] )
   {
      while( true )
      {
         try
         {
            String    sql   = "select * from gps_raw_data order by stamp limit 100";
            ArrayList al    = DB.getData( sql );
            if( al.size()==0 )
            {
               System.out.println( new Timestamp( System.currentTimeMillis() ) + ": NO DATA ..." );
               try { Thread.sleep( 3*1000 ); } catch( Exception e2 ) {}
               continue;
            }
            for( int i=0; i<al.size(); i++ )
            {
               HashMap  hm          =  (HashMap) al.get(i);
               String   id          =  (String) hm.get( "id"   );
                        id          =  id==null?"":id.toString();
               String   data        =  (String) hm.get( "data" );
                        data        =  data==null?"":data.toString();
               if(   data != null && 
                     data.length() >= 2 && 
                     data.substring( 0, 1 ).equals("*") && 
                     data.substring( data.length()-1, data.length() ).equals( "#" ) )
               {
                  String[] dataArray   =  data.split("#");
                  for( int j = 0 ; j < dataArray.length ; j++ )
                  {
                     GPSEvent event =  new GPSEvent( dataArray[j]+"#" );
                     if( !event.getStatus().equals( "E" ) && !event.getStatus().equals( "" ) )
                     {
                        System.out.println( new Timestamp( System.currentTimeMillis() ) + ": " + event.getQueryToSaveIntoDB( id ) );
                        DB.executeSQL( event.getQueryToSaveIntoDB( id )    );
                     }
                     else
                     {
                        System.out.println( new Timestamp( System.currentTimeMillis() ) + ": " + event.getQueryToRemoveFromDB( id ) );
                        DB.executeSQL( event.getQueryToRemoveFromDB( id )  );
                     }
                  }
               }
               else
               {
                  GPSEvent event =  new GPSEvent( data );
                  System.out.println( new Timestamp( System.currentTimeMillis() ) + ": " + event.getQueryToRemoveFromDB( id ) );
                  DB.executeSQL( event.getQueryToRemoveFromDB( id )  );
               }
            }
         }
         catch( Exception e )
         {
            e.printStackTrace();
            try { Thread.sleep( 60*1000 ); } catch( Exception e2 ) {}
         }
      }
   }
}
