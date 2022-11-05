package br.com.futurodev.apispring.service;

import br.com.futurodev.apispring.model.Cliente;
import br.com.futurodev.apispring.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CadastroClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public Cliente salvar(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    @Transactional
    public void delete(Long idCliente){
        clienteRepository.deleteById(idCliente);
    }


    public List<Cliente> getClientes(){
        return clienteRepository.findAll();
    }


    public Cliente getClienteById(Long idCliente){
        return clienteRepository.findById(idCliente).get();
    }
}
