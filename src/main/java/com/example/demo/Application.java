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
// cuando se realiza una venta reducir stock o interrumpir la transacción si no hay disponibles
// agregar rutas a géneros y libros que devuelvan estadísticas generales

// donde y como reducimos stock
// conseguir el usuario desde el sistema
// error en la ruta de get libros
// consultar si deberíamos agregar algo más
// consultar sobre lo entregable