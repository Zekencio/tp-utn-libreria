**Resumen**
- **Proyecto**: API Rest y Frontend de un E-commerce de libros (Utilizando Spring Boot + Angular).
- **Propósito**: CRUD de libros, autores y géneros; gestión de usuarios, perfiles de vendedor, carrito, pagos (tarjetas) y ventas.

**Estructura del Proyecto**
- **Backend:** código Java con Spring Boot en `src/main/java/com/example/demo`.
- **Frontend:** aplicación Angular en `Frontend/bookstore`.
- **Base de datos:** utiliza una base de datos MYSQL, incluye script SQL en `Script SQL Funcional.sql` para crear esquema y tablas.

**Funcionalidad principal**
- Autenticación vía Basic Auth (encabezado `Authorization: Basic <token>`). El frontend gestiona el token en `localStorage`.
- Roles de usuario: `ROLE_CLIENT`, `ROLE_SELLER`, `ROLE_ADMIN`. Restricciones de acceso definidas en `SecurityConfig`.
- Registro y login de usuarios.
- Gestión de perfiles de vendedor: crear perfil, ver catálogo propio y ventas.
- CRUD de libros, autores y géneros; operaciones para vendedores y administradores según permisos.
- Carrito de compras (operaciones sobre libros: añadir, quitar), cálculo de precio y creación de venta.
- Gestión de tarjetas (asociadas a usuarios) para realizar ventas.
- Estadísticas: precio promedio de libros y cantidad de libros por autor.


**Frontend (Angular)**
- Carpeta: `Frontend/bookstore`.
- Rutas principales: `/` (home), `/about`, `/register`, `/login`, `/profile` (con subrutas para cliente, vendedor y admin).
- Componentes y servicios relevantes:
 - `AuthService` — login, registro, gestión de usuario y token
 - `BookService`, `AuthorService`, `GenreService`, `CardService`, `SellerProfileService` — consumo de la API REST
 - Páginas: `home`, `login`, `register`, `profile` (client, seller, admin), `seller catalog`, `seller sales`, `cards` y `compras`
- Scripts npm en `package.json`: `start` (se inicializa con proxy), `build`, `watch`, `test`.

**Base de datos**
- Archivo con esquema y tablas: `Script SQL Funcional.sql`. Crea tablas: `users`, `authors`, `sellers`, `genres`, `cards`, `sales`, `books`, tablas intermedias `books_genres`, `sales_books`.
- En `src/main/resources/application.properties` se usa MySQL, base de datos por defecto `book_store` y credenciales de ejemplo.

**Configuración importante**
- Archivo: `src/main/resources/application.properties` — contiene `spring.datasource.url`, `username`, `password`, `spring.jpa.hibernate.ddl-auto=update`.
- Puerto por defecto: Spring Boot usa `8080` (no sobrescrito en propiedades).

**Cómo ejecutar (desarrollo)**
- Requisitos: Java 21, Maven, Node.js y npm.

1) Backend (desde la raíz del proyecto):

```bash
# instalar y compilar (Linux / macOS)
./mvnw clean package

# ejecutar (dev)
./mvnw spring-boot:run
```


2) Base de datos:

```bash
# Crear la base de datos y tablas (MySQL)
# Abrir en cliente MySQL y ejecutar `Script SQL Funcional.sql`
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

