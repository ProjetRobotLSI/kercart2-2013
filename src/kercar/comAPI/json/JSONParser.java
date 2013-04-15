package kercar.comAPI.json;

import java.util.Iterator;

import kercar.comAPI.IRawMessage;
import kercar.comAPI.Message;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONParser {

	/**
	 * Encode un message en JSON
	 * @param m Le message a encoder
	 * @return Le message sous forme de string JSON
	 */
	public static String encode(IRawMessage m){
		JSONObject jso = new JSONObject();
		jso.put("id", m.getMessageNum());
		jso.put("type", m.getType());
		
		JSONArray jsa = new JSONArray();
		Iterator<String> it = m.getParams().iterator();
		while(it.hasNext()){
			jsa.put(it.next());
		}
		jso.put("params", jsa);
		
		return jso.toString();
	}
	
	/**
	 * Decode un message JSON en message !!!!
	 * @param s Le string JSON a decoder
	 * @return Un objet de type AbstractMessage
	 */
	public static IRawMessage decode(String s){
		JSONObject jso = new JSONObject(s);
		Message res = new Message(jso.getInt("type"));
		res.setMessageNum(jso.getInt("id"));
		JSONArray jsa = jso.getJSONArray("params");
		for(int i = 0; i < jsa.length(); i++){
			res.addParam(jsa.getString(i));
		}
		return res;
	}
}
