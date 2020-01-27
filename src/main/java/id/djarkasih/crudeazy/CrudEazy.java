package id.djarkasih.crudeazy;

import id.djarkasih.crudeazy.model.Database;
import id.djarkasih.crudeazy.model.DatabaseManager;
import id.djarkasih.crudeazy.model.Collection;
import id.djarkasih.crudeazy.repository.DatabaseManagerRepository;
import id.djarkasih.crudeazy.util.Constants;
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
    
    Logger logger = LoggerFactory.getLogger(CrudEazy.class);

    public static void main(String[] args) {
	SpringApplication.run(CrudEazy.class, args);
    }

    @Bean
    public CommandLineRunner initDatabaseInfo(DatabaseManagerRepository repo) {
        return args -> {
            
            Set<String> profs = Set.of(env.getActiveProfiles());
            logger.info("Active profiles = " + profs.toString());
        
            DatabaseManager mgr;

            if ((profs.contains("dev")) && (repo.count() == 0)) {
                
                mgr = new DatabaseManager(Constants.CRUD_EAZY);

                var db1 = new Database(
                   "h2mem",
                   "org.h2.Driver",
                   "jdbc:h2:mem:memdb",
                   "user",
                   "password"
                );
                
                db1.addCollection(new Collection("table1"));
                db1.addCollection(new Collection("table2"));
                db1.addCollection(new Collection("table3"));

                var db2 = new Database(
                   "h2file",
                   "org.h2.Driver",
                   "jdbc:h2:./localdb",
                   "user",
                   "password"
                );

                db2.addCollection(new Collection("table1"));
                db2.addCollection(new Collection("table2"));
                db2.addCollection(new Collection("table3"));

                var db3 = new Database(
                   "mariadb",
                   "org.mariadb.jdbc.Driver",
                   "jdbc:mariadb://localhost:3306/springbootdb",
                   "user",
                   "password"
                );

                var db4 = new Database(
                   "postgresdb",
                   "org.postgresql.Driver",
                   "jdbc:postgresql://localhost:5432/postgres",
                   "user",
                   "password"
                );
                
                mgr.addDatabase(db1);
                mgr.addDatabase(db2);
                mgr.addDatabase(db3);
                mgr.addDatabase(db4);
                                
                repo.save(mgr);
                
                db1 = mgr.getDatabases().get("h2mem");
                db1.addCollection(new Collection("table4"));
                db1.addCollection(new Collection("table5"));

                db2 = mgr.getDatabases().get("h2file");
                db2.addCollection(new Collection("table6"));
                db2.addCollection(new Collection("table7"));
                
                repo.save(mgr);
                
                logger.info("Dummy data created.");
            }

            logger.info("There are " + repo.count() + " Database Manager.");
            logger.info("There are " + repo.numberOfDatabase() + " Database.");
            logger.info("There are " + repo.numberOfCollection() + " Collection.");
            
            mgr = repo.findByName("CrudEazy");
            if (mgr != null) {
                logger.info("mgr = " + mgr.toString());
                
                var dbs = mgr.getDatabases();
                if (dbs != null) {
                    
                    dbs.forEach((String dbName, Database db) -> {
                       
                       logger.info("db = " +  db.toString()); 
                        
                       var recs = db.getRecords();
                       if (recs != null){
                       
                           recs.forEach((String recName, Collection rec) -> {
                               logger.info("rec = " + rec.toString());
                           });
                       
                       }
                        
                    });
                    
                }
            }
        };
    }
}
