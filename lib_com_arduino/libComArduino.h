#include "servoMoteur.h"	

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

typedef struct  
{
	int id;
	long param1;
	long param2;	
} message;

/*
* Envoie un message
*/
static inline message* send_order(int id)
{

	return NULL;
}

/*
* Appelle la fonction correspondant à l'ID
*/
static inline void call_order(message* msg)
{
	switch(msg->id)
	{
		case STOP : 
			stop();
			break;
		case FORWARD :
			moveForward(msg->param1);
			break;
		case BACKWARD :
			moveBackward(msg->param1);
			break;
		case LEFT :
			turnLeft(msg->param1);
			break;
		case RIGHT :
			turnRight(msg->param2);
			break;
		case GOTOPOS :
			//TODO
			break;
		case GETPOS :
			send_order(msg->id);
			break;
		case GETANGLE :
			send_order(msg->id);
			break;
		case GETGPSINFO :
			//TODO
			break;
	}
}