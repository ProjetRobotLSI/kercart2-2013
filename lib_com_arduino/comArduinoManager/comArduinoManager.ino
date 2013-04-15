#include <Servo.h>
#include <servoMoteur.h>
#include <libComArduino.h>

// initialisation de la bete
void setup() {
  Serial.begin(9600);
  servoAngle.attach(numPinLeft);  //the pin for the servo control 
  servoSpeed.attach(numPinRight);
}

void loop(){
    int i;
    //on attend que le buffer est le bon nombre d'octets (taille d'un message)
    if(Serial.available() == NB_OCTETS)
    {
        message msgRecu;
        char* msgAenvoyer;
        msgRecu.id = Serial.read();
        for(i=0;i<4;i++){
            delay(25);
            msgRecu.param1.octets[i] = Serial.read();
        }
        for(i=0;i<4;i++){
            delay(25);
            msgRecu.param2.octets[i] = Serial.read();
        }
        msgAenvoyer = call_order(&msgRecu);
        //s'il y a un message à envoyer :
        if(msgAenvoyer != NULL)
        {
          
            for(i = 0; i < NB_OCTETS; i++)
            {
              Serial.print(msgAenvoyer[i]);
              delay(25);
            }
            free(msgAenvoyer);
        }
		
		//TODO Récupérer télémetre et voir si le kercar est bloqué
    }
    delay(25);
}
