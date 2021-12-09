package Ex2;

import Ex2.api.GeoLocation;
import com.google.gson.Gson;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class Landmark implements GeoLocation {
    private double x;
    private double y;
    private double z;

    public Landmark(String json_file, int index) {
        try {
            // create Gson instance
            Gson gson = new Gson();
            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get(json_file));
            // convert JSON file to map
            HashMap<?, ?> map = gson.fromJson(reader, HashMap.class);
            String N = map.get("Nodes").toString();
            N = N.replace("{", "");
            N = N.substring(1, N.length() - 2);
            String[] Nodes = N.split("}, ");
            Nodes[index] = Nodes[index].replace("pos=", "");
            String[] tmp = Nodes[index].split(",");
            this.x = Double.parseDouble(tmp[0]);
            this.y = Double.parseDouble(tmp[1]);
            this.z = Double.parseDouble(tmp[2]);
            // close reader
            reader.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Landmark () {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Landmark(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return "Landmark{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

    @Override
    public double distance(GeoLocation g) {
        double d = Math.pow((Math.pow(this.x - g.x(), 2) +
                Math.pow(this.y - g.y(), 2) +
                Math.pow(this.z - g.z(), 2) *
                        1.0), 0.5);
        return d;
    }
}
