package helloxml;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;



public class UserHandler extends DefaultHandler {

    public static ArrayList<Student> list = new ArrayList<>();
    private Student currentStudent;
    String currentTag = "";

    @Override
    public void startElement(
            String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        if(qName.equals("student")){
            currentStudent = new Student();
            currentStudent.setRollno(attributes.getValue("rollno"));
        }
        currentTag = qName;
    }

    @Override
    public void endElement(String uri,
                           String localName, String qName) throws SAXException {
        if(qName.equals("student")){
            list.add(currentStudent);
        }
        currentTag = "";
    }


    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        if (currentTag.equals("firstname")) {
            currentStudent.setFirstname(new String(ch, start, length));
        } else if (currentTag.equals("lastname")) {
            currentStudent.setLastname(new String(ch, start, length));
        } else if (currentTag.equals("nickname")) {
            currentStudent.setNickname(new String(ch, start, length));
        } else if (currentTag.equals("marks")) {
            currentStudent.setMarks(new String(ch, start, length));
        }
    }
}