package com.technologies.venom.cep.api;

import com.technologies.venom.cep.models.OrdemServico;

import retrofit2.Call;
import retrofit2.http.GET;


public interface RESTService {

    //consultar CEP no webservice do ViaCEP
    @GET("ordemservico")
    Call<OrdemServico> consultarOrdemServico();
}
