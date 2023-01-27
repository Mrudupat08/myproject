package test;
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class first {
	
    public static String extractTimestamp(String json) {
        String timestamp = "";
        Pattern pattern = Pattern.compile("\"timestamp\":(\\d+)");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            timestamp = matcher.group(1);
        }
        return timestamp;
    }
    public static String extractContent(String json) {
        String Content = "";
        Pattern pattern = Pattern.compile("content\":\"(.+?)\"");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
        	Content = matcher.group(1);
        }
        return Content;
    }
    public static void main(String[]args) throws FileNotFoundException, IOException  {
    	
        File myFile= new File("C:\\Users\\mrudula.patankar\\eclipse-workspace\\test\\src\\cerence_ark_sdk_2023-01-18_18-05-07.log");
        String SearchWord;
        String SearchWord2;
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter word you want to search");
        SearchWord=sc.nextLine();
        System.out.print("Enter word you want to search");
        SearchWord2=sc.nextLine();
        
  
        List<String> timestamps = new ArrayList<>();
        List<String> timestamps2 = new ArrayList<>();
        List<String> contents = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(myFile))) {
            String json;
            while ((json = br.readLine()) != null) {
            	if(json.contains("Hey Cerence")) {
            		continue;
            	}
                if (json.contains(SearchWord )&& json.contains("TimeMarker")) {
                    //System.out.println(json);
                    String timestamp = extractTimestamp(json);
                    timestamps.add(timestamp);
                }
                if (json.contains(SearchWord2)&&json.contains("TimeMarker")) {
                    //System.out.println(json);
                    String timestamp2 = extractTimestamp(json);
                    timestamps2.add(timestamp2);
                }
                if(json.contains(SearchWord)&&json.contains("ace.TimeMarker ")) {
                	 if (json.contains("content")) {
                		 String content=extractContent(json);
                		 contents.add(content);
                	 } 
                } 
                /*if(json.contains("Hey cerence")) {
               	 if (json.contains("content")) {
            		 String content=extractContent(json);
            		 contents.add(content);
            	 } 
            } */
            }
        }
        for (int i = 0; i < timestamps.size(); i++) {
            timestamps.set(i, timestamps.get(i).replaceAll("[^\\d]", ""));
        }
        
        for (int i = 0; i < timestamps2.size(); i++) {
            timestamps2.set(i, timestamps2.get(i).replaceAll("[^\\d]", ""));
        }
        //System.out.println("Timestamps for searchWord1: " + timestamps);
        
        List<Long> differences = new ArrayList<>();
        for (int i = 0; i < timestamps.size(); i++) {
            long timestamp1 = Long.parseLong(timestamps.get(i));
            long timestamp2 = Long.parseLong(timestamps2.get(i));
            long difference = timestamp1- timestamp2;
            differences.add(difference);
        }
        //System.out.println("Differences between timestamps: " + differences);
        for (int i = 0; i < timestamps.size(); i++) {
   
        	 System.out.printf("For utterance: %s, the timestamps are: %s and %s and latency is %s .\n", contents.get(i), timestamps.get(i),timestamps2.get(i),differences.get(i));
        }
        
        /*System.out.println("utterances:"+contents);
        System.out.println("Timestamps for searchWord1: " + timestamps);
        System.out.println("Timestamps for searchWord2: " + timestamps2);*/
        FileWriter writer = new FileWriter("C:\\Users\\mrudula.patankar\\eclipse-workspace\\test\\src\\test\\Solution.txt");
        writer.write("Differences between timestamps: " + differences + "\n");
        for (int i = 0; i < timestamps.size(); i++) {
            writer.write(String.format("%s %s %s %s \n", contents.get(i), timestamps.get(i),timestamps2.get(i),differences.get(i)));
        }
        writer.write("utterances:"+contents + "\n");
        writer.write("Timestamps for searchWord1: " + timestamps + "\n");
        writer.write("Timestamps for searchWord2: " + timestamps2 + "\n");
        writer.close();
        sc.close();
    }
}