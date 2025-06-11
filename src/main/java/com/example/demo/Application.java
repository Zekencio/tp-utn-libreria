package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

// evitar duplicados
// crear un editar perfil para admins
// cuando se realiza una venta reducir stock o interrumpir la transaccion si no hay disponibles
// agrgar rutas a generos y libros que debuelban estadisicas generales

// donde y como reducimos stock
// conseguir el usuario desde el sistema
// error en la ruta de get libros
// deberiamos gregar algo mas
// consulta sobre lo entrgable