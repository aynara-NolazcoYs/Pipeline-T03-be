# Stock API - Ejemplos de Uso

## URLs de Acceso

- **Swagger/OpenAPI**: `http://localhost:8080/swagger-ui.html`
- **API Docs**: `http://localhost:8080/api-docs`

## Endpoints

### 1. Listar todo el Stock (GET)

```
GET http://localhost:8080/api/stock
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "productId": 1,
    "warehouseId": 1,
    "quantity": 100,
    "lastUpdate": "2024-06-09T10:30:00",
    "status": "A"
  },
  {
    "id": 2,
    "productId": 2,
    "warehouseId": 1,
    "quantity": 50,
    "lastUpdate": "2024-06-09T11:00:00",
    "status": "A"
  }
]
```

---

### 2. Obtener Stock por ID (GET)

```
GET http://localhost:8080/api/stock/{id}
```

**Ejemplo:**
```
GET http://localhost:8080/api/stock/1
```

**Response (200 OK):**
```json
{
  "id": 1,
  "productId": 1,
  "warehouseId": 1,
  "quantity": 100,
  "lastUpdate": "2024-06-09T10:30:00",
  "status": "A"
}
```

---

### 3. Crear o Actualizar Stock (POST)

```
POST http://localhost:8080/api/stock/save
Content-Type: application/json
```

**Request Body (Nuevo Stock):**
```json
{
  "warehouseId": 1,
  "productId": 5,
  "quantity": 150
}
```

**Response (200 OK):**
```json
{
  "id": 3,
  "productId": 5,
  "warehouseId": 1,
  "quantity": 150,
  "lastUpdate": "2024-06-09T14:30:00",
  "status": "A"
}
```

**Request Body (Actualizar Stock Existente - se suma la cantidad):**
```json
{
  "warehouseId": 1,
  "productId": 1,
  "quantity": 50
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "productId": 1,
  "warehouseId": 1,
  "quantity": 150,
  "lastUpdate": "2024-06-09T15:00:00",
  "status": "A"
}
```

---

## Validaciones

- **warehouseId**: Requerido (no nulo)
- **productId**: Requerido (no nulo)
- **quantity**: Requerido, debe ser >= 0

**Error Response (400 Bad Request):**
```json
{
  "timestamp": "2024-06-09T14:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation error",
  "details": {
    "warehouseId": "El almacén es obligatorio",
    "productId": "El producto es obligatorio",
    "quantity": "La cantidad es obligatoria"
  }
}
```

---

## Instrucciones para Docker Compose

### 1. Construir la imagen
```bash
docker compose -f docker-compose-aynara/docker-compose-be.yml build
```

### 2. Levantar los servicios
```bash
docker compose -f docker-compose-aynara/docker-compose-be.yml up -d
```

### 3. Verificar que esté corriendo
```bash
docker compose -f docker-compose-aynara/docker-compose-be.yml ps
```

### 4. Ver logs
```bash
docker compose -f docker-compose-aynara/docker-compose-be.yml logs -f backend
```

### 5. Parar servicios
```bash
docker compose -f docker-compose-aynara/docker-compose-be.yml down
```

---

## Ejemplos con cURL

### GET - Listar todo
```bash
curl -X GET "http://localhost:8080/api/stock" \
  -H "Content-Type: application/json"
```

### GET - Por ID
```bash
curl -X GET "http://localhost:8080/api/stock/1" \
  -H "Content-Type: application/json"
```

### POST - Crear/Actualizar
```bash
curl -X POST "http://localhost:8080/api/stock/save" \
  -H "Content-Type: application/json" \
  -d '{
    "warehouseId": 1,
    "productId": 5,
    "quantity": 150
  }'
```

---

## Acceso a Swagger

Una vez que el backend esté corriendo en Docker:

1. Abre tu navegador
2. Ve a: `http://localhost:8080/swagger-ui.html`
3. Verás todos los endpoints documentados
4. Puedes hacer pruebas directamente desde Swagger con el botón "Try it out"

---

## Notas Importantes

- La cantidad se **suma** si el producto ya existe en ese almacén
- El estado por defecto es "A" (activo)
- La fecha de actualización se registra automáticamente
- Todos los campos son validados en el backend
