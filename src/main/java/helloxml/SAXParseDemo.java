package helloxml;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXParseDemo {
    public static void main(String[] args) {
        try {
            File inputFile = new File("src/main/resources/hello.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();

            SAXParser saxParser = factory.newSAXParser();

            UserHandler userhandler = new UserHandler();
            saxParser.parse(inputFile, userhandler);

            ArrayList<Student> list = UserHandler.list;
            for (int i = 0; i < list.size(); i++) {
                Student student = list.get(i);
                System.out.printf("Rollno: %s, firstname: %s, lastname: %s, nickname: %s, marks :%s",
                        student.getRollno(), student.getFirstname(), student.getLastname(), student.getNickname(), student.getMarks());
                System.out.println("");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
