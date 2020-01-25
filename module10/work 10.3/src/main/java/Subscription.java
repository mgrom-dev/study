import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Formatter;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
class SubscriptionPK implements Serializable {
    @Column(name = "student_id")
    int studentId;

    @Column(name = "course_id")
    int courseId;

    SubscriptionPK(){}
}

@Entity
@Table(name = "Subscriptions")
@Data
public class Subscription {
    @EmbeddedId
    private SubscriptionPK id;

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false)
    private Course course;

    @Column(name = "subscription_date")
    private Date subscriptionDate;

    @Override
    public String toString(){
        return new Formatter().format("Subscription (studentName: %s, courseName: %s, subscriptionDate: %3$td.%3$tm.%3$tY)",
                student.getName(), course.getName(), subscriptionDate).toString();
    }
}
