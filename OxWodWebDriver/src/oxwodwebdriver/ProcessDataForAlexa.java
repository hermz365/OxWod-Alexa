package oxwodwebdriver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author herman
 */
public class ProcessDataForAlexa {
    
    // Return the text would be shown on the card in Alexa app.
    public static String GetCardText(String date, String wod)
    {
        String cardTxt = date + "\n" + wod;
        System.out.println("\n" + cardTxt + "\n");   
        return cardTxt + "\n\nSee link - http://oxbox.wodtime.com/home.php"+
                "\n\n Note: OxWod is not sponsored or endorsed or affiliated with OxBox Gym.";
    }
        
    // Return a ssml Wod for Alexa to speak to the user
    public static String ProcessRawWodData(String date, String wod, Map<String, String> termsToReplace) {
        System.out.println("About to Process Raw Wod Data into ssml");
        String lines[] = wod.split("\\r?\\n");
        StringBuilder sb = new StringBuilder();
        sb.append("<speak>");
        
        String spokenDate = ProcessDateToSpeechHelper(date);
        if(spokenDate != null)
            sb.append(spokenDate);
        
        for(String line : lines)
        {
            line = line.trim().toLowerCase();
            if(line.length() == 0) continue;
            
            for (String key : termsToReplace.keySet()) {
                if(line.contains(key.toLowerCase()))
                    line = line.replace(key.toLowerCase(), (String)termsToReplace.get(key));
            }
            
            // 24/20 -> 24 / 20 because Alexa woud not speak correctly
            if(line.contains("/")) line = line.replace("/", " / ");
            sb.append("<s>").append(line).append("</s>");   
        }
        
        sb.append("</speak>");
        System.out.println("Finished Process Raw Wod Data into ssml");
        System.out.println("\n" + sb.toString() + "\n");        
        return sb.toString();
    }
    
    private static String ProcessDateToSpeechHelper(String date) 
    {
        System.out.println("About to Process Raw Date into ssml");
        DateFormat inDf = new SimpleDateFormat("MMM dd, yyyy");

        DateFormat weekDayDf = new SimpleDateFormat("EEEE");
        DateFormat monthDf = new SimpleDateFormat("MM");
        DateFormat dayDf = new SimpleDateFormat("dd");

        String dateSpeak = null;

        try {
            //format() method Formats a Date into a date/time string. 
            Date d1 = inDf.parse(date);
            dateSpeak = String.format("%s <say-as interpret-as=\"date\">????%s%s</say-as>", weekDayDf.format(d1), monthDf.format(d1), dayDf.format(d1));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        System.out.println("Finished Process Raw Date into ssml");
        return dateSpeak;
    }
}
