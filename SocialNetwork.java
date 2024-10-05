import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SocialNetwork {
    private List<Person> peopleNetwork = new ArrayList<>();
    private List<Friendship> relationships = new ArrayList<>(); 

    // Method to add a person to the network
    public void addPerson(Person person) {
        if (findPersonById(person.getIdentifier()) == null) {
            peopleNetwork.add(person);
            System.out.println(person.getName() + " added to the network.");
        } else {
            System.out.println("Person with ID " + person.getIdentifier() + " already exists.");
        }
    }
    // Search for a person by their identifier
    public void searchPerson(String id) {
        Person person = findPersonById(id);
        if (person != null) {
            // Print all details about the person
            System.out.println("Found person:");
            System.out.println("ID: " + person.getIdentifier());
            System.out.println("Name: " + person.getName());
            System.out.println("Surname: " + person.getSurname());
            System.out.println("Birth Date: " + person.getBirthDate());
            System.out.println("Gender: " + person.getGender());
            System.out.println("Birthplace: " + person.getBirthplace());
            System.out.println("Hometown: " + person.getHometown());
            System.out.println("Studied At: " + person.getStudiedAt());
            System.out.println("Worked At: " + person.getWorkedAt());
            System.out.println("Favorite Movies: " + person.getMovies());
            System.out.println("Group Code: " + person.getGroupCode());
        } else {
            System.out.println("Person with ID " + id + " not found.");
        }
    }
    
    // Method to find a person by their identifier
    private Person findPersonById(String id) {
        for (Person person : peopleNetwork) {
            if (person.getIdentifier().equals(id)) {
                return person;
            }
        }
        return null;
    }
    // Method to load people data from a file
    public void loadPeopleData(String filename) {
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] personData = line.split(","); 
            String identifier = personData[0]; 

            // Split and assign the studiedAt, workedAt, and movies fields as lists
            List<String> studiedAtList = personData.length > 7 ? Arrays.asList(personData[7].split(";")) : new ArrayList<>();
            List<String> workedAtList = personData.length > 8 ? Arrays.asList(personData[8].split(";")) : new ArrayList<>();
            List<String> moviesList = personData.length > 9 ? Arrays.asList(personData[9].split(";")) : new ArrayList<>();

            // Create a new Person object with the data, handling missing fields
            Person person = new Person(
                identifier,
                personData.length > 1 ? personData[1] : null,  // Name
                personData.length > 2 ? personData[2] : null,  // Surname
                personData.length > 3 ? personData[3] : null,  // BirthDate
                personData.length > 4 ? personData[4] : null,  // Gender
                personData.length > 5 ? personData[5] : null,  // Birthplace
                personData.length > 6 ? personData[6] : null,  // Hometown
                studiedAtList,                                // StudiedAt
                workedAtList,                                 // WorkedAt
                moviesList,                                   // Movies
                personData.length > 10 ? personData[10] : null // GroupCode
            );

            // Add the person to the network
            addPerson(person);
        }
    } catch (IOException e) {
        System.err.println("Error reading file: " + e.getMessage());
    }
}


    // Method to load friendships from the file
    public void retrieveFriends(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] friends = line.split(",");
                if (friends.length == 2) {
                    String friend1 = friends[0].trim();
                    String friend2 = friends[1].trim();

                    // Add the relationship in both directions
                    addFriendship(friend1, friend2);
                    addFriendship(friend2, friend1);
                }
            }
            System.out.println("Friendships loaded successfully from " + filename);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    // Print out all people in the network
    public void printPeopleToFile() {
        if (peopleNetwork.isEmpty()) {
            System.out.println("No people in the network.");
        } else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("people_output.txt"))) {
                for (Person person : peopleNetwork) {
                    writer.write(person.getIdentifier() + ": " + person.getName() + " " + person.getSurname());
                    writer.newLine(); 
                }
                System.out.println("People have been written to people_output.txt.");
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
            }
        }
    }

    private void addFriendship(String personId, String friendId) {
        if (findPersonById(personId) != null && findPersonById(friendId) != null) {
            relationships.add(new Friendship(personId, friendId));
        } else {
            System.out.println("One or both of the persons (" + personId + ", " + friendId + ") do not exist in the network.");
        }
    }

    // Print friendship relation
    public void printFriendships(String personId) {
        Person person = findPersonById(personId);
        if (person != null) {
            List<String> friends = getFriends(personId);
            if (friends.isEmpty()) {
                System.out.println(personId + " has no friends in the network.");
            } else {
                System.out.println(personId + " is friends with: " + String.join(", ", friends));
            }
        } else {
            System.out.println("Person with ID " + personId + " not found in the network.");
        }
    }

// Method to find persons by surname and print their relationships
public void findPersonsBySurname(String surname) {
    boolean found = false;

    for (Person person : peopleNetwork) {
        if (person.getSurname().equals(surname)) {
            found = true;
            System.out.println("Person: " + person.getName() + " " + person.getSurname() + ", ID: " + person.getIdentifier());
            printFriendships(person.getIdentifier());
        }
    }

    if (!found) {
        System.out.println("No persons with surname " + surname + " found in the network.");
    }
}

    private List<String> getFriends(String personId) {
        List<String> friends = new ArrayList<>();
        for (Friendship friendship : relationships) {
            if (friendship.getPerson1().equals(personId)) {
                friends.add(friendship.getPerson2());
            }
        }
        return friends;
    }

    public void deletePerson(String personId) {
        // Check if the person exists in the network
        Person personToDelete = findPersonById(personId);
        if (personToDelete == null) {
            System.out.println("Person with ID " + personId + " does not exist in the network.");
            return;
        }
    
        // Remove the person from the people network
        peopleNetwork.remove(personToDelete);
    
        // Remove all friendships involving this person
        relationships.removeIf(friendship -> 
            friendship.getPerson1().equals(personId) || friendship.getPerson2().equals(personId)
        );
    
        // Remove the person's ID from other people's friend lists
        for (Friendship friendship : new ArrayList<>(relationships)) {
            if (friendship.getPerson1().equals(personId) || friendship.getPerson2().equals(personId)) {
                continue;
            }
            // If the friendship involves the deleted person, remove it from the corresponding friend's list
            if (friendship.getPerson1().equals(personId)) {
                relationships.remove(friendship);
            } else if (friendship.getPerson2().equals(personId)) {
                relationships.remove(friendship);
            }
        }
    
        System.out.println("Person with ID " + personId + " and their relationships have been removed.");
    }
    //Get people who were born in a city
    public void retrieveCitizens(String city) {
        boolean found = false;
    
        for (Person person : peopleNetwork) {
            if (person.getBirthplace() != null && person.getBirthplace().equals(city)) {
                found = true;
                System.out.println("Person: " + person.getIdentifier() + ", Surname: " + person.getSurname());
            }
        }
    
        if (!found) {
            System.out.println("No persons born in " + city + " found in the network.");
        }
    }

    public void bornBetween(int year1, int year2) {
    List<Person> filteredList = new ArrayList<>();
    Pattern yearPattern = Pattern.compile("(\\d{4})"); // looking for 4 consecutative numbers in a date

    for (Person person : peopleNetwork) {
        if (person.getBirthDate() != null && !person.getBirthDate().isEmpty()) {
            Matcher matcher = yearPattern.matcher(person.getBirthDate());
            if (matcher.find()) { // Check if a 4-digit year exists in the birthdate
                try {
                    int birthYear = Integer.parseInt(matcher.group(1));
                    if (birthYear >= year1 && birthYear <= year2) {
                        filteredList.add(person);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Invalid birth year format for person: " + person.getIdentifier());
                }
            }
        }
    }

    // Sort by birthplace, surname, and name
    List<Person> sortedList = sortPeopleLexicographically(filteredList);
    for (Person person : sortedList) {
        System.out.println("Birthplace: " + person.getBirthplace()+ ", Surname: " + person.getSurname() + ", Person: " + person.getName());
    }
}
    
public List<Person> sortPeopleLexicographically(List<Person> people) {
    List<Person> sortedList = new ArrayList<>(people);
    sortedList.sort(Comparator.comparing(Person::getBirthplace)
                               .thenComparing(Person::getSurname)
                               .thenComparing(Person::getName));
    return sortedList;
}

    public List<Group> buildGroups() {
        Map<List<String>, List<Person>> movieGroups = new HashMap<>();
        List<Group> groups = new ArrayList<>();
        int groupCounter = 1; // Counter for sequential group codes
    
        for (Person person : peopleNetwork) {
            List<String> favoriteMovies = person.getMovies(); 
            movieGroups.computeIfAbsent(favoriteMovies, k -> new ArrayList<>()).add(person);
        }
    
        // Create groups and assign sequential codes
        for (Map.Entry<List<String>, List<Person>> entry : movieGroups.entrySet()) {
            String groupCode = "G" + groupCounter++;
            Group group = new Group(groupCode, entry.getKey());
            groups.add(group);
    
            // Print group information
            System.out.println(group);
            for (Person member : entry.getValue()) {
                // Update the groupCode for each person in the group
                member.setGroupCode(groupCode);
                System.out.println(" - " + member.getIdentifier() + ": " + member.getName() + " " + member.getSurname());
            }
        }
        return groups; 
    }
    
    
public void findNatives(String filename) {
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        String line;
        Set<String> hometowns = new HashSet<>();
        
        // Read identifiers from the file and collect hometowns
        while ((line = reader.readLine()) != null) {
            String identifier = line.trim();
            Person person = findPersonById(identifier);
            
            // Check if person is found
            if (person != null) {
                System.out.println("Found person: " + person.getIdentifier() + ", Name: " + person.getName() + ", Surname: " + person.getSurname());
                
                // Get and trim hometown
                String hometown = person.getHometown();
                if (hometown != null) {
                    hometown = hometown.trim();
                    System.out.println("Hometown: [" + hometown + "]"); 
                    hometowns.add(hometown);
                } else {
                    System.out.println("Hometown is null for this person.");
                }
            } else {
                System.out.println("Person with ID " + identifier + " not found.");
            }
        }

        // Retrieve and print people born in the hometowns found
        System.out.println("Retrieving people from the found hometowns:");
        for (String hometown : hometowns) {
            System.out.println("People born in " + hometown + ":"); 
            for (Person person : peopleNetwork) {
                String birthplace = person.getBirthplace();
                if (birthplace != null) {
                    birthplace = birthplace.trim();
                   if (birthplace.equalsIgnoreCase(hometown)) {
                    System.out.println("Person: " + person.getIdentifier() + 
                        ", Name: " + person.getName() + 
                        ", Surname: " + person.getSurname() + 
                        ", Birthplace: " + person.getBirthplace());
                    
                    if (person.getStudiedAt() != null && !person.getStudiedAt().isEmpty()) {
                        System.out.println("StudiedAt: " + String.join(", ", person.getStudiedAt()));
                    } else {
                        System.out.println("StudiedAt: Not Available");
                    }
                }
                }
            }
        }
    } catch (IOException e) {
        System.err.println("Error reading file: " + e.getMessage());
    }
}
}
