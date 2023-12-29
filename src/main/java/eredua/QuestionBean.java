package eredua;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.SelectEvent;

import businessLogic.BLFacade;
import domain.Event;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

@ManagedBean
@SessionScoped
public class QuestionBean implements Serializable {
	private Event event;
	private List<Event> eventList=new ArrayList<Event>();
	private String questionDesctiption;
	private float minimumBet;
	private Date eguna;
	private Event selectedEvent;
	
	public QuestionBean() {
//		this.allEvent();
	}
	public Date getEguna() {
		return eguna;
	}
	public void setEguna(Date eguna) {
		this.eguna = eguna;
		System.out.println(eguna);
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public List<Event> getEventList() {
		return eventList;
	}
	public Event getSelectedEvent() {
	    return selectedEvent;
	}

	public void setSelectedEvent(Event selectedEvent) {
	    this.selectedEvent = selectedEvent;
	}
	public void setEventList(List<Event> evenList) {
		this.eventList = evenList;
	}
	public void getEvents() {
		BLFacade facadeBL=FacadeBean.getBusinessLogic();
		eventList=facadeBL.getEvents(eguna);
	}
	public String getQuestionDesctiption() {
		return questionDesctiption;
	}

	public void setQuestionDesctiption(String questionDesctiption) {
		this.questionDesctiption = questionDesctiption;
	}

	public float getMinimumBet() {
		return minimumBet;
	}

	public void setMinimumBet(float minimumBet) {
		this.minimumBet = minimumBet;
	}

	public void onDateSelect(SelectEvent event) {
	    BLFacade facadeBL = FacadeBean.getBusinessLogic();
	    this.eguna = (Date) event.getObject();
	    getEvents(); 
	}

	public void onEventSelect(SelectEvent event) {
		this.event = (Event) event.getObject();
		System.out.println(this.event);
		BLFacade facadeBL=FacadeBean.getBusinessLogic();
		eventList=facadeBL.getEvents(this.eguna);
		System.out.println(event.toString());
	}
	public void addQuestion() {
	    if (event == null) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Errorea: gertaera bat aukeratu behar da"));
	    } else if (this.questionDesctiption.isEmpty()) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Errorea: deskribapen bat eman!"));
	    } else if (this.minimumBet <= 0) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Errorea: minimoak 0 baino handiagoa izan behar du"));
	    } else {
	        BLFacade facadeBL = FacadeBean.getBusinessLogic();
	        try {
	            facadeBL.createQuestion(this.event, this.questionDesctiption, this.minimumBet);
	            facadeBL = FacadeBean.getBusinessLogic();
	            eventList = facadeBL.getEvents(this.eguna);
	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sortu da galdera"));
	        } catch (EventFinished e) {
	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Errorea: Gertaera amaituta dago"));
	        } catch (QuestionAlreadyExist e) {
	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Errorea: Galdera jadanik existitzen da gertaeran"));
	        } catch (Exception e) {
	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Errorea: Something went wrong"));
	        }
	    }
	}
	public void allEvent() {
		BLFacade facadeBL = FacadeBean.getBusinessLogic();
		this.eventList=facadeBL.allEvent();
	}

	public Date getEgunaFromEventList() {
	    if (eventList != null && !eventList.isEmpty()) {
	        return eventList.get(0).getEventDate();
	    }
	    return null;
	}
	public void showEventDescription() {
	    if (this.event != null) {
	        this.selectedEvent = this.event;
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Event Description: " + this.event.getDescription()));
	    } else {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error: Select an event first."));
	    }
	}

	/*
	 * Dena hasieratu (Baliteke orain ez izatea guztiz beharrezkoa)
	 */
	public void restart() {
		eguna=null;
		event=null;
		eventList=null;
	}
	public String toQuery() {
		restart();
		return "query";
	}
	public String toCreateQuestion() {
		restart();
		return "create";
	}
}
