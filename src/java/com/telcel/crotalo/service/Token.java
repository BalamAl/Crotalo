package com.telcel.crotalo.service;

import com.telcel.crotalo.config.DeployEnvironment;
import okhttp3.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Token {
    public String token;
    public String url;
    public String user;
    public String pass;
    public String port;
    //generar cliente y consultar http
    private OkHttpClient client;


    public void Generar_token(){

        DeployEnvironment ambiente = new DeployEnvironment();
        ambiente.peticionIp();

        url = ambiente.getUrl();
        user = ambiente.getUser();
        pass = ambiente.getPass();
        port = ambiente.getPort();

        client = new OkHttpClient().newBuilder().build();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String requestBodyContent = "username=" + URLEncoder.encode(user)
                + "&password=" + URLEncoder.encode(pass);
        RequestBody body = RequestBody.create(mediaType, requestBodyContent);

        Request request = new Request.Builder()
                .url("http://" + url + ":" + port + "/api/jwt/login")
                .post(body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                token = response.body().string().trim();
                System.out.println("Token generado correctamente: " + token);
            } else {
                System.out.println("Error al generar el token. Código: " + response.code());
            }
        } catch (IOException e) {
            System.out.println("Error al generar el token de Remedy: " + e.getMessage());
        }
    }

    public String getToken() {
        return token;
    }
}
