package kercar.raspberry;

import kercar.comAPI.IMessage;
import kercar.comAPI.json.JSONParser;

import org.json.JSONObject;

public class ComRaspberry implements IComRaspberry {

	@Override
	public void recevoirMessage(String message) {
		
		// cr�ation de JSon  et le decoder en IMessage
		JSONParser jso = new JSONParser();
		IMessage m=jso.decode(message);
		
		// appel d'une fonction pour transmette IMessage � Raspberry
		// TODO
	
	}

	@Override
	public String demandeInformation(String message) {
		// TODO Auto-generated method stub
		return null;
	}

}
