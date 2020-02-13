package id.djarkasih.crudeazy;

import id.djarkasih.crudeazy.model.domain.Database;
import id.djarkasih.crudeazy.model.domain.Collection;
import id.djarkasih.crudeazy.repository.CollectionRepository;
import id.djarkasih.crudeazy.repository.DatabaseRepository;
import id.djarkasih.crudeazy.service.CrudService;
import id.djarkasih.crudeazy.service.GenericCrudService;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(CrudEazy.class);

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
                   "h2file",
                   "org.h2.Driver",
                   "jdbc:h2:./mydb",
                   "fileadmin",
                   "f1l34dmK3y"
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
                                
                dbService.save(db1);
                dbService.save(db2);
                dbService.save(db3);
                dbService.save(db4);
                
                logger.info("Example database data created.");
            }

            logger.info("There are " + dbService.count() + " Database.");
            logger.info("There are " + collService.count() + " Collection.");
            
        };
    }
}
