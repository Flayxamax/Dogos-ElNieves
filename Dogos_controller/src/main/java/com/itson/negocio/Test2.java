/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.itson.negocio;

import com.itson.dogos_model.Venta;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Elkur
 */
public class Test2 {

    public static void main(String[] args) {
        
        ReporteNegocio rn = new ReporteNegocio();
        Calendar f1 = Calendar.getInstance();
        f1.set(2024, 4, 9);
        Calendar f2 = Calendar.getInstance();
        f2.set(2024, 4, 13);
        
        List<Venta> ventas = rn.getVentasEnPeriodo(f1, f2);
        System.out.println(ventas.size());
        for (Venta venta : ventas) {
            System.out.println(venta);
        }
    }
}
