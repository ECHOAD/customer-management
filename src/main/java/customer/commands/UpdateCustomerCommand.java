package customer.commands;

public record UpdateCustomerCommand(
    Long id,
    String address,
    String phoneNumber
) {
}
