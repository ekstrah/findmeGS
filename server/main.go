package main

import (
	"bufio"
	"encoding/csv"
	"encoding/json"
	"fmt"
	"github.com/gorilla/mux"
	"io"
	"log"
	"net/http"
	"os"
	"strings"
)

type Label struct {
	Product       string   `json:"proudctName"`
	Date string `json:"date"`
	Price string   `json:"price"`
	Location  string   `json:"location"`
	Brand  string `json:"brand"`
	Sale string `json:"sale"`
	Opo string `json:"opo"`
}
type SHOPPY struct {
	Busan string `json:"busan"`
}

//// our get shop function
//func GetPeople(w http.ResponseWriter, r *http.Request) {
//
//}
var shop []Label

func ReadCsvFile(filePath string)  {
	f, _ := os.Open(filePath)

	r := csv.NewReader(bufio.NewReader(f))
	for {
		record, err := r.Read()
		if err == io.EOF {
			break
		}
		// Display record.
		for value := range record{
			temp := strings.Split(record[value], ",")
			shop = append(shop, Label{temp[0], temp[1], temp[2], temp[3], temp[4], temp[5], temp[6]})
		}
	}
}

func GetShop(w http.ResponseWriter, r *http.Request) {
	json.NewEncoder(w).Encode(shop)
}

func GetItem(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	var c_item []Label
	var item SHOPPY
	_ = json.NewDecoder(r.Body).Decode(&item)
	for i, _ := range shop {
		if strings.Compare(shop[i].Product, item.Busan) == 0 {
			c_item = append(c_item, Label{
				shop[i].Product,
				shop[i].Date,
				shop[i].Price,
				shop[i].Location,
				shop[i].Brand,
				shop[i].Sale,
				shop[i].Opo,
			})
		}
	}
	if len(c_item) == 0 {
		fmt.Println("NO EMPTY YOU KNOW?")
	} else {
		fmt.Println(c_item)
	}
	//fmt.Println(shop[0].Product)
}

// our main function
func main() {
	ReadCsvFile("./Book1.csv")
	//Server being up
	router := mux.NewRouter()
	router.HandleFunc("/shop", GetShop).Methods("GET")
	router.HandleFunc("/item", GetItem).Methods("GET")
	log.Fatal(http.ListenAndServe(":8000", router))
//
}





