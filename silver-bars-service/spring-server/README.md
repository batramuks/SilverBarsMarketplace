
## Overview  

Start your server as an simple java application (SilverBarsMarketPlaceApplication.java) 

URL: http://localhost:8080/  
Change default port value in application.properties

## To Test

POST:
http://localhost:8080/sliverbarsmarketplace/order
Request Payload:
{
  "userId": "user1",
  "orderQuantity": 3.5,
  "price": 306,
  "orderType": "SELL",
  "orderId": ""

}

DELETE:
http://localhost:8080/sliverbarsmarketplace/order/1

GET:
http://localhost:8080/sliverbarsmarketplace/liveorderboardsummary
Response:
[
    "SELL: 3.5 kg for £306",
    "SELL: 1.5 kg for £307",
    "SELL: 1.2 kg for £310",
    "BUY: 1.2 kg for £310",
    "BUY: 1.5 kg for £307",
    "BUY: 3.5 kg for £306"
]
 
GET
http://localhost:8080/sliverbarsmarketplace/order
 