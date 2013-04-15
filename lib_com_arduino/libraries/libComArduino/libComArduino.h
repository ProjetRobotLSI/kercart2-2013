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

/*
* Envoie un message
*/
static inline char* send_order(int id)
{
	char* message;
	longToChar test;
	message = (char*)malloc(NB_OCTETS);
	
	//Initialisation du message
	int i;
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
			message[0] = SENDANGLE;
			//ToDo : récupérer infos boussole
			break;
		case ARRIVED :
			message[0] = ARRIVED;
			break;
		case GETGPSINFO :
			message[0] = SENDGPSINFO;
			//ToDO : récupérer info gps
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
			stop();
			break;
		case FORWARD :
			moveForward((int) (msg->param1.entier));
			break;
		case BACKWARD :
			moveBackward((int) (msg->param1.entier));
			break;
		case LEFT :
			turnLeft((int) (msg->param1.entier));
			break;
		case RIGHT :
			turnRight((int) (msg->param1.entier));
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
