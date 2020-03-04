package id.djarkasih.crudeazy;

import id.djarkasih.crudeazy.model.domain.Database;
import id.djarkasih.crudeazy.model.domain.Collection;
import id.djarkasih.crudeazy.service.CrudService;
import java.util.Set;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class CrudEazy {
        
    @Autowired
    private Environment env;

    @Autowired
    private CrudService<Database,Long> dbService;
    
    @Autowired
    private CrudService<Collection,Long> collService;

    @Autowired
    private Logger logger;

    public static void main(String[] args) {
	SpringApplication.run(CrudEazy.class, args);
    }

    @Bean
    public CommandLineRunner initDatabaseInfo() {
        return args -> {
            
            Set<String> profs = Set.of(env.getActiveProfiles());
            logger.info("Active profiles = " + profs.toString());

            if ((profs.contains("dev")) && (dbService.count() == 0)) {

                var db1 = new Database(
                   "h2mem",
                   "org.h2.Driver",
                   "jdbc:h2:mem:mydb",
                   "memadmin",
                   "m3m4dmK3y"
                );

                var db2 = new Database(
                   "dummy",
                   "org.h2.Driver",
                   "jdbc:h2:./dummy",
                   "dummyadm",
                   "dummyK3y"
                );

                var db3 = new Database(
                   "mariadb",
                   "org.mariadb.jdbc.Driver",
                   "jdbc:mariadb://localhost:3306/mydb",
                   "mysqladmin",
                   "mysql4dmK3y"
                );

                var db4 = new Database(
                   "postgresdb",
                   "org.postgresql.Driver",
                   "jdbc:postgresql://localhost:5432/mydb",
                   "postgresadmin",
                   "p05tgr3s4dmK3y"
                );
                                
                var db5 = new Database(
                   "demo",
                   "org.h2.Driver",
                   "jdbc:h2:./demo",
                   "demoadmin",
                   "d3m04dm1n"
                );

                dbService.save(db1);
                dbService.save(db2);
                dbService.save(db3);
                dbService.save(db4);
                dbService.save(db5);
                
                logger.info("Example database data created.");
            }

            logger.info("There are " + dbService.count() + " Database.");
            logger.info("There are " + collService.count() + " Collection.");
            
        };
    }
}
