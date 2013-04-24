#include <Servo.h>
#include <servoMoteur.h>
#include <libComArduino.h>

//Telemetre
#define PIN_TELEMETER_BACK 2
#define PIN_TELEMETER_FRONT 4
#define SEUIL_ARRET 8

// initialisation de la bete
void setup() {
  Serial.begin(9600);
  servoAngle.attach(numPinLeft);  //the pin for the servo control 
  servoSpeed.attach(numPinRight);
}

void loop(){
    int i, distTelemeterFront, distTelemeterBack;
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
    }
    //Test du télémetre
    distTelemeterBack = readTelemeter(PIN_TELEMETER_BACK);
    distTelemeterFront = readTelemeter(PIN_TELEMETER_FRONT);
    //si le robot est trop près d'un obstacle et que les moteurs ont pour ordre d'avancer
    if((distTelemeterFront < SEUIL_ARRET || distTelemeterBack < SEUIL_ARRET) && servoMoteur_isBlock() != 1)
    {
        servoMoteur_stop();
        send_order(BLOCK);//envoie au raspberry qu'on est bloqué
    }
    
    delay(25);
}

float readTelemeter(int pinTelemetre)
{
    int value = analogRead(pinTelemetre);
    
    if (value < 3)
        return -1; // invalid value
    
    // Exemple trouvé sur des forums.
    // Il est aussi conseillé d'utiliser une table de correspondace (20 = 15 cm, 40 = 25 cm, etc...)
    return (6787.0 /((float)value - 3.0)) - 4.0;
}
