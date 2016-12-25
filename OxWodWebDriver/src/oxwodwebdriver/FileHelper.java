package oxwodwebdriver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author herman
 */
public class FileHelper {
    
    // This file contains terms that need to translate or expand, so Alexa can speak it more naturally
    private static final String SpeakTermsTxt = "../Data/terms.txt";
    // This file contains the last wod uploaded to DB. Use it to check if there is a need to update DB
    private static final String FinalSpeech = "../output/wod.txt";
    
    public static Map<String, String> InitData()
    {
        Map<String, String> termsToReplace = new HashMap<>();
                 
        String line;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(SpeakTermsTxt))) {
            while ((line = bufferedReader.readLine()) != null) {
                String lines[] = line.split(";");
                termsToReplace.put(lines[0], lines[1]);
            }
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + SpeakTermsTxt + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + SpeakTermsTxt + "'");                              
        }        
        
        return termsToReplace;
    }
    
    public static Boolean ShouldUpdateDB(String wod)
    {           
        String line;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FinalSpeech))) {
            line = bufferedReader.readLine().trim();                      
            return !line.equals(wod);
        }
        catch(FileNotFoundException e) {
            System.out.println("Unable to open file '" + FinalSpeech + "'");             
        }
        catch(IOException e) {
            System.out.println("Error reading file '" + FinalSpeech + "'");               
        } 
        return true;
    }
    
    public static void ExportProcessedWodData(String wod) {
        System.out.println("About to write the data to local text file");
        try {
            PrintWriter writer = new PrintWriter(FinalSpeech, "UTF-8");
            writer.println(wod);
            writer.close();       
        } catch (Exception e) {
            System.out.println("Error writing data to local text file - " + e.getMessage());
        }
        System.out.println("Finished write the data to local text file");
    }
}
