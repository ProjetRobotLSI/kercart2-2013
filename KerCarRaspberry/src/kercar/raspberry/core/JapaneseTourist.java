package kercar.raspberry.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Watashi wa, nihonjin kankōkyakudesu
 * Watashi ga shashin o totte, messēji o sōshin suru koto ga dekimasu
 * @author kid
 *
 */
public class JapaneseTourist {
	
	private static int id = 0;

	public static void takePhoto(){
		System.out.println("Kawaii desu ne ?");
		ProcessBuilder pb = new ProcessBuilder("fswebcam", "-r 640x480 ~/photo_"+String.valueOf(id++));
		try {
			Process p = pb.start();
			p.waitFor();
			getProcessOutput(p);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Yameteeeeeeee!");
			e.printStackTrace();
		}
	}
	
	public static void clearPhotos(){
		System.out.println("Suppression des photos");
		ProcessBuilder pb = new ProcessBuilder("rm", "/home/photo_*.jpg");
		try {
			Process p = pb.start();
			p.waitFor();
			getProcessOutput(p);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Yameteeeeeeee!");
			e.printStackTrace();
		}
	}
	
	public static void sendPhotos(){
		System.out.println("Envoi des photos");
		ProcessBuilder pb = new ProcessBuilder("/opt/apache-tomcat-7.0.35/sendPhotos.sh");
		try {
			Process p = pb.start();
			p.waitFor();
			getProcessOutput(p);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Yameteeeeeeee!");
			e.printStackTrace();
		}
	}
	
	public static void sendLogs(){
		System.out.println("Envoi des logs");
		ProcessBuilder pb = new ProcessBuilder("/opt/apache-tomcat-7.0.35/sendLogs.sh");
		try {
			Process p = pb.start();
			p.waitFor();
			getProcessOutput(p);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Yameteeeeeeee!");
			e.printStackTrace();
		}
	}
	
    private static String getProcessOutput(Process p){
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String tmp;
        StringBuffer bf = new StringBuffer();

        try{
                while( (tmp = br.readLine()) != null ){
                        bf.append(tmp+System.getProperty("line.separator"));
                }
                br.close();
        } catch(Exception e){
                e.printStackTrace();
        }
        System.out.println(bf.toString());
        return bf.toString();
}

}
