import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class SAXParser extends DefaultHandler {
    private static final SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");
    private static final SimpleDateFormat visitDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    private Voter voter;
    private HashMap<Voter, Integer> voterCounts = new HashMap<>();
    private HashMap<Integer, WorkTime> voteStationWorkTimes = new HashMap<>();

    public SAXParser(String fileName){
        try {
            SAXParserFactory.newInstance().newSAXParser().parse(new File(fileName), this);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        try {
            if (qName.equals("voter") && voter == null) {
                Date birthday = birthDayFormat.parse(attributes.getValue("birthDay"));
                voter = new Voter(attributes.getValue("name"), birthday);
            } else if (qName.equals("visit") && voter != null) {
                int count = voterCounts.getOrDefault(voter, 0);
                voterCounts.put(voter, count + 1);

                Date time = visitDateFormat.parse(attributes.getValue("time"));
                Integer station = Integer.parseInt(attributes.getValue("station"));
                WorkTime workTime = voteStationWorkTimes.getOrDefault(station, new WorkTime());
                voteStationWorkTimes.put(station, workTime);
                workTime.addVisitTime(time.getTime());
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equals("voter")){
            voter = null;
        }
    }

    public HashMap<Voter, Integer> getVoterCounts() {
        return voterCounts;
    }

    public HashMap<Integer, WorkTime> getVoteStationWorkTimes() {
        return voteStationWorkTimes;
    }
}
