package com.telcel.crotalo.pruebas;

import com.telcel.crotalo.service.Token;
import okhttp3.*;

import java.io.IOException;

public class PruebaPeticion {
    public static void main(String[] args) {
        Token tokenGenerator = new Token();
        tokenGenerator.Generar_token();
        String token = tokenGenerator.getToken();
        System.out.println("Token: [" + token + "]");

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://100.127.4.180:48100/api/arsys/v1/entry/CHG:Infrastructure Change/?fields=values(1)&q='7'=\"0\"AND'536870913'=\"2\"AND'1000000001'=\"TELCEL\"AND'1000001270'=\"SOFTWARE\"AND'1000001271'=\"APLICACION\"AND'1000001272'=\"RCONTROL\"AND'1000002268'=\"CROTALO\"AND'1000000015'=\"CORP-OPMA-ORDENES DE TRABAJO MSC\"AND'1000000568'=\"6000\"")
                .method("GET", null)
                .addHeader("Authorization", "AR-JWT " + token)
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println("Response Code: " + response.code());
            System.out.println("Response Body: " + response.body().string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
