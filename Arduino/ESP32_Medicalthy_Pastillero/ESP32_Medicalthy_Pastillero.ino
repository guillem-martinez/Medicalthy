//BASADO EN: https://github.com/mobizt/Firebase-ESP-Client/blob/main/examples/Firestore/CommitDocument/SetUpdateDelete/SetUpdateDelete.ino

#include <WiFi.h>
#include <WiFiUdp.h>
#include <Firebase_ESP_Client.h>
#include <addons/TokenHelper.h>
#include <NTPClient.h>
#include "Credentials.h"

WiFiUDP ntpUDP;
NTPClient timeClient(ntpUDP);

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

void sendCommand(char c){
    Serial.print('<');
    Serial.print(c);
    Serial.println('>');
}
void sendCommand(char c, int v){
    Serial.print('<');
    Serial.print(c);
    Serial.print(v);
    Serial.println('>');
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


unsigned long getNTPHour(){
    while(!timeClient.update()){
        timeClient.forceUpdate();
    }

    int hh = timeClient.getHours();
    int mm = timeClient.getMinutes();
    int ss = timeClient.getSeconds();
    int dd = timeClient.getDay();

    //Poner la siguiente fecha: https://www.epochconverter.com/ considerando día, mes y año, a las 00h00 (en SEGUNDOS)
    unsigned long t = 1653782400 + ((hh + 1) * 3600) + (mm * 60) + ss;
    
    return t;
}


bool sendDataToFirebase(){
    std::vector<struct fb_esp_firestore_document_write_t> writes;
    struct fb_esp_firestore_document_write_t transform_write;
    transform_write.type = fb_esp_firestore_document_write_type_transform;
    transform_write.document_transform.transform_document_path = "test_HW/test_document";
    struct fb_esp_firestore_document_write_field_transforms_t field_transforms;
    field_transforms.fieldPath = "counter";
    field_transforms.transform_type = fb_esp_firestore_transform_type_increment;
    FirebaseJson values;
    values.set("integerValue", "1"); // increase by 1
    field_transforms.transform_content = values.raw();
    transform_write.document_transform.field_transforms.push_back(field_transforms);
    writes.push_back(transform_write);
    if (Firebase.Firestore.commitDocumentAsync(&fbdo, FIREBASE_PROJECT_ID, "", writes, "")){
        return 1;
    }
    else{
        Serial.println(fbdo.errorReason());
        return 0;
    }
}


void setup()
{
    Serial.begin(115200);
    
    connectToWiFi();
    
    Serial.printf("Firebase Client v%s\n\n", FIREBASE_CLIENT_VERSION);

    //Ver 'Credentials.h'
    config.api_key = API_KEY;
    auth.user.email = USER_EMAIL;
    auth.user.password = USER_PASSWORD;

    config.token_status_callback = tokenStatusCallback; // see addons/TokenHelper.h

    fbdo.setResponseSize(2048);
    Firebase.begin(&config, &auth);
    Firebase.reconnectWiFi(true);

    timeClient.begin();
    timeClient.setTimeOffset(3600);
}

void loop()
{
    unsigned long base_time_seconds = getNTPHour();

    Serial.println(base_time_seconds);

    unsigned long millis_now = 0;
    unsigned long next_millis = millis() + 1000;

    moverMotor(45);
    delay(2000);
    activarBuzzer(1000);
    delay(2000);
    activarLuz(-1, 1000);
    delay(2000);
    while(pastillasEnCubeta() == false) { delay(100); }  
    delay(1000);
    while(pastillasTomadas() == false) { delay(100); }  


    while(true){
        millis_now = millis();
        if (Firebase.ready() && ((millis_now >= next_millis) || (next_millis == 0)))
        {
            next_millis = millis() + 1000;
            
            if(sendDataToFirebase()){
                //Envío correcto
            }
            else{
                //Fallo al enviar...                
            }
        }
    }
}
