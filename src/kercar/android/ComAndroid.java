package kercar.android;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import kercar.comAPI.GETStateMessage;
import kercar.comAPI.IRawMessage;
import kercar.comAPI.IStateMessage;
import kercar.comAPI.Message;
import kercar.comAPI.StateMessage;
import kercar.comAPI.json.JSONParser;

/**
 * Gestion de l'envoi de message depuis Android
 * @author itooh
 */
public class ComAndroid implements IComAndroid {
	
	/**
	 * Adresse URL du Raspberry
	 */
	private URL adresseRaspberry;
	
	/**
	 * Lien de connexion au Raspberry
	 */
	private HttpURLConnection con;
	
	/**
	 * Le nombre de paquets envoyés jusque ici
	 */
	private int numPackets = 0;
	
	/**
	 * Le manager (singleton) de communication côté Android
	 */
	private static ComAndroid instance;
	
	private static final int wait = 300000;
	
	private ComAndroid(){
		
	}
	
	/**
	 * Récupération d'une instance (singleton)
	 */
	public static ComAndroid getManager(){
		if(instance == null){
			instance = new ComAndroid();
		}
		return instance;
	}

	/**
	 * Définit l'adresse URL du Rapsberry
	 * @param La nouvelle adresse du Raspberry
	 */
	@Override
	public void setURL(String address) {
		try {
			this.adresseRaspberry = new URL(address);
		} catch (MalformedURLException e) {
			System.err.println("Mauvais format URL");
			e.printStackTrace();
		}
	}
	
	/**
	 * Envoie un message au Raspberry
	 * @param message Le message a envoyer
	 */
	@Override
	public void envoyerMessage(Message message) throws Exception{
		if(this.adresseRaspberry == null){
			throw new Exception("Aucune URL de définie !");
		}
		
		try {
			con = (HttpURLConnection)this.adresseRaspberry.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestMethod("POST");
			BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
			message.setMessageNum(this.numPackets++);
			System.out.println(this.adresseRaspberry);
			System.out.println("message="+message.toString());
			bf.write("message="+message.toString());
			bf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Lit la réponse à un message
	 */
	@Override
	public String lireReponse() throws Exception {
		if(con == null){
			throw new Exception("Aucune connexion ouverte");
		}
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String temp;
			StringBuilder content = new StringBuilder();
			while( (temp = br.readLine()) != null ){
				content.append(temp);
			}
			br.close();
			return content.toString();
		} catch(Exception e){
			System.err.println("Impossible de lire la réponse");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Demande de l'état du robot au Raspberry
	 * @return Message contenant les différentes informations sur le robot
	 */
	public IStateMessage demanderEtat() throws Exception {
		GETStateMessage demande = new GETStateMessage();
		
		this.envoyerMessage(demande);
		String reponse = this.lireReponse();
		
		IRawMessage message = JSONParser.decode(reponse);
		IStateMessage etat = new StateMessage((Message) message);
		
		return etat;
	}
}
