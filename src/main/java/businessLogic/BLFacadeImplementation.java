package businessLogic;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import dataAccess.DataAccessInterface;
import domain.Question;
import domain.Event;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the business logic as a web service.
 */
public class BLFacadeImplementation  implements BLFacade {
	DataAccessInterface dbManager;

	public BLFacadeImplementation() {
		System.out.println("Creating BLFacadeImplementation instance");
	}

	public BLFacadeImplementation(DataAccessInterface da) {
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		// No deberías inicializar la base de datos aquí, ya que podría ser llamada múltiples veces.
		// Deja la inicialización de la base de datos para un lugar más apropiado, como un método de inicialización.
		dbManager = da;
		dbManager.initializeDB();
	}
	

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished if current data is after data of the event
 	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
   public Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist{
	   
	    //The minimum bed must be greater than 0
		//dbManager.open();
		Question qry=null;
		
	    
		if(new Date().compareTo(event.getEventDate())>0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));
				
		
		 qry=dbManager.createQuestion(event,question,betMinimum);		

		//dbManager.close();
		
		return qry;
   };
	
	/**
	 * This method invokes the data access to retrieve the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public List<Event> getEvents(Date date)  {
		//dbManager.open();
		List<Event>  events=dbManager.getEvents(date);
		//dbManager.close();
		return events;
	}

    
	/**
	 * This method invokes the data access to retrieve the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	public List<Date> getEventsMonth(Date date) {
		//dbManager.open();
		List<Date>  dates=dbManager.getEventsMonth(date);
		//dbManager.close();
		return dates;
	}
	
	
	public void close() {
		//DataAccess dB4oManager=new DataAccess(false);

		//dB4oManager.close();
		//dbManager.close();

	}

	/**
	 * This method invokes the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	 public void initializeBD(){
    	//dbManager.open();
		dbManager.initializeDB();
		//dbManager.close();
	}

	@Override
	public List<Event> allEvent() {
		// TODO Auto-generated method stub
		return dbManager.allEvent();
	}
}