package b2tkt.sample.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import blackboard.db.*;
import blackboard.persist.Id;
import blackboard.persist.KeyNotFoundException;
import blackboard.persist.PersistenceException;
import blackboard.persist.course.CourseDbPersister;
import blackboard.platform.contentarea.Attachment;
import blackboard.platform.contentarea.ContentArea;
import blackboard.platform.contentarea.service.ContentAreaDbPersister;
import blackboard.platform.context.Context;
import blackboard.platform.context.ContextManagerFactory;
import blackboard.platform.plugin.PlugInUtil;
import blackboard.platform.validation.ConstraintViolationException;
import blackboard.data.ValidationException;
import blackboard.data.content.BlankPage;
import blackboard.data.content.Content;
import blackboard.data.content.Folder;
import blackboard.data.course.Course;
import blackboard.admin.data.course.CourseSite;
import blackboard.admin.persist.course.CloneConfig;
import blackboard.admin.persist.course.CourseSiteLoader;
import blackboard.admin.persist.course.CourseSitePersister;
import blackboard.base.FormattedText;
import blackboard.persist.course.CourseDbLoader;

@Controller
public class AdminPage {
	
//-------------------------------------------------------------------------------------------------------------------------------

	public void CreateContent(int type, JSONArray array)
	{
		switch (type)
		{
			case 1:				
				try {					
					ContentArea cArea = new ContentArea();
					cArea.setId((Id) array.get(0));
					cArea.setTitle((String) array.get(1));
					cArea.setText((FormattedText) array.get(2));
					ContentAreaDbPersister ContAreaPersi;
					ContAreaPersi= ContentAreaDbPersister.Default.getInstance();
				} catch (PersistenceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}				
				break;
			case 2:
			BlankPage oBlankPage = new BlankPage();
			oBlankPage.setId((Id) array.get(0));
			oBlankPage.setTitle((String) array.get(1));
			oBlankPage.addContent((Content) array.get(1));
			oBlankPage.setAllowObservers(true);
			oBlankPage.setIsAvailable(true);
			oBlankPage.setIsTracked(true);
				break;
			case 3:
			Attachment Attach = new Attachment();
			Attach.setFile(null);
			
			File f = new File("sdsajkdfsajkd");
			
			Attach.setFile(f);
			Attach.setFileName((String) array.get(1));
				break;
			case 4:
				
				
				break;
		}
	}
	
	public String CreateCourse(String[] cadena){
		
		String msg = "";
		try {
			Course c = new Course();
			msg += "New Course <br>";
			c.setCourseId(cadena[cadena.length-2]);
			msg += "New Course id<br>";
			c.setTitle(cadena[cadena.length-1]);	
			msg += "New Course name<br>";
						
			CourseDbPersister CP;
			CP = CourseDbPersister.Default.getInstance();
			msg += "Load db persister<br>";
			
			CP.persist(c);
			msg += "Persist Course <br>";
			
			
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return msg;
	}
//-------------------------------------------------------------------------------------------------------------------------------
	
	@RequestMapping(value = {"AdminPage"}, method = RequestMethod.POST)
    public String getAdminPage(final HttpServletRequest request, final HttpServletResponse response, final ModelMap model) throws UnsupportedEncodingException,
            PersistenceException, IOException {
		String msg = "NO RESULTS </br>";
		CourseDbPersister CDBP = CourseDbPersister.Default.getInstance() ;
		CourseDbLoader CDBL;
        try {
        	//Funcion que me envia el json
        	getJson oGetJson = new getJson();
        	String url ="https://migration-moodle-charmeleon-char.c9.io/GET_CONTENT.php/";
        	JSONArray oJson = oGetJson.readJsonFromUrl(url);
        	JSONObject CourseInfo = null;
        	
			     	
            CourseInfo= (JSONObject) oJson.get(0);
            
            
        	Course C = new Course();      	
        	/*	if(CDBL.doesCourseIdExist((String) oJson.get("CourseId"))){       		
        			CDBP.deleteById(CDBL.loadByCourseId((String) oJson.get("CourseId")));
        	}*/
        	
        	//Set CourseId & CourseBatchUid
        	C.setCourseId((String) CourseInfo.get("CourseId"));
        	C.setBatchUid((String) CourseInfo.get("NameId"));        		
        	    		
        	//Set CourseId & CourseBatchUid
        	C.setShowInCatalog(true);        	
    		C.setTitle((String) CourseInfo.get("NameId"));
    		msg = "FINISHED COURSE SETTINGS</br>";
    		
    	
    		CDBP.persist(C);
    		
    		JSONArray temp = null;
    		  
    		  for(int i = 1; i < oJson.size(); i++){
    		  
    		  System.out.println(((JSONObject)oJson.get(i)).get("name"));
    		  
    		  temp = (JSONArray)((JSONObject)oJson.get(i)).get("modules");
    		  
    		  for(int j = 0; j < temp.size(); j++){
    			  if(((JSONObject)temp.get(j)).get("modname") == "Url" ){
    				  CreateContent(1,temp);
    			  }
    			  System.out.println("------" + ((JSONObject)temp.get(j)).get("name") + " -- " +((JSONObject)temp.get(j)).get("modname") + " -- " + ((JSONObject)temp.get(j)).get("modplural"));
    		  }
    		}
    		//CourseContent.persist();    		
    		msg += "FINISHED PERSISTING COURSE</br>";
    		
    		
    		
        } catch (Exception e) {

        	msg = e.toString();
        }
        
        model.addAttribute("msg", msg);
        return "AdminPage";
    }
	
}// fin class