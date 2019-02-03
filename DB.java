import java.sql.*;
import java.util.*;

public class DB
{
   static String            URL       = "jdbc:mysql://localhost:3306/gps?useSSL=false";
   static String            USER      = "gpsuser";
   static String            PASSWORD  = "Luc4s2017";
   
   /*-------------------------------------------------------------------------*/
   /*---   getConnection ...                                               ---*/
   /*-------------------------------------------------------------------------*/
   private static Connection getConnection() throws Exception
   {
      Class.forName( "com.mysql.jdbc.Driver" );
      return DriverManager.getConnection( URL, USER, PASSWORD );
   }
   
   /*-------------------------------------------------------------------------*/
   /*---   closeConnection ...                                             ---*/
   /*-------------------------------------------------------------------------*/
   private static void closeConnection( Connection conn ) throws Exception
   {
      conn.close();
   }

   /*-------------------------------------------------------------------------*/
   /*---   getRecord ...                                                   ---*/
   /*-------------------------------------------------------------------------*/
   static HashMap getRecord( String query )
   {
    //System.out.println( query );
      try
      {
         return (HashMap) DB.getData(query).get(0);
      }
      catch( Exception e )
      {
         return (new HashMap());
      }
   }
   
   /*-------------------------------------------------------------------------*/
   /*---   getData ...                                                     ---*/
   /*-------------------------------------------------------------------------*/
   static ArrayList getData( String query )
   {
      //System.out.println( query );
      ArrayList<HashMap>   al   = new ArrayList<HashMap>();
      Connection           conn = null;
      Statement            stmt = null;
      ResultSet            rs   = null;
      ResultSetMetaData    md   = null;
      int                  cols = 0;
      try
      {
         conn = getConnection();
         stmt = conn.createStatement();
         rs   = stmt.executeQuery( query );
         md   = rs.getMetaData();
         cols = md.getColumnCount();
         while( rs.next() )
         {
            HashMap<String,String>   hm = new HashMap<String,String>();
            for( int j=0; j<cols; j++ )
            {
               String colType = md.getColumnTypeName(j+1);
               String colLbl  = md.getColumnLabel(j+1);
               String colVal  = "";
               
               if( colType.equalsIgnoreCase("date") || colType.equalsIgnoreCase("datetime") )
               {
                  StringBuffer tmp = new StringBuffer( "" );
                  if( rs.getString( colLbl ) == null )
                     colVal = "  /  /    ";
                  else
                  {
                     colVal = (rs.getDate( colLbl )).toString();
                     String stk[] = colVal.split( "-" );
                     tmp.append(stk[1]).append( "/" ).append( stk[2] ).append( "/" ).append( stk[0] );
                  }
                  if( colType.equalsIgnoreCase("datetime") )
                  {
                     try { tmp.append( "@" ).append( rs.getTime( colLbl ).toString() ); } catch( Exception e ) {}
                  }
                  colVal = tmp.toString();
               }
               else
               {
                  colVal  = rs.getString( colLbl );
                  colVal  = (colVal==null)?"":colVal.trim();
               }
               hm.put( colLbl.replaceAll("\\(.+?\\)", "").trim().toLowerCase(), colVal );
            }
            al.add( hm );
         }
      }
      catch( Exception e )
      {
         e.printStackTrace();
      }
      finally
      {
         try { rs.close();             } catch( Exception e ) {}
         try { stmt.close();           } catch( Exception e ) {}
         try { closeConnection(conn);  } catch( Exception e ) {}
      }
      return al;
   }

   /*-------------------------------------------------------------------------*/
   /*---   executeSQL ...                                                  ---*/
   /*-------------------------------------------------------------------------*/
   static void executeSQL( String query )
   {
//    System.out.println( query );
      Connection           conn = null;
      Statement            stmt = null;
      try
      {
         conn = getConnection();
         stmt = conn.createStatement();
         stmt.executeUpdate( query );
      }
      catch( Exception e )
      {
         e.printStackTrace();
      }
      finally
      {
         try { stmt.close();           } catch( Exception e ) {}
         try { closeConnection(conn);  } catch( Exception e ) {}
      }
   }

   /*-------------------------------------------------------------------------*/
   /*---   getHashData ...                                                 ---*/
   /*-------------------------------------------------------------------------*/
   public static HashMap getHashData( String sql, String key, String val )
   {
      ArrayList  v = getData(sql);
      HashMap h = new HashMap();
      if( v.size()==0 )
         return h;
      for( int i=0; i<v.size(); i++ )
      {
         HashMap hm = (HashMap) v.get(i);
         h.put( hm.get(key), hm.get(val) );
      }
      return h;
   }
}
