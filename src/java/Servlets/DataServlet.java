/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import BusinessModel.RoomModel;
import BusinessModel.RoomTypeModel;
import BusinessModel.StudentRoomViewModel;
import DataAccessLayer.DataAccessImplementation;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Bishodeep
 */
public class DataServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JSONException {
        response.setContentType("text/html;charset=UTF-8");
        String form = request.getParameter("type");
        int id = 0;
        if (request.getParameter("id") != null) {
            id = Integer.parseInt(request.getParameter("id"));
        }
        DataAccessImplementation room = new DataAccessImplementation();
        switch (form) {
            case "type":
                List<RoomTypeModel> listRoomType = room.getRoomtype();
                JSONArray json = new JSONArray();
                for (RoomTypeModel roomtype : listRoomType) {
                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("id", roomtype.getId());
                        obj.put("type", roomtype.getType());
                        obj.put("available", roomtype.getAvailable());
                        json.put(obj);
                    } catch (JSONException ex) {
                        Logger.getLogger(DataServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.getWriter().write(json.toString());
                break;
            case "roomassign":
                List<StudentRoomViewModel> studentRooms = room.getStudentRooms();
                JSONArray jsonassign = new JSONArray();
                for (StudentRoomViewModel studentroom : studentRooms) {
                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("name", studentroom.getName());
                        obj.put("type", studentroom.getRoomType());
                        obj.put("description", studentroom.getDescription());
                        obj.put("payment", studentroom.getPayment());
                        jsonassign.put(obj);
                    } catch (JSONException ex) {
                        Logger.getLogger(DataServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.getWriter().write(jsonassign.toString());
                break;
            case "room":
                List<RoomModel> listRooms = room.getRooms();
                JSONArray jsonroom = new JSONArray();
                for (RoomModel roomtype : listRooms) {
                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("id", roomtype.getId());
                        obj.put("type", roomtype.getTypeName());
                        obj.put("available", roomtype.getAvailable());
                        obj.put("description", roomtype.getDescription());
                        obj.put("location", roomtype.getLocation());
                        obj.put("monthlycharge", roomtype.getMonthlyCharge());
                        jsonroom.put(obj);
                    } catch (JSONException ex) {
                        Logger.getLogger(DataServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.getWriter().write(jsonroom.toString());
                break;
            case "updateroomtype":
                RoomTypeModel roomModel = room.getRoomtypeId(id);
                JSONObject obj = new JSONObject();
                try {
                    obj.put("id", roomModel.getId());
                    obj.put("type", roomModel.getType());
                    obj.put("available", roomModel.getAvailable());
                } catch (JSONException ex) {
                    Logger.getLogger(DataServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                response.getWriter().write(obj.toString());
                break;
            case "updateroom":
                RoomModel roomToUpdate = room.getRoomId(id);
                JSONObject objroom = new JSONObject();
                try {
                    objroom.put("id", roomToUpdate.getId());
                    objroom.put("type", roomToUpdate.getTypeName());
                    objroom.put("available", roomToUpdate.getAvailable());
                    objroom.put("location", roomToUpdate.getLocation());
                    objroom.put("charge", roomToUpdate.getMonthlyCharge());
                    objroom.put("description", roomToUpdate.getDescription());
                } catch (JSONException ex) {
                    Logger.getLogger(DataServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                response.getWriter().write(objroom.toString());
                break;
            case "studentroom":
                HttpSession sessionuser = request.getSession(false);
                int studentid = (int) sessionuser.getAttribute("userid");
                RoomModel studentroom = room.getUserRoom(studentid);
                JSONObject objstudent = new JSONObject();
                try {
                    objstudent.put("id", studentroom.getId());
                    objstudent.put("type", studentroom.getTypeName());
                    objstudent.put("available", studentroom.getAvailable());
                    objstudent.put("location", studentroom.getLocation());
                    objstudent.put("charge", studentroom.getMonthlyCharge());
                    objstudent.put("description", studentroom.getDescription());
                } catch (JSONException ex) {
                    Logger.getLogger(DataServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                response.getWriter().write(objstudent.toString());
                break;

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(DataServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(DataServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
