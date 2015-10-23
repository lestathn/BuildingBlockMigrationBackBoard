package b2tkt.sample.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import blackboard.db.*;
import blackboard.persist.Id;
import blackboard.persist.KeyNotFoundException;
import blackboard.persist.PersistenceException;
import blackboard.persist.course.CourseDbPersister;
import blackboard.platform.context.Context;
import blackboard.platform.context.ContextManagerFactory;
import blackboard.platform.plugin.PlugInUtil;
import blackboard.platform.validation.ConstraintViolationException;
import blackboard.data.ValidationException;
import blackboard.data.content.Folder;
import blackboard.data.course.Course;
import blackboard.admin.data.course.CourseSite;
import blackboard.admin.persist.course.CloneConfig;
import blackboard.admin.persist.course.CourseSiteLoader;
import blackboard.admin.persist.course.CourseSitePersister;
import blackboard.persist.course.CourseDbLoader;

@Controller
public class AdminPage {
	
//-------------------------------------------------------------------------------------------------------------------------------
		
	/*private void PassArray(){
    String[] arrayw = new String[4]; //populate array
    PrintA(arrayw);
}

private void PrintA(String[] a){
    //do whatever with array here
}*/
	public String CreateCourse(String[] cadena){
		
		String msg = "";
		try {
			Course c = new Course();
			msg += "New Course <br>";
			c.setCourseId(cadena[0]);
			msg += "New Course id<br>";
			c.setTitle(cadena[1]);	
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
    public String AdminPage(final HttpServletRequest request, final HttpServletResponse response, final ModelMap model) throws UnsupportedEncodingException,
            PersistenceException, IOException {

String msg = "NO RESULTS </br>";
		
        try {
        	String[] json = ((String) request.getAttribute("jsonRecibido")).split(",");
            Context ctx = ContextManagerFactory.getInstance().getContext();
            ContextManagerFactory.getInstance().setContext(request);        	
        	CourseDbPersister CDBP = CourseDbPersister.Default.getInstance();
        	CourseDbLoader CDBL = CourseDbLoader.Default.getInstance();
        	
        	Course C = new Course();
        	Id CId = null;
        	//String CourseId = "jsonRecibido";
        	//String [][] CourseId = cadena();
        	
        	if(CDBL.doesCourseIdExist(json[0])){       		
        			CDBP.deleteById(CDBL.loadByCourseId(json[0]).getId());
        	}
        	
        	//Set CourseId & CourseBatchUid
        	C.setCourseId(json[0]);
        	C.setBatchUid(json[1]);        		
        	    		
        	//Set CourseId & CourseBatchUid
        	C.setShowInCatalog(true); 
        	/*for(int i=0;i<CourseId.length;i++)
        	{
        		
        	}*/
    		C.setTitle(json[1]);
    		msg = "FINISHED COURSE SETTINGS</br>";
    		
    		/*
    		CId = C.getId();
    		
    		CourseToc CourseContent = new CourseToc();
    		
    		CourseContent.setCourseId(CId);
    		//Id ContentId = generateId();
    		
    		//CourseContent.setC
    		CourseContent.setTargetType(CourseToc.Target.CONTENT);
    		CourseContent.setLabel("Test Content");
    		CourseContent.setContentAvailable(true);
    		*/
    		CDBP.persist(C);
    		//CourseContent.persist();
    		
    		
    		msg += "FINISHED PERSISTING COURSE</br>";
    		
    		
        } catch (Exception e) {

        	msg = e.toString();
        }
        
        model.addAttribute("msg", msg);
        return "AdminPage";
    }
	
}// fin class