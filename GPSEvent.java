import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

public class GPSEvent
{
   public String  id;
   public String  maker;
   public String  serialNumber;
   public String  command;
   public Date    timeStamp;
   public String  status; //Effective mark of data, ‘A’ stand of effective, ‘V’ stand of invalid.
   public String  latitude; //DDFF.FFFF, DD : Degree(00 ~ 90), FF.FFFF : minute (00.0000 ~ 59.9999), keep four decimal places.
   public String  latitudeCardinal; //N:north, S:south
   public String  longitude; //DDDFF.FFFF, DDD : Degree(000 ~ 180), FF.FFFF : minute
   public String  longitudeCardinal; //E:east, W:west
   public String  speed;   //Range of 000.00 ~ 999.99 knots, Keep two decimal places.
   public String  direction;  //Azimuth, north to 0 degrees, resolution 1 degrees, clockwise direction.
   public String  vehicleStatus;
   public String  netMcc;
   public String  netMnc;
   public String  netLac;
   public String  netCellId;

   public GPSEvent()
   {
      this.id                 =  UUID.randomUUID().toString();
      this.maker              =  "";
      this.serialNumber       =  "";
      this.command            =  "";
      this.timeStamp          =  new Date();
      this.status             =  "E";
      this.latitude           =  "";
      this.latitudeCardinal   =  "";
      this.longitude          =  "";
      this.longitudeCardinal  =  "";
      this.speed              =  "";
      this.direction          =  "";
      this.vehicleStatus      =  "";
      this.netMcc             =  "";
      this.netMnc             =  "";
      this.netLac             =  "";
      this.netCellId          =  "";
   }
   public GPSEvent( String data )
   {
      this();
      if( data != null && data.length() >= 2 )
      {
         String[] parts =  data.substring( 1, data.length()-1 ).split(",");
         if( parts.length == 17 )
         {
            this.maker              =  parts[0];   //*HQ
            this.serialNumber       =  parts[1];   //9170670374
            this.command            =  parts[2];   //V1
            this.timeStamp          =  getDateFromString( parts[11] + parts[3] );   //  111218 + 233457
            this.status             =  parts[4];   //A
            this.latitude           =  parts[5];   //1000.4295
            this.latitudeCardinal   =  parts[6];   //N
            this.longitude          =  parts[7];   //08416.2627
            this.longitudeCardinal  =  parts[8];   //W
            this.speed              =  parts[9];   //000.39
            this.direction          =  parts[10];  //000
            this.vehicleStatus      =  parts[12];  //FFF7BBFF
            this.netMcc             =  parts[13];  //712
            this.netMnc             =  parts[14];  //04
            this.netLac             =  parts[15];  //42003
            this.netCellId          =  parts[16];  //12801#
         }
      }
   }

   public String toString()
   {
      StringBuilder result = new StringBuilder("");
      result.append( "{" );
      result.append( "\"id\":\""                ).append( id                     ).append( "\"," );
      result.append( "\"maker\":\""             ).append( maker                  ).append( "\"," );
      result.append( "\"serialNumber\":\""      ).append( serialNumber           ).append( "\"," );
      result.append( "\"command\":\""           ).append( command                ).append( "\"," );
      result.append( "\"timeStamp\":\""         ).append( timeStamp              ).append( "\"," );
      result.append( "\"status\":\""            ).append( status                 ).append( "\"," );
      result.append( "\"latitude\":\""          ).append( latitude               ).append( "\"," );
      result.append( "\"latitudeCardinal\":\""  ).append( latitudeCardinal       ).append( "\"," );
      result.append( "\"longitude\":\""         ).append( longitude              ).append( "\"," );
      result.append( "\"longitudeCardinal\":\"" ).append( longitudeCardinal      ).append( "\"," );
      result.append( "\"speed\":\""             ).append( speed                  ).append( "\"," );
      result.append( "\"direction\":\""         ).append( direction              ).append( "\"," );
      result.append( "\"vehicleStatus\":\""     ).append( vehicleStatus          ).append( "\"," );
      result.append( "\"netMcc\":\""            ).append( netMcc                 ).append( "\"," );
      result.append( "\"netMnc\":\""            ).append( netMnc                 ).append( "\"," );
      result.append( "\"netLac\":\""            ).append( netLac                 ).append( "\"," );
      result.append( "\"netCellId\":\""         ).append( netCellId              ).append( "\"" );
      result.append( "}" );
      return result.toString();   
   }

   public String getGoogleMapsLink()
   {
      //http://maps.google.com/maps?q=+10.00717,-084.27095
      StringBuilder result = new StringBuilder("");
      result.append( "http://maps.google.com/maps?q=" );

      if( this.latitudeCardinal.equals( "N" ) )
      {
         result.append( "+" );
      }
      else if( this.latitudeCardinal.equals( "S" ) )
      {
         result.append( "-" );
      }

      if( latitude.length() >= 2 )
      {
         result.append( latitude.substring( 0, 2 ) ).append( " " ).append( latitude.substring( 2 ) );
      }
   
      result.append( ","      );

      if( this.longitudeCardinal.equals( "E" ) )
      {
         result.append( "+" );
      }
      else if( this.longitudeCardinal.equals( "W" ) )
      {
         result.append( "-" );
      }

      if( longitude.length() >= 2 )
      {
         result.append( longitude.substring( 0, 3 ) ).append( " " ).append( longitude.substring( 3 ) );
      }

      return result.toString();
   }

   protected String utcStamp( Date date )
   {
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      //df.setTimeZone( TimeZone.getTimeZone("UTC") );
      return df.format(date).toString();
   }


   protected Date getDateFromString( String date )
   {
      try
      {
         return new SimpleDateFormat( "ddMMyyHHmmss" ).parse( date );
      }
      catch ( Exception e )
      {
         return null;
      }
   }
}