# Spin Challenge Service

API REST para ejecutar y consultar transacciones financieras `CREDIT` y `DEBIT`.

## Requisitos

- Java 25
- Maven 3.9+

## Ejecutar

Desde la carpeta del repositorio:

```bash
cd challenge
mvn spring-boot:run
```

La aplicación inicia en `http://localhost:8080`.

Verificación rápida de que arrancó correctamente:

```text
http://localhost:8080/actuator/health
```

Swagger UI está disponible en:

```text
http://localhost:8080/swagger-ui/index.html
```

## Endpoints

El servicio usa `/api/v1` como prefijo de versionado. El path completo es configurable mediante la propiedad `transactions.api.path` (`application.properties`), sin necesidad de recompilar.

### Ejecutar transacción

```http
POST /api/v1/transactions
Content-Type: application/json
```

```json
{
  "accountId": "acc-123456",
  "type": "CREDIT",
  "amount": 1500.00,
  "currency": "MXN",
  "description": "Transferencia recibida"
}
```

### Consultar transacciones

```http
GET /api/v1/transactions?accountId=acc-123456&status=EXECUTED&type=CREDIT&page=0&limit=50
```

Los filtros `accountId`, `status` y `type` son opcionales. `page` inicia en `0` y `limit` acepta entre `1` y `100`.

## Flujo

1. Spring valida el request con Bean Validation.
2. El servicio aplica las reglas de negocio.
3. `ProviderClient` ejecuta la transacción contra el proveedor mock.
4. Se persiste la respuesta en la entidad JPA `Transaction`.
5. La API devuelve la transacción creada.

### Reglas de negocio

- El monto debe ser mayor a `1.00`.
- Las transacciones `DEBIT` no pueden superar `10000.00`; `CREDIT` no tiene límite.
- Solo se acepta la moneda `MXN`.

Estas reglas se validan antes de llamar al proveedor. Si alguna falla, la API responde `400` con un cuerpo como:

```json
{
  "timestamp": "2026-08-21T10:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "path": "/api/v1/transactions",
  "errors": ["El monto de una transaccion DEBIT no puede superar 10000."]
}
```

El mock genera un `providerTransactionId` único por operación. Para simular un rechazo:

```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--provider.mock.outcome=REJECTED
```

El comportamiento normal es `APPROVED` y se puede controlar con `provider.mock.outcome`.

`ProviderClient` es una interfaz; `MockProviderClient` es la única implementación. Un proveedor real podría añadirse implementando esa misma interfaz sin tocar el controlador ni el servicio.

Los `toString()` de los modelos usados en logs enmascaran el `accountId`; por ejemplo, `acc-123456` se registra como `***123456`.

## Persistencia

Se usa Spring Data JPA con H2 en memoria para que el challenge sea autocontenido. El esquema está en `challenge/src/main/resources/schema.sql` e incluye índices para las consultas por cuenta y proveedor.

La tabla y las columnas están nombradas en español (`transacciones`, `cuenta_id`, `monto`, etc.); los atributos Java y el contrato de la API permanecen en inglés.

Los datos se eliminan al reiniciar la aplicación. En un ambiente productivo se sustituiría H2 por PostgreSQL o Azure Database for PostgreSQL, manteniendo la interfaz del repositorio.

## Testing

```bash
cd challenge
mvn test
```

Los tests cubren:

- Ejecución de transacción aprobada y rechazada por el proveedor.
- Reglas de negocio: moneda inválida y límite de monto `DEBIT`.
- Consulta con filtros y paginación (unitaria e integración JPA con H2).
- Flujo HTTP completo (`POST`/`GET`) con `MockMvc`.
- Mapeo hacia el contrato del proveedor.
- Enmascaramiento de datos sensibles en logs.

## Arquitectura

```text
Controller -> Service -> ProviderClient
			   |              |
			   v              v
		   Repository     MockProviderClient
			   |
			   v
		   Transaction (JPA Entity)
```

Los DTOs de la API y del proveedor están separados para evitar acoplar el contrato público al externo. `Transaction` representa únicamente la persistencia y `TransactionResponse` la respuesta HTTP.

### Estructura del proyecto

```text
controller  TransactionsController        endpoints REST
service     TransactionService            orquesta el flujo y las reglas de negocio
client      ProviderClient / MockProviderClient   integración con el proveedor externo
model       TransactionsModel             request de la API
dto         TransactionResponse, Provider*  contratos de respuesta y del proveedor
entity      Transaction                    entidad JPA
mapper      ProviderTransactionMapper      conversión entre modelo, entidad y proveedor
repository  TransactionRepository, TransactionSpecifications  acceso a datos y filtros
exception   TransactionValidationException, ValidationExceptionHandler  manejo de errores
config      SensitiveDataMasker            utilidades transversales (enmascarado de logs)
constants   Constants                      mensajes, ejemplos y valores compartidos
```

## Uso de IA

Se utilizó GitHub Copilot como apoyo durante el desarrollo para generar boilerplate, revisar decisiones de diseño, resolver dudas sobre Spring/JPA y ayudar con debugging y pruebas. Las decisiones finales, validación y comprensión del código son responsabilidad del autor.
