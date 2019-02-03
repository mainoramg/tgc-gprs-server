
public class Parser
{
   public static void main( String[] args ) {
      if(args.length < 1)
         return;

      String record =  args[0];
      //*HQ,9170670374,V1,202617,A,1000.4314,N,08416.2526,W,000.18,000,111218,FFFFBBFF,712,04,42003,12803#
      GPSEvent event = new GPSEvent( record );

      System.out.println( event.toString() );
      System.out.println( event.getGoogleMapsLink() );
      System.out.println( event.getQueryToSaveIntoDB( "test" ) );
      System.out.println( event.getQueryToRemoveFromDB( "test" ) );
   }
}