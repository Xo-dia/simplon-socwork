package co.simplon.socworkbusiness.dtos;

public record AccountAuthentificate(String username, String password) {
    @Override
    public String toString() {
	return "{username=" + username + ", password=[PROTECTED]}";
    }
}
