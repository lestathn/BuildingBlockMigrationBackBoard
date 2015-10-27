package b2tkt.sample.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class getJson {

  private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

  public static JSONArray readJsonFromUrl(String url) throws IOException, JSONException {
    InputStream is = new URL(url).openStream();
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      
      JSONParser parser = new  JSONParser();
      Object obj = parser.parse(jsonText);
      JSONArray json = (JSONArray)obj;
      
      return json;
    } catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
      is.close();
    }
	return null;
  }

  public static void main(String[] args) throws IOException, JSONException {
	  JSONArray json = readJsonFromUrl("https://migration-moodle-charmeleon-char.c9.io/GET_CONTENT.php/");
	  JSONObject primerElemento = null;
	  
	  primerElemento = (JSONObject) json.get(2);
	  
	  JSONArray temp = null;
	  
	  for(int i = 1; i < json.size(); i++){
		  
		  System.out.println(((JSONObject)json.get(i)).get("name"));
		  
		  temp = (JSONArray)((JSONObject)json.get(i)).get("modules");
		  
		  for(int j = 0; j < temp.size(); j++){
			  
			  System.out.println("------" + ((JSONObject)temp.get(j)).get("name") + " -- " +((JSONObject)temp.get(j)).get("modname") + " -- " + ((JSONObject)temp.get(j)).get("modplural"));
		  }
	  }
    
  }
}