package BusinessModel;

public class RoomTypeModel {
	 protected int id;
	    protected String typename;
	    protected int available;
	    public RoomTypeModel(int id,String typename,int available) {
	    	this.typename=typename;
	    	this.available=available;
	    	this.id=id;
	    }
	    public RoomTypeModel(String typename,int available) {
	    	this.typename=typename;
	    	this.available=available;
	    }
	    public int getId() {
	        return id;
	    }
	 
	    public void setId(int id) {
	        this.id = id;
	    }
	    public String getType() {
	        return typename;
	    }
	 
	    public void setName(String name) {
	        this.typename = name;
	    }
	    public int getAvailable() {
	        return available;
	    }
	 
	    public void setAvailable(int available) {
	        this.available = available;
	    }
}
