package arquitectura.grupo19;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import arquitectura.grupo19.utils.DbHelper;

@SpringBootApplication
public class Application implements CommandLineRunner  {
	
	@Autowired
	private DbHelper dbHelper;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		dbHelper.populateDB();
	}

}
