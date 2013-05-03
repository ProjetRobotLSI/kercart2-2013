#include <Arduino.h>

//Telemetre
#define PIN_TELEMETER_BACK 2
#define PIN_TELEMETER_FRONT 4
#define SEUIL_ARRET 8

float readTelemeter(int pinTelemetre)
{
    int value = analogRead(pinTelemetre);
    
    if (value < 3)
        return -1; // invalid value
    
    // Exemple trouvé sur des forums.
    // Il est aussi conseillé d'utiliser une table de correspondace (20 = 15 cm, 40 = 25 cm, etc...)
    return (6787.0 /((float)value - 3.0)) - 4.0;
}