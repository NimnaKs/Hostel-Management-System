package Bo.util;

import dto.TenantDto;
import entity.Tenant;

public class Converter {
    private static Converter converter;

    private Converter() {

    }

    public static Converter getInstance() {
        return converter == null ? converter = new Converter() : converter;
    }

    /*public UserDto toUserDto(User entity) {
        return new UserDto(entity.getId(), entity.getPassword(), entity.getPasswordHint());
    }

    public User toUserEntity(UserDto dto) {
        return new User(dto.getId(), dto.getPassword(), dto.getPasswordHint());
    }*/

    public TenantDto toTenantDto(Tenant student) {
        TenantDto tenantDto = new TenantDto();
        tenantDto.setStudent_id(student.getStudent_id());
        tenantDto.setName(student.getName());
        tenantDto.setContact_no(student.getContact_no());
        tenantDto.setDob(student.getDate());
        tenantDto.setGender(student.getGender());
        tenantDto.setAddress(student.getAddress());
        return tenantDto;
    }

    public Tenant toTenantEntity(TenantDto dto) {
        Tenant tenant = new Tenant();
        tenant.setStudent_id(dto.getStudent_id());
        tenant.setName(dto.getName());
        tenant.setContact_no(dto.getContact_no());
        tenant.setDate(dto.getDob());
        tenant.setGender(dto.getGender());
        tenant.setAddress(dto.getAddress());
        return tenant;
    }

    /*public RoomDto toRoomDto(Room entity) {
        RoomDto dto = new RoomDto();
        dto.setRoom_type_id(entity.getRoom_type_id());
        dto.setType(entity.getType());
        dto.setKey_money(entity.getKey_money());
        dto.setQty(entity.getQty());
        return dto;
    }

    public Room toRoomEntity(RoomDto dto) {
        Room entity = new Room();
        entity.setRoom_type_id(dto.getRoom_type_id());
        entity.setType(dto.getType());
        entity.setKey_money(dto.getKey_money());
        entity.setQty(dto.getQty());
        return entity;
    }

    public Reservation toReservationEntity(ReservationDto dto) {
        System.out.println(dto);
        Reservation entity = new Reservation();
        entity.setRes_id(dto.getRes_id());
        entity.setStudent(toStudentEntity(dto.getStudentDto()));
        entity.setRoom(toRoomEntity(dto.getRoomDto()));
        entity.setDate(dto.getDate());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    public ReservationDto toReservationDto(Reservation entity) {
        ReservationDto dto = new ReservationDto();
        dto.setRes_id(entity.getRes_id());
        dto.setStudentDto(toStudentDto(entity.getStudent()));
        dto.setRoomDto(toRoomDto(entity.getRoom()));
        dto.setDate(entity.getDate());
        dto.setStatus(entity.getStatus());
        return dto;
    }*/
}
