package com.example.capturahoras.datos.API;

public class ActualizarCodigos {
    /*private static Exception exception=null;

    public static boolean actualizarCodigo(CodigoVehiculos codigoVehiculos){
        String url = Settings.URL+"/vehiculos/PutCodigoVehiculos";

        try {
            JSONObject jsonObject = codigoVehiculos.toJson();
            Request request = Bridge.put(url).body(jsonObject).request();

            Response response = request.response();
            if (response.isSuccess()) {
                return true;
            }else {
                if (response.code() == 409) {
                    exception = new Exception("la peticion no se pudo resolver");
                    return false;
                } else {
                    exception = new Exception("error inesperado, "+response.code());
                    return false;
                }
            }

        } catch(BridgeException e){
            e.printStackTrace();
            exception = e;
            return false;
        }catch (Exception e){
            e.printStackTrace();
            exception = e;
            return false;
        }
    }

    public static Exception getException() {
        return exception;
    }*/
}
