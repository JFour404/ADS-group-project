import java.util.List;
import java.util.Scanner;

public class SocialNetworkMenu {
    private SocialNetwork socialNetwork;

    public SocialNetworkMenu() {
        socialNetwork = new SocialNetwork();
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        String input;
        int choice = -1;

        do {
            System.out.println("MY_MENU");
            System.out.println("1. Load 'people' into the network");
            System.out.println("2. Load 'relationships'");
            System.out.println("3. Print out people");
            System.out.println("4. Search");
            System.out.println("5. Print friendships");
            System.out.println("6. Delete a person");
            System.out.println("7. Find people in the city");
            System.out.println("8. Find people born between");
            System.out.println("9. Find people in the same hometowns");
            System.out.println("10. Group users by favourite movies");
            System.out.println("0. Log out");
            System.out.print("Enter your choice: ");

            input = scanner.nextLine();

            if (input.matches("\\d+")) {
                choice = Integer.parseInt(input);
            } else {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter the filename to load 'people' (e.g., people.txt): ");
                    String peopleFile = scanner.nextLine();
                    socialNetwork.loadPeopleData(peopleFile);
                    break;
                case 2:
                    System.out.print("Enter the filename to load 'relationships' (e.g., friends.txt): ");
                    String friendsFile = scanner.nextLine();
                    socialNetwork.retrieveFriends(friendsFile);
                    break;
                case 3:
                    socialNetwork.printPeopleToFile();
                    break;
                case 4:
                    System.out.print("Enter the identifier to search: ");
                    String id = scanner.nextLine();
                    socialNetwork.searchPerson(id);
                    break;
                case 5:
                    System.out.print("Enter the person's surname to print friendships: ");
                    String personId = scanner.nextLine();
                    socialNetwork.findPersonsBySurname(personId);
                    break;
                case 6:
                    System.out.print("Enter the identifier of the person to delete: ");
                    String deleteId = scanner.nextLine();
                    socialNetwork.deletePerson(deleteId);
                    break;
                case 7:
                    System.out.print("Enter the city to retrieve people born there: ");
                    String city = scanner.nextLine();
                    socialNetwork.retrieveCitizens(city);
                    break;
                case 8:
                    System.out.print("Enter the start year: ");
                    String year1Str = scanner.nextLine(); 
                    System.out.print("Enter the end year: ");
                    String year2Str = scanner.nextLine(); 
                    try {
                        int year1 = Integer.parseInt(year1Str); 
                        int year2 = Integer.parseInt(year2Str); 
                        socialNetwork.bornBetween(year1, year2);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid year input. Please enter valid years.");
                    }
                    break;
                case 9:
                    System.out.print("Enter the filename for residential data (e.g., residential.txt): ");
                    String residentialFile = scanner.nextLine();
                    socialNetwork.findNatives(residentialFile);
                    break;
                case 10:
                    List<Group> groups = socialNetwork.buildGroups();
                    break;
                case 0:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }

        } while (choice != 0);

        scanner.close();
    }
}
