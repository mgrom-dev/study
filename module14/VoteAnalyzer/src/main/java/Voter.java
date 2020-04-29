import java.text.SimpleDateFormat;

public class Voter
{
    private String name;
    private String birthDay;

    public Voter(String name, String birthDay)
    {
        this.name = name;
        this.birthDay = birthDay.replace(".", "-");
    }

    @Override
    public boolean equals(Object obj)
    {
        if (getClass() != obj.getClass()) {
            return false;
        }
        Voter voter = (Voter) obj;
        return name.equals(voter.name) && birthDay.equals(voter.birthDay);
    }

    @Override
    public int hashCode()
    {
        return name.hashCode() + birthDay.hashCode();
    }

    public String toString()
    {
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy.MM.dd");
        return name + " (" + dayFormat.format(birthDay) + ")";
    }

    public String getName()
    {
        return name;
    }

    public String getBirthDay()
    {
        return birthDay;
    }
}
