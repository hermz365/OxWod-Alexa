package oxwodwebdriver;

import java.util.Map;

/**
 *
 * @author herman
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {   
        String Wod, Date, AlexaCardText, AlexaSpeech;
        
        Map<String, String> TermsToReplace = FileHelper.InitData();             
        
        WebCrawler crawler = WebCrawler.getInstance();
        crawler.GoToPage("http://oxbox.wodtime.com/home.php");
        Wod = crawler.GetWod();
        Date = crawler.GetDate();
        crawler.QuitBrowser();
        
        AlexaCardText = ProcessDataForAlexa.GetCardText(Date, Wod);
        AlexaSpeech = ProcessDataForAlexa.ProcessRawWodData(Date, Wod, TermsToReplace);        
        
        if(FileHelper.ShouldUpdateDB(AlexaSpeech))
        {            
            DynamoHelper.UploadProcessedWodDataToDynamoDB(AlexaSpeech, AlexaCardText);
            FileHelper.ExportProcessedWodData(AlexaSpeech);
        }
        else
        {
            System.out.println("No need to update DB. Save some $$!");
        }
    }
}
