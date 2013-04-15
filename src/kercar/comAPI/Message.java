package kercar.comAPI;

import java.util.ArrayList;
import java.util.List;

import kercar.comAPI.json.JSONParser;

/**
 * Class representing a 
 * @author Kid
 *
 */
public class Message implements IRawMessage, IMessage {
	
	public static final int COM_WIFI = -1;
	public static final int COM_BLUETOOTH = -2;

	public static final int CMD_ERROR = 0;
	public static final int CMD_STOP = 1;
	public static final int CMD_MOVE = 2;
	public static final int CMD_TURN = 3;
	public static final int CMD_PHOTO = 4;
	
	//private int com;
	private final int type;
	protected List<String> params;
	
	/**
	 * Le numero du Message niveau packet
	 */
	private int numMessage;
	
	
	public Message(int type){
		this.type = type;
		this.params = new ArrayList<String>();
	}
	
	public int getType(){
		return this.type;
	}
	
	public List<String> getParams(){
		return this.params;
	}
	
	public void addParam(String param){
		this.params.add(param);
	}
	
	public int getMessageNum() {
		return this.numMessage;
	}
	
	public void setMessageNum(int num){
		this.numMessage = num;
	}
	
	public String toString(){
		return JSONParser.encode(this);
	}
}
