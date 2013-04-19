int numPinLeft(4),numPinRight(7);
int SpeedForward(0);
int SpeedBackward(180);
int SpeedStop(90);

Servo servoAngle;  
Servo servoSpeed;
boolean forward = true; //definit le sens de la marche, 1=av,0,=aR

/*
* applique la commande avancer/reculer
* @ param vitesseDegres : compris en 0 et 180
* 			0 = Avant , 90 = arret, 180 = marche arriere
*			
*/
void move (int vitesseDegres){
	servoSpeed.write(vitesseDegres);
}

/*
* Fait reculer le robot à la vitesse vitessePourcent
* @param 0 < vitessePourcent <= 100
*/
void moveBackward(int vitessePourcent){
	if(vitessePourcent >= 0 && vitessePourcent <=100)
	{
		move(90+45*vitessePourcent/100);
		forward = false;
	}
}

/*
* Fait avancer le robot à la vitesse vitessePourcent
* @param 0 < vitessePourcent <= 100
*/
void moveForward(int vitessePourcent){
	if(vitessePourcent >= 0 && vitessePourcent <=100)
	{
		move(90-45*vitessePourcent/100);
		forward = true;
	}
}

/*
* fait tourner le robot
* @param angleArd : 0=gauche,90=nul,180=droite
*/
void turn (int angleArd){
	servoAngle.write(angleArd);
	if (forward)
	{
		moveForward(25);
	}
	else
	{
		moveBackward(25);
	}
}
/* 
* Fait tourner le robot à gauche de angleRasp degrés
* @param angleRasp : 0 < angleRasp <= 90
*/
void turnLeft(int angleRasp){
	if(angleRasp > 0 && angleRasp <= 90)
		turn(90-45*angleRasp/90);
}
/* 
* Fait tourner le robot à droite de angleRasp degrés
* @param angleRasp : 0 < angleRasp <= 90
*/
void turnRight(int angleRasp){
	if(angleRasp > 0 && angleRasp <= 90)
		turn(90+45*angleRasp/90);
}

/*
* stop le robot et reinitialise les angles appliqués aux moteurs
*/
void stop(){
	servoAngle.write(90);
	servoSpeed.write(90);
}

/************************************************/
/*		Fonctions d'état		*/
/************************************************/

/*
* retourne l'etat courant de la vitesse des roues
* 0=MaxAvant,90=Stop,180=MaxArr
*/
int getStateSpeed(){
	return (servoSpeed.read());
}

/*
* vérifie si le robot est arrêté
* @return : 1 si arrêté, 0 sinon 
*/
int block(){
	return (getStateSpeed() == 90 );
}

/*
* retourne l'etat courant de la vitesse des roues
* @return : 0=gauche,90=nul,180=droite
*/
int getStateAngle(){
	return (servoAngle.read());
}