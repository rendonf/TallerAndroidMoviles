package com.example.tallermovilesandroid.ui.reportes;

public class Modelo {
    private String nombre;
    private String apellido;
    private String edad;
    private String fecha;

    public Modelo(String nombre,String apellido, String edad, String fecha){
        this.nombre=nombre;
        this.apellido=apellido;
        this.edad=edad;
        this.fecha=fecha;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
