package com.desafio_itau.services;

import com.desafio_itau.entities.Transacao;
import com.desafio_itau.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

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

    public List<Double> getEstatisca(){
        try {
            LocalDateTime agora = LocalDateTime.now();

            List<Transacao> transacoes = findAllTransacao().stream()
                    .filter(t -> {
                        LocalDateTime dataTransacao = t.getDataHora();
                        long segundos = Duration.between(dataTransacao, agora).getSeconds();
                        return segundos <= 60;
                    })
                    .toList();

            List<Double> estaticas = new ArrayList<>();
            estaticas.add(getCount(transacoes));
            estaticas.add(getMax(transacoes));
            estaticas.add(getMed(transacoes));
            estaticas.add(getMin(transacoes));
            estaticas.add(getSum(transacoes));
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
