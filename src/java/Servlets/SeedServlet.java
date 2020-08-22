/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import DataAccessLayer.DataAccessImplementation;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SeedServlet implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        DataAccessImplementation seed = new DataAccessImplementation();
        seed.SeedData(); // TODO Auto-generated catch block
        // TODO Auto-generated catch block

    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub

    }
}
