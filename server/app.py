#!/usr/bin/env python3
# -*- coding: utf-8 -*-
from flask import Flask
from flask import request
import requests, json 
import csv
from operator import itemgetter
from math import radians, sin, cos, acos
import math
import base64



api_key = "AIzaSyBC5l9HMZ-egyxT888zQiQ_9D6XSilaCv0"
  
# url variable store url 
url = "https://maps.googleapis.com/maps/api/place/textsearch/json?"

#https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=이마트풍산점&inputtype=textquery&fields=geometry&key=AIzaSyBC5l9HMZ-egyxT888zQiQ_9D6XSilaCv0
  
# The text string on which to search 
#서울우유 흰우유(1000ml),2018-09-21,2570,이마트동구미점,서울우유,N,N
class shop:
    def __init__(self, name, date,price, place, brand, sale, opo):
        self.name = name
        self.date = date
        self.price = price
        self.place = place
        self.brand = brand
        self.sale = sale
        self.opo = opo



class shoploca():
    def __init__(self, lat, lng):
        self.lat = lat
        self.lng = lng

StoreName = []
ProductName = []
shopDict = {}
shopLoc = {}

def initSetup():

    getData()
    getLocation()
    

def get_distance(point1, point2): 
    slat = radians(float(point1.lat))
    slon = radians(float(point1.lng))
    elat = radians(float(point2.lat))
    elon = radians(float(point2.lng))
    dist = 6371.01 * acos(sin(slat)*sin(elat) + cos(slat)*cos(elat)*cos(slon - elon))
    return dist

def getLocation():
    global StoreName
    global shopLoc
    for store in StoreName:
        lat, lng = getLngLat(store)
        shopLoc[store] = []
        shopLoc[store].append(shoploca(lat, lng))
    # print(shopLoc['이마트칠성점'][0].lat)

def getData():
    global StoreName
    global shopDict
    global ProductName
    with open("xaa.csv")as f:
        fReader = csv.DictReader(f)
        for row in fReader:
            # print(row)
            #row['\ufeff"상품명'] is just whole string read dont' know why it happened but happened
            tempA = row['\ufeff"상품명'].split(",")
            ProductName.append(tempA[0])
            if tempA[3] not in StoreName:
                shopDict[tempA[3]] = []
                StoreName.append(tempA[3])
            
            shopDict[tempA[3]].append(shop(tempA[0], tempA[1], tempA[2], tempA[3], tempA[4], tempA[5], tempA[6]))
        print(shopDict['이마트칠성점'][0].name)

        #이마트충주점

def findNear(currentPosition):
    near = 400.0 #near 10km
    getMax = 10
    global shopLoc
    returnShop = []
    distanceDict = {}
    for shop in shopLoc:
        lat = shopLoc[shop][0].lat
        lng = shopLoc[shop][0].lng
        if lat == None or lng == None:
            continue
        distance = get_distance(currentPosition, shopLoc[shop][0])
        if distance <= near:
            distanceDict[shop] = distance

    sorted_distance = sorted(distanceDict.items(), key=lambda x: x[1])
    if len(sorted_distance) > 10:
        for i in range(0, getMax):
            returnShop.append(sorted_distance[i])
    else:
        for i in range(len(sorted_distance)):
            returnShop.append(sorted_distance[i])
    return returnShop

def getLngLat(shopName):
    query = "&inputtype=textquery&fields=geometry"
    query = "input="+shopName+query
    # print(url+"query="+query+"&key="+api_key)
    r = requests.get(url + "query=" + query +
                            "&key=" + api_key) 
    
    # json method of response object convert 
    #  json format data into python format data 
    x = r.json() 
    
    # now x contains list of nested dictionaries 
    # we know dictionary contain key value pair 
    # store the value of result key in variable y 
    y = x['results'] 
    
    # keep looping upto lenght of y 
    lat = None
    lng = None
    for i in range(len(y)): 
        
        # Print value corresponding to the 
        # 'name' key at the ith index of y 
        # print(y[i]["geometry"]["location"])
        lat = y[i]["geometry"]["location"]["lat"]
        lng = y[i]["geometry"]["location"]["lng"]
    
    return lat, lng

def stringToBase64(s):
    return base64.b64encode(s.encode('utf-8'))

def base64ToString(b):
    return base64.b64decode(b).decode('utf-8')
app = Flask(__name__)

@app.route("/")
def hello():
    return "Hello World!"

@app.route("/shoploc", methods=['POST'])
def json_shoploc():
    global shopDict
    global shopLoc
    print("p0 received")
    storeName = request.get_json('currentLocation')
    print(storeName)
    lat = float(storeName['currentLocation']['lat'])
    lng = float(storeName['currentLocation']['lng'])
    currentLoc = shoploca(lat, lng)
    closeShop = findNear(currentLoc)
    stringShops = ""
    for word in closeShop:
        stringShops  = stringShops + "location: " + word[0] +"\n"

    return stringShops

@app.route("/shoplocation/<item>", methods=['POST'])
def json_shopLocation(item):
    global shopLoc

    if "," in item:#location
        tempArray = item.split(",")
        lat = float(tempArray[0])
        lng = float(tempArray[1])
        currentLoc = shoploca(lat, lng)
        closeShop = findNear(currentLoc)
        print("passed")
        url = 'http://192.168.123.107:8000/place'
        a = None
        for word in closeShop:
            print(word[0])
            data = {"busan": word[0]}
            r = requests.post(url, verify=False, json=data)
            a = r.text


# url = 'http://localhost:8000/item'
# >>> 
# >>> data = {"busan": "머거본 꿀땅콩(135g)"}
# >>> r = requests.post(url,verify=False, json=data)
# >>> 
# >>> print(r.status_code)
# 200
# >>> print(r.body)

    return a
if __name__ == '__main__':
    
    initSetup()
    app.run(debug=True)