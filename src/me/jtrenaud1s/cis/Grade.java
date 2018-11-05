package me.jtrenaud1s.cis;

public class Grade {
    private double percentage;
    private String course;

    public Grade(double percentage, String course) {
        this.percentage = percentage;
        this.course = course;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getLetterGrade() {
        if(percentage < 60D) {
            return "F";
        } else if (percentage >= 60 && percentage < 70) {
            return "D";
        } else if(percentage >= 70 && percentage < 80) {
            return "C";
        } else if(percentage >= 80 && percentage < 90) {
            return "B";
        } else if(percentage >= 90) {
            return "A";
        }
        return "Error";
    }
}
