package dataAccess;

import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import configuration.UtilDate;
import domain.Event;
import domain.Question;
import eredua.HibernateUtil;
import exceptions.QuestionAlreadyExist;
import exceptions.UserAlreadyExist;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess implements DataAccessInterface {

	private Session session;
//	ConfigXML c=ConfigXML.getInstance();

	public DataAccess(boolean initializeMode) {

		// System.out.println("Creating DataAccess instance => isDatabaseLocal:
		// "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

		open();

		/*
		 * if (initializeMode) initializeDB();
		 */
	}

	public DataAccess() {
//		 new DataAccess(false);

	}

	/**
	 * This is the data access method that initializes the database with some events
	 * and questions. This method is invoked by the business logic (constructor of
	 * BLFacadeImplementation) when the option "initialize" is declared in the tag
	 * dataBaseOpenMode of resources/config.xml file
	 */
	public void initializeDB() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			month += 1;
			int year = today.get(Calendar.YEAR);
			if (month == 12) {
				month = 0;
				year += 1;
			}
//
//			Event ev1 = new Event("Atlético-Athletic", UtilDate.newDate(year, month, 17));
//			Event ev2 = new Event("Eibar-Barcelona", UtilDate.newDate(year, month, 17));
//			Event ev3 = new Event("Getafe-Celta", UtilDate.newDate(year, month, 17));
//			Event ev4 = new Event("Alavés-Deportivo", UtilDate.newDate(year, month, 17));
//			Event ev5 = new Event("Español-Villareal", UtilDate.newDate(year, month, 17));
//			Event ev6 = new Event("Las Palmas-Sevilla", UtilDate.newDate(year, month, 17));
//			Event ev7 = new Event("Malaga-Valencia", UtilDate.newDate(year, month, 17));
//			Event ev8 = new Event("Girona-Leganés", UtilDate.newDate(year, month, 17));
//			Event ev9 = new Event("Real Sociedad-Levante", UtilDate.newDate(year, month, 17));
//			Event ev10 = new Event("Betis-Real Madrid", UtilDate.newDate(year, month, 17));
//
//			Event ev11 = new Event("Atletico-Athletic", UtilDate.newDate(year, month, 1));
//			Event ev12 = new Event("Eibar-Barcelona", UtilDate.newDate(year, month, 1));
//			Event ev13 = new Event("Getafe-Celta", UtilDate.newDate(year, month, 1));
//			Event ev14 = new Event("Alavés-Deportivo", UtilDate.newDate(year, month, 1));
//			Event ev15 = new Event("Español-Villareal", UtilDate.newDate(year, month, 1));
//			Event ev16 = new Event("Las Palmas-Sevilla", UtilDate.newDate(year, month, 1));
//
//			Event ev17 = new Event("Málaga-Valencia", UtilDate.newDate(year, month, 28));
//			Event ev18 = new Event("Girona-Leganés", UtilDate.newDate(year, month, 28));
//			Event ev19 = new Event("Real Sociedad-Levante", UtilDate.newDate(year, month, 28));
//			Event ev20 = new Event("Betis-Real Madrid", UtilDate.newDate(year, month, 28));
//
//			Question q1;
//			Question q2;
//			Question q3;
//			Question q4;
//			Question q5;
//			Question q6;
//
//			if (Locale.getDefault().equals(new Locale("es"))) {
//				q1 = ev1.addQuestion("¿Quién ganará el partido?", 1);
//				q2 = ev1.addQuestion("¿Quién meterá el primer gol?", 2);
//				q3 = ev11.addQuestion("¿Quién ganará el partido?", 1);
//				q4 = ev11.addQuestion("¿Cuántos goles se marcarán?", 2);
//				q5 = ev17.addQuestion("¿Quién ganará el partido?", 1);
//				q6 = ev17.addQuestion("¿Habrá goles en la primera parte?", 2);
//			} else if (Locale.getDefault().equals(new Locale("en"))) {
//				q1 = ev1.addQuestion("Who will win the match?", 1);
//				q2 = ev1.addQuestion("Who will score first?", 2);
//				q3 = ev11.addQuestion("Who will win the match?", 1);
//				q4 = ev11.addQuestion("How many goals will be scored in the match?", 2);
//				q5 = ev17.addQuestion("Who will win the match?", 1);
//				q6 = ev17.addQuestion("Will there be goals in the first half?", 2);
//			} else {
//				q1 = ev1.addQuestion("Zeinek irabaziko du partidua?", 1);
//				q2 = ev1.addQuestion("Zeinek sartuko du lehenengo gola?", 2);
//				q3 = ev11.addQuestion("Zeinek irabaziko du partidua?", 1);
//				q4 = ev11.addQuestion("Zenbat gol sartuko dira?", 2);
//				q5 = ev17.addQuestion("Zeinek irabaziko du partidua?", 1);
//				q6 = ev17.addQuestion("Golak sartuko dira lehenengo zatian?", 2);
//
//			}
//			session.persist(ev1);
//			session.persist(ev2);
//			session.persist(ev3);
//			session.persist(ev4);
//			session.persist(ev5);
//			session.persist(ev6);
//			session.persist(ev7);
//			session.persist(ev8);
//			session.persist(ev9);
//			session.persist(ev10);
//			session.persist(ev11);
//			session.persist(ev12);
//			session.persist(ev13);
//			session.persist(ev14);
//			session.persist(ev15);
//			session.persist(ev16);
//			session.persist(ev17);
//			session.persist(ev18);
//			session.persist(ev19);
//			session.persist(ev20);

			session.getTransaction().commit();
	        System.out.println("Db initialized");
		} catch (Exception e) {
	      
	        e.printStackTrace();
	    }
	}

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */
	public Question createQuestion(Event event, String question, float betMinimum) throws QuestionAlreadyExist {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
	
//		Event ev = (Event) session.get(Event.class, event.getEventNumber());
		Query a = session.createQuery("from Event ev WHERE ev.eventNumber= :eventNumber");
		a.setParameter("eventNumber", event.getEventNumber());
		Event ev= (Event) a.uniqueResult();
		System.out.println(ev+"fwojgbwwwwwwwwwww");
		
		if (ev.DoesQuestionExists(question))
			throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));

		
		Question q = ev.addQuestion(question, betMinimum);
		// db.persist(q);
		session.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added in questions
								// property of Event class
		// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		session.getTransaction().commit();
		return q;

	}

	/**
	 * This method retrieves from the database the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public List<Event> getEvents(Date date) {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		System.out.println(">> DataAccess: getEvents");
		List<Event> res = new ArrayList<Event>();
//		TypedQuery<Event> query = session.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1", Event.class);
//		query.setParameter(1, date);
		Query result = session.createQuery("from Event ev WHERE ev.eventDate= :date");
		result.setParameter("date", date);

		List<Event> events = result.list();
		for (Event ev : events) {
			System.out.println(ev.toString());
			res.add(ev);
		}
		session.getTransaction().commit();
		return res;
	}

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	public List<Date> getEventsMonth(Date date) {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		System.out.println(">> DataAccess: getEventsMonth");
		List<Date> res = new ArrayList<Date>();

		session.beginTransaction();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);

		// TypedQuery<Date> query = session.createQuery("SELECT DISTINCT ev.eventDate
		// FROM Event ev WHERE ev.eventDate BETWEEN ?1 and ?2",Date.class);
		Query result = session
				.createQuery("from Event ev where ev.evenDate BETWEEN :firstDayMonthDate and :lastDayMonthDate");
		result.setParameter("firstDayMonthDate", firstDayMonthDate);
		result.setParameter("lastDayMonthDate", lastDayMonthDate);
		List<Date> dates = result.list();
		for (Date d : dates) {
			System.out.println(d.toString());
			res.add(d);
		}
		return res;
	}

	@Override
	public void open() {

//		System.out.println("Opening DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
//				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());
//
//		String fileName = c.getDbFilename();
//
//		if (c.isDatabaseLocal()) {
//			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
//			db = emf.createEntityManager();
//		} else {
//			Map<String, String> properties = new HashMap<String, String>();
//			properties.put("javax.persistence.jdbc.user", c.getUser());
//			properties.put("javax.persistence.jdbc.password", c.getPassword());
//
//			emf = Persistence.createEntityManagerFactory(
//					"objectdb://" + c.getDatabaseNode() + ":" + c.getDatabasePort() + "/" + fileName, properties);
//
//			db = emf.createEntityManager();
//		}
		System.out.println("DBa irekitzen...");
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
		System.out.println("DBa irekita");
	}

	public boolean existQuestion(Event event, String question) {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		System.out.println(">> DataAccess: existQuestion=> event= " + event + " question= " + question);
		session.beginTransaction();

		Event ev = (Event) session.get(Event.class, event.getEventNumber());
		return ev.DoesQuestionExists(question);

	}

	public void close() {
		session.close();
		System.out.println("DataBase closed");
	    }

	@Override
	public void emptyDatabase() {

//		File f = new File(c.getDbFilename());
//		f.delete();
//		File f2 = new File(c.getDbFilename() + "$");
//		f2.delete();

	}

	@Override
	public List<Event> allEvent() {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		System.out.println("Showing all events");
		session.beginTransaction();
		Query query = session.createQuery("from Event ev");
	    return query.list();
	}

}
