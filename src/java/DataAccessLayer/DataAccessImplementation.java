/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer;

import BusinessModel.RegistrationModel;
import BusinessModel.RoomModel;
import BusinessModel.RoomTypeModel;
import BusinessModel.StudentRoomViewModel;
import BusinessModel.UserModel;
import DataAccessLayer.Helper.DatabaseConnection;
import Helper.Hash;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bishodeep
 */
public class DataAccessImplementation implements DataAccessInterface {

    @Override
    public boolean registerUser(RegistrationModel registerModel) {
        try {
            Connection con = DatabaseConnection.initializeDatabase();
            PreparedStatement queryRoles = con.prepareStatement("select *  from role where role_name=?");
            queryRoles.setString(1, "student");
            ResultSet role = queryRoles.executeQuery();
            int userRoleId = 0;
            if (role.next()) {
                userRoleId = role.getInt("id");
            }
            String hashPassword = Hash.hashValue(registerModel.getPassword());
            PreparedStatement queryUseCheck = con.prepareStatement("select *  from user_info where user_name=? and password=?");
            queryUseCheck.setString(1, registerModel.getUsername());
            queryUseCheck.setString(2, hashPassword);
            ResultSet userCheck = queryUseCheck.executeQuery();
            if (userCheck.next()) {
                return false;
            }
            PreparedStatement queryUser = con
                    .prepareStatement("insert into user_info(id,user_name,password,role_id) values(?,?, ?,?)");
            queryUser.setString(1, null);
            queryUser.setString(2, registerModel.getUsername());
            queryUser.setString(3, hashPassword);
            queryUser.setInt(4, userRoleId);
            queryUser.executeUpdate();
            PreparedStatement queryUserGet = con.prepareStatement("select *  from user_info where user_name=? and password=?");
            queryUserGet.setString(1, registerModel.getUsername());
            queryUserGet.setString(2, hashPassword);
            ResultSet user = queryUserGet.executeQuery();
            if (user.next()) {
                PreparedStatement queryStudent = con.prepareStatement("insert into "
                        + "student_details(id,Student_name,Student_age,Student_address,Student_contact"
                        + ",User_id)"
                        + " values(?,?,?,?,?,?)");
                queryStudent.setString(1, null);
                queryStudent.setString(2, registerModel.getName());
                queryStudent.setInt(3, registerModel.getAge());
                queryStudent.setString(4, registerModel.getAddress());
                queryStudent.setInt(5, registerModel.getContact());
                queryStudent.setInt(6, user.getInt("id"));
                queryStudent.execute();
                queryStudent.close();
            }
            queryUser.close();
            con.close();
            return true;
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;

        }
    }

    @Override
    public UserModel validateUser(String username, String password) {
        UserModel userModel = null;
        try {
            Connection con = DatabaseConnection.initializeDatabase();
            String hashPassword = Hash.hashValue(password);
            PreparedStatement queryUser = con.prepareStatement("select * from user_info where user_name=? and password=? ");
            queryUser.setString(1, username);
            queryUser.setString(2, hashPassword);
            ResultSet user = queryUser.executeQuery();
            if (user.next()) {
                String userNameDb = user.getString("user_name");
                int roleIdDb = user.getInt("role_id");
                int userid = user.getInt("id");
                PreparedStatement queryRoles = con.prepareStatement("select * from role where id=? ");
                queryRoles.setInt(1, roleIdDb);
                ResultSet role = queryRoles.executeQuery();
                if (role.next()) {
                    String roleNameDb = role.getString("role_name");
                    userModel = new UserModel(userid, userNameDb, roleIdDb, roleNameDb);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return userModel;
    }

    @Override
    public boolean addRoom(RoomModel roomModel) {
        try {
            Connection con = DatabaseConnection.initializeDatabase();
            PreparedStatement queryRoomGet = con.prepareStatement("select * from room_type where id=?");
            queryRoomGet.setInt(1, roomModel.getTypeID());
            ResultSet room = queryRoomGet.executeQuery();
            if (room.next()) {
                String typename = room.getString("typename");
                PreparedStatement queryRoom = con.prepareStatement("insert into room_details values(?,?,?,?,?,?,?)");
                queryRoom.setString(1, null);
                queryRoom.setInt(2, roomModel.getTypeID());
                queryRoom.setString(3, roomModel.getLocation());
                queryRoom.setInt(4, roomModel.getMonthlyCharge());

                queryRoom.setBoolean(5, roomModel.getAvailable());
                queryRoom.setString(6, roomModel.getDescription());
                queryRoom.setString(7, typename);
                queryRoom.execute();
                queryRoom.close();
            }

            con.close();
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }

    @Override
    public List<RoomModel> getRooms() {
        try {
            List<RoomModel> listroomTypeModel = new ArrayList<>();
            Connection con = DatabaseConnection.initializeDatabase();
            PreparedStatement queryRoomGet = con.prepareStatement("select * from room_details");
            ResultSet rooms = queryRoomGet.executeQuery();
            while (rooms.next()) {
                String location = rooms.getString("room_location");
                String description = rooms.getString("room_description");
                boolean available = rooms.getBoolean("room_available");
                String typename = rooms.getString("roomtype_name");
                int charge = rooms.getInt("monthly_charge");
                int id = rooms.getInt("id");
                int typeid = rooms.getInt("type_id");
//				PreparedStatement queryRoomavailable = con.prepareStatement(
//						"select * from room_details where id=?");
//				queryRoomavailable.setInt(1, id);
//				ResultSet roomavailable = queryRoomavailable.executeQuery();

//				if (roomavailable.next()) {
//					int available = roomavailable.getInt("available");
//					boolean isAvailable = true;
//					if (available <= 0) {
//						isAvailable = false;
//						return false;
//					}
//				}
                RoomModel roomTypeModel = new RoomModel(id, typeid, typename, available, location, charge, description);
                listroomTypeModel.add(roomTypeModel);
            }
            con.close();
            return listroomTypeModel;
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    @Override
    public RoomModel getRoomId(int id) {
        try {
            RoomModel roomTypeModel = null;
            Connection con = DatabaseConnection.initializeDatabase();
            PreparedStatement queryRoomGet = con.prepareStatement("select * from room_details where id=?");
            queryRoomGet.setInt(1, id);
            ResultSet rooms = queryRoomGet.executeQuery();
            if (rooms.next()) {
                String location = rooms.getString("room_location");
                String description = rooms.getString("room_description");
                boolean available = rooms.getBoolean("room_available");
                String typename = rooms.getString("roomtype_name");
                int charge = rooms.getInt("monthly_charge");
                int idroom = rooms.getInt("id");
                int typeid = rooms.getInt("type_id");
                roomTypeModel = new RoomModel(idroom, typeid, typename, available, location, charge, description);
            }
            return roomTypeModel;
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    @Override
    public RoomModel getUserRoom(int userid) {
        try {
            RoomModel roomTypeModel = null;
            Connection con = DatabaseConnection.initializeDatabase();
            PreparedStatement queryStudent = con.prepareStatement("select * from student_details where User_id=?");
            queryStudent.setInt(1, userid);
            ResultSet student = queryStudent.executeQuery();
            if (student.next()) {
                PreparedStatement queryStudentRoom = con
                        .prepareStatement("select * from student_room where student_id=?");
                queryStudentRoom.setInt(1, student.getInt("id"));
                ResultSet room = queryStudentRoom.executeQuery();
                if (room.next()) {
                    PreparedStatement queryRoomGet = con.prepareStatement("select * from room_details where id=?");
                    queryRoomGet.setInt(1, room.getInt("room_id"));
                    ResultSet rooms = queryRoomGet.executeQuery();
                    if (rooms.next()) {
                        String location = rooms.getString("room_location");
                        String description = rooms.getString("room_description");
                        boolean available = rooms.getBoolean("room_available");
                        String typename = rooms.getString("roomtype_name");
                        int charge = rooms.getInt("monthly_charge");
                        int idroom = rooms.getInt("id");
                        int typeid = rooms.getInt("type_id");
                        roomTypeModel = new RoomModel(idroom, typeid, typename, available, location, charge,
                                description);
                    }
                }
            }

            return roomTypeModel;
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    @Override
    public boolean changePaymentStaus(int id) {
        try {
            Connection con = DatabaseConnection.initializeDatabase();
            PreparedStatement queryRoomCheck = con
                    .prepareStatement("select * from student_details where User_id=?");
            queryRoomCheck.setInt(1, id);
            ResultSet roomcheck = queryRoomCheck.executeQuery();
            if (roomcheck.next()) {
                PreparedStatement queryRoom = con.prepareStatement("update accommodation.student_room"
                        + " set payement_status=1 where student_id=?");
                queryRoom.setInt(1, roomcheck.getInt("id"));
                boolean updated = queryRoom.executeUpdate() > 0;
                return updated;
            }
            return false;
        } catch (Exception ex) {
            return false;
        }

    }

    @Override
    public List<StudentRoomViewModel> getStudentRooms() {
        try {
            List<StudentRoomViewModel> studentRooms = new ArrayList<StudentRoomViewModel>();
            Connection con = DatabaseConnection.initializeDatabase();
            PreparedStatement queryRoomGet = con.prepareStatement("select sd.Student_name,rd.roomtype_name,rd.room_description,sr.payement_status from accommodation.student_room as sr\n"
                    + "left join accommodation.room_details as rd on sr.room_id =rd.id\n"
                    + "left join accommodation.student_details as sd on sr.student_id =sd.id");
            ResultSet rooms = queryRoomGet.executeQuery();
            if (rooms.next()) {
                String name = rooms.getString("Student_name");
                String description = rooms.getString("room_description");
                String roomType = rooms.getString("roomtype_name");
                boolean payment = rooms.getBoolean("payement_status");
                StudentRoomViewModel studentRoom = new StudentRoomViewModel(name, description, roomType, payment);
                studentRooms.add(studentRoom);
            }
            return studentRooms;
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    @Override
    public boolean insertEditRoom(RoomModel roomModel) {
        try {
            Connection con = DatabaseConnection.initializeDatabase();
            PreparedStatement queryRoomGet = con.prepareStatement("select * from room_type where id=?");
            queryRoomGet.setInt(1, roomModel.getTypeID());
            ResultSet room = queryRoomGet.executeQuery();
            if (room.next()) {
                String typename = room.getString("typename");

                PreparedStatement queryRoom = con.prepareStatement(
                        "update room_details set roomtype_name=?, room_available=?,room_location=?,monthly_charge=?,"
                        + "room_description=?,type_id=? where id=?");
                queryRoom.setString(1, typename);
                queryRoom.setBoolean(2, true);
                queryRoom.setString(3, roomModel.getLocation());
                queryRoom.setInt(4, roomModel.getMonthlyCharge());
                queryRoom.setString(5, roomModel.getDescription());
                queryRoom.setInt(6, roomModel.getTypeID());
                queryRoom.setInt(7, roomModel.getId());
                boolean rowUpdated = queryRoom.executeUpdate() > 0;

                queryRoom.close();
                con.close();
                return rowUpdated;
            }
            return false;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }

    @Override
    public boolean deleteRoom(int id) {
        try {
            Connection con = DatabaseConnection.initializeDatabase();
            PreparedStatement queryRoom = con.prepareStatement("delete from room_details  where id=?");
            queryRoom.setInt(1, id);
            boolean deleted = queryRoom.executeUpdate() > 0;
            return deleted;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }

    @Override
    public boolean bookRoom(int roomid, int userid) {
        try {
            Connection con = DatabaseConnection.initializeDatabase();

            PreparedStatement queryStudent = con.prepareStatement("select * from student_details where User_id=?");
            queryStudent.setInt(1, userid);
            ResultSet student = queryStudent.executeQuery();
            if (student.next()) {
                PreparedStatement queryRoomCheck = con
                        .prepareStatement("select * from student_room where student_id=?");
                queryRoomCheck.setInt(1, student.getInt("id"));
                ResultSet roomcheck = queryRoomCheck.executeQuery();
                if (roomcheck.next()) {
                    return false;
                }
                PreparedStatement queryRoomavailable = con.prepareStatement(
                        "select d.type_id,d.room_location,t.available " + "from accommodation.room_details as d\r\n"
                        + "left join accommodation.room_type as t on d.id =t.id\r\n" + "where d.id=?");
                queryRoomavailable.setInt(1, roomid);
                ResultSet roomavailable = queryRoomavailable.executeQuery();

                if (roomavailable.next()) {
                    int available = roomavailable.getInt("available");
                    int rommTypeId = roomavailable.getInt("type_id");
                    boolean isAvailable = true;
                    if (available <= 0) {
                        isAvailable = false;
                        return false;
                    }
                    PreparedStatement queryRoom = con
                            .prepareStatement("insert into student_room(id,student_id,room_id) values" + "(?,?,?)");
                    queryRoom.setString(1, null);
                    queryRoom.setInt(3, roomid);
                    queryRoom.setInt(2, student.getInt("id"));
                    queryRoom.execute();
                    PreparedStatement queryRoomdetails = con
                            .prepareStatement("update room_details set room_available=? where id=?");

                    queryRoomdetails.setBoolean(1, isAvailable);
                    queryRoomdetails.setInt(2, roomid);
                    boolean rowUpdated = queryRoomdetails.executeUpdate() > 0;
                    PreparedStatement queryRoomtype = con
                            .prepareStatement("update room_type set available=? where id=?");

                    queryRoomtype.setInt(1, available - 1);
                    queryRoomtype.setInt(2, rommTypeId);
                    boolean rowUpdatedtype = queryRoomtype.executeUpdate() > 0;
                    queryRoomdetails.close();
                    queryRoom.close();
                }
                queryRoomavailable.close();

                return true;
            }

            queryStudent.close();
            con.close();
            return false;

        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }

    @Override
    public boolean addRoomtype(RoomTypeModel roomtypeModel) {
        try {
            Connection con = DatabaseConnection.initializeDatabase();
            PreparedStatement queryRoom = con.prepareStatement("insert into room_type values(?,?,?)");
            queryRoom.setString(1, null);
            queryRoom.setString(2, roomtypeModel.getType());
            queryRoom.setInt(3, roomtypeModel.getAvailable());
            queryRoom.execute();
            queryRoom.close();
            con.close();
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }

    @Override
    public List<RoomTypeModel> getRoomtype() {
        try {
            List<RoomTypeModel> listroomTypeModel = new ArrayList<>();
            Connection con = DatabaseConnection.initializeDatabase();
            PreparedStatement queryRoomGet = con.prepareStatement("select * from room_type");
            ResultSet rooms = queryRoomGet.executeQuery();
            while (rooms.next()) {
                String typename = rooms.getString("typename");
                int available = rooms.getInt("available");
                int id = rooms.getInt("id");
                RoomTypeModel roomTypeModel = new RoomTypeModel(id, typename, available);
                listroomTypeModel.add(roomTypeModel);
            }
            rooms.close();
            con.close();
            return listroomTypeModel;
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    @Override
    public RoomTypeModel getRoomtypeId(int id) {
        try {
            RoomTypeModel roomTypeModel = null;
            Connection con = DatabaseConnection.initializeDatabase();
            PreparedStatement queryRoomGet = con.prepareStatement("select * from room_type where id=?");
            queryRoomGet.setInt(1, id);
            ResultSet room = queryRoomGet.executeQuery();
            if (room.next()) {
                String typename = room.getString("typename");
                int available = room.getInt("available");
                int idroom = room.getInt("id");
                roomTypeModel = new RoomTypeModel(idroom, typename, available);
            }
            return roomTypeModel;
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    @Override
    public boolean inserEditRoomtype(RoomTypeModel roomTypeModel) {
        try {
            Connection con = DatabaseConnection.initializeDatabase();
            PreparedStatement queryRoom = con
                    .prepareStatement("update room_type set typename=?, available=? where id=?");
            queryRoom.setInt(3, roomTypeModel.getId());
            queryRoom.setString(1, roomTypeModel.getType());
            queryRoom.setInt(2, roomTypeModel.getAvailable());
            boolean rowUpdated = queryRoom.executeUpdate() > 0;
            queryRoom.close();
            con.close();
            return rowUpdated;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }

    @Override
    public boolean deleteRoomtype(int id) {
        try {
            Connection con = DatabaseConnection.initializeDatabase();
            PreparedStatement queryRoom = con.prepareStatement("delete from room_type  where id=?");
            queryRoom.setInt(1, id);
            boolean deleted = queryRoom.executeUpdate() > 0;
            return deleted;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }

    @Override
    public void SeedData() {
        try {
            Connection con = DatabaseConnection.initializeDatabase();
            PreparedStatement queryRolecheck = con.prepareStatement("select *  from role ");
            ResultSet rolecheck = queryRolecheck.executeQuery();
            if (!rolecheck.next()) {
                PreparedStatement queryRoles = con.prepareStatement("insert into  role  values(?,?)");
                queryRoles.setString(1, null);
                queryRoles.setString(2, "admin");
                queryRoles.executeUpdate();
                queryRoles.close();
                PreparedStatement queryRolesstu = con.prepareStatement("insert into  role  values(?,?)");
                queryRolesstu.setInt(1, 2);
                queryRolesstu.setString(2, "student");
                queryRolesstu.executeUpdate();
                queryRolesstu.close();
            }
            PreparedStatement queryUsercheck = con.prepareStatement("select *  from user_info ");
            ResultSet usercheck = queryUsercheck.executeQuery();
            if (!usercheck.next()) {
                String hashPassword = Hash.hashValue("Admin@123");
                PreparedStatement queryUser = con
                        .prepareStatement("insert into user_info(id,user_name,password,role_id) values(?,?, ?,?)");
                queryUser.setString(1, null);
                queryUser.setString(2, "Admin");
                queryUser.setString(3, hashPassword);
                queryUser.setInt(4, 1);
                queryUser.executeUpdate();
                queryUser.close();
            }
            con.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}
