package com.telcel.crotalo.pruebas;

import com.telcel.crotalo.service.Token;

public class TokenTest {
    public static void main(String[] args) {
        // Crear instancia de la clase Token
        Token tokenGenerator = new Token();

        // Llamar al mtodo para generar el token
        tokenGenerator.Generar_token();

        // Obtener el token generado
        String token = tokenGenerator.getToken();

        // Imprimir el token en consola
        System.out.println("Token generado: " + token);

        // Verificar si el token no es nulo ni vacío
        if (token != null && !token.isEmpty()) {
            System.out.println("El token se generó correctamente.");
        } else {
            System.out.println("Error: No se generó un token válido.");
        }
    }
}
