package com.example.tallermovilesandroid.ui.reportes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tallermovilesandroid.R;
import com.example.tallermovilesandroid.ui.login.Conexion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ReportesFragment extends Fragment {

    private ReportesViewModel reportesViewModel;
    ListView lv1;
    ListAdapter mAdapter;
    private List<Modelo> mLista = new ArrayList<>();
    private String nombres[]={"nando","nanda"};
    private String edad[]={"22","25"};
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reportesViewModel =
                ViewModelProviders.of(this).get(ReportesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        lv1 = (ListView) root.findViewById(R.id.lv1);
        LlenarLista();
        mAdapter = new ListAdapter(getActivity().getApplicationContext(),R.layout.list_item_reportes,mLista);
        lv1.setAdapter(mAdapter);
        return root;
    }

    public void LlenarLista(){
        Conexion conexion = new Conexion();
        String respuesta;
        try {
            List<String> con = new ArrayList<String>();
            respuesta = conexion.execute("http://www.mocky.io/v2/5ec7049c2f00007800426f53").get();
            JSONObject jsonObject = new JSONObject(respuesta);
            JSONArray lista = jsonObject.optJSONArray("reporte");

            for (int i = 0; i < lista.length(); i++) {

                JSONObject json_data = lista.getJSONObject(i);
                mLista.add(new Modelo(json_data.getString("nombre"),json_data.getString("apellido"),json_data.getString("edad"),json_data.getString("fecha")));

            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
