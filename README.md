# Fast food delivery

## **Требования**

- Java 11
- Spring Boot 2.7.17
- Docker

Запустите Docker Compose для развертывания базы данных PostgreSQL:
docker-compose up

```bash
docker-compose up
```

Это создаст контейнер с PostgreSQL, где имя хоста - "postgres", а пароль - "1".

Spring Boot приложение будет доступно по адресу **`http://localhost:8080`** (порт может отличаться в зависимости от конфигурации).

## **Мониторинг базы данных**

Для мониторинга базы данных установлен клиент, доступный на порту **`localhost:90`**.

## USER CRUD

### `default Administrator: login - admin, password - admin`

- POST-GET /users - create user, get all users
- PATCH, DELETE, GET /users/{id} - update-delete-get-user
- GET /users/{id}/orders - user’s orders

### Create-Update user JSON example

```json
{
    "username" : "iznullaaskarov@gmail.com",
    "password" : "123",
    "role" : "USER",
    "name" : "Carlos",
    "surname" : "Sainz",
    "address" : {
        "street" : "MEGA",
        "country" : "Uzbekistan",
        "longitude" : 41.37011004662482,
        "latitude" : 69.29047371707807,
        "city" : "Tashkent"
    }
}
```

## MENU CRUD

- POST-GET /menu - create menu, get all menus
- PATCH, DELETE, GET /menu/{id} - update-delete-get menu

### Create-Update menu JSON example

```json
{
    "restaurantId" : 3,
    "name" : "KFC Burger",
    "price" : 34000,
    "cookingTime" : 6

}
```

## ORDER CRUD

- POST-GET /orders - create order, get all orders
- PATCH, DELETE, GET /orders/{id} - update-delete-get order

### Create order JSON example

```json
{
    "address" : {
        "street" : "MEGA",
        "country" : "Uzbekistan",
        "longitude" : 41.37011004662482,
        "latitude" : 69.29047371707807,
        "city" : "Tashkent"
    },
    "orderMenu" : 
    [
        {
            "menuId" : 1,
            "quantity" : 3
        },
        {
           "menuId" : 3,
           "quantity" : 2 
        }
    ]
}
```

### Update order status from admin or waiter

 

```json
{
    "orderStatus" : "SHIPPED"
}
```
## FILIAL CRUD

- POST-GET /filial - create filial, get all filial
- PATCH, DELETE, GET /filial/{id} - update-delete-get filial

### Create-Update filial JSON example
```json
{
    "restaurantId" : 3,
    "name" : "KFC Fayz",
    "address" : {
        "street" : "Fayz",
        "country" : "Uzbekistan",
        "longitude" : 41.32776171557638,
        "latitude" : 69.33060764607195,
        "city" : "Tashkent"
    }
}
```