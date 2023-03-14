package org.bguerra.hibernateapp;

import jakarta.persistence.EntityManager;
import org.bguerra.hibernateapp.entity.Cliente;
import org.bguerra.hibernateapp.services.ClienteService;
import org.bguerra.hibernateapp.services.ClienteServiceImpl;
import org.bguerra.hibernateapp.util.JpaUtil;

import java.util.List;
import java.util.Optional;

public class HibernateCrudService {
    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();

        ClienteService service = new ClienteServiceImpl(em);
        System.out.println("============ Listar ============");
        List<Cliente> clientes = service.listar();
        clientes.forEach(System.out::println);
        System.out.println("============ Obtener por id ==============");
        Optional<Cliente> optionalCliente = service.porId(1L);
        /*if (optionalCliente.isPresent()){
            System.out.println(optionalCliente.get());
        }*/
        optionalCliente.ifPresent(System.out::println);
        System.out.println("=============== Insertar ==============");
        Cliente cliente = new Cliente();
        cliente.setNombre("Luci");
        cliente.setApellido("Mena");
        cliente.setApellido("Paypal");
        service.guardar(cliente);
        System.out.println("Cliente guardado con exito");
        service.listar().forEach(System.out::println);

        System.out.println("============= Editar Cliente ============");
        Long id = cliente.getId();
        optionalCliente = service.porId(id);
        optionalCliente.ifPresent(c -> {
            c.setFormaPago("Mercado Pago");
            service.guardar(c);
            System.out.println("Cliente editado con exito!");
            service.listar().forEach(System.out::println);
        });
        System.out.println("==========  Eliminar ===========");
        id = cliente.getId();
        optionalCliente = service.porId(id);
        optionalCliente.ifPresent(c -> {
          service.eliminar(c.getId());
            System.out.println("Cliente eliminado con exito!");
            service.listar().forEach(System.out::println);
        });
        /*if (optionalCliente.isPresent()){
            service.eliminar(id);
        }*/
        em.close();
    }
}
