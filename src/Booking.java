import java.time.LocalDate;

public class Booking {
    private int id;
    private String vehicleNo;
    private String customerName;
    private String serviceType;
    private LocalDate serviceDate;

    public Booking() {}

    public Booking(int id, String vehicleNo, String customerName, String serviceType, LocalDate serviceDate) {
        this.id = id;
        this.vehicleNo = vehicleNo;
        this.customerName = customerName;
        this.serviceType = serviceType;
        this.serviceDate = serviceDate;
    }

    // getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getVehicleNo() { return vehicleNo; }
    public void setVehicleNo(String vehicleNo) { this.vehicleNo = vehicleNo; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }

    public LocalDate getServiceDate() { return serviceDate; }
    public void setServiceDate(LocalDate serviceDate) { this.serviceDate = serviceDate; }

    @Override
    public String toString() {
        String formattedDate = serviceDate.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return String.format("ID: %d | Vehicle: %s | Customer: %s | Service: %s | Date: %s",
            id, vehicleNo, customerName, serviceType, formattedDate);
        }
}
