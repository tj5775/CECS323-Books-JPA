/*
 * Licensed under the Academic Free License (AFL 3.0).
 *     http://opensource.org/licenses/AFL-3.0
 *
 *  This code is distributed to CSULB students in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE, other than educational.
 *
 *  2018 Alvaro Monge <alvaro.monge@csulb.edu>
 *
 */

package csulb.cecs323.app;

// Import all of the entity classes that we have written for this application.
import csulb.cecs323.model.*;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;


public class BookRecords {
   //private static Object Authoring_Entities;
   private EntityManager entityManager;
   private static final Logger LOGGER = Logger.getLogger(BookRecords.class.getName());
   public BookRecords(EntityManager manager) {
      this.entityManager = manager;
   }

   public static void main(String[] args) {
      LOGGER.fine("Creating EntityManagerFactory and EntityManager");
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookRecords");
      EntityManager manager = factory.createEntityManager();

      // Create an instance of BookRecords and store our new EntityManager as an instance variable.
      BookRecords publishedBookRecords = new BookRecords(manager);

      List<Individual_authors> individual_authors = new ArrayList<>();
      List<Ad_hoc_teams> ad_hoc_teams = new ArrayList<>();
      List<Writing_groups> writing_groups = new ArrayList<>();
      List<Books> books = new ArrayList<>();

      LOGGER.fine("Begin of Transaction");
      EntityTransaction tx = manager.getTransaction();
      tx.begin();

      publishedBookRecords.populateAuthoringEnt(individual_authors, ad_hoc_teams, writing_groups, books);
      publishedBookRecords.createEntity(individual_authors);
      publishedBookRecords.createEntity(ad_hoc_teams);
      publishedBookRecords.createEntity(writing_groups);
      publishedBookRecords.createEntity(books);

      publishedBookRecords.displayAllPublishers();
      publishedBookRecords.cls();

      // Commit the changes so that the new data persists and is visible to other users.
      tx.commit();
      LOGGER.fine("End of Transaction");
      publishedBookRecords.mainMenu();


   } // End of the main method

   /**
    * Method to take in user input, navigating through the main menu options
    */
   public void mainMenu(){
      //User interface
      Scanner input = new Scanner(System.in);

      boolean exitVar = true;
      while(exitVar) {
         System.out.println("Main Menu. Type in a number to choose an option");
         System.out.println("\n1. Publishers \t\t\t2. Books");
         System.out.println("3. Authoring Entities\t4. List all primary keys");
         System.out.println("5. Exit");
         int userOption = input.nextInt();
         switch(userOption) {
            case 1:
               System.out.println("Option 1");
               exitVar = false;
               publishersMenu();
               break;
            case 2:
               System.out.println("Option 2");
               exitVar = false;
               booksMenu();
               break;
            case 3:
               System.out.println("Option 3");
               exitVar = false;
               authoringEntitiesMenu();
               break;
            case 4:
               System.out.println("Option 4");
               exitVar = false;
               displayPrimaryKeys();
               System.out.println();
               primaryKeyMenu();
               break;
            case 5:
               System.out.println("Option 5");
               exitVar = false;
               break;
            default:
               System.out.println("You have chosen an invalid option, please enter another input");
               break;
         }
      }
   }

   /**
    * Method to take in user input, navigating through the general authoring entities menu
    */
   public void authoringEntitiesMenu(){
      Scanner input = new Scanner(System.in);
      boolean exitVar = true;
      while(exitVar) {
         System.out.println("Authoring Entities Menu. Type in a number to choose an option");
         System.out.println("\n1. Display all Authoring Entities \t2. Add a new Authoring Entity");
         System.out.println("3. Display all Ad Hoc Team Members\t4. Go to Main menu");

         int userOption = input.nextInt();
         switch(userOption) {
            case 1:
               System.out.println("Display all Authoring Entity");
               exitVar = false;
               displayAllAuthoringEntities();
               authoringEntitiesMenu();
               break;
            case 2:
               System.out.println("Add a new Authoring Entity");
               exitVar = false;
               authoringEntityAddMenu();
               authoringEntitiesMenu();
               break;
           case 3:
               System.out.println("Display Ad Hoc Team members");
               exitVar = false;
               displayAdHocTeamMembers();
               authoringEntitiesMenu();
               break;
            case 4:
               System.out.println("Go to main menu");
               exitVar = false;
               mainMenu();
               break;
            default:
               System.out.println("You have chosen an invalid option, please enter another input");
               break;
         }
      }
   }

   /**
    * Method to take in user input, navigating through options to add a new authoring entity
    */
   public void authoringEntityAddMenu(){
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookRecords");
      EntityManager manager = factory.createEntityManager();
      BookRecords publishedBookRecords = new BookRecords(manager);
      Scanner scanner = new Scanner(System.in);
      List<List<String>> allAuthoringEntList = getAllAuthoringEntities();
      boolean exitVar = true;
      while(exitVar) {
         System.out.println("Authoring Entities Add Menu. What type of Authoring Entity do you want to add?");
         System.out.println("\n1. Add Individual Author \t2. Add Ad Hoc Team");
         System.out.println("3. Add Writing Group \t\t4. Add an existing Individual Author to a Ad Hoc Team");
         System.out.println("5. Go to Authoring Entities menu");

         int userOption = scanner.nextInt();
         switch(userOption) {
            case 1:
               System.out.println("Add new Individual Author");
               exitVar = false;
               addIndividualAuthor();
               authoringEntityAddMenu();
               break;
            case 2:
               System.out.println("Add new Ad Hoc Team");
               exitVar = false;
               addAdHocTeam();
               authoringEntityAddMenu();
               break;
            case 3:
               System.out.println("Add new Writing Group");
               exitVar = false;
               addWritingGroup();
               authoringEntityAddMenu();
               break;
            case 4:
               System.out.println("Add an existing Individual Author to a Ad Hoc Team");
               exitVar = false;
               addIndividualToTeam();
               authoringEntityAddMenu();
               break;
            case 5:
               System.out.println("Go to Authoring Entities menu");
               exitVar = false;
               break;
            default:
               System.out.println("You have chosen an invalid option, please enter another input");
               break;
         }
      }
   }

   /**
    * Method to create a new author instance using user input
    */
   public void addIndividualAuthor(){
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookRecords");
      EntityManager manager = factory.createEntityManager();
      BookRecords publishedBookRecords = new BookRecords(manager);
      List<List<String>> authoringEntList = getAllAuthoringEntities();
      Scanner scanner = new Scanner(System.in);
      String name = "";
      String email = "";
      boolean validInfo = false;
      System.out.println("Please enter the following Individual Author's information:");

      // Selecting Name
      while(!validInfo) {
         System.out.print("Name: ");
         name = scanner.nextLine();
         if(!isInteger(name)){
            validInfo = true;
         }
         else{
            System.out.println("Invalid input. Try again.");
         }
      }

      // Selecting Email
      validInfo = false;
      while(!validInfo) {
         System.out.print("Email: ");
         email = scanner.nextLine();
         if(!isInteger(email)){
            boolean validEmail = true;
            for(int i = 0; i < authoringEntList.size(); i++){
               if(authoringEntList.get(i).get(2).equals(email)){
                  validEmail = false;
               }
            }
            if(validEmail) {
               validInfo = true;
            }
            else{
               System.out.println(email + " already exist in the database. Please select a different email.");
            }
         }
         else{
            System.out.println("Invalid input. Try again.");
         }
      }

      EntityTransaction tx = manager.getTransaction();
      List<Individual_authors> listIndividualAuthors = new ArrayList<>();
      tx.begin();

      Individual_authors newIndivAuthor = new Individual_authors(email, name);
      listIndividualAuthors.add(newIndivAuthor);
      publishedBookRecords.createEntity(listIndividualAuthors);

      tx.commit();
      LOGGER.fine("End of Transaction");
   }

   /**
    * Method to create new a new Ad Hoc team using user input
    */
   public void addAdHocTeam(){
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookRecords");
      EntityManager manager = factory.createEntityManager();
      BookRecords publishedBookRecords = new BookRecords(manager);
      List<List<String>> authoringEntList = getAllAuthoringEntities();
      Scanner scanner = new Scanner(System.in);
      String name = "";
      String email = "";
      boolean validInfo = false;
      System.out.println("Please enter the following Ad Hoc Team's information:");

      // Selecting Name
      while(!validInfo) {
         System.out.print("Team's Name: ");
         name = scanner.nextLine();
         if(!isInteger(name)){
            validInfo = true;
         }
         else{
            System.out.println("Invalid input. Try again.");
         }
      }

      // Selecting Email
      validInfo = false;
      while(!validInfo) {
         System.out.print("Team's Email: ");
         email = scanner.nextLine();
         if(!isInteger(email)){
            boolean validEmail = true;
            for(int i = 0; i < authoringEntList.size(); i++){
               if(authoringEntList.get(i).get(2).equals(email)){
                  validEmail = false;
               }
            }
            if(validEmail) {
               validInfo = true;
            }
            else{
               System.out.println(email + " already exist in the database. Please select a different email.");
            }
         }
         else{
            System.out.println("Invalid input. Try again.");
         }
      }

      EntityTransaction tx = manager.getTransaction();
      List<Ad_hoc_teams> listAdHocTeam = new ArrayList<>();
      tx.begin();

      Ad_hoc_teams newAdHocTeam = new Ad_hoc_teams(email, name);
      listAdHocTeam.add(newAdHocTeam);
      publishedBookRecords.createEntity(listAdHocTeam);

      tx.commit();
      LOGGER.fine("End of Transaction");
   }

   /**
    * Method to create new writing groups using user input
    */
   public void addWritingGroup(){
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookRecords");
      EntityManager manager = factory.createEntityManager();
      BookRecords publishedBookRecords = new BookRecords(manager);
      List<List<String>> authoringEntList = getAllAuthoringEntities();
      Scanner scanner = new Scanner(System.in);
      String name = "";
      String email = "";
      String head_writer = "";
      String year_formed = "";
      boolean validInfo = false;
      System.out.println("Please enter the following Writing Group's information:");

      // Selecting Name
      while(!validInfo) {
         System.out.print("Writing Group's Name: ");
         name = scanner.nextLine();
         if(!isInteger(name)){
            validInfo = true;
         }
         else{
            System.out.println("Invalid input. Try again.");
         }
      }

      // Selecting Email
      validInfo = false;
      while(!validInfo) {
         System.out.print("Writing Group's Email: ");
         email = scanner.nextLine();
         if(!isInteger(email)){
            boolean validEmail = true;
            for(int i = 0; i < authoringEntList.size(); i++){
               if(authoringEntList.get(i).get(2).equals(email)){
                  validEmail = false;
               }
            }
            if(validEmail) {
               validInfo = true;
            }
            else{
               System.out.println(email + " already exist in the database. Please select a different email.");
            }
         }
         else{
            System.out.println("Invalid input. Try again.");
         }
      }

      // Selecting Head Writer
      validInfo = false;
      while(!validInfo) {
         System.out.print("Head writer: ");
         head_writer = scanner.nextLine();
         if(!isInteger(head_writer)){
            validInfo = true;
         }
         else{
            System.out.println("Invalid input. Try again.");
         }
      }

      // Selecting year formed
      validInfo = false;
      while(!validInfo) {
         System.out.print("(4 digits) Year Formed: ");
         year_formed = scanner.nextLine();
         if(isInteger(year_formed) && year_formed.length() == 4){
            validInfo = true;
         }
         else{
            System.out.println("Invalid input (4 digits only). Try again.");
         }
      }



      EntityTransaction tx = manager.getTransaction();
      List<Writing_groups> listWritingGroup = new ArrayList<>();
      tx.begin();

      Writing_groups newWritingGroup = new Writing_groups(email, name, head_writer, Integer.parseInt(year_formed));
      listWritingGroup.add(newWritingGroup);
      publishedBookRecords.createEntity(listWritingGroup);

      tx.commit();
      LOGGER.fine("End of Transaction");
   }

   /**
    * Method to take in user input to get information about an author, then add that author to a specific team
    */
   public void addIndividualToTeam(){
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookRecords");
      EntityManager manager = factory.createEntityManager();
      BookRecords publishedBookRecords = new BookRecords(manager);
      List<Individual_authors> individual_authorsList =
              this.entityManager.createNamedQuery("ReturnAllIndividualAuthors", Individual_authors.class).getResultList();
      //List<List<String>> authoringEntList = getAllAuthoringEntities();
      displayAllIndividualAuthors();
      Scanner scanner = new Scanner(System.in);
      String userInput = "";
      boolean validUserInput = false;
      boolean isValidAuthorNumb = false;
      int authorNumber = -1;

      while (!validUserInput || !isValidAuthorNumb) {
         System.out.print("Please, select the individual author you want to join a Ad Hoc Team: ");
         userInput = scanner.nextLine();
         validUserInput = isInteger(userInput);
         if (!validUserInput) {
            System.out.println("Invalid input. Please enter an integer.");
         }
         else {
            authorNumber = Integer.parseInt(userInput);
            if(authorNumber > 0 && authorNumber < individual_authorsList.size() + 1){
               isValidAuthorNumb = true;
            }
            else{
               System.out.println(authorNumber + " is not a valid individual author number. Try again.");
               isValidAuthorNumb = false;
            }
         }
      }
      String indivAuthor = individual_authorsList.get(authorNumber - 1).getEmail();

      Individual_authors selectedIndividual = manager.find(Individual_authors.class, indivAuthor);
      List<Ad_hoc_teams> listOfTeams = selectedIndividual.getAd_hoc_teams();
      listOfTeams = displayAvailableTeams(listOfTeams);
      System.out.println("List: " + listOfTeams);

      //System.out.println("Name: " + listOfTeams.get(0).getName() + listOfTeams.size());
      List<Ad_hoc_teams> ad_hoc_teamsList =
              this.entityManager.createNamedQuery("ReturnAllAdHocTeams", Ad_hoc_teams.class).getResultList();
      if(!listOfTeams.isEmpty()) {
         //displayAllAdHocTeams();
         validUserInput = false;
         boolean isValidNumb = false;
         int teamNumber = -1;

         while (!validUserInput || !isValidNumb) {
            System.out.print("Please, select the Ad Hoc Team for " + individual_authorsList.get(authorNumber - 1).getName() + ": ");
            userInput = scanner.nextLine();
            validUserInput = isInteger(userInput);
            if (!validUserInput) {
               System.out.println("Invalid input. Please enter an integer.");
            } else {
               teamNumber = Integer.parseInt(userInput);
               if (teamNumber > 0 && teamNumber < listOfTeams.size() + 1) {
                  isValidNumb = true;
               } else {
                  System.out.println(teamNumber + " is not a valid Ad Hoc Team number. Try again.");
                  isValidNumb = false;
               }
            }
         }

         //System.out.println("Ad Hoc team: " + listOfTeams.get(teamNumber - 1).getName());
         //System.out.println("Selected book: " + allBooks.get(bookNumber - 1).getTitle());
         String selectedIndivAuthor = individual_authorsList.get(authorNumber - 1).getEmail();
         String selectedTeamEmail = listOfTeams.get(teamNumber - 1).getEmail();
         String selectedTeamName = listOfTeams.get(teamNumber - 1).getName();
         Individual_authors author = manager.find(Individual_authors.class, selectedIndivAuthor);
         Ad_hoc_teams teamToJoin = manager.find(Ad_hoc_teams.class, selectedTeamEmail);
         EntityTransaction tx = manager.getTransaction();
         tx.begin();
         author.add_ad_hoc_teams(teamToJoin);
         tx.commit();
         LOGGER.fine("End of Transaction");


      }
      else{
         System.out.println("No Ad Hoc Teams available. Add a new Ad Hoc Team");
      }
   }

   /**
    * Method to take in user input, navigating the general books menu
    */
   public void booksMenu(){
      Scanner input = new Scanner(System.in);
      boolean exitVar = true;
      while(exitVar) {
         System.out.println("Books Menu. Type in a number to choose an option");
         System.out.println("\n1. Display all books \t2. Add a new book");
         System.out.println("3. Remove a book      \t4. Update a book");
         System.out.println("5. Go to Main menu");

         int userOption = input.nextInt();
         switch(userOption) {
            case 1:
               System.out.println("Display all books");
               exitVar = false;
               displayAllBooks();
               booksMenu();
               break;
            case 2:
               System.out.println("Add a new book");
               exitVar = false;
               booksAddMenu();
               booksMenu();
               break;
            case 3:
               System.out.println("Remove a book");
               exitVar = false;
               booksRemoveMenu();
               booksMenu();
               break;
            case 4:
               System.out.println("Update a book");
               exitVar = false;
               booksUpdateMenu();
               booksMenu();
               break;
            case 5:
               System.out.println("Go to main menu");
               exitVar = false;
               mainMenu();
               break;
            default:
               System.out.println("You have chosen an invalid option, please enter another input");
               break;
         }
      }
   }

   /**
    * Method to take in user input, navigating through the books-add submenu
    */
   public void booksAddMenu(){
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookRecords");
      EntityManager manager = factory.createEntityManager();
      BookRecords publishedBookRecords = new BookRecords(manager);
      Scanner scanner = new Scanner(System.in);
      List<Books> booksList = this.entityManager.createNamedQuery("ReturnAllBooks", Books.class)
              .getResultList();
      String ISBN = "";
      String title = "";
      String year_published = "";
      boolean validInfo = false;
      System.out.println("Please enter the following book's information:");

      // Selecting ISBN
      while(!validInfo) {
         System.out.print("(7 digits) ISBN: ");
         ISBN = scanner.nextLine();
         if(isInteger(ISBN) && ISBN.length() == 7){
            validInfo = true;
            for(int i = 0; i < booksList.size(); i++){
               if(booksList.get(i).getISBN().equals(ISBN)){
                  validInfo = false;
                  System.out.println("Title: " + title + " already exists. Try a different title name.");
               }
            }
         }
         else{
            System.out.println("Invalid input (7 digits only). Try again.");
         }
      }

      // Selecting title
      validInfo = false;
      while(!validInfo) {
         System.out.print("Title: ");
         title = scanner.nextLine();
         if(!isInteger(title)){
            validInfo = true;
         }
         else{
            System.out.println("Invalid input. Try again.");
         }
      }

      // Selecting year published
      validInfo = false;
      while(!validInfo) {
         System.out.print("(4 digits) Year Published: ");
         year_published = scanner.nextLine();
         if(isInteger(year_published) && year_published.length() == 4){
            validInfo = true;
         }
         else{
            System.out.println("Invalid input (4 digits only). Try again.");
         }
      }

      // Selecting Authoring Entity
      displayAllAuthoringEntities();
      List<List<String>> allAuthoringEntities = getAllAuthoringEntities();
      boolean validUserInput = false;
      boolean isValidPublisherNumb = false;
      int authoringNumber = -1;
      String userInput = "";

      while (!validUserInput || !isValidPublisherNumb) {
         System.out.print("Please, select an authoring entity: ");
         userInput = scanner.nextLine();
         validUserInput = isInteger(userInput);
         if(!validUserInput){
            System.out.println("Invalid input. Please enter an integer.");
         }
         else{
            authoringNumber = Integer.parseInt(userInput);

            if(authoringNumber > 0 && authoringNumber < allAuthoringEntities.size() + 1){
               isValidPublisherNumb = true;
            }
            else{
               System.out.println(authoringNumber + " is not a valid authoring entity number. Try again.");
               isValidPublisherNumb = false;
            }

         }
      }
      int authoringCategory = 0;

      if(allAuthoringEntities.get(authoringNumber - 1).get(0).equals("Individual Authors")){
         authoringCategory = 1;
      }
      else if(allAuthoringEntities.get(authoringNumber - 1).get(0).equals("Ad Hoc Team       ")){
         authoringCategory = 2;
      }
      else{
         authoringCategory = 3;
      }


      //Selecting Publisher
      displayAllPublishers();
      List<Publisher> publisherList =
              this.entityManager.createNamedQuery("ReturnAllPublishers", Publisher.class).getResultList();
      validUserInput = false;
      isValidPublisherNumb = false;
      int publisherNumber = -1;

      while (!validUserInput || !isValidPublisherNumb) {
         System.out.print("Please, select a publisher: ");
         userInput = scanner.nextLine();
         validUserInput = isInteger(userInput);
         if(!validUserInput){
            System.out.println("Invalid input. Please enter an integer.");
         }
         else{
            publisherNumber = Integer.parseInt(userInput);

            if(publisherNumber > 0 && publisherNumber < publisherList.size() + 1){
               isValidPublisherNumb = true;
            }
            else{
               System.out.println(publisherNumber + " is not a valid publisher number. Try again.");
               isValidPublisherNumb = false;
            }

         }
      }

      //System.out.println("PublisherNumb: " + publisherNumber);


      // Adding books information to database
      String selectedAuthoringEntity = allAuthoringEntities.get(authoringNumber - 1).get(2);
      String selectedPublisher = publisherList.get(publisherNumber - 1).getName();
      List<Books> newBookList = new ArrayList<>();
      EntityTransaction tx = manager.getTransaction();

      tx.begin();
      Publisher newPublisher = manager.find(Publisher.class, selectedPublisher);

      // If authoring entity is Individual Author
      if(authoringCategory == 1){
         Individual_authors newIndividualAuthor = manager.find(Individual_authors.class, selectedAuthoringEntity);
         newBookList.add(new Books(ISBN, title, Integer.parseInt(year_published), newIndividualAuthor, newPublisher));
      }

      // If anthoring entity is Ad Hoc Team
      else if(authoringCategory == 2){
         Ad_hoc_teams newAdHocTeam = manager.find(Ad_hoc_teams.class, selectedAuthoringEntity);
         newBookList.add(new Books(ISBN, title, Integer.parseInt(year_published), newAdHocTeam, newPublisher));
      }

      // If anthoring entity is Writing Group
      else{
         Writing_groups newWritingGroup = manager.find(Writing_groups.class, selectedAuthoringEntity);
         newBookList.add(new Books(ISBN, title, Integer.parseInt(year_published), newWritingGroup, newPublisher));

      }

      publishedBookRecords.createEntity(newBookList);
      tx.commit();
      LOGGER.fine("End of Transaction");
   }

   /**
    * Method to take in user input, navigating through the books-remove submenu
    */
   public void booksRemoveMenu(){
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookRecords");
      EntityManager manager = factory.createEntityManager();
      List<Books> allBooks =
              this.entityManager.createNamedQuery("ReturnAllBooks", Books.class).getResultList();
      displayAllBooks();
      Scanner scanner = new Scanner(System.in);
      String userInput = "";
      boolean validUserInput = false;
      boolean isValidBookNumb = false;
      int bookNumber = -1;

      while (!validUserInput || !isValidBookNumb) {
         System.out.print("Please, select the book you want to remove: ");
         userInput = scanner.nextLine();
         validUserInput = isInteger(userInput);
         if (!validUserInput) {
            System.out.println("Invalid input. Please enter an integer.");
         }
         else {
            bookNumber = Integer.parseInt(userInput);
            if(bookNumber > 0 && bookNumber < allBooks.size() + 1){
               isValidBookNumb = true;
            }
            else{
               System.out.println(bookNumber + " is not a valid book number. Try again.");
               isValidBookNumb = false;
            }
         }
      }

      //System.out.println("Selected book: " + allBooks.get(bookNumber - 1).getTitle());
      String selectedBook = allBooks.get(bookNumber - 1).getISBN();
      EntityTransaction tx = manager.getTransaction();
      Books bookToRemove = manager.find(Books.class, selectedBook);
      tx.begin();
      manager.remove(bookToRemove);
      tx.commit();
      LOGGER.fine("End of Transaction");
   }

   /**
    * Method to take in user input, navigating through the books-update submenu
    */
   public void booksUpdateMenu(){
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookRecords");
      EntityManager manager = factory.createEntityManager();
      List<Books> allBooks =
              this.entityManager.createNamedQuery("ReturnAllBooks", Books.class).getResultList();
      displayAllBooks();
      Scanner scanner = new Scanner(System.in);
      String userInput = "";
      boolean validUserInput = false;
      boolean isValidBookNumb = false;
      int bookNumber = -1;

      while (!validUserInput || !isValidBookNumb) {
         System.out.print("Please, select the book you want to update: ");
         userInput = scanner.nextLine();
         validUserInput = isInteger(userInput);
         if (!validUserInput) {
            System.out.println("Invalid input. Please enter an integer.");
         }
         else {
            bookNumber = Integer.parseInt(userInput);
            if(bookNumber > 0 && bookNumber < allBooks.size() + 1){
               isValidBookNumb = true;
            }
            else{
               System.out.println(bookNumber + " is not a valid book number. Try again.");
               isValidBookNumb = false;
            }
         }
      }

      String selectedBookISBN = allBooks.get(bookNumber - 1).getISBN();

      // Selecting Authoring Entity
      displayAllAuthoringEntities();
      List<List<String>> allAuthoringEntities = getAllAuthoringEntities();
      validUserInput = false;
      boolean isValidAuthoringNumb = false;
      int authoringNumber = -1;
      //userInput = "";

      while (!validUserInput || !isValidAuthoringNumb) {
         System.out.print("Please, select an authoring entity: ");
         userInput = scanner.nextLine();
         validUserInput = isInteger(userInput);
         if(!validUserInput){
            System.out.println("Invalid input. Please enter an integer.");
         }
         else{
            authoringNumber = Integer.parseInt(userInput);

            if(authoringNumber > 0 && authoringNumber < allAuthoringEntities.size() + 1){
               isValidAuthoringNumb = true;
            }
            else{
               System.out.println(authoringNumber + " is not a valid authoring entity number. Try again.");
               isValidAuthoringNumb = false;
            }

         }
      }
      int authoringCategory = 0;

      if(allAuthoringEntities.get(authoringNumber - 1).get(0).equals("Individual Authors")){
         authoringCategory = 1;
      }
      else if(allAuthoringEntities.get(authoringNumber - 1).get(0).equals("Ad Hoc Team       ")){
         authoringCategory = 2;
      }
      else{
         authoringCategory = 3;
      }

      // Adding books information to database
      String selectedAuthoringEntity = allAuthoringEntities.get(authoringNumber - 1).get(2);
      EntityTransaction tx = manager.getTransaction();
      Books bookToUpdate = manager.find(Books.class, selectedBookISBN);

      tx.begin();

      // If authoring entity is Individual Author
      if(authoringCategory == 1){
         Individual_authors newIndividualAuthor = manager.find(Individual_authors.class, selectedAuthoringEntity);
         bookToUpdate.setAuthoring_entity_name(newIndividualAuthor);
      }

      // If authoring entity is Ad Hoc Team
      else if(authoringCategory == 2){
         Ad_hoc_teams newAdHocTeam = manager.find(Ad_hoc_teams.class, selectedAuthoringEntity);
         bookToUpdate.setAuthoring_entity_name(newAdHocTeam);
      }

      // If authoring entity is Writing Group
      else{
         Writing_groups newWritingGroup = manager.find(Writing_groups.class, selectedAuthoringEntity);
         bookToUpdate.setAuthoring_entity_name(newWritingGroup);

      }

      tx.commit();
      LOGGER.fine("End of Transaction");
   }

   /**
    * Method to take in user input, navigating through the publishers submenu
    */
   public void publishersMenu(){
      Scanner input = new Scanner(System.in);
      boolean exitVar = true;
      while(exitVar) {
         System.out.println("Publishers Menu. Type in a number to choose an option");
         System.out.println("\n1. Display all publishers \t2. Add a new publishers");
         System.out.println("3. Remove a publishers   \t4. Go to Main menu");

         int userOption = input.nextInt();
         switch(userOption) {
            case 1:
               System.out.println("Display all publishers");
               exitVar = false;
               displayAllPublishers();
               publishersMenu();
               break;
            case 2:
               System.out.println("Add a new publisher");
               exitVar = false;
               publisherAddMenu();
               publishersMenu();
               break;
            case 3:
               System.out.println("Remove a publisher");
               exitVar = false;
               publisherRemoveMenu();
               publishersMenu();
               break;
            case 4:
               System.out.println("Go to main menu");
               exitVar = false;
               mainMenu();
               break;
            default:
               System.out.println("You have chosen an invalid option, please enter another input");
               break;
         }
      }
   }

   /**
    * Method to take in user input, navigating through the publisher-add menu
    */
   public void publisherAddMenu(){
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookRecords");
      EntityManager manager = factory.createEntityManager();
      BookRecords publishedBookRecords = new BookRecords(manager);
      Scanner scanner = new Scanner(System.in);
      List<Publisher> publishersList = this.entityManager.createNamedQuery("ReturnAllPublishers", Publisher.class)
              .getResultList();
      String name = "";
      String phone = "";
      String email = "";
      boolean validInfo = false;
      System.out.println("Please enter the following publisher's information:");
      while(!validInfo) {
         System.out.print("Name: ");
         name = scanner.nextLine();
         if(!isInteger(name)){
            validInfo = true;
            for(int i = 0; i < publishersList.size(); i++){
               if (publishersList.get(i).getName().equals(name)){
                  validInfo = false;
                  System.out.println("Publisher: " + name + " already exists. Try a different publisher name.");
               }
            }
         }
         else{
            System.out.println("Invalid input. Try again.");
         }
      }

      validInfo = false;
      while(!validInfo) {
         System.out.print("(10 digits) Phone: ");
         phone = scanner.nextLine();
         if(isInteger(phone) && phone.length() == 10){
            validInfo = true;
         }
         else{
            System.out.println("Invalid input (10 digits only). Try again.");
         }
      }

      validInfo = false;
      while(!validInfo) {
         System.out.print("Email: ");
         email = scanner.nextLine();
         if(!isInteger(email)){
            validInfo = true;
         }
         else{
            System.out.println("Invalid input. Try again.");
         }
      }
      phone = phone.substring(0,3) + "-" + phone.substring(3, 6) + "-" + phone.substring(6);
      System.out.println("phone: " + phone);

      EntityTransaction tx = manager.getTransaction();

      List<Publisher> newPublisherList = new ArrayList<>();
      tx.begin();
      newPublisherList.add(new Publisher(name, phone, email));
      publishedBookRecords.createEntity(newPublisherList);
      tx.commit();
      LOGGER.fine("End of Transaction");
      System.out.println("Publisher successfully added to the database.");

   }

   /**
    * Method to take in user input, navigating through the publisher-remove submenu
    */
   public void publisherRemoveMenu(){
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookRecords");
      EntityManager manager = factory.createEntityManager();
      List<Publisher> allPublishers =
              this.entityManager.createNamedQuery("ReturnAllPublishers", Publisher.class).getResultList();
      List<Books> allBooks =
              this.entityManager.createNamedQuery("ReturnAllBooks", Books.class).getResultList();
      displayAllPublishers();
      Scanner scanner = new Scanner(System.in);
      String userInput = "";
      boolean validUserInput = false;
      boolean isValidPublisherNumb = false;
      int publisherNumber = -1;

      while (!validUserInput || !isValidPublisherNumb) {
         System.out.print("Please, select the publisher you want to remove: ");
         userInput = scanner.nextLine();
         validUserInput = isInteger(userInput);
         if(!validUserInput){
            System.out.println("Invalid input. Please enter an integer.");
         }
         else{
            publisherNumber = Integer.parseInt(userInput);

            if(publisherNumber > 0 && publisherNumber < allPublishers.size() + 1){
               boolean isPublisherInBook = false;
               for(int i = 0; i < allBooks.size(); i++){
                  if(allBooks.get(i).getPublisher_name().getName().equals(allPublishers.get(publisherNumber - 1).getName())){
                     isPublisherInBook = true;
                  }
               }
               if(isPublisherInBook){
                  System.out.println(allPublishers.get(publisherNumber - 1).getName() + " has a record in Books table." +
                          " To remove " + allPublishers.get(publisherNumber - 1).getName() +
                          ", you need to remove it from Books table.");
               }
               else {
                  isValidPublisherNumb = true;
                  //System.out.println("Publisher: " + allPublishers.get(publisherNumber - 1).getName());
               }
            }
            else{
               System.out.println(publisherNumber + " is not a valid publisher number. Try again.");
               isValidPublisherNumb = false;
            }

         }
      }
      String selectedPublisher = allPublishers.get(publisherNumber - 1).getName();
      EntityTransaction tx = manager.getTransaction();
      Publisher publisher = manager.find(Publisher.class, selectedPublisher);
      tx.begin();
      manager.remove(publisher);
      tx.commit();
      LOGGER.fine("End of Transaction");
   }

   /**
    * Method to take in user input, navigating through the primary-key submenu
    */
   public void primaryKeyMenu() {
      System.out.println("Option 1: Go back To Main Menu\nOption 2: Exit");
      Scanner input = new Scanner(System.in);
      boolean exitVar = true;
      while (exitVar) {
         int userOption = input.nextInt();
         switch (userOption) {
            case 1:
               System.out.println("Go to main menu");
               exitVar = false;
               mainMenu();
               break;
            case 2:
               exitVar = false;
               break;
            default:
               System.out.println("You have chosen an invalid option, please enter another input");
               break;
         }
      }
   }

   /**
    * Method to populate the database of authoring entities.
    *
    * @param individual_authorsList            The list of authors
    * @param ad_hoc_teamsList                  The list of ad hoc teams
    * @param writing_groupsList                The list of writing groups
    * @param booksList                         The list of books
    */
   public void populateAuthoringEnt(List<Individual_authors> individual_authorsList, List<Ad_hoc_teams> ad_hoc_teamsList,
                                    List<Writing_groups> writing_groupsList, List<Books> booksList){

      /**Adding new Individual Authors*/
      Individual_authors individual_author1 = new Individual_authors("Mary.Jonson@gmail.com", "Mary Jonson");
      Individual_authors individual_author2 = new Individual_authors("Mel.Cambridge@gmail.com", "Mel Cambridge");
      Individual_authors individual_author3 = new Individual_authors("Carlos.Parker@gmail.com", "Carlos Parker");
      Individual_authors individual_author4 = new Individual_authors("Mathew.Rogers@gmail.com", "Mathew Rogers");
      Individual_authors individual_author5 = new Individual_authors("David.Williams@gmail.com", "David Williams");
      individual_authorsList.add(individual_author1);
      individual_authorsList.add(individual_author2);
      individual_authorsList.add(individual_author3);
      individual_authorsList.add(individual_author4);
      individual_authorsList.add(individual_author5);

      /**Adding new Ad Hoc Teams*/
      Ad_hoc_teams ad_hoc_teams1 = new Ad_hoc_teams("Friday.Nights@gmail.com", "Friday Nights");
      Ad_hoc_teams ad_hoc_teams2 = new Ad_hoc_teams("Saturday.Night@gmail.com", "Saturday Night");
      ad_hoc_teamsList.add(ad_hoc_teams1);
      ad_hoc_teamsList.add(ad_hoc_teams2);

      /**Adding Individual Authors to Ad Hoc Teams*/
      individual_author1.add_ad_hoc_teams(ad_hoc_teams1);
      individual_author1.add_ad_hoc_teams(ad_hoc_teams2);
      individual_author2.add_ad_hoc_teams(ad_hoc_teams1);
      individual_author4.add_ad_hoc_teams(ad_hoc_teams2);

      /**Adding new Writing Groups*/
      Writing_groups writing_groups1 = new Writing_groups("Larry.Windsor@gmail.com", "Larry Group", "Albert T", 2020);
      Writing_groups writing_groups2 = new Writing_groups("Sally.Nguyen@gmail.com", "Sally Group", "Thomas L", 2019);
      writing_groupsList.add(writing_groups1);
      writing_groupsList.add(writing_groups2);

      /**Adding new Books with existing Publishers and Authors*/
      Books book1 = new Books("3747362", "The House of Mirth", 2002,
              individual_author1,
              new Publisher("Graywolf Press", "323","Graywolf.Press@gmail.com"));
      booksList.add(book1);
      Books book2 = new Books("9382921", "The Sun Also Rises", 2015,
              new Authoring_Entities("David.Williams@gmail.com", "David Williams"),
              new Publisher("Random House", "760-483-2934","Random.House@gmail.com"));
      booksList.add(book2);
   }


   /**
    * Method to display all stored publishers.
    */
   public void displayAllPublishers(){
      List<Publisher> allPublishers =
              this.entityManager.createNamedQuery("ReturnAllPublishers", Publisher.class).getResultList();

      System.out.println("List of Publishers:");
      System.out.println("\tNAME\t\t\t\tPHONE\t\t\t\tEMAIL");
      if(!allPublishers.isEmpty()) {
         for (int i = 0; i < allPublishers.size(); i++) {
            System.out.println((i+ 1) + ". " + allPublishers.get(i).getName() + "  \t|  " + allPublishers.get(i).getPhone() +
                    "\t\t|  " + allPublishers.get(i).getEmail());
         }

         //System.out.println("Phone: " + allPublishers.get(0).getPhone());
      }
      else{
         System.out.println("Records of PUBLISHERS do NOT exist.");
      }
   }

   /**
    * Method to display all the stored books.
    */
   public void displayAllBooks(){
      List<Books> allBooks =
              this.entityManager.createNamedQuery("ReturnAllBooks", Books.class).getResultList();

      System.out.println("List of Books:");
      System.out.println("\tISBN\t\tTITLE\t\t\t\t YEAR PUBLISHED\t\tPUBLISHER\t\t\tAUTHORING ENTITY");
      if(!allBooks.isEmpty()) {
         for (int i = 0; i < allBooks.size(); i++) {
            System.out.println((i+ 1) + ". " + allBooks.get(i).getISBN() + "\t|  " + allBooks.get(i).getTitle() + "\t|  " +
                    allBooks.get(i).getYear_published() + "\t\t\t|  " + allBooks.get(i).getPublisher_name().getName() + " \t|  " +
                    allBooks.get(i).getAuthoring_entity_name().getName());
         }
      }
      else{
         System.out.println("Records of BOOKS do NOT exist.");
      }
   }

   /**
    * Method to display all the stored Individual Authors
    */
   public void displayAllIndividualAuthors(){
      List<Individual_authors> allIndivAuthors =
              this.entityManager.createNamedQuery("ReturnAllIndividualAuthors", Individual_authors.class).getResultList();

      System.out.println("Individual Authors:");
      System.out.println("\t NAME \t\t\t\tEMAIL");
      if(!allIndivAuthors.isEmpty()) {
         for (int i = 0; i < allIndivAuthors.size(); i++) {
            System.out.println((i+ 1) + ". " + allIndivAuthors.get(i).getName() + "  \t|  " + allIndivAuthors.get(i).getEmail());
         }
      }
      else{
         System.out.println("Records of INDIVIDUAL AUTHORS do NOT exist.");
      }
   }

   /**
    * Method to display all the stored Ad Hoc teams
    */
   public void displayAllAdHocTeams(){
      List<Ad_hoc_teams> allAdHocTeams =
              this.entityManager.createNamedQuery("ReturnAllAdHocTeams", Ad_hoc_teams.class).getResultList();

      System.out.println("List of Ad Hoc Teams:");
      System.out.println("\t NAME \t\t\t\tEMAIL");
      if(!allAdHocTeams.isEmpty()) {
         for (int i = 0; i < allAdHocTeams.size(); i++) {
            System.out.println((i+ 1) + ". " + allAdHocTeams.get(i).getName() + "\t|  " + allAdHocTeams.get(i).getEmail());
         }
      }
      else{
         System.out.println("Records of AD HOC TEAMS do NOT exist.");
      }
   }

   /**
    * Method to display all the stored writing groups
    */
   public void displayAllWritingGroups(){
      List<Writing_groups> allWritingGroups =
              this.entityManager.createNamedQuery("ReturnAllWritingGroups", Writing_groups.class).getResultList();

      System.out.println("List of Writing Groups:");
      System.out.println("\t NAME \t\t\t\tEMAIL");
      if(!allWritingGroups.isEmpty()) {
         for (int i = 0; i < allWritingGroups.size(); i++) {
            System.out.println((i+ 1) + ". " + allWritingGroups.get(i).getName() + "\t|  " + allWritingGroups.get(i).getEmail());
         }
      }
      else{
         System.out.println("Records of WRITING GROUPS do NOT exist.");
      }

   }

   /**
    * Method to display all the stored authoring entities
    */
   public void displayAllAuthoringEntities(){
      List<List<String>> authoring_entitiesList = getAllAuthoringEntities();

      System.out.println("List of Authoring entities:");
      System.out.println("\t AUTHORING ENTITY \t   NAME \t\t\t\tEMAIL");
      if(!authoring_entitiesList.isEmpty()) {
         for (int i = 0; i < authoring_entitiesList.size(); i++) {
               System.out.println((i + 1) + ". " + authoring_entitiesList.get(i).get(0) + "\t|  " + authoring_entitiesList.get(i).get(1) +
                       "  \t|  " + authoring_entitiesList.get(i).get(2));
         }
      }
      else{
         System.out.println("Records of AD HOC TEAMS do NOT exist.");
      }
   }


   /**
    * Method to display all the primary keys of each table
    */
   public void displayPrimaryKeys(){
      List<Books> bookList =
              this.entityManager.createNamedQuery("ReturnAllBooks", Books.class).getResultList();
      List<Publisher> publisherList =
              this.entityManager.createNamedQuery("ReturnAllPublishers", Publisher.class).getResultList();
      List<List<String>> listAuthoringEntities = getAllAuthoringEntities();
      System.out.println("Book Primary Keys\n");
      //Book Primary Keys
      if(!bookList.isEmpty()) {
         for (int i = 0; i < bookList.size(); i++) {
            System.out.println((i + 1) + ". " + bookList.get(i).getISBN() + " " + bookList.get(i).getTitle());
         }
      }
      else{
         System.out.println("Records of Books does not exist.");
      }

      //Publisher Primary Keys
      System.out.println("\nPublisher Primary Keys\n");
      if(!publisherList.isEmpty()) {
         for (int i = 0; i < publisherList.size(); i++) {
            System.out.println((i + 1) + ". " +publisherList.get(i).getName());
         }
      }
      else{
         System.out.println("Records of Publishers does not exist.");
      }

      //Authoring Entities List
      System.out.println("\nAuthoring Entities Primary Keys\n");
      if(!listAuthoringEntities.isEmpty()) {
         for (int i = 0; i < listAuthoringEntities.size(); i++) {
            System.out.println((i + 1) + ". " + listAuthoringEntities.get(i).get(2) + "\t  Type: " + listAuthoringEntities.get(i).get(0));

         }
      }
      else{
         System.out.println("Records of Publishers does not exist.");
      }
   }


   /**
    * Method to get all the authoring entities
    * @return                 The list of all authoring entities, including individual authors, groups, and teams
    */
   public List<List<String>> getAllAuthoringEntities(){
      List<Individual_authors> individual_authorsList =
              this.entityManager.createNamedQuery("ReturnAllIndividualAuthors", Individual_authors.class).getResultList();
      List<Ad_hoc_teams> ad_Hoc_TeamsList =
              this.entityManager.createNamedQuery("ReturnAllAdHocTeams", Ad_hoc_teams.class).getResultList();
      List<Writing_groups> writing_groupsList =
              this.entityManager.createNamedQuery("ReturnAllWritingGroups", Writing_groups.class).getResultList();

      List<List<String>> allAuthoringEntities = new ArrayList<>();

      for(int i = 0; i < individual_authorsList.size(); i++){
         List<String> info = new ArrayList<>();
         info.add("Individual Authors");
         info.add(individual_authorsList.get(i).getName());
         info.add(individual_authorsList.get(i).getEmail());
         allAuthoringEntities.add(info);
      }

      for(int i = 0; i < ad_Hoc_TeamsList.size(); i++){
         List<String> info = new ArrayList<>();
         info.add("Ad Hoc Team       ");
         info.add(ad_Hoc_TeamsList.get(i).getName());
         info.add(ad_Hoc_TeamsList.get(i).getEmail());
         allAuthoringEntities.add(info);
      }

      for(int i = 0; i < writing_groupsList.size(); i++){
         List<String> info = new ArrayList<>();
         info.add("Writing Group       ");
         info.add(writing_groupsList.get(i).getName());
         info.add(writing_groupsList.get(i).getEmail());
         allAuthoringEntities.add(info);
      }
      return allAuthoringEntities;
   }


   /**
    * Method to display all the available teams for individual authors.
    *
    * @param authorInTeams    The author to be part of a team
    * @return                 The list of Ad Hoc teams the author can join
    */
   public List<Ad_hoc_teams> displayAvailableTeams(List<Ad_hoc_teams> authorInTeams){
      List<Ad_hoc_teams> temp = new ArrayList<>();
      List<Ad_hoc_teams> ad_hoc_teamsList =
              this.entityManager.createNamedQuery("ReturnAllAdHocTeams", Ad_hoc_teams.class).getResultList();
      if(authorInTeams.isEmpty()){
         displayAllAdHocTeams();
         return ad_hoc_teamsList;
      }
      else if(authorInTeams.size() != ad_hoc_teamsList.size()) {
         int count = 1;
         System.out.println("List of Ad Hoc Teams:");
         System.out.println("\t NAME \t\t\t\tEMAIL");
         for (int i = 0; i < ad_hoc_teamsList.size(); i++) {
            boolean found = false;
            for(int index = 0; index < authorInTeams.size(); index++) {
               if (authorInTeams.get(index).getEmail().equals(ad_hoc_teamsList.get(i).getEmail())) {
                  found = true;
               }
            }
            if(!found){
               System.out.println(count + ". " + ad_hoc_teamsList.get(i).getName() + "\t|  " + ad_hoc_teamsList.get(i).getEmail());
               temp.add(ad_hoc_teamsList.get(i));
               count++;
            }
         }
         return temp;
      }
      else{
         System.out.println("Individual author is part of all available Ad Hoc Teams. Add a new Ad Hoc Team");
         return new ArrayList<>();
      }
   }

   /**
    * Method to display all the members of an Ad Hoc team.
    */
   public void displayAdHocTeamMembers(){
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookRecords");
      EntityManager manager = factory.createEntityManager();
      List<Individual_authors> individual_authorsList =
              this.entityManager.createNamedQuery("ReturnAllIndividualAuthors", Individual_authors.class).getResultList();
      System.out.println("\tINDIVIDUAL AUTHORS EMAILS\t\t\tAD HOC TEAMS EMAIL");
      for(int i = 0; i < individual_authorsList.size(); i++){
         String indivAuthor = individual_authorsList.get(i).getEmail();
         Individual_authors selectedIndividual = manager.find(Individual_authors.class, indivAuthor);
         List<Ad_hoc_teams> listOfTeams = selectedIndividual.getAd_hoc_teams();
         for(int index = 0; index < listOfTeams.size(); index++){
            System.out.println(individual_authorsList.get(i).getEmail() + "\t\t" + listOfTeams.get(index).getEmail());
         }
      }

   }


   /**
    * Method to check if a certain input is an integer.
    * 
    * @param input            The value to be checked for integer status.
    * @return                 Whether or not the input is an integer. (True = integer, False = non-integer)    
    */
   public boolean isInteger(String input){
      try{
         Long.valueOf(input);
         //Integer.parseInt(input);
      }
      catch (NumberFormatException e){
         return false;
      }
      catch (NullPointerException e) {
         return false;
      }
      return true;
   }


   /**
    * Create and persist a list of objects to the database.
    * @param entities   The list of entities to persist.  These can be any object that has been
    *                   properly annotated in JPA and marked as "persistable."  I specifically
    *                   used a Java generic so that I did not have to write this over and over.
    */
   public <E> void createEntity(List <E> entities) {
      for (E next : entities) {
         LOGGER.info("Persisting: " + next);
         // Use the CustomerOrders entityManager instance variable to get our EntityManager.
         this.entityManager.persist(next);
      }

      // The auto generated ID (if present) is not passed in to the constructor since JPA will
      // generate a value.  So the previous for loop will not show a value for the ID.  But
      // now that the Entity has been persisted, JPA has generated the ID and filled that in.
      for (E next : entities) {
         LOGGER.info("Persisted object after flush (non-null id): " + next);
      }
   } // End of createEntity member method

   public void cls(){
      for(int i = 0; i < 25; i ++){
         System.out.println();
      }
   }


} // End of CustomerOrders class
