package kercar.raspberry.test;

import java.util.LinkedList;
import java.util.List;

import kercar.comAPI.CMDMissionMessage;
import kercar.comAPI.CMDMoveMessage;
import kercar.comAPI.CMDStopMessage;
import kercar.comAPI.CMDTurnMessage;
import kercar.raspberry.core.Core;

public class TestIA {
	
	public static void main(String args[]) {
		
		System.out.println(testCalculAngle(7, 4, 3, 1, 45));
		System.out.println(testCalculAngle(2, 4, 6, 1, 106));
		System.out.println(testCalculAngle(1, 1, 4, 2, 23));
		System.out.println(testCalculAngle(1, 4, 4, 1, 295));
		System.out.println(testCalculAngle(4, 5, 2, 1, 295));
		System.out.println(testCalculAngle(5, 0, 2, 1, 315));
		
		System.out.println(toGPSCompatibleData(301043242));
		
		/*
		 * Commenter les lignes concernant le serial manager pour les tests dans Core
		 * Décommenter updateAnglet, goToNextPoint et isArrived dans Core pour ne pas utiliser les valeurs de l'arduino
		 */
		/*
		Core core = new Core(".");
		core.start();
		
		core.messageReceived(new CMDStopMessage());
		CMDMoveMessage moveBackward = new CMDMoveMessage(50, true);
		core.messageReceived(moveBackward);
		CMDMoveMessage moveForward = new CMDMoveMessage(50, false);
		core.messageReceived(moveForward);
		CMDTurnMessage turnLeft = new CMDTurnMessage(false);
		core.messageReceived(turnLeft);
		CMDTurnMessage turnRight = new CMDTurnMessage(true);
		core.messageReceived(turnRight);
		
		List<Integer> list = new LinkedList<Integer>();
		list.add(0);
		list.add(0);
		CMDMissionMessage mission = new CMDMissionMessage(list, false, false, "miaou");
		core.messageReceived(mission);
		core.messageReceived(new CMDStopMessage());
		
		list.clear();
		list.add(1);
		list.add(1);
		list.add(0);
		list.add(0);
		mission = new CMDMissionMessage(list, false, false, "miaou");
		core.messageReceived(mission);*/
		
	}
	
	private static double toGPSCompatibleData(int data) {
		//2 chiffres les plus à gauche : degré
		//2 suivant : minutes
		//4 suivant : décimales minutes
		//le plus à droite : orientation : N = O, S = 1, E = 2, W = 3
		
		// calc DD=Degré+min/60+sec/3600
		/*301043242
		
		30 = degré
		10,4324 = minutes
		2 = E*/
		
		String tmp = String.valueOf(data);
		double degree = Integer.parseInt(tmp.substring(0, 2));
		double minutes = Integer.parseInt(tmp.substring(2, 4));
		double sec = Integer.parseInt(tmp.substring(4, 9));
		
		double result = degree + (minutes / 60.0) + (sec / 3600.0); 
		
		//int degree
		
		return result;
	}
	
	public static int testCalculAngle(int pointLongitude, int pointLatitude, int gpsLongitude, int gpsLatitude, int compass) {
		
		//Calcul de a²
		double a = Math.pow(pointLongitude - gpsLongitude, 2) + Math.pow(pointLatitude - gpsLatitude, 2);
		System.out.println("a " + a);
		
		//Calcul de b²
		int YNord = gpsLatitude + 5;
		int XNord = gpsLongitude;
		double b = Math.pow(XNord - gpsLongitude, 2) + Math.pow(YNord - gpsLatitude, 2);
		System.out.println("c " + b);
		
		//Calcul de c²	
		double c = Math.pow(pointLongitude - XNord, 2) + Math.pow(pointLatitude - YNord, 2);
		System.out.println("b " + c);
		
		//Al kashi
		//TODO Attention si a +b  > c à voir si BOUMBOUM
		double angleBetaRadian =  Math.acos(Math.abs(a + b - c) / (2 * Math.sqrt(a) * Math.sqrt(b)));
		int angleBeta = (int) ((180 * angleBetaRadian) / Math.PI);
		System.out.println("angleBeta " + angleBeta);
		
		if(compass <= 180) {
			if(pointLongitude >= XNord)
				return compass + angleBeta;
			else if (angleBeta >= compass)
				return 360 - (angleBeta - compass);
			else
				return compass - angleBeta;
		} else {
			compass = 360 - compass;
			if(pointLongitude <= XNord)
				return 360 - (compass + angleBeta);
			else if (angleBeta >= compass)
				return angleBeta - compass;
			else
				return 360 - (compass - angleBeta);
		}
		
	}
}
