//#include <Servo.h>

// declaration & initialisation des variables
//TX envoie, RX recoit
//TX
int numPinLeft(4),numPinRight(7);
int SpeedForward(0);
int SpeedBackward(180);
int SpeedStop(90);

// create servo object to control a servo 
Servo servoAngle;  
Servo servoSpeed;
/*
// initialisation de la bete
void setup() {
  Serial.begin(9600);
  servoAngle.attach(numPinLeft);  //the pin for the servo control 
  servoSpeed.attach(numPinRight);
}

void loop () {


  Serial.println("avance tout droit pendant une seconde");
	servoSpeed.write(45);
  delay(1000);
  Serial.println("recule tout droit pendant une seconde");
  	servoSpeed.write(135);
  delay(1000);
  Serial.println("avance vers la droite pendant une seconde");
  	servoSpeed.write(45);
  	servoAngle.write(135);
  delay(1000);
  Serial.println("avance vers la gauche pendant une seconde");
  	servoSpeed.write(45);
  	servoAngle.write(45);
  delay(1000);
  stop();
  

  Serial.println("avance tout droit pendant une seconde");
  moveForward(100);
  delay(1000);
  Serial.println("recule tout droit pendant une seconde");
  moveBackward(100);
  delay(1000);
  Serial.println("avance vers la droite pendant une seconde");
  turnRight(90);
  moveForward(100);
  delay(1000);
  Serial.println("avance vers la gauche pendant une seconde");
  turnLeft(90);
  moveForward(100);
  delay(1000);
  stop();

}*/
/* envoie la commande avancer/reculer
* @ param 	vitesseDegres compris en 0 et 180
* 			0 = Avant , 90 = arret, 180 = marche arriere
*			
*/
void move (int vitesseDegres){
	servoSpeed.write(vitesseDegres);
}

/*
* @param vitessePourcent compris 0.0 et 1.0 selon la puissance que l'on veut
* 90 = arret, 180 
* 
*/
void moveBackward(int vitessePourcent){
	move(90+45*vitessePourcent/100);
	//move(135);
}

void moveForward(int vitessePourcent){
	move(90-45*vitessePourcent/100);
	//move(45);
	}

/*
* fait varier langle des roues, 0=gauche,90=nul,180=droite
*/
void turn (int angleArd){
	servoAngle.write(angleArd);
}
/* 
* tourner a gauche a 90degres selon mavlink, fait ecrire la valeur zero
*/
void turnLeft(int angleRasp){
	turn(90-45*angleRasp/90);
	//turn(45);
}
/* 
* tourner a droite a 90degres selon mavlink, fait ecrire la valeur 180
*/
void turnRight(int angleRasp){
	turn(90+45*angleRasp/90);
	//turn(135);
}

/* appele par STOP
* stoppe les roues, et reinitialise leurs angles
*/
void stop(){
	servoAngle.write(90);
	servoSpeed.write(90);
}

int block(){
	return (getStateSpeed() == 90 );
}

/*
* retourne l'etat courant de la vitesse des roues
* 0=MaxAvant,90=Stop,180=MaxArr
*/
int getStateSpeed(){
	return (servoSpeed.read());
}

/* appeler par SENDANGLE
* retourne l'etat courant de la vitesse des roues
* 0=MaxAvant,90=Stop,180=MaxArr
*/
int getStateAngle(){
	return (servoAngle.read());
}

