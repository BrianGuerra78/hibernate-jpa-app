package org.bguerra.hibernateapp;

import jakarta.persistence.EntityManager;
import org.bguerra.hibernateapp.dominio.ClienteDTO;
import org.bguerra.hibernateapp.entity.Cliente;
import org.bguerra.hibernateapp.util.JpaUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HibernateQL {
    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();
        System.out.println("============= Consultar Todos =============");
        List<Cliente> clientes = em.createQuery("select c from Cliente c", Cliente.class).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("=========== Consulta por id ============");
        Cliente cliente = em.createQuery("select c from Cliente c where c.id=:idCliente", Cliente.class)
                .setParameter("idCliente", 1L).getSingleResult();
        System.out.println(cliente);

        System.out.println("====== Consulta solo el nombre por el id ======");
        String nombreCliente = em.createQuery("select c.nombre from Cliente c where c.id=:id", String.class)
                .setParameter("id", 2L)
                .getSingleResult();
        System.out.println(nombreCliente);

        System.out.println("===== Consulta por campos personalizados ====");
        Object[] objectCliente = em.createQuery("select c.id, c.nombre, c.apellido from Cliente c where c.id=:id", Object[].class)
                .setParameter("id", 1L).getSingleResult();
        Long id = (Long) objectCliente[0];
        String nombre = (String) objectCliente[1];
        String apellido = (String) objectCliente[2];
        System.out.println("id= " + id + " ,nombre= " + nombre + " ,apellido= " + apellido);

        System.out.println("===== Consulta por campos personalizados lista ====");
        List<Object[]> registros = em.createQuery("select c.id, c.nombre, c.apellido from Cliente c", Object[].class)
                .getResultList();
        for (Object[] reg : registros) {
            id = (Long) reg[0];
            nombre = (String) reg[1];
            apellido = (String) reg[2];
            System.out.println("id= " + id + " ,nombre= " + nombre + " ,apellido= " + apellido);
        }
        System.out.println("======== Consulta por cliente y forma de pago =========");
        registros = em.createQuery("select c, c.formaPago from Cliente c", Object[].class).getResultList();
        registros.forEach(reg2 -> {
            Cliente c = (Cliente) reg2[0];
            String formaPago = (String) reg2[1];
            System.out.println("FormaPago = " + formaPago + ", " + c);
        });

        System.out.println("=========== Consulta que puebla y devuelve objeto entity de una clase personalizada =======");
        clientes = em.createQuery("select new Cliente(c.nombre, c.apellido) from Cliente c", Cliente.class).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("=========== Consulta que puebla y devuelve objeto dto de una clase personalizada =======");
        List<ClienteDTO> clientesDTO = em.createQuery("select new org.bguerra.hibernateapp.dominio.ClienteDTO(c.nombre, c.apellido) from Cliente c", ClienteDTO.class).getResultList();
        clientesDTO.forEach(System.out::println);

        System.out.println("====== Consulta con nombres de clientes =========");
        List<String> nombres = em.createQuery("select c.nombre from Cliente c", String.class).getResultList();
        nombres.forEach(System.out::println);

        System.out.println("======= Consulta con nombre unico de cliente =========");
        nombres = em.createQuery("select distinct(c.nombre) from Cliente c", String.class).getResultList();
        nombres.forEach(System.out::println);

        System.out.println("============ Consulta con formas de pago unicas ==========");
        List<String> formasPago = em.createQuery("select distinct(c.formaPago) from Cliente c", String.class).getResultList();
        formasPago.forEach(System.out::println);

        System.out.println("============ Consulta con numero de formas de pago unicas ==========");
        Long totalFormasPago = em.createQuery("select count(distinct(c.formaPago)) from Cliente c", Long.class).getSingleResult();
        System.out.println(totalFormasPago);

        System.out.println("========= Consulta con nombre y apellido concatenados ==========");
        //nombres = em.createQuery("select concat(c.nombre, ' ', c.apellido) as nombreCompleto from Cliente c", String.class)
        //                .getResultList();
        nombres = em.createQuery("select c.nombre || ' ' || c.apellido as nombreCompleto from Cliente c", String.class)
                .getResultList();
        nombres.forEach(System.out::println);

        System.out.println("========= Consulta con nombre y apellido concatenados en mayuscula ==========");
        nombres = em.createQuery("select upper(concat(c.nombre, ' ', c.apellido)) as nombreCompleto from Cliente c", String.class)
                .getResultList();
        nombres.forEach(System.out::println);

        System.out.println("========== Consulta para buscar por nombre ==========");
        String param = "brian";
        clientes = em.createQuery("select c from Cliente c where c.nombre like : parametro", Cliente.class)
                .setParameter("parametro", "%" + param + "%").getResultList();
        clientes.forEach(System.out::println);

        System.out.println("======= Consultas por rangos =========");
        //clientes = em.createQuery("select c from Cliente c where c.id between 2 and 5", Cliente.class).getResultList();
        clientes = em.createQuery("select c from Cliente c where c.nombre between 'J' and 'P'", Cliente.class).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("========= Consulta con orden ==========");
        //clientes = em.createQuery("select c from Cliente c order by c.nombre asc", Cliente.class).getResultList();
        clientes = em.createQuery("select c from Cliente c order by c.nombre asc, c.apellido desc", Cliente.class).getResultList();
        clientes.forEach(System.out::println);

        System.out.println("===== Consulta con total de registros de la tabla =========");
        Long total = em.createQuery("select count(c) as total from Cliente c", Long.class).getSingleResult();
        System.out.println(total);

        System.out.println("======= Consulta con valor minimo del id ===========");
        Long minId = em.createQuery("select min(c.id) as minimo from Cliente c", Long.class).getSingleResult();
        System.out.println(minId);

        System.out.println("======= Consulta con valor max / ultimo id ===========");
        Long maxId = em.createQuery("select max(c.id) as maximo from Cliente c", Long.class).getSingleResult();
        System.out.println(maxId);

        System.out.println("======= Consulta con nombre y su largo ==========");
        registros = em.createQuery("select c.nombre, length(c.nombre) from Cliente c", Object[].class).getResultList();
        registros.forEach(reg -> {
            String nom = (String) reg[0];
            Integer largo = (Integer) reg[1];
            System.out.println("nombre= " + nom + ", largo= " + largo);
        });

        System.out.println("====== Consulta con el nombre mas corto ========");
        Integer minLargoNombre = em.createQuery("select min(length(c.nombre)) from Cliente c", Integer.class).getSingleResult();
        System.out.println(minLargoNombre);

        System.out.println("====== Consulta con el nombre mas largo ========");
        Integer maxLargoNombre = em.createQuery("select max(length(c.nombre)) from Cliente c", Integer.class).getSingleResult();
        System.out.println(maxLargoNombre);

        System.out.println("======== consultas resumen funciones agregaciones count min max avg sum ========");
        Object[] estadisticas = em.createQuery("select min(c.id), max(c.id), sum(c.id), count(c.id), avg(length(c.nombre)) from Cliente c", Object[].class)
                .getSingleResult();
        Long min = (Long) estadisticas[0];
        Long max = (Long) estadisticas[1];
        Long sum = (Long) estadisticas[2];
        Long count = (Long) estadisticas[3];
        Double avg = (Double) estadisticas[4];
        System.out.println("min= " + min + " , max= " + max + " , sum= " + sum + " , count= " + count + " , avg= " + avg);

        System.out.println("=========== Consulta con el nombre mas corto y su largo ========");
        registros = em.createQuery("select c.nombre, length(c.nombre) from Cliente c where " +
                        "length(c.nombre) = (select min(length(c.nombre)) from Cliente c)", Object[].class)
                .getResultList();
        registros.forEach(reg -> {
            String nom = (String) reg[0];
            Integer largo = (Integer) reg[1];
            System.out.println("nombre= " + nom + " , largo= " + largo);
        });

        System.out.println("======== Consulta para obtener el ultimo registro =========");
        Cliente ultimoCliente = em.createQuery("select c from Cliente c where c.id = (select max(c.id) from Cliente c)", Cliente.class)
                .getSingleResult();
        System.out.println(ultimoCliente);

        System.out.println("======= Consulta where in =======");
        //clientes = em.createQuery("select c from Cliente c where c.id in (1, 2, 10)", Cliente.class).getResultList();
        clientes = em.createQuery("select c from Cliente c where c.id in :ids", Cliente.class)
                .setParameter("ids", Arrays.asList(1L, 2L, 10L, 40L))
                .getResultList();
        clientes.forEach(System.out::println);

        em.close();
    }
}
