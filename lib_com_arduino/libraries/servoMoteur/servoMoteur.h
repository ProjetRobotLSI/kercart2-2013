int numPinLeft(4),numPinRight(7);
int SpeedForward(0);
int SpeedBackward(180);
int SpeedStop(90);

Servo servoAngle;  
Servo servoSpeed;

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
*/
void moveBackward(int vitessePourcent){
	if(vitessePourcent > 0 && vitessePourcent <=100)
		move(90+45*vitessePourcent/100);
}

void moveForward(int vitessePourcent){
	if(vitessePourcent > 0 && vitessePourcent <=100)
		move(90-45*vitessePourcent/100);
}

/*
* fait varier langle des roues, 0=gauche,90=nul,180=droite
*/
void turn (int angleArd){
	servoAngle.write(angleArd);
	moveForward(25);
}
/* 
* tourner a gauche a 90degres selon mavlink, fait ecrire la valeur zero
*/
void turnLeft(int angleRasp){
	if(angleRasp > 0 && angleRasp <= 90)
		turn(90-45*angleRasp/90);
}
/* 
* tourner a droite a 90degres selon mavlink, fait ecrire la valeur 180
*/
void turnRight(int angleRasp){
	if(angleRasp > 0 && angleRasp <= 90)
		turn(90+45*angleRasp/90);
}

/* appele par STOP
* stoppe les roues, et reinitialise leurs angles
*/
void stop(){
	servoAngle.write(90);
	servoSpeed.write(90);
}


/*
* retourne l'etat courant de la vitesse des roues
* 0=MaxAvant,90=Stop,180=MaxArr
*/
int getStateSpeed(){
	return (servoSpeed.read());
}

int block(){
	return (getStateSpeed() == 90 );
}

/* appeler par SENDANGLE
* retourne l'etat courant de la vitesse des roues
* 0=MaxAvant,90=Stop,180=MaxArr
*/
int getStateAngle(){
	return (servoAngle.read());
}
