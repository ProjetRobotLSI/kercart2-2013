#include <stdlib.h>
#include <stdio.h>

//Variables
#define NB_PARAM 2
#define SIZE_PARAM 4
#define NB_OCTETS (1 + (NB_PARAM * SIZE_PARAM))

//Functions
#define STOP 1
#define FORWARD 2
#define BACKWARD 3
#define LEFT 4
#define RIGHT 5
#define GOTOPOS 6
#define GETPOS 7
#define GETANGLE 8
#define GETGPSINFO 9

#define BLOCK 10
#define SENDPOS 11
#define SENDANGLE 12
#define ARRIVED 13
#define SENDGPSINFO 14
#define STOPTURN 16

Compass compass;

typedef union
{
	long entier;
	char octets[4];
}longToChar;

typedef struct
{
	char id;
	longToChar param1;
	longToChar param2;
} message;

static inline void turnScrutation(char sens, int angleAtourner)
{
	int orientationCourante, orientationFinale;
	compass.sendOrder('A');
	delay(10);
	orientationCourante = compass.RetrieveValueNumeric();
	//calcul modulo de l'angle que l'on veut obtenir
	if(sens == 'L')
		orientationFinale = (360 +  orientationCourante - angleAtourner) % 360;
	else
		orientationFinale = (orientationCourante + angleAtourner) % 360;
	if(sens == 'L')
			servoMoteur_turnLeft(30);
		else
			servoMoteur_turnRight(30);
	do{
		compass.sendOrder('A');
		delay(10);
		orientationCourante = compass.RetrieveValueNumeric();
		if(sens == 'L')
		{
			if(orientationCourante < orientationFinale && orientationCourante > 0)
				orientationCourante += 360;
		}
		else
		{
			if(orientationCourante < 360 && orientationCourante > orientationFinale)
				orientationCourante -= 360;
		}
	}while((orientationCourante >= orientationFinale && sens == 'L') || (orientationCourante <= orientationFinale && sens == 'R'));
	servoMoteur_stop();
}

/*
* Envoie un message
*/
static inline char* send_order(int id)
{
	char* message;
	message = (char*)malloc(NB_OCTETS);
	
	//Initialisation du message
	int i, valCompass;
	for(i = 0; i < NB_OCTETS; i++)
		message[i] = 0;
	
	switch(id)
	{
		case BLOCK :
			message[0] = BLOCK;
			break;
		case GETPOS :
			message[0] = SENDPOS;
			//ToDo : récupérer valeur GPS
			break;
		case GETANGLE:
			compass.sendOrder('A');
			delay(10);
			message[0] = SENDANGLE;
			valCompass = compass.RetrieveValueNumeric();
			for(i=0;i<SIZE_PARAM;i++)
			{
				message[i+1] = (char)(*(&valCompass)+i);
			}
			break;
		case ARRIVED :
			message[0] = ARRIVED;
			break;
		case GETGPSINFO :
			message[0] = SENDGPSINFO;
			//ToDO : récupérer info gps
			break;
		case STOPTURN:
			message[0] = STOPTURN;
			break;
	}
	return message;//ToDo : faire free du malloc
}

/*
* Appelle la fonction correspondant à l'ID
*/
static inline char* call_order(message* msg)
{
	switch(msg->id)
	{
		case STOP : 
			servoMoteur_stop();
			break;
		case FORWARD :
			servoMoteur_moveForward((int) (msg->param1.entier));
			break;
		case BACKWARD :
			servoMoteur_moveBackward((int) (msg->param1.entier));
			break;
		case LEFT :
			turnScrutation('L', (int) (msg->param1.entier));
			return(send_order(STOPTURN));
			break;
		case RIGHT :
			turnScrutation('R', (int) (msg->param1.entier));
			return(send_order(STOPTURN));
			break;
		case GOTOPOS :
			//TODO
			break;
		case GETPOS :
			return(send_order(msg->id));
			break;
		case GETANGLE :
			return(send_order(msg->id));
			break;
		case GETGPSINFO :
			return(send_order(msg->id));
			break;
	}
	return NULL;
}