package com.example.memoriaparcial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ImageButton bt0, bt1, bt2, bt3;
    private Button btnivel;
    private TextView txtpuntaje;

    //Un diccionario para relacionar imagen a boton
    Map<ImageButton, Integer> fig_map = new HashMap<ImageButton, Integer>();

    private Boolean previo_click = false;
    private ImageButton previo;
    private Integer puntaje = 0;
    private Integer aciertos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt0 = (ImageButton)findViewById(R.id.bt0);
        bt1 = (ImageButton)findViewById(R.id.bt1);
        bt2 = (ImageButton)findViewById(R.id.bt2);
        bt3 = (ImageButton)findViewById(R.id.bt3);
        txtpuntaje = (TextView)findViewById(R.id.txtpuntaje);
        btnivel = (Button)findViewById(R.id.btnivel);

        btnivel.setClickable(false);
        btnivel.setEnabled(false);

        bt0.setTag("oculto");
        bt1.setTag("oculto");
        bt2.setTag("oculto");
        bt3.setTag("oculto");

        //Relacionando imagen a boton
        fig_map.put(bt0, R.mipmap.elefante);
        fig_map.put(bt1, R.mipmap.mono);
        fig_map.put(bt2, R.mipmap.elefante);
        fig_map.put(bt3, R.mipmap.mono);

    }

    public void Mostrar(View view)
    {
        ImageButton bt = (ImageButton)view;
        Boolean acierto = false;
        if (view.getTag() == "oculto")
        {
            bt.setImageResource(fig_map.get(bt));
            bt.setTag("visible");
        }
        //Hubo eleccion previa?
        if (previo_click)
        {
            //Verificar Jugada
            switch (bt.getId())
            {
                case R.id.bt0: case R.id.bt2:
                {
                    if (bt0.getTag() == "visible" && bt2.getTag() == "visible")
                    {
                        acierto = true;
                    }
                    break;
                }
                case R.id.bt1: case R.id.bt3:
                {
                    if (bt1.getTag() == "visible" && bt3.getTag() == "visible")
                    {
                        acierto = true;
                    }
                    break;
                }
            }
        }
        else
        {
            previo_click = true;
            previo = bt;
            return;
        }

        if (acierto)
        {
            Toast.makeText(this, "Muy bien!", Toast.LENGTH_SHORT).show();
            previo_click = false;
            previo = null;
            puntaje++;
            txtpuntaje.setText("Puntaje: " + puntaje.toString());
            aciertos++;
        }
        else
        {
            Toast.makeText(this, "Fallaste!", Toast.LENGTH_SHORT).show();
            puntaje--;
            txtpuntaje.setText("Puntaje: " + puntaje.toString());
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    previo.setImageResource(R.mipmap.reverse);
                    bt.setImageResource(R.mipmap.reverse);
                    previo.setTag("oculto");
                    bt.setTag("oculto");
                    previo_click = false;
                    previo = null;
                }
            }, 1500);

        }
        if (aciertos == 2)
        {
            btnivel.setClickable(true);
            btnivel.setEnabled(true);
        }
    }

    public void CambiarNivel(View view)
    {
        Intent i = new Intent(this, Nivel2.class);
        i.putExtra("puntaje", puntaje);
        startActivity(i);
    }
}