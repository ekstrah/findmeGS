package main

import (
	"bufio"
	"encoding/csv"
	"encoding/json"
	"fmt"
	"io"
	"log"
	"net/http"
	"os"
	"strings"

	raven "github.com/getsentry/raven-go"
	"github.com/gorilla/mux"
)

type Label struct {
	Product  string `json:"proudctName"`
	Date     string `json:"date"`
	Price    string `json:"price"`
	Location string `json:"location"`
	Brand    string `json:"brand"`
	Sale     string `json:"sale"`
	Opo      string `json:"opo"`
}
type SHOPPY struct {
	Busan string `json:"busan"`
}

type LabelMin struct {
	Product string `json:"proudctName"`
	Date    string `json:"date"`
	Price   string `json:"price"`
	Brand   string `json:"brand"`
	Sale    string `json:"sale"`
	Opo     string `json:"opo"`
}

type eachShop struct {
	Name    string     `json:"shopName"`
	Product []LabelMin `json:"products"`
}

type shopNamy struct {
	Name string `json: "busan"`
}
type shopAddress struct {
	Name string  `json: "shopName"`
	lat  float64 `json: "lat"`
	lng  float64 `json: "lng"`
}

var shop []Label
var shopName []string
var shopLoc []string //All the shop names are stored here
var shoppingList []eachShop

func init() {
	raven.SetDSN("https://cf077f2871f44a1ba7861c38fbe65c59:4147153d16634737b0ca2246d562b5ef@sentry.io/1328225")
}

func ProError(w http.ResponseWriter, r *http.Request) {
	json.NewEncoder(w).Encode("ERROR TESTING")
	f, err := os.Open("error.log")
	if err != nil {
		raven.CaptureErrorAndWait(err, nil)
		log.Panic(err)
	}
	f.Close()
}

func in_array(val string, array []string) (exists bool, index int) {
	exists = false
	index = -1

	for i, v := range array {
		if val == v {
			index = i
			exists = true
			return
		}
	}

	return
}

func indexOf(word string, array []eachShop) int {
	for k, v := range array {
		if strings.Compare(word, v.Name) == 0 {
			return k
		}
	}
	return -1
}

func ReadCsvFile(filePath string) {
	f, _ := os.Open(filePath)

	r := csv.NewReader(bufio.NewReader(f))
	for {
		record, err := r.Read()
		if err == io.EOF {
			break
		}
		// Display record.
		for value := range record {
			temp := strings.Split(record[value], ",")
			shop = append(shop, Label{temp[0], temp[1], temp[2], temp[3], temp[4], temp[5], temp[6]})
		}
	}
	fmt.Println(len(shop))

	for nameShop := range shop {
		exists, _ := in_array(shop[nameShop].Location, shopLoc)
		if exists == false {
			shopLoc = append(shopLoc, shop[nameShop].Location)
			//Add the new shopping list to struct
			var tempA []LabelMin
			tempA = append(tempA, LabelMin{
				shop[nameShop].Product,
				shop[nameShop].Date,
				shop[nameShop].Price,
				shop[nameShop].Brand,
				shop[nameShop].Sale,
				shop[nameShop].Opo,
			})
			shoppingList = append(shoppingList, eachShop{
				shop[nameShop].Location,
				tempA,
			})
		} else {
			var index = indexOf(shop[nameShop].Location, shoppingList)
			if index == -1 {
				continue
			} else {
				shoppingList[index].Product = append(shoppingList[index].Product, LabelMin{
					shop[nameShop].Product,
					shop[nameShop].Date,
					shop[nameShop].Price,
					shop[nameShop].Brand,
					shop[nameShop].Sale,
					shop[nameShop].Opo,
				})
			}
		}
	}
	//fmt.Println(shopLoc[0])
	//u := shopNamy{Name: shopLoc[0]}
	//b := new(bytes.Buffer)
	//json.NewEncoder(b).Encode(u)
	//res, _ := http.Post("localhost:5000/getshop", "application/json; charset=utf-8", b)
	//io.Copy(os.Stdout, res.Body)
	//fmt.Println(res.Body)

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
		fmt.Println("NOT EMPTY")
		json.NewEncoder(w).Encode("NOT FOUND")
	} else {
		fmt.Println(c_item)
		json.NewEncoder(w).Encode(c_item)
	}
	//fmt.Println(shop[0].Product)
}

func GetPlace(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	var c_place []Label
	var item SHOPPY

	_ = json.NewDecoder(r.Body).Decode(&item)
	for i, _ := range shop {
		if strings.Compare(shop[i].Location, item.Busan) == 0 {

			c_place = append(c_place, Label{
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
	if len(c_place) == 0 {
		fmt.Println("NOT EMPTY")
		json.NewEncoder(w).Encode("NOT FOUND")
	} else {
		fmt.Println(c_place)
		json.NewEncoder(w).Encode(c_place)
	}
}

func GetSearch(w http.ResponseWriter, r *http.Request) {
	fmt.Println("Finish this code later")
	json.NewEncoder(w).Encode(shoppingList)
}

// our main function
func main() {
	ReadCsvFile("./xaa.csv")
	//Server being up
	router := mux.NewRouter()
	router.HandleFunc("/shop", raven.RecoveryHandler(GetShop)).Methods("GET")
	router.HandleFunc("/error", raven.RecoveryHandler(ProError)).Methods("GET")
	router.HandleFunc("/item", raven.RecoveryHandler(GetItem)).Methods("GET")
	router.HandleFunc("/place", raven.RecoveryHandler(GetPlace)).Methods("GET")
	router.HandleFunc("/search", raven.RecoveryHandler(GetSearch)).Methods("GET")
	log.Fatal(http.ListenAndServe(":8000", router))
	//
}
