package ipss.parcticas;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeneradorPassword {

	// Aquí tengo un main pequeño que uso solo para generar hashes BCrypt de contraseñas
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		// Aquí indico la contraseña en texto plano que quiero convertir a hash
		String raw = "1234";
		String hash = encoder.encode(raw);
		// Imprimo el hash en consola para luego pegarlo en la base de datos si lo necesito
		System.out.println("Hash para '" + raw + "': " + hash);
	}
}
