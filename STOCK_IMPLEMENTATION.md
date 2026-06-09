# Stock API - Implementación Completada

## ✅ Estructura Implementada

### 1. Modelos (Model)
- **Stock.java**: Entidad JPA que mapea la tabla `stock` existente
  - id, productId, warehouseId, quantity, lastUpdate, status

### 2. DTOs (Data Transfer Objects)
- **StockTransactionRequest.java**: DTO para POST
  - warehouseId, productId, quantity
  
- **StockItemRequest.java**: (No usado en la versión simplificada, disponible para futuros cambios)

### 3. Repositorio (Repository)
- **StockRepository.java**: JpaRepository + método personalizado
  - `findByProductIdAndWarehouseId()`: Busca stock existente por producto y almacén

### 4. Servicio (Service)
- **StockService.java**: Interfaz de servicio
  - `findAll()`: Lista todo el stock
  - `findById(Long id)`: Obtiene un stock por ID
  - `createOrUpdateStock()`: Crea o actualiza stock (transaccional)

- **StockServiceImpl.java**: Implementación
  - Lógica transaccional con `@Transactional`
  - Si existe stock para el producto en el almacén, suma la cantidad
  - Si no existe, crea uno nuevo

### 5. REST Controller
- **StockRest.java**: Controlador REST
  - **GET** `/api/stock` - Listar todo
  - **GET** `/api/stock/{id}` - Obtener por ID
  - **POST** `/api/stock/save` - Crear o actualizar (cabecera + lógica en una acción)

---

## 📊 Características

✅ **GET**: Listar cabecera (stock)
✅ **POST**: Crear/actualizar en una sola acción
✅ **Transaccional**: Uso de `@Transactional` para integridad de datos
✅ **Validaciones**: Anotaciones Jakarta Validation
✅ **Swagger/OpenAPI**: Documentación automática
✅ **CORS**: Habilitado para acceso desde frontend
✅ **Docker Compose**: Listo para ejecutar

---

## 🗂️ Tablas de Base de Datos

### Tabla: stock
```sql
CREATE TABLE stock (
    id int IDENTITY(1,1) PRIMARY KEY,
    product_id int NOT NULL,
    warehouse_id int NOT NULL,
    quantity int NOT NULL,
    last_update datetime NOT NULL,
    status char(1) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (warehouse_id) REFERENCES warehouse(id)
);
```

---

## 🚀 Cómo Ejecutar

### Opción 1: Docker Compose

```bash
# Ir a la carpeta del proyecto
cd /Users/apple/ASE251S3_T03-be

# Levantar servicios (backend + SQL Server)
docker compose -f docker-compose-aynara/docker-compose-be.yml up -d --build

# Ver logs
docker compose -f docker-compose-aynara/docker-compose-be.yml logs -f backend

# Parar servicios
docker compose -f docker-compose-aynara/docker-compose-be.yml down
```

### Opción 2: Local (sin Docker)

```bash
# Compilar
mvn clean compile

# Ejecutar
mvn spring-boot:run
```

---

## 🧪 Pruebas

### Swagger UI
```
http://localhost:8080/swagger-ui.html
```

### Ejemplos POST (cURL)

**Crear nuevo stock:**
```bash
curl -X POST "http://localhost:8080/api/stock/save" \
  -H "Content-Type: application/json" \
  -d '{
    "warehouseId": 1,
    "productId": 1,
    "quantity": 100
  }'
```

**Actualizar stock existente (suma cantidad):**
```bash
curl -X POST "http://localhost:8080/api/stock/save" \
  -H "Content-Type: application/json" \
  -d '{
    "warehouseId": 1,
    "productId": 1,
    "quantity": 50
  }'
```

**Listar todo:**
```bash
curl -X GET "http://localhost:8080/api/stock"
```

**Obtener por ID:**
```bash
curl -X GET "http://localhost:8080/api/stock/1"
```

---

## 📝 Archivos Creados/Modificados

### Nuevos:
- `src/main/java/vallegrande/edu/pe/losQueensAgro/model/Stock.java`
- `src/main/java/vallegrande/edu/pe/losQueensAgro/repository/StockRepository.java`
- `src/main/java/vallegrande/edu/pe/losQueensAgro/service/StockService.java`
- `src/main/java/vallegrande/edu/pe/losQueensAgro/service/impl/StockServiceImpl.java`
- `src/main/java/vallegrande/edu/pe/losQueensAgro/rest/StockRest.java`
- `src/main/java/vallegrande/edu/pe/losQueensAgro/dto/StockTransactionRequest.java`
- `src/main/java/vallegrande/edu/pe/losQueensAgro/dto/StockItemRequest.java`
- `STOCK_API_GUIDE.md` - Guía de uso detallada
- `STOCK_IMPLEMENTATION.md` - Este archivo

### Modificados:
- `scripts_sql.sql` - Foreign keys de tabla stock
- `src/main/resources/application.yaml` - Configuración Swagger mejorada

---

## ✨ Notas

- La solución mantiene la estructura existente de tu proyecto (igual que SaleOrder)
- No hay conflictos con `stock_movement` (tabla de auditoría)
- Totalmente transaccional y thread-safe
- Listo para producción en Docker
