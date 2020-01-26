package id.djarkasih.crudeazy;

import id.djarkasih.crudeazy.model.Database;
import id.djarkasih.crudeazy.model.DatabaseManager;
import id.djarkasih.crudeazy.model.Collection;
import id.djarkasih.crudeazy.repository.DatabaseManagerRepository;
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
        
            DatabaseManager mgr = new DatabaseManager("CrudEazy");

            if (profs.contains("dev")) {
                mgr = new DatabaseManager("CrudEazy");
                
                var db1 = new Database(
                   "h2db",
                   "h2driver",
                   "h2dburl",
                   "h2user",
                   "h2password"
                );
                
                db1.addCollection(new Collection("table1"));
                db1.addCollection(new Collection("table2"));
                db1.addCollection(new Collection("table3"));

                var db2 = new Database(
                   "pgdb",
                   "pgdriver",
                   "pgdburl",
                   "pguser",
                   "pgpassword"
                );

                db2.addCollection(new Collection("table1"));
                db2.addCollection(new Collection("table2"));
                db2.addCollection(new Collection("table3"));

                mgr.addDatabase(db1);
                mgr.addDatabase(db2);
                
                repo.save(mgr);
                
                db1 = mgr.getDatabases().get("h2db");
                db1.addCollection(new Collection("table4"));
                db1.addCollection(new Collection("table5"));

                db2 = mgr.getDatabases().get("pgdb");
                db2.addCollection(new Collection("table6"));
                db2.addCollection(new Collection("table7"));
                
                repo.save(mgr);
            }

            logger.info("There are " + repo.count() + " Database Manager.");
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
