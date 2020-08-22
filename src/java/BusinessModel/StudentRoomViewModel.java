/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessModel;

/**
 *
 * @author Bishodeep
 */
public class StudentRoomViewModel {
    protected Boolean payment;
	    protected String name;
	    protected String description;
            protected String roomType;
            
             public StudentRoomViewModel(String name,String description,String roomType,boolean payment) {
	    	this.name=name;
                this.description=description;
                this.roomType=roomType;
	    	this.payment=payment;
	    }
              
	    public String getName() {
	        return name;
	    }
	 
	    public void setName(String name) {
	        this.name = name;
	    }
             
	    public String getDescription() {
	        return description;
	    }
	 
	    public void setDescription(String description) {
	        this.description = description;
	    }
             
	    public String getRoomType() {
	        return roomType;
	    }
	 
	    public void setRoomType(String type) {
	        this.roomType= type;
	    }
             
	    public boolean getPayment() {
	        return payment;
	    }
	 
	    public void setPayment(boolean payment) {
	        this.payment = payment;
	    }
}
