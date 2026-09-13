package com.kenRodriguez.flightswebapp.AuthService;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Send data to users.xml
public class UserRepository {
    // Set to the specific users.xml file for consistency w/ registration through the webpage.
    private final String fileName = "src/main/resources/xml/users.xml";

    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();

        // Try to read from the local XML file. Convert each entry into a User object based on the User class.
        // Then, return a list of our registered users.
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = null;
            try {
                dBuilder = dbFactory.newDocumentBuilder();
            }
            catch (ParserConfigurationException ex) {
                throw new RuntimeException(ex);
            }

            File file = new File(fileName);
            if (!file.exists()) {
                throw new IllegalStateException("User file not found: " + fileName);
            }
            // Try to read the XML file. if it fails, throw exception ex.
            Document document;
            try {
                document = dBuilder.parse(file);
            }
            catch (SAXException | IOException ex) {
                throw new RuntimeException(ex);
            }
            document.getDocumentElement().normalize();

            // Grab all Users from our XML file.
            NodeList nList = document.getElementsByTagName("user");

            // for each User element, grab each username and password.
            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);

                // Check to see if our user is actually an XML element.
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // parse our username/password elements, get their text, assign them to a variable,
                    // then output the username and password to a User object for our system to parse more easily.
                    String username = element
                            .getElementsByTagName("username")
                            .item(0)
                            .getTextContent();
                    String password = element
                            .getElementsByTagName("password")
                            .item(0)
                            .getTextContent();

                    users.add(new User(username, password));
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Unable to read users.xml", e);
        }

        return users;
    }

    public User findByUsername(String username) {
        // Leverage our findAllUsers() method to quickly get a list of our users.
        List<User> users = findAllUsers();

        // for every user in users, see if any match the requested username.
        // if so, return the user. if not, return null.
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public void addUser(User user) {
        // Try to read users.xml with DocumentBuilder.
        // If everything works, add user data to the input stream.
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Adjust our file reading for greater consistency between the XML DB and webpage.
            File file = new File(fileName);
            if (!file.exists()) {
                throw new IllegalStateException("User file not found: " + fileName);
            }

            Document document = dBuilder.parse(file);
            // Parse our file, normalizing its output into something reasonable.
            document.getDocumentElement().normalize();

            // Get the root of our users element to add new users
            Element root = document.getDocumentElement();

            // Make a new user, then add data to that element (username and password)
            Element userElement = document.createElement("user");

            Element usernameElement = document.createElement("username");
            usernameElement.setTextContent(user.getUsername());

            Element  passwordElement = document.createElement("password");
            passwordElement.setTextContent(user.getPassword());

            // build our new user for the XML file. then, append to the document itself.
            userElement.appendChild(usernameElement);
            userElement.appendChild(passwordElement);
            root.appendChild(userElement);

            // Rewrite our XML document with the new user.
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            // Improve our XML formatting w/ proper indents
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(document);

            // place our XML doc in its appropriate location, writing to our "live" file.
            StreamResult result = new StreamResult(
                    file
            );

            // finally, actually save the XML result to the source file. what a journey
            transformer.transform(source, result);
        }
        catch (Exception e) {
            throw new RuntimeException("Unable to add user", e);
        }

    }
}
