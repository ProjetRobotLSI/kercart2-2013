package kercar.comAPI;

public class CMDStopMessage extends Message implements ICMDStopMessage {

	public CMDStopMessage() {
		super(Message.CMD_STOP);
	}
	
	public CMDStopMessage(Message m){
		super(Message.CMD_STOP);
		this.params = m.getParams();
	}

}
