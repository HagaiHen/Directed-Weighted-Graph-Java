package Ex2;

import Ex2.api.EdgeData;
import com.google.gson.Gson;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;



public class Edge implements EdgeData {

    private int src;
    private int dest;
    private double weight;
    private String info;
    public static final int WHITE = 0, GREY = 1, BLACK = 2;
    private int tag;

    public Edge(String json_file, int index) {
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(json_file));
            HashMap<?, ?> map = gson.fromJson(reader, HashMap.class);
            String E = map.get("Edges").toString();
            E = E.replace("{", "");
            E = E.substring(1, E.length() - 2);
            String[] Edges = E.split("}, ");
            String[] tmp = Edges[index].split(",");
            tmp[0] = tmp[0].replace("src=", "");
            tmp[1] = tmp[1].replace(" w=", "");
            tmp[2] = tmp[2].replace(" dest=", "");
            reader.close();
            double tmpSrc = Double.parseDouble(tmp[0]);
            double tmpDest = Double.parseDouble(tmp[2]);

            this.src = (int) tmpSrc;
            this.weight = Double.parseDouble(tmp[1]);
            this.dest = (int) tmpDest;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.info = "";
    }

    public Edge() {
        this.weight = 0;
        this.tag = 0;
        this.info = "";
        this.src = 0;
        this.dest = 0;
    }

    public Edge(int src, int dest, double weight, String info) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
        this.info = info;
        this.tag = 0;
    }

    public Edge(String e) {
        String[] ed = e.split(", ");
        ed[0] = ed[0].replace("src=", "");
        ed[1] = ed[1].replace("w=", "");
        ed[2] = ed[2].replace("dest=", "");
        this.src = (int) Double.parseDouble(ed[0]);
        this.weight = Double.parseDouble(ed[1]);
        this.dest = (int) Double.parseDouble(ed[2]);
        this.tag = 0;
        this.info = "";
    }

    @Override
    public int getSrc() {
        return this.src;
    }

    @Override
    public int getDest() {
        return this.dest;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    public void setSrc(int src) {
        this.src = src;
    }

    public void setDest(int dest) {
        this.dest = dest;
    }

    public void setWeight(double w) {
        this.weight = w;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }

}
