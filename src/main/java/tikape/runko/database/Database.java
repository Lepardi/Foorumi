package tikape.runko.database;

import java.net.URI;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        if (this.databaseAddress.contains("postgres")) {
            try {
                URI dbUri = new URI(databaseAddress);

                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

                return DriverManager.getConnection(dbUrl, username, password);
            } catch (Throwable t) {
                System.out.println("Error: " + t.getMessage());
                t.printStackTrace();
            }
        }
        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> lauseet = sqliteLauseet();
        if (this.databaseAddress.contains("postgres")) {
            lauseet = postgreLauseet();
        } else {
            lauseet = sqliteLauseet();
        }

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> postgreLauseet() {
        ArrayList<String> lista = new ArrayList<>();
        
        lista.add("CREATE TABLE Alue (id SERIAL PRIMARY KEY, aihe varchar(255));");
        lista.add("INSERT INTO Alue (aihe) VALUES ('Koirat');");
        //lista.add("INSERT INTO Alue (aihe) VALUES ('Koirat');");
        
        //lista.add("CREATE TABLE Ketju (id integer PRIMARY KEY, otsikko varchar(50) NOT NULL, alue integer NOT NULL, FOREIGN KEY (alue) REFERENCES Alue(id));");
        //lista.add("INSERT INTO Ketju (aihe) VALUES ('Koirat');");
        
        //lista.add("CREATE TABLE Viesti (id integer PRIMARY KEY, kayttaja varchar(50) NOT NULL, paivamaara date NOT NULL, teksti varchar (2000) NOT NULL, ketju integer NOT NULL, FOREIGN KEY (ketju) REFERENCES Ketju(id));");
        //lista.add("INSERT INTO Viesti (aihe) VALUES ('Koirat');");

        return lista;
    }
    
    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä


        //Näillä voi putsata tietokantaa
        //lista.add("DELETE FROM Viesti WHERE id = '1'");

        //lista.add("DELETE FROM Ketju WHERE id = '1'");

        //lista.add("DELETE FROM Alue WHERE id = '1'");

        
        return lista;
    }
}
