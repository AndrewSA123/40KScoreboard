#include <ArduinoJson.h>

#include <LiquidCrystal.h>

#include<ESP8266WiFi.h>
#include<ESP8266WebServer.h>
#include <ESP8266WiFiMulti.h>
#include<Wire.h>

int Team1CP = 0;
int Team1VP = 0;
int Team2CP = 0;
int Team2VP = 0;
const int CAPACITY = JSON_OBJECT_SIZE(4);
StaticJsonDocument<CAPACITY> doc;
LiquidCrystal lcd(D0, D1, D2, D3, D4, D5);

int button = D8;

ESP8266WebServer server;
ESP8266WiFiMulti wifiMulti;

IPAddress local_IP(192, 168, 1, 245);

const uint32_t connectTimeoutMs = 5000;

void TestHeaders();
void AddCP();
void RemoveCP();
void AddVP();
void RemoveVP();
void GetScores();

void setup() {
  pinMode(button, INPUT);
  Serial.begin(9600);

  
  wifiMulti.addAP("Wifi Name Here", "Wifi Password here");

  while (wifiMulti.run(connectTimeoutMs) != WL_CONNECTED) {
    Serial.println("Connecting");
    delay(1000);
  }

  if (wifiMulti.run(connectTimeoutMs) == WL_CONNECTED) {
    WiFi.hostname("40k Scoreboard");
  }

  Serial.print("SSID: ");
  Serial.println(WiFi.SSID());
  Serial.print("Device Name: ");
  Serial.println(WiFi.hostname());
  Serial.print("IP ADDRESS: ");
  Serial.println(WiFi.localIP());

  server.on("/", []() {
    server.send(200, "text/plain", "EndPoints: \n /addcp Arguments: team and cp \n /removecp Arguments: team and cp \n /addvp Arguments: team and vp \n /removevp Arguments: team and vp \n /start Displays scores on LCD");
  });
  server.on("/start", HTTP_GET, GetScores);
  server.on("/addcp", HTTP_GET, AddCP);
  server.on("/removecp", HTTP_GET, RemoveCP);
  server.on("/addvp", HTTP_GET, AddVP);
  server.on("/removevp", HTTP_GET, RemoveVP);
  server.onNotFound([]() {
    server.send(404, "text/plain", "Endpoint not found");
  });
  server.begin();

    lcd.begin(16, 2);
    lcd.print(WiFi.localIP());
}

void loop() {
  if (wifiMulti.run(connectTimeoutMs) != WL_CONNECTED) {
    Serial.println("Reconnecting");
    delay(1000);

    if (wifiMulti.run(connectTimeoutMs) == WL_CONNECTED) {
      WiFi.hostname("40k Scoreboard");
      Serial.print("SSID: ");
      Serial.println(WiFi.SSID());
      Serial.print("Device Name: ");
      Serial.println(WiFi.hostname());
      Serial.print("IP ADDRESS: ");
      Serial.println(WiFi.localIP());
      lcd.clear();
      lcd.print(WiFi.localIP());
      delay(10000);
      GetScores();
    }
  }

  if(digitalRead(button)==HIGH){
    lcd.clear();
    lcd.print(WiFi.localIP());
    delay(10000);
    GetScores();
  }

  server.handleClient();
}

void GetScores(){
  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print("Team1 CP" + String(Team1CP) + " VP" + String(Team1VP));
  lcd.setCursor(0,1);
  lcd.print("Team2 CP" + String(Team2CP) + " VP" + String(Team2VP));
doc["Team1CP"] = Team1CP;
doc["Team1VP"] = Team1VP;
doc["Team2CP"] = Team2CP;
doc["Team2VP"] = Team2VP;

char jsonString[100];

serializeJson(doc, jsonString);

Serial.println(jsonString);
  
  server.send(200, "text/plain", jsonString);
}

void UpdateScores(){
  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print("Team1 CP" + String(Team1CP) + " VP" + String(Team1VP));
  lcd.setCursor(0,1);
  lcd.print("Team2 CP" + String(Team2CP) + " VP" + String(Team2VP));
}

void AddCP() {

  if (!server.hasArg("team")) {
    server.send(404, "text/plain", "Team argument missing");
  }
  if (!server.hasArg("cp")) {
    server.send(404, "text/plain", "cp arguement missing");
  }
  if (server.hasArg("team") && server.hasArg("cp")) {
    if (server.arg("team").toInt() == 1) {
      Team1CP += server.arg("cp").toInt();
      Serial.println(server.arg("team") + ":" + server.arg("cp") + ":" + Team1CP);
    }
    if (server.arg("team").toInt() == 2) {
      Team2CP += server.arg("cp").toInt();
      Serial.println(server.arg("team") + ":" + server.arg("cp") + ":" + Team2CP);
    }
  }
doc["Team1CP"] = Team1CP;
doc["Team1VP"] = Team1VP;
doc["Team2CP"] = Team2CP;
doc["Team2VP"] = Team2VP;

char jsonString[100];

serializeJson(doc, jsonString);

Serial.println(jsonString);

  UpdateScores();
  server.send(200, "text/plain", jsonString);
}

void RemoveCP() {

  if (!server.hasArg("team")) {
    server.send(404, "text/plain", "Team argument missing");
  }
  if (!server.hasArg("cp")) {
    server.send(404, "text/plain", "cp arguement missing");
  }
  if (server.hasArg("team") && server.hasArg("cp")) {
    if (server.arg("team").toInt() == 1) {
      Team1CP -= server.arg("cp").toInt();
      if (Team1CP < 0) Team1CP = 0;
      Serial.println(server.arg("team") + ":" + server.arg("cp") + ":" + Team1CP);
    }
    if (server.arg("team").toInt() == 2) {
      Team2CP -= server.arg("cp").toInt();
      if (Team2CP < 0) Team2CP = 0;
      Serial.println(server.arg("team") + ":" + server.arg("cp") + ":" + Team2CP);
    }
  }

doc["Team1CP"] = Team1CP;
doc["Team1VP"] = Team1VP;
doc["Team2CP"] = Team2CP;
doc["Team2VP"] = Team2VP;

char jsonString[100];

serializeJson(doc, jsonString);

Serial.println(jsonString);
  UpdateScores();
  server.send(200, "text/plain", jsonString);
}

void AddVP() {

  if (!server.hasArg("team")) {
    server.send(404, "text/plain", "Team argument missing");
  }
  if (!server.hasArg("vp")) {
    server.send(404, "text/plain", "vp arguement missing");
  }
  if (server.hasArg("team") && server.hasArg("vp")) {
    if (server.arg("team").toInt() == 1) {
      Team1VP += server.arg("vp").toInt();
      Serial.println(server.arg("team") + ":" + server.arg("vp") + ":" + Team1VP);
    }
    if (server.arg("team").toInt() == 2) {
      Team2VP += server.arg("vp").toInt();
      Serial.println(server.arg("team") + ":" + server.arg("vp") + ":" + Team2VP);
    }
  }

doc["Team1CP"] = Team1CP;
doc["Team1VP"] = Team1VP;
doc["Team2CP"] = Team2CP;
doc["Team2VP"] = Team2VP;

char jsonString[100];

serializeJson(doc, jsonString);

Serial.println(jsonString);
  UpdateScores();
  server.send(200, "text/plain", jsonString);
}

void RemoveVP() {

  if (!server.hasArg("team")) {
    server.send(404, "text/plain", "Team argument missing");
  }
  if (!server.hasArg("vp")) {
    server.send(404, "text/plain", "vp arguement missing");
  }
  if (server.hasArg("team") && server.hasArg("vp")) {
    if (server.arg("team").toInt() == 1) {
      Team1VP -= server.arg("vp").toInt();
      if (Team1VP < 0) Team1VP = 0;
      Serial.println(server.arg("team") + ":" + server.arg("vp") + ":" + Team1VP);
    }
    if (server.arg("team").toInt() == 2) {
      Team2VP -= server.arg("vp").toInt();
      if (Team2VP < 0) Team2VP = 0;
      Serial.println(server.arg("team") + ":" + server.arg("vp") + ":" + Team2VP);
    }
  }

    doc["Team1CP"] = Team1CP;
doc["Team1VP"] = Team1VP;
doc["Team2CP"] = Team2CP;
doc["Team2VP"] = Team2VP;

char jsonString[100];

serializeJson(doc, jsonString);

Serial.println(jsonString);
  UpdateScores();
server.send(200, "text/plain", jsonString);
}


//server.send(ERRORCODE, "text/plain" response type, RESPONSE)
