package net.huseyinsekmenoglu.fragments;

/**
 * Created by huseyin.sekmenoglu on 17.2.2016.
 * https://maps.googleapis.com/maps/api/place/nearbysearch/xml?location=41.0178154,29.0835963&radius=200&types=mosque&name=&sensor=false&key=AIzaSyBEJCJxic8cst3oMa_xzxVPOaXH5MinoHI
 */

public class NearestMosque {
/*    public static Comparator<Cami> mesafeComperator;

    static class 1 implements Comparator<Cami> {
        1() {
        }

    public int compare(Cami o1, Cami o2) {
        if (o1.getMesafe() < o2.getMesafe()) {
            return -1;
        }
        if (o1.getMesafe() > o2.getMesafe()) {
            return 1;
        }
        return 0;
    }
}

    public static ArrayList<Cami> getCamiList(int distance, double lat, double lon) {
        ArrayList<Cami> camiler = new ArrayList();
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/xml?location=" + lat + "," + lon + "&radius=" + distance + "&types=mosque&name=&sensor=false&key=AIzaSyBEJCJxic8cst3oMa_xzxVPOaXH5MinoHI").openStream());
            doc.getDocumentElement().normalize();
            NodeList itemlist = doc.getElementsByTagName(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_STATUS);
            if (itemlist.item(0).getChildNodes().item(0).getNodeValue().equals("OVER_QUERY_LIMIT")) {
                doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/xml?location=" + lat + "," + lon + "&radius=" + distance + "&types=mosque&name=&sensor=false&key=AIzaSyDHrk9zmvmJY3IcBuaG0XZ5UfwgVWH70gg").openStream());
                doc.getDocumentElement().normalize();
                itemlist = doc.getElementsByTagName(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_STATUS);
            }
            if (itemlist.item(0).getChildNodes().item(0).getNodeValue().equals("ZERO_RESULTS")) {
                return getCamiList(distance * 2, lat, lon);
            } else if (!itemlist.item(0).getChildNodes().item(0).getNodeValue().equals("OK")) {
                return camiler;
            } else {
                itemlist = doc.getElementsByTagName("result");
                for (int i = 0; i < itemlist.getLength(); i++) {
                    Cami cami = new Cami();
                    Element elementMain = (Element) itemlist.item(i);
                    Element nameValue = (Element) elementMain.getElementsByTagName(ShareConstants.WEB_DIALOG_PARAM_NAME).item(0);
                    Element adresValue = (Element) elementMain.getElementsByTagName("vicinity").item(0);
                    String str = "location";
                    Element locElement = (Element) ((Element) elementMain.getElementsByTagName("geometry").item(0)).getElementsByTagName(r30).item(0);
                    Element latElement = (Element) locElement.getElementsByTagName("lat").item(0);
                    Element lonElement = (Element) locElement.getElementsByTagName("lng").item(0);
                    cami.setIsim(nameValue.getChildNodes().item(0).getNodeValue());
                    cami.setAdres(adresValue.getChildNodes().item(0).getNodeValue());
                    cami.setLat(Double.parseDouble(latElement.getChildNodes().item(0).getNodeValue()));
                    cami.setLon(Double.parseDouble(lonElement.getChildNodes().item(0).getNodeValue()));
                    camiler.add(cami);
                }
                return sortByDistance(new LatLng(lat, lon), camiler);
            }
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    private static ArrayList sortByDistance(LatLng l, ArrayList<Cami> camiler) {
        for (int i = 0; i < camiler.size(); i++) {
            ((Cami) camiler.get(i)).setMesafe(distanceTo(new LatLng(((Cami) camiler.get(i)).getLat(), ((Cami) camiler.get(i)).getLon()), l));
        }
        Collections.sort(camiler, mesafeComperator);
        return camiler;
    }

static {
        mesafeComperator = new 1();
        }

public static double distanceTo(LatLng camiPos, LatLng mPos) {
        double lat1Rad = Math.toRadians(mPos.latitude);
        double lat2Rad = Math.toRadians(camiPos.latitude);
        return (1000.0d * Math.acos((Math.sin(lat1Rad) * Math.sin(lat2Rad)) + ((Math.cos(lat1Rad) * Math.cos(lat2Rad)) * Math.cos(Math.toRadians(camiPos.longitude - mPos.longitude))))) * ((double) 6371);
        }*/
}