package com.desafio.services;

import com.desafio.entities.Transacao;
import com.desafio.repositories.TransacaoRepository;
import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    public List<Transacao> findAllTransacao(){
        try {
            return transacaoRepository.findAll();
        }catch (Exception e){
            e.getStackTrace();
            return null;
        }
    }

    @Transactional
    public void addTransacao(Transacao transacao) {
        try {
            transacaoRepository.save(transacao);
        }catch (Exception e){
            e.getCause();
        }

    }

    public JSONObject getEstatisca(){
        try {
            LocalDateTime agora = LocalDateTime.now();

            List<Transacao> transacoes = findAllTransacao().stream()
                    .filter(t -> {
                        LocalDateTime dataTransacao = t.getDataHora();
                        long segundos = Duration.between(dataTransacao, agora).getSeconds();
                        return segundos <= 60;
                    })
                    .toList();

            JSONObject estaticas = new JSONObject();
            estaticas.put("count", getCount(transacoes));
            estaticas.put("sum", getSum(transacoes));
            estaticas.put("avg", getMed(transacoes));
            estaticas.put("min", getMin(transacoes));
            estaticas.put("max", getMax(transacoes));
            return estaticas;
        } catch (Exception e){
            e.getCause();
            return null;
    }
    }



    public Double getMax(List<Transacao> values) throws Exception{
        return values.stream()
                .mapToDouble(Transacao::getValor)
                .max()
                .orElse(0.0);
    }

    public Double getMed(List<Transacao> values) throws Exception{
        return values.stream()
                .mapToDouble(Transacao::getValor)
                .average()
                .orElse(0.0);
    }

    public Double getCount(List<Transacao> values) throws Exception{
        return (double) values.stream()
                .map(Transacao::getValor)
                .count();
    }

    public Double getMin(List<Transacao> values) throws Exception{
        return values.stream()
                .mapToDouble(Transacao::getValor)
                .min()
                .orElse(0.0);
    }

    public Double getSum(List<Transacao> values) throws Exception{
        return values.stream()
                .mapToDouble(Transacao::getValor)
                .sum();
    }


}
