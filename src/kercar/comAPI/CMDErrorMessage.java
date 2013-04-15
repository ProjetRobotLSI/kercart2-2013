package kercar.comAPI;

public class CMDErrorMessage extends Message implements ICMDErrorMessage {

	public static final int INDEX_NUMPACKAGE = 0;
	
	public CMDErrorMessage(int numPackage) {
		super(Message.CMD_ERROR);
		
		this.params.add(INDEX_NUMPACKAGE, Integer.toString(numPackage));
	}
	
	public CMDErrorMessage(Message m){
		super(Message.CMD_ERROR);
		this.params = m.getParams();
	}

	@Override
	public int getNumPackage() {
		return Integer.parseInt(this.params.get(INDEX_NUMPACKAGE));
	}

	@Override
	public void setNumPackage(int num) {
		this.params.set(INDEX_NUMPACKAGE, Integer.toString(num));
	}

}
