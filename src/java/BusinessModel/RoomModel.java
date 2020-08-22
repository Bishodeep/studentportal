package BusinessModel;

public class RoomModel {
	 protected int id;
	 protected int typeid;
	    protected String typename;
	    protected Boolean available;
	    protected String location;
	    protected String description;
	    protected int monthlycharge;
	    
	    public RoomModel(int id,int type,String typename,Boolean available,String location, int monthlycharge,String description) {
	    	this.typename=typename;
	    	this.available=available;
	    	this.id=id;
	    	this.typeid=type;
	    	this.location=location;
	    	this.monthlycharge=monthlycharge;
	    	this.description=description;
	    }
	    public RoomModel(int type,String typename,Boolean available,String location, int monthlycharge,String description) {
	    	this.typename=typename;
	    	this.available=available;
	    	this.typeid=type;
	    	this.location=location;
	    	this.monthlycharge=monthlycharge;
	    	this.description=description;
	    }
	    
	    public RoomModel(int type,Boolean available,String location, int monthlycharge,String description) {
	    	
	    	this.available=available;
	    	this.typeid=type;
	    	this.location=location;
	    	this.monthlycharge=monthlycharge;
	    	this.description=description;
	    }
 public RoomModel(int id,int type,Boolean available,String location, int monthlycharge,String description) {
	    	
	    	this.available=available;
	    	this.typeid=type;
	    	this.location=location;
	    	this.monthlycharge=monthlycharge;
	    	this.description=description;
	    	this.id=id;
	    }
	    
	    public int getId() {
	        return id;
	    }
	 
	    public void setId(int id) {
	        this.id = id;
	    }
	    public int getTypeID() {
	        return typeid;
	    }
	 
	    public void setTypeId(int typeid) {
	        this.typeid = typeid;
	    }
	    public int getMonthlyCharge() {
	        return monthlycharge;
	    }
	 
	    public void setMonthlyCharge(int charge) {
	        this.monthlycharge = charge;
	    }
	    
	    public String getTypeName() {
	        return typename;
	    }
	 
	    public void setTypeName(String name) {
	        this.typename = name;
	    }
	    public String getLocation() {
	        return location;
	    }
	 
	    public void setLocation(String location) {
	        this.location = location;
	    }
	    public Boolean getAvailable() {
	        return available;
	    }
	 
	    public void setAvailable(Boolean available) {
	        this.available = available;
	    }
	    public String getDescription() {
	        return description;
	    }
	 
	    public void setDescription(String description) {
	        this.description = description;
	    }
}
