# discount-codes-management
Endpoints : 

"/discount" - for calculating discounts [POST]

Example : http://localhost:8080/discount 

Body : 

{

  "productId" : 1,
  
  "discountCode" : "XXXX1"
  
}

"/products" - for finding all products [GET]

Example : http://localhost:8080/products

"/products" - for creating new products [POST]

Example : http://localhost:8080/products

Body : 

{

  "name" : "apple",
  
  "price" : 2.0,
  
  "description" : "a apple" [Optional],
  
  "currency" : "USD"
  
}

"/products/{id}" - for updating a product [PUT] {id} - is a variable here you put product id like 1 for example

Example : http://localhost:8080/products/1

Body : 

{

  "name" : "banana",
  
  "price" : 3.0,
  
  "description" : "a banana" [Optional],
  
  "currency" : "USD"
  
}

"/promoCodes" - for finding all promo codes [GET]

Example : http://localhost:8080/promoCodes

"/promoCodes/{code}" - for finding specific promo code [GET] {code} - is a variable here you put the code of the promo code like XXXX for example

Example : http://localhost:8080/promoCodes/XXX1

"/promoCodes" - for creating a new promo code [POST]

Example : http://localhost:8080/promoCodes

Body : 

{

  "code" : "XXXX",
  
  "discount" : 2.0,
  
  "currency" : "USD",
  
  "expirationDate" : "2024-07-10",
  
  "maxUsages" : 20,
  
  "leftUsages" : 20, [Optional]
  
  "isPercent" : false [Optional]
  
}

"/purchase" - for creating a new purchase [POST]

Example : http://localhost:8080/purchase

Body : 

{

  "productId" : 1,
  
  "discountCode" : "XXXX"
  
}

"/report" - for getting a sales report [GET]

Example : http://localhost:8080/report
