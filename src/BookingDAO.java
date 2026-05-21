import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public boolean addBooking(Booking b) {
        String sql = "INSERT INTO bookings (vehicle_no, customer_name, service_type, service_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, b.getVehicleNo());
            pst.setString(2, b.getCustomerName());
            pst.setString(3, b.getServiceType());
            pst.setDate(4, Date.valueOf(b.getServiceDate()));

            int affected = pst.executeUpdate();
            return affected == 1;
        } catch (SQLException ex) {
            System.err.println("Error adding booking: " + ex.getMessage());
            return false;
        }
    }

    public List<Booking> getAllBookings() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Booking b = new Booking(
                    rs.getInt("id"),
                    rs.getString("vehicle_no"),
                    rs.getString("customer_name"),
                    rs.getString("service_type"),
                    rs.getDate("service_date").toLocalDate()
                );
                list.add(b);
            }
        } catch (SQLException ex) {
            System.err.println("Error fetching bookings: " + ex.getMessage());
        }
        return list;
    }

    public Booking getBookingById(int id) {
        String sql = "SELECT * FROM bookings WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Booking(
                        rs.getInt("id"),
                        rs.getString("vehicle_no"),
                        rs.getString("customer_name"),
                        rs.getString("service_type"),
                        rs.getDate("service_date").toLocalDate()
                    );
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error finding booking: " + ex.getMessage());
        }
        return null;
    }

    public boolean updateService(int id, String newServiceType, LocalDate newDate) {
        String sql = "UPDATE bookings SET service_type = ?, service_date = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, newServiceType);
            pst.setDate(2, Date.valueOf(newDate));
            pst.setInt(3, id);
            int affected = pst.executeUpdate();
            return affected == 1;
        } catch (SQLException ex) {
            System.err.println("Error updating booking: " + ex.getMessage());
            return false;
        }
    }

    public boolean deleteBooking(int id) {
        String sql = "DELETE FROM bookings WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, id);
            int affected = pst.executeUpdate();
            return affected == 1;
        } catch (SQLException ex) {
            System.err.println("Error deleting booking: " + ex.getMessage());
            return false;
        }
    }

    public List<Booking> searchByVehicleNo(String vehicleNo) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE vehicle_no LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, "%" + vehicleNo + "%");
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Booking b = new Booking(
                        rs.getInt("id"),
                        rs.getString("vehicle_no"),
                        rs.getString("customer_name"),
                        rs.getString("service_type"),
                        rs.getDate("service_date").toLocalDate()
                    );
                    list.add(b);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error searching bookings: " + ex.getMessage());
        }
        return list;
    }
}
