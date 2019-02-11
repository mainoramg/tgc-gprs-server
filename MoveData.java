import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

class MoveData
{
   public static void main( String args[] )
   {
      if( args.length < 2 )
         return;
      try
      {
         String   tableName   =  args[0];
         String   columnName  =  args[1];

         Calendar cal         =  Calendar.getInstance();
         cal.add( Calendar.HOUR, -24 );
         Date     currentTime =  cal.getTime();
         SimpleDateFormat  sdfDate  =  new SimpleDateFormat( "yyyy-MM-dd" );
         sdfDate.setTimeZone( TimeZone.getTimeZone( "GMT-6" ) );
         String   nowDate     =  sdfDate.format( currentTime );
         //SimpleDateFormat  sdfTime  =  new SimpleDateFormat( "HH:mm:ss" );
         SimpleDateFormat  sdfTime  =  new SimpleDateFormat( "HH" );
         sdfTime.setTimeZone( TimeZone.getTimeZone( "GMT-6" ) );
         String   nowTime     =  sdfTime.format( currentTime );

         String   tableDate   =  "_" + nowDate.substring( 0, 7 ).replace( "-", "_" );

         String   sqlInsert   =  "insert into "    + tableName + tableDate;
                  sqlInsert  +=  " select * from " + tableName;
                  sqlInsert  +=  " where " + columnName + " < CONVERT_TZ(STR_TO_DATE('" + nowDate + "@" + nowTime + ":00:00', '%Y-%m-%d@%H:%i:%s'),'-06:00','+00:00');";
         String   sqlDelete   =  "delete from " + tableName;
                  sqlDelete  +=  " where " + columnName + " < CONVERT_TZ(STR_TO_DATE('" + nowDate + "@" + nowTime + ":00:00', '%Y-%m-%d@%H:%i:%s'),'-06:00','+00:00');";

         System.out.println( new Timestamp( System.currentTimeMillis()) + ": START" );
         System.out.println( new Timestamp( System.currentTimeMillis()) + ": " + sqlInsert );
         DB.executeSQL( sqlInsert );
         System.out.println( new Timestamp( System.currentTimeMillis()) + ": END" );
         System.out.println();
         System.out.println( new Timestamp( System.currentTimeMillis()) + ": START" );
         System.out.println( new Timestamp( System.currentTimeMillis()) + ": " + sqlDelete );
         DB.executeSQL( sqlDelete );
         System.out.println( new Timestamp( System.currentTimeMillis()) + ": END" );
         System.out.println();
      }
      catch( Exception e )
      {
         e.printStackTrace();
      }
   }
}
