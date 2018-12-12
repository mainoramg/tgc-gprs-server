
public class Parser
{
   public static void main( String[] args ) {
      if(args.length < 1)
         return;

      String record =  args[0];

      GPSEvent event = new GPSEvent( record );

      System.out.println( event.toString() );
      System.out.println( event.getGoogleMapsLink() );
   }
}