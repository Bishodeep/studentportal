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
import java.util.List;

/**
 *
 * @author Bishodeep
 */
public interface DataAccessInterface {

    public boolean registerUser(RegistrationModel registerModel);

    public UserModel validateUser(String username, String password);

    public boolean addRoom(RoomModel roomModel);

    public List<RoomModel> getRooms();

    public RoomModel getRoomId(int id);

    public RoomModel getUserRoom(int userid);

    public boolean changePaymentStaus(int id);

    public List<StudentRoomViewModel> getStudentRooms();

    public boolean insertEditRoom(RoomModel roomModel);

    public boolean deleteRoom(int id);

    public boolean bookRoom(int roomid, int userid);

    public boolean addRoomtype(RoomTypeModel roomtypeModel);

    public List<RoomTypeModel> getRoomtype();

    public RoomTypeModel getRoomtypeId(int id);

    public boolean inserEditRoomtype(RoomTypeModel roomTypeModel);

    public boolean deleteRoomtype(int id);

    public void SeedData();
}
