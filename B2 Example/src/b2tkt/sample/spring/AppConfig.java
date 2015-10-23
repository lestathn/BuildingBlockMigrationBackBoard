package b2tkt.sample.spring;

import java.sql.Connection;

import javax.annotation.PreDestroy;

import net.sf.cglib.proxy.Enhancer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import blackboard.db.ConnectionManager;
import blackboard.persist.BbPersistenceManager;
import blackboard.persist.course.CourseDbLoader;
import blackboard.persist.course.CourseMembershipDbLoader;
import blackboard.persist.course.GroupDbLoader;
import blackboard.persist.gradebook.impl.AttemptDbLoader;
import blackboard.persist.registry.SystemRegistryEntryDbLoader;
import blackboard.persist.registry.SystemRegistryEntryDbPersister;
import blackboard.persist.user.UserDbLoader;
import blackboard.platform.context.ContextManager;
import blackboard.platform.context.ContextManagerFactory;
import blackboard.platform.coursecontent.AssignmentManager;
import blackboard.platform.coursecontent.AssignmentManagerFactory;
import blackboard.platform.impl.services.task.TaskManagerService;
import blackboard.platform.impl.services.task.TaskManagerServiceFactory;
import blackboard.platform.persistence.PersistenceServiceFactory;
import blackboard.platform.session.BbSessionManagerService;
import blackboard.platform.session.BbSessionManagerServiceFactory;


@Configuration
public class AppConfig
{
  private static final Logger LOGGER = LoggerFactory.getLogger( AppConfig.class );
  
  public @Bean
  GroupDbLoader groupDbLoader()
  {
    GroupDbLoader groupDbLoader = null;
    try
    {
      groupDbLoader = GroupDbLoader.Default.getInstance();
    }
    catch ( Exception e )
    {
      LOGGER.error( "Can't get GroupDbLoader loader", e );
    }
    return groupDbLoader;
  }
 
  public @Bean
  SystemRegistryEntryDbLoader systemRegistryEntryDbLoader()
  {
    SystemRegistryEntryDbLoader systemRegistryEntryDbLoader = null;
    try
    {
      systemRegistryEntryDbLoader = SystemRegistryEntryDbLoader.Default.getInstance();
    }
    catch ( Exception e )
    {
      LOGGER.error( "Can't get db loaders", e );
    }
    return systemRegistryEntryDbLoader;
  }

  public @Bean
  SystemRegistryEntryDbPersister systemRegistryEntryDbPersister()
  {
    SystemRegistryEntryDbPersister systemRegistryEntryDbPersister = null;
    try
    {
      systemRegistryEntryDbPersister = SystemRegistryEntryDbPersister.Default.getInstance();
    }
    catch ( Exception e )
    {
      LOGGER.error( "Can't get db loaders", e );
    }
    return systemRegistryEntryDbPersister;
  }

  public @Bean
  AttemptDbLoader attemptDbLoader()
  {
    AttemptDbLoader attemptDbLoader = null;
    try
    {
      BbPersistenceManager pm = PersistenceServiceFactory.getInstance().getDbPersistenceManager();
      attemptDbLoader = (AttemptDbLoader) pm.getLoader( AttemptDbLoader.TYPE );
    }
    catch ( Exception e )
    {
      LOGGER.error( "Can't get db loaders", e );
    }
    return attemptDbLoader;
  }

  public @Bean
  TaskManagerService taskManagerService()
  {
    TaskManagerService taskManagerService = null;
    try
    {
      taskManagerService = TaskManagerServiceFactory.getInstance();
    }
    catch ( Exception e )
    {
      LOGGER.error( "Can't load TaskManagerService" );
    }
    return taskManagerService;
  }

  public @Bean
  ContextManager contextManager()
  {
    ContextManager contextManager = null;
    try
    {
      contextManager = ContextManagerFactory.getInstance();
    }
    catch ( Exception e )
    {
      LOGGER.error( "Could not laod ContextManager" );
    }
    return contextManager;
  }

  public @Bean
  BbSessionManagerService bbSessionManagerService()
  {
    BbSessionManagerService bbSessionManagerService = null;
    try
    {
      bbSessionManagerService = BbSessionManagerServiceFactory.getInstance();
    }
    catch ( Exception e )
    {
      LOGGER.error( "Could not load BbSessionManagerService", e );
    }
    return bbSessionManagerService;
  }

  public @Bean
  CourseDbLoader courseDbLoader()
  {
    CourseDbLoader courseDbLoader = null;
    try
    {
      BbPersistenceManager pm = PersistenceServiceFactory.getInstance().getDbPersistenceManager();
      courseDbLoader = (CourseDbLoader) pm.getLoader( CourseDbLoader.TYPE );
    }
    catch ( Exception e )
    {
      LOGGER.error( "Can't get db loaders", e );
    }
    return courseDbLoader;
  }

  public @Bean
  UserDbLoader userDbLoader()
  {
    UserDbLoader userDbLoader = null;
    try
    {
      BbPersistenceManager pm = PersistenceServiceFactory.getInstance().getDbPersistenceManager();
      userDbLoader = (UserDbLoader) pm.getLoader( UserDbLoader.TYPE );
    }
    catch ( Exception e )
    {
      LOGGER.error( "Can't get db loaders", e );
    }
    return userDbLoader;
  }

  public @Bean
  CourseMembershipDbLoader courseMembershipDbLoader()
  {
    CourseMembershipDbLoader courseMembershipDbLoader = null;
    try
    {
      BbPersistenceManager pm = PersistenceServiceFactory.getInstance().getDbPersistenceManager();
      courseMembershipDbLoader = (CourseMembershipDbLoader) pm.getLoader( CourseMembershipDbLoader.TYPE );
    }
    catch ( Exception e )
    {
      LOGGER.error( "Can't get db loaders", e );
    }
    return courseMembershipDbLoader;
  }

  public @Bean
  BbPersistenceManager bbPersistenceManager()
  {
    BbPersistenceManager bbPersistenceManager = null;
    try
    {
      bbPersistenceManager = PersistenceServiceFactory.getInstance().getDbPersistenceManager();
    }
    catch ( Exception e )
    {
      LOGGER.error( "Could not load BbPersistenceManager" );
    }
    return bbPersistenceManager;
  }

  public @Bean
  AssignmentManager assignmentManager()
  {
    AssignmentManager assignmentManager = null;
    try
    {
      assignmentManager = AssignmentManagerFactory.getInstance();
    }
    catch ( Exception e )
    {
      LOGGER.error( "Could not load AssignmentManager", e );
    }
    return assignmentManager;
  }

  /**
   * A connection to the core blackboard database. This is particularly useful for API calls that perform heavy loads.
   * 
   * @return
   */
  public @Bean
  Connection blackboardConnection()
  {
    Connection blackboardConnection = null;
    try
    {
      blackboardConnection = ConnectionManager.getDefaultConnection();
    }
    catch ( Throwable t )
    {
      LOGGER.error( "Could not load connection to blackboard database", t );
    }
    return blackboardConnection;
  }

  /* Fix to "BeanEnhancer" issue suggested in https://jira.springsource.org/browse/SPR-7901 */
  @PreDestroy
  void preDestroy()
  {
    Enhancer.registerStaticCallbacks( this.getClass(), null );
  }
}
