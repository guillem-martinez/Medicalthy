//BASADO EN: https://github.com/mobizt/Firebase-ESP-Client/blob/main/examples/Firestore/CommitDocument/SetUpdateDelete/SetUpdateDelete.ino

#include <WiFi.h>
#include <Firebase_ESP_Client.h>
#include <addons/TokenHelper.h>
#include "Credentials.h"

// Define Firebase Data object
FirebaseData fbdo;

FirebaseAuth auth;
FirebaseConfig config;

void connectToWiFi(){
    WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
    Serial.print("Connecting to Wi-Fi");
    while (WiFi.status() != WL_CONNECTED)
    {
        Serial.print(".");
        delay(300);
    }
    Serial.println();
    Serial.print("Connected with IP: ");
    Serial.println(WiFi.localIP());
    Serial.println();
}


void setup()
{
    Serial.begin(115200);
    
    connectToWiFi();

    Serial.printf("Firebase Client v%s\n\n", FIREBASE_CLIENT_VERSION);

    config.api_key = API_KEY;
    auth.user.email = USER_EMAIL;
    auth.user.password = USER_PASSWORD;

    config.token_status_callback = tokenStatusCallback; // see addons/TokenHelper.h

    fbdo.setResponseSize(2048);

    Firebase.begin(&config, &auth);
    Firebase.reconnectWiFi(true);
}

void sendCommand(char c){
    Serial.print('<');
    Serial.print(c);
    Serial.print('>');
}
void sendCommand(char c, int v){
    Serial.print('<');
    Serial.print(c);
    Serial.print(v);
    Serial.print('>');
}

void getNextHour(int& next_hour){
    
}

void moverMotor(int angulo){
    sendCommand('M', angulo);
}

void activarBuzzer(int tiempo){
    sendCommand('B', tiempo);
}

void activarLuz(int tipo, int tiempo){
    sendCommand('L', tiempo);
}

bool pastillasTomadas(){
    sendCommand('T');
    while(!Serial.available()){
        delay(1);
    }
    
    bool result = false;
    while(Serial.available()){
        char c = Serial.read();
        //Operar con c
        if(c == '1')
            result = true;
    }

    return result;
}

bool pastillasEnCubeta(){
    sendCommand('C');
    while(!Serial.available()){
        delay(1);
    }

    bool result = false;
    while(Serial.available()){
        char c = Serial.read();
        //Operar con c
        if(c == '1')
            result = true;
    }

    return result;
}


void loop()
{
    unsigned long millis_now = 0;
    unsigned long next_millis = millis() + 1000;

    while(true){
        millis_now = millis();
        if (Firebase.ready() && ((millis_now >= next_millis) || (next_millis == 0)))
        {
            next_millis = millis() + 1000;
            
            Serial.println("Test");
        }
    }
}
