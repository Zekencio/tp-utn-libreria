**Resumen**
- **Proyecto**: API Rest y Frontend de un E-commerce de libros (Utilizando Spring Boot + Angular).
- **Propósito**: CRUD de libros, autores y géneros; gestión de usuarios, perfiles de vendedor, carrito, pagos (tarjetas) y ventas.

**Resumen**
- **Proyecto**: API Rest y Frontend de un E-commerce de libros (Spring Boot + Angular).
- **Propósito**: CRUD de libros, autores y géneros; gestión de usuarios, perfiles de vendedor, carrito, pagos (tarjetas) y ventas.
- **Frontend:** aplicación Angular en `Frontend/bookstore`.
- **Base de datos:** utiliza una base de datos MYSQL, incluye script SQL en `Script SQL Funcional.sql` para crear esquema y tablas.
**Funcionalidad principal**
- Autenticación vía JWT Bearer Token (encabezado `Authorization: Bearer <token>`). El backend emite JWT en el login y el frontend guarda el token en `localStorage` bajo la clave `jwtToken`.
- Registro: `POST /api/users/register` — crea usuario (retorna `UserDTO`), pero ya no entrega credenciales; el cliente debe autenticarse contra `POST /api/auth/login` para obtener el JWT.
- **Gestion:** el proyecto fue gestionado utilizando el siguente tablero JIRA: `https://chevi0546.atlassian.net/jira/software/projects/ALEC/summary?atlOrigin=eyJpIjoiYWZlMDdmN2M5M2ZjNDljODhkNTViYzljM2MyMWFkMTEiLCJwIjoiaiJ9`

**Autenticación y roles**
- Tipo: JWT (Bearer). El servidor expone:
	- `POST /api/auth/login` — recibe JSON `{ "name": "...", "password": "..." }`, responde `{ "token": "<jwt>", "user": { ... } }`.
	- `POST /api/users/register` — registro (como antes) pero no almacena/retorna credenciales en `localStorage`.
- Frontend: el token se guarda en `localStorage` con la clave `jwtToken`. Un interceptor añade `Authorization: Bearer <token>` a peticiones salientes.
- Rutas públicas: consultas de géneros, autores y libros. Operaciones de creación/actualización/eliminación siguen requiriendo autenticación.
- Roles de usuario: `ROLE_CLIENT`, `ROLE_SELLER`, `ROLE_ADMIN`. Restricciones de acceso definidas en `SecurityConfig`.
- Registro y login de usuarios.
**Configuración importante**
- Archivo: `src/main/resources/application.properties` — contiene `spring.datasource.url`, `username`, `password`, `spring.jpa.hibernate.ddl-auto=update`.
- JWT: nuevas propiedades configurables (sugeridas):
	- `app.jwt.secret` — secreto usado para firmar JWT (debe ser largo y fuerte; configure en `application.properties` o variable de entorno).
	- `app.jwt.expiration-ms` — tiempo de expiración del token en milisegundos (por defecto 3600000 = 1h).
	Ejemplo en `application.properties`:

```properties
app.jwt.secret=una_clave_muy_larga_y_segura_para_hs256_DEMASIADO_CORTA_NO
app.jwt.expiration-ms=3600000
```

- Puerto por defecto: Spring Boot usa `8080`.
- Carrito de compras (operaciones sobre libros: añadir, quitar), cálculo de precio y creación de venta.
- Gestión de tarjetas (asociadas a usuarios) para realizar ventas.
**Cómo ejecutar (desarrollo)**
- Requisitos: Java 21, Maven, Node.js y npm.

1) Backend (desde la raíz del proyecto):

```bash
./mvnw clean package

./mvnw spring-boot:run
```

3) Frontend (desde `Frontend/bookstore`):

```bash
cd Frontend/bookstore
npm install
npm start
```
 - `BookService`, `AuthorService`, `GenreService`, `CardService`, `SellerProfileService` — consumo de la API REST
 - Páginas: `home`, `login`, `register`, `profile` (client, seller, admin), `seller catalog`, `seller sales`, `cards` y `compras`
**Notas sobre seguridad y migración a JWT**
- Migración: la autenticación anterior basada en HTTP Basic fue reemplazada por JWT. Código relevante:
	- Backend: `src/main/java/com/example/demo/configuration/JwtUtil.java`, `JwtAuthenticationFilter.java`, `AuthController.java`, y cambios en `SecurityConfig.java`.
	- Frontend: `Frontend/bookstore/src/app/services/auth.service.ts` (ahora usa `/api/auth/login` y guarda `jwtToken`) y `AuthInterceptor` que añade `Authorization: Bearer <token>`.
- Evite almacenar contraseñas en `localStorage`. Este repositorio ya no guarda credenciales Base64; sólo se guarda el JWT bajo `jwtToken`.
- Producción: considere enviar JWT en cookie `HttpOnly` y usar refresh tokens para mayor seguridad. Asegúrese de rotar/ocultar `app.jwt.secret` y usar HTTPS.

**Base de datos**
**Archivos relevantes**
- `pom.xml` — dependencias y plugin de Spring Boot (agregadas dependencias JJWT para JWT).
- `src/main/java/com/example/demo` — código principal (controladores, servicios, repositorios, DTOs).
- `src/main/resources/application.properties` — configuración de la aplicación (añadir `app.jwt.*` si desea cambiar valores por defecto).
- `Frontend/bookstore` — cliente Angular (interceptor y `auth.service` actualizados para JWT).
- `Script SQL Funcional.sql` — esquema de base de datos.
- Puerto por defecto: Spring Boot usa `8080` (no sobrescrito en propiedades).

**Cómo ejecutar (desarrollo)**
- Requisitos: Java 21, Maven, Node.js y npm.

1) Backend (desde la raíz del proyecto):

```bash
./mvnw clean package

./mvnw spring-boot:run
```


2) Base de datos:

```bash
mysql -u root -p < "Script SQL Funcional.sql"
```


3) Frontend (desde `Frontend/bookstore`):

```bash
cd Frontend/bookstore
npm install
npm start
```

El frontend usa un `proxy.conf.json` para redirigir llamadas a la API local (por ejemplo a `http://localhost:8080`).

**Autenticación y roles**
- El proyecto usa autenticación HTTP Basic. El frontend codifica `name:password` en Base64 y lo guarda en `localStorage` (`basicAuth`).
- Rutas públicas: consultas de géneros, autores y libros. Operaciones de creación/actualización/eliminación requieren autenticación.

**Notas y limitaciones**
- El script SQL incluido contiene ejemplos y puede requerir ajustes (nombres de tablas/constraints) antes de ejecutarlo tal cual.
- `application.properties` contiene credenciales de ejemplo; cámbialas en producción.
- La protección por roles y rutas está configurada en `SecurityConfig` (revisar matchers si se desea otro comportamiento).

**Archivos relevantes**
- `pom.xml` — dependencias y plugin de Spring Boot.
- `src/main/java/com/example/demo` — código principal (controladores, servicios, repositorios, DTOs).
- `src/main/resources/application.properties` — configuración de la aplicación.
- `Frontend/bookstore` — cliente Angular.
- `Script SQL Funcional.sql` — esquema de base de datos.

**Módulos Backend**
- **`author`**: gestión de autores (CRUD). Endpoints:
 - `GET /api/authors` - lista de autores,
 - `GET /api/authors/{id}` - detalles de autor especifico,
 - `POST /api/authors` - agregar autor,
 - `PUT /api/authors/{id}` - actualizacion de autor,
 - `DELETE /api/authors/{id}`- eliminar autor.
  
- **`book`**: gestión de libros, operaciones de carrito y estadísticas. Endpoints principales:
 - `GET /api/books` — lista reducida de libros
 - `GET /api/books/{id}` — detalle
 - `GET /api/books/author/{id}` — libros por autor
 - `GET /api/books/genre/{id}` — libros por género
 - `POST /api/books` — crear libro
 - `PUT /api/books/update/{id}` — editar libro
 - `DELETE /api/books/{id}` — eliminar libro
 - `PUT /api/books/add/{id}` y `PUT /api/books/remove/{id}` — añadir / quitar unidades al carrito
 - `GET /api/books/statistics/averageprice` y `GET /api/books/statistics/books-per-author` — estadísticas
   
- **`genre`**: gestión de géneros (CRUD). Endpoints:
 - `GET /api/genres` - listado de generos,
 - `GET /api/genres/{id}`- detalles de generos,
 - `POST /api/genres` - agragado de generos,
 - `PUT /api/genres/{id}` - actualizacion de generos,
 - `DELETE /api/genres/{id}` - eliminacion de generos.
   
- **`user`**: registro, autenticación (Basic Auth), perfil y operaciones del usuario actual. Endpoints:
 - `POST /api/users/register` — registro,
 - `GET /api/users/me` — obtener usuario actual (autenticado),
 - `PUT /api/users/update` — actualizar usuario autenticado,
 - `DELETE /api/users/delete` — eliminar usuario autenticado.
   
- **`cards`**: gestión de tarjetas (CRUD) asociadas a usuarios (para pagos). Endpoints:
 - `GET /api/cards` - listado de tarjetas,
 - `GET /api/cards/{id}` - detalles de trjeta,
 - `POST /api/cards` - agregado de tarjeta,
 - `PUT /api/cards/{id}` - actualizacion de tarjeta,
 - `DELETE /api/cards/{id}` - eliminacion de tarjeta.
   
- **`sale`**: gestión de ventas. Creación de venta (usa el carrito / tarjetas), lectura y eliminación. Endpoints:
 - `GET /api/sales` - listado de ventas,
 - `GET /api/sales/{id}` - detalles de ventas,
 - `POST /api/sales` (crea venta a partir de id de tarjeta/usuario),
 - `PUT /api/sales/{id}` - actualizacion de ventas,
 - `DELETE /api/sales/{id}` - eliminacion de ventas.
   
- **`sellerprofile`**: perfil de vendedor — creación y administración de catálogo/ventas por vendedor. Endpoints:
 - `GET /api/sellerProfiles` - visualizacion de listado de vendedores,
 - `GET /api/sellerProfiles/{id}` - visaluzacion de detales de vendedor,
 - `POST /api/sellerProfiles` - cracion de perfil de vendedor,
 - `GET /api/sellerProfiles/me` - visaluzacion de detales de vendedor,
 - `PUT /api/sellerProfiles/update` -  actualizacion de detalles de vendedor,
 - `DELETE /api/sellerProfiles/delete` - eliminacion de vendedor.
    
- **`configuration`**: seguridad y validaciones
 - `SecurityConfig` — configuración de Spring Security (autenticación Basic, control de accesos por método HTTP y rutas).
 - `PasswordConfig` — `PasswordEncoder` (BCrypt).
 - `ValidationHandler` — manejo centralizado de errores de validación.
- **`exceptions`**: excepciones personalisadas (`NotFoundException`, `AlreadyExistingException`, `UnautorizedException`, `InsufficientStockException`).

