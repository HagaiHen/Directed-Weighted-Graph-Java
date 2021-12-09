package Ex2;

import Ex2.api.GeoLocation;
import Ex2.api.NodeData;
import com.google.gson.Gson;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;


public class Node implements NodeData {
    private Landmark pos;
    private int id;
    public static final int WHITE = 0, GREY = 1, BLACK = 2;
    private int tag;
    private String info;

    public void setPos(Landmark pos) {
        this.pos = pos;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Node(String json_file, int index) {
        try {
            this.pos = new Landmark(json_file, index);
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
            tmp[3] = tmp[3].replace(" id=", "");
            double tmpID = Double.parseDouble(tmp[3]);
            this.id = (int) tmpID;
            // close reader
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Node() {
        this.pos = new Landmark();
        this.tag = 0;
        this.info = "";
        this.id = 0;
    }

    public Node(Landmark pos, String info, int id) {
        this.pos = pos;
        this.tag = 0;
        this.id = id;
        this.info = info;
    }

    public Node(String n) {
        String[] node = n.split(", ");
        node[0] = node[0].replace("pos=", "");
        String[] pos = node[0].split(",");
        double x = Double.parseDouble(pos[0]);
        double y = Double.parseDouble(pos[1]);
        double z = Double.parseDouble(pos[2]);
        Landmark l = new Landmark(x, y, z);
        this.pos = l;
        this.tag = 0;
        node[1] = node[1].replace("id=", "");
        this.id = (int) Double.parseDouble(node[1]);
    }

    @Override
    public int getKey() {
        return this.id;
    }

    @Override
    public GeoLocation getLocation() {
        return pos;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.pos.setX(p.x());
        this.pos.setY(p.y());
        this.pos.setZ(p.z());
    }

    @Override
    public double getWeight() {
        return 0;
    }

    @Override
    public void setWeight(double w) {

    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public int getTag() {
        return tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    @Override
    public String toString() {
        return "Node{" +
                "pos=" + pos +
                ", id=" + id +
                ", tag=" + tag +
                ", info='" + info + '\'' +
                '}';
    }
}