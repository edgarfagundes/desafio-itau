package com.desafio.controllers;

import com.desafio.entities.Transacao;
import com.desafio.services.TransacaoService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @GetMapping("/transacoes")
    @ResponseStatus(HttpStatus.OK)
    public List<Transacao> getTransacoes(){
            return transacaoService.findAllTransacao();
    }

    @GetMapping("/estatistica")
    @ResponseStatus(HttpStatus.OK)
    public String getEstatistica(){
        String jsonsFormated = transacaoService.getEstatisca().toString();
        return jsonsFormated;
    }

    @PostMapping("/transacao")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTransacao(@RequestBody Transacao transacao){
        transacaoService.addTransacao(transacao);
    }
}
