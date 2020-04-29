import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class SAXParser extends DefaultHandler {
    private static final SimpleDateFormat visitDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    private final String FILE_NAME; // путь к файлу для парсинга
    private Runnable method; // метод который будет вызываться при очистке памяти
    private SAXParserFactory saxParser;

    private String voterName; // текущий избиратель
    private String voterBirthday; // дата рождения избирателя
    private StringBuilder voters = new StringBuilder(); // проголосовавшие избиратели
    private HashMap<Integer, WorkTime> voteStationWorkTimes = new HashMap<>(); // время работы изибиратьельных участков

    public SAXParser(String fileName){
        FILE_NAME = fileName;
        saxParser = SAXParserFactory.newInstance();
    }

    /**
     * Парсинг файла fileName
     */
    public void parse(Runnable method){
        try{
            this.method = method;
            saxParser.newSAXParser().parse(new File(FILE_NAME), this);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void endDocument() {
        method.run();
        voters.setLength(0);
        voteStationWorkTimes.clear();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        try {
            if (qName.equals("voter") && voterName == null) {
                voterBirthday = attributes.getValue("birthDay").replace('.','-');
                voterName = attributes.getValue("name");
            } else if (qName.equals("visit") && voterName != null) {
                //добавляем проголосовавшего избирателя
                voters.append((voters.length() == 0 ? "" : ",") +
                        "('" + voterName + "', '" + voterBirthday + "', 1)");

//                //добавляем время посещения избирательного участка
//                Date time = visitDateFormat.parse(attributes.getValue("time"));
//                Integer station = Integer.parseInt(attributes.getValue("station"));
//                WorkTime workTime = voteStationWorkTimes.getOrDefault(station, new WorkTime());
//                voteStationWorkTimes.put(station, workTime);
//                workTime.addVisitTime(time.getTime());

                //Если свободной памяти осталось меньше 2МБ, вызываем method переданный в parse и очищаем избирателей и время работы станции
                if (voters.length() > 524288) {
                    method.run();
                    voters.setLength(0);
                    voteStationWorkTimes.clear();
                }
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equals("voter")){
            voterName = null;
        }
    }

    public StringBuilder getVoters() {
        return voters;
    }

    public HashMap<Integer, WorkTime> getVoteStationWorkTimes() {
        return voteStationWorkTimes;
    }
}
