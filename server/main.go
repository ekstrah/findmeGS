package main

import (
	"bufio"
	"encoding/csv"
	"encoding/json"
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
	//fmt.Println(shop)
}
func GetShop(w http.ResponseWriter, r *http.Request) {
	json.NewEncoder(w).Encode(shop)
}

// our main function
func main() {
	ReadCsvFile("./Book1.csv")
	//Server being up
	router := mux.NewRouter()
	router.HandleFunc("/shop", GetShop).Methods("GET")
	log.Fatal(http.ListenAndServe(":8000", router))
//
}