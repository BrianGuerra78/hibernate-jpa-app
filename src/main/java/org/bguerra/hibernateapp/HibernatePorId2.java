package org.bguerra.hibernateapp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.bguerra.hibernateapp.entity.Cliente;
import org.bguerra.hibernateapp.util.JpaUtil;

import java.util.Scanner;

public class HibernatePorId2 {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        EntityManager em = JpaUtil.getEntityManager();
        System.out.println("Ingrese el ID: ");
        Long id = s.nextLong();
        Cliente cliente = em.find(Cliente.class, id);
        System.out.println(cliente);

        Cliente cliente2 = em.find(Cliente.class, id);
        System.out.println(cliente2);
        em.close();
    }
}
