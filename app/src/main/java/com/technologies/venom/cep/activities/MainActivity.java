package com.technologies.venom.cep.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.technologies.venom.cep.R;
import com.technologies.venom.cep.api.RESTService;
import com.technologies.venom.cep.models.CEP;
import com.technologies.venom.cep.models.OrdemServico;
import com.technologies.venom.cep.utils.Mascara;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final String URL = "http://localhost:9090/datasnap/rest/TSM/";

    private Retrofit retrofit;
    private Button btnConsultar;
    private TextInputLayout lay;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lay = findViewById(R.id.txtinplayCEP);
        btnConsultar = findViewById(R.id.btnConsultarCEP);
        progressBar = findViewById(R.id.progressBarCEP);

        //configurando como invisível
        progressBar.setVisibility(View.GONE);

        //configura os recursos do retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)                                       //endereço do webservice
                .addConverterFactory(GsonConverterFactory.create()) //conversor
                .build();

        btnConsultar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnConsultarCEP:
                consultarOrdemServico();
                break;
        }
    }

    private void consultarOrdemServico(){

        //instanciando a interface
        RESTService restService = retrofit.create(RESTService.class);

        //passando os dados para consulta
        Call<OrdemServico> call = restService.consultarOrdemServico();

        //exibindo a progressbar
        progressBar.setVisibility(View.VISIBLE);

        //colocando a requisição na fila para execução
        call.enqueue(new Callback<OrdemServico>() {
            @Override
            public void onResponse(Call<OrdemServico> call, Response<OrdemServico> response) {
                if (response.isSuccessful()){
                    OrdemServico ordemServico = response.body();
                    Toast.makeText(getApplicationContext(), "OS consultado com sucesso", Toast.LENGTH_LONG).show();

                    //escondendo a progressbar
                    progressBar.setVisibility(View.GONE);                }
            }

            @Override
            public void onFailure(Call<OrdemServico> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Ocorreu um erro ao tentar consultar OS. Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();

                //escondendo a progressbar
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
