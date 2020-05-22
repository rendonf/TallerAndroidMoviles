package com.example.tallermovilesandroid.ui.autoevaluacion;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.tallermovilesandroid.R;

public class AutoevaluacionFragment extends Fragment {

    private AutoevaluacionViewModel autoevaluacionViewModel;

    EditText p1;
    EditText lugar;
    EditText p2;
    EditText nombre;
    EditText apellido;
    EditText edad;
    EditText documento;
    Button boton;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        inicializarFormulario(root);

        return root;
    }

    public void inicializarFormulario(View root) {
        p1 = (EditText) root.findViewById(R.id.entradaP1);
        lugar = (EditText) root.findViewById(R.id.entradaLugar);
        p2 = (EditText) root.findViewById(R.id.entradaP2);
        nombre = (EditText) root.findViewById(R.id.entradaNombre);
        apellido = (EditText) root.findViewById(R.id.entradaApellido);
        edad = (EditText) root.findViewById(R.id.entradaEdad);
        documento = (EditText) root.findViewById(R.id.entradaLugar);
        boton = (Button) root.findViewById(R.id.button);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Hola");

                String[] reporte = new String[7];

                reporte[0] = p1.getText().toString();
                reporte[1] = lugar.getText().toString();
                reporte[2] = p2.getText().toString();
                reporte[3] = nombre.getText().toString();
                reporte[4] = apellido.getText().toString();
                reporte[5] = edad.getText().toString();
                reporte[6] = documento.getText().toString();

                /*for(int i=0; i<7; i++){
                    System.out.println(reporte[i]);
                }*/
            }
        });
    }
}