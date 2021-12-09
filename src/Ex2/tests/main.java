package Ex2.tests;

import Ex2.MyGraph;

public class main {
    public static void main(String[] args) {

/*
        try {
            String file_name = "src/Ex2/data/G3.json";
            // create Gson instance
            Gson gson = new Gson();

            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get(file_name));

            // convert JSON file to map
            HashMap<?, ?> map = gson.fromJson(reader, HashMap.class);

            // print map entries
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                System.out.println(entry.getKey() + "=" + entry.getValue());
            }
            String E = map.get("Edges").toString();
            String N = map.get("Nodes").toString();
            E=E.replace("{", "");
            E=E.substring(1,E.length()-2);
            String[] Edges = E.split("}, ");
            for (int i = 0; i < Edges.length; i++) {
                System.out.println(Edges[i]);
            }
            N=N.replace("{", "");
            N=N.substring(1,N.length()-2);
            String[] Nodes = N.split("}, ");
            for (int i = 0; i < Nodes.length; i++) {
                System.out.println(Nodes[i]);
            }

            // close reader
            reader.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        int index = 0;
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get("src/Ex2/data/G3.json"));
            HashMap<?, ?> map = gson.fromJson(reader, HashMap.class);
            String E = map.get("Edges").toString();
            E = E.replace("{", "");
            E = E.substring(1, E.length() - 2);
            String[] Edges = E.split("}, ");
            String[] tmp = Edges[index].split(",");
            tmp[0] = tmp[0].replace("src=","");
            System.out.println(tmp[0]);
            tmp[1] = tmp[1].replace(" w=","");
            System.out.println(tmp[1]);
            tmp[2] = tmp[2].replace(" dest=","");
            System.out.println(tmp[2]);
            reader.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Landmark l = new Landmark("src/Ex2/data/G3.json", 0);
        System.out.println(l);
        Node n = new Node("src/Ex2/data/G3.json", 0);
        System.out.println(n);

        Node tmp = new Node();
        tmp.setTag(n.getTag());
        tmp.setLocation(n.getLocation());
        tmp.setWeight(n.getWeight());
        tmp.setInfo(n.getInfo());
        System.out.println("tmp is: " + tmp);

        MyGraph g1 = new MyGraph("src/Ex2/data/G3.json");
        MyGraph g2 = new MyGraph(g1.getEdges(), g1.getNodes());
        System.out.println(g1 + "," + g2);

        Stack s = new Stack();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            gson.toJson(g1, new FileWriter("src/Ex2/data/file1.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        double startTime = System.currentTimeMillis();
        HashMap<String, EdgeData> edge = new HashMap<>();
        HashMap<Integer, NodeData> node = new HashMap<>();
        Random r = new Random();
        int i = 0, src, dest;
        double w = 0;
        String key = "";
        EdgeData e = new Edge();
        NodeData n = new Node();
        Landmark pos = new Landmark();
        MyGraph g = new MyGraph(edge, node);
        int j = 0;
        int check = 10000;

 */
//        GenerateGraph gg = new GenerateGraph(100, 100);
//        gg.GenerateJson(gg, "src/Ex2/data/100n.json");
//        double startTime = System.currentTimeMillis();
//        MyGraph g = new MyGraph("src/Ex2/data/Test1.json");
//        MyGraphAlgo algo = new MyGraphAlgo(g);
//        double minTime = System.currentTimeMillis();
//        System.out.println((minTime - startTime) / 1000);
//        System.out.println(algo.center());
//        double endTime = System.currentTimeMillis();
//        System.out.println((endTime - startTime) / 1000);
//        int check = 100000;
//        GenerateGraph gg = new GenerateGraph(10 * check, check);
//        gg.GenerateJson(gg, "src/Ex2/data/100k.json");

//        int check = 1000000;
//        double startTime = System.currentTimeMillis();
//        Node n = new Node();
        MyGraph g = new MyGraph("src/Ex2/data/1m.json");
//        for (int i = 0; i < check; i++) {
//            g.addNode(n);
//        }
//        double minTime = System.currentTimeMillis();
//        System.out.println(minTime - startTime);
//        for (int i = 0; i < check * 2 - 1; i++) {
//            g.connect(i, i + 1, 0);
//            System.out.println(i);
//        }
//        double endTime = System.currentTimeMillis();
//        System.out.println((endTime - startTime) / 1000);

    }
}