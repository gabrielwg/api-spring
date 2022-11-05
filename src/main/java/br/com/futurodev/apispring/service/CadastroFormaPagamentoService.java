package br.com.futurodev.apispring.service;

import br.com.futurodev.apispring.model.FormaPagamento;
import br.com.futurodev.apispring.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CadastroFormaPagamentoService {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;


    @Transactional
    public FormaPagamento salvar(FormaPagamento pagamento){
        return formaPagamentoRepository.save(pagamento);
    }

    @Transactional
    public void delete(Long idFormaPagamentoModel){
        formaPagamentoRepository.deleteById(idFormaPagamentoModel);
    }


    public List<FormaPagamento> getFormaPagamentos(){
        return formaPagamentoRepository.findAll();
    }

    public FormaPagamento getFormaPagamentoById(Long idFormaPagamento){
        return formaPagamentoRepository.findById(idFormaPagamento).get();
    }
}
