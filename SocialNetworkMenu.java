import java.util.Scanner;

public class SocialNetworkMenu {
    private SocialNetwork socialNetwork;

    public SocialNetworkMenu() {
        socialNetwork = new SocialNetwork();
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("MY_MENU");
            System.out.println("1. Load 'people' into the network");
            System.out.println("2. Load 'relationships'");
            System.out.println("3. Print out people");
            System.out.println("4. Search");
            System.out.println("5. Print friendships");
            System.out.println("6. Delete a person");
            System.out.println("0. Log out");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter the filename to load 'people' (e.g., people.txt): ");
                    String peopleFile = scanner.nextLine();
                    socialNetwork.loadPeopleData(peopleFile);
                    break;
                case 2:
                    System.out.print("Enter the filename to load 'relationships' (e.g., friends.txt): ");
                    String friendsFile = scanner.nextLine();
                    socialNetwork.loadRelationships(friendsFile);
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
                    System.out.print("Enter the identifier to print friendship: ");
                    String personId = scanner.nextLine();
                    socialNetwork.printFriendships(personId);
                    break;
                case 6:
                    System.out.print("Enter the identifier of the person to delete: ");
                    String deleteId = scanner.nextLine();
                    socialNetwork.deletePerson(deleteId);
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
