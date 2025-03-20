# ♻️ ReciclArte - Backend  

## 📖 Descripción  
**ReciclArte** es una plataforma web donde los usuarios pueden donar artículos que ya no necesitan, promoviendo la **economía circular**.  

Este repositorio contiene el **backend** del proyecto, desarrollado con **Spring Boot** y **MySQL**.  

---

## 🛠️ Tecnologías utilizadas  
- **Java 21**  
- **Spring Boot** (Web, Data JPA)  
- **MySQL** (Base de datos)  
- **Maven** (Gestión de dependencias)  

---

## 📡 Endpoints de la API (CRUD)  

### 🔹 **CREATE - Crear un nuevo artículo**  
📌 **Método:** `POST`  
📌 **Endpoint:** `/api/items`  
📌 **Cuerpo de la solicitud (JSON):**  
```json
{
  "name": "Silla de oficina",
  "description": "Cómoda y en buen estado",
  "category": "Muebles",
  "itemCondition": "Usado",
  "imgUrl": "https://image.com/silla.jpg",
  "location": "Barcelona",
  "reserved": false
}
```
📌 **Respuesta (201 Created):**
```json
{
  "id": 2,
  "name": "Silla de oficina",
  "description": "Cómoda y en buen estado",
  "category": "Muebles",
  "itemCondition": "Usado",
  "imgUrl": "https://image.com/silla.jpg",
  "location": "Barcelona",
  "reserved": false
}
```

### 🔹 READ - Obtener todos los artículos
📌 **Método:** `GET`  
📌 **Endpoint:** `/api/items`  
📌 **Respuesta (200 OK):**  
```json
{
    "id": 1,
    "name": "Bicicleta usada",
    "description": "En buen estado",
    "category": "Transporte",
    "itemCondition": "Usado",
    "imgUrl": "https://image.com/bike.jpg",
    "location": "Madrid",
    "reserved": false
  }
```
### 🔹 READ - Obtener un artículo por ID
📌 **Método**: `GET`  
📌 **Endpoint**: `/api/items/{id}`  
📌 **Respuesta si el artículo existe (200 OK)**:  
```json
{
  "id": 1,
  "name": "Bicicleta usada",
  "description": "En buen estado",
  "category": "Transporte",
  "itemCondition": "Usado",
  "imgUrl": "https://image.com/bike.jpg",
  "location": "Madrid",
  "reserved": false
}
```

📌 Si el ID no existe (404 Not Found):
```json
{
  "error": "The item with id 99 does not exist."
}
```

### 🔹 UPDATE - Actualizar un artículo
📌 Método: `PUT`  
📌 Endpoint: `/api/items/{id}`  
📌 **Cuerpo de la solicitud (JSON)**:
```json
{
  "name": "Silla ergonómica",
  "description": "Casi nueva",
  "category": "Muebles",
  "itemCondition": "Nuevo",
  "imgUrl": "https://image.com/silla-nueva.jpg",
  "location": "Barcelona",
  "reserved": true
}
```
📌 **Respuesta (200 OK)**:
```json
{
  "id": 2,
  "name": "Silla ergonómica",
  "description": "Casi nueva",
  "category": "Muebles",
  "itemCondition": "Nuevo",
  "imgUrl": "https://image.com/silla-nueva.jpg",
  "location": "Barcelona",
  "reserved": true
}
```
### 🔹 UPDATE - Reservar un artículo
📌 **Método**: `PUT`  
📌 **Endpoint**: `/api/items/{id}/reserve`  
📌 **Ejemplo de solicitud**:  
```json
{
  "id": 1,
  "name": "Bicicleta usada",
  "description": "En buen estado",
  "category": "Transporte",
  "itemCondition": "Usado",
  "imgUrl": "https://image.com/bike.jpg",
  "location": "Madrid",
  "reserved": true
}
```
### 🔹 DELETE - Eliminar un artículo
📌 **Método**: `DELETE`  
📌 **Endpoint**: `/api/items/{id}`  
📌 **Respuesta (200 OK) si el artículo se elimina correctamente**.  
📌 **Si el ID no existe (404 Not Found)**:  
```json
{
  "error": "The item with id 99 does not exist."
}
```
### 🔗 Información adicional  
📂 **Código fuente:** [ReciclArte Backend](https://github.com/Paola077/reciclArte_backend)  
🎨 **Código fuente Frontend:** [ReciclArte Frontend](https://github.com/rebecabernal/ReciclArteFront) 








