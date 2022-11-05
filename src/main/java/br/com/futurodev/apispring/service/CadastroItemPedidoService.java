package br.com.futurodev.apispring.service;

import br.com.futurodev.apispring.model.ItemPedido;
import br.com.futurodev.apispring.repository.ItemPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CadastroItemPedidoService {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Transactional
    public void deleteItemPedidoById(Long idItemPedido){
        itemPedidoRepository.deleteById(idItemPedido);
    }


    @Transactional
    public ItemPedido salvar(ItemPedido item){
        return itemPedidoRepository.save(item);
    }

    @Transactional
    public void deleteItemPedido(ItemPedido itemPedido){
        itemPedidoRepository.delete(itemPedido);
    }

    public ItemPedido getItemPedido(Long idPedido, Long idItemPedido){
       return itemPedidoRepository.getItemPedido(idPedido, idItemPedido);
    }

    public List<ItemPedido> getItensPedido(){
        return itemPedidoRepository.findAll();
    }

}
