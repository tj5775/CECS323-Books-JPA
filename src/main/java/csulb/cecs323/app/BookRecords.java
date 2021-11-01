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
               break;
            case 3:
               System.out.println("Option 3");
               exitVar = false;
               break;
            case 4:
               System.out.println("Option 4");
               exitVar = false;
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
         System.out.print("Phone: ");
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
                  System.out.println("Publisher: " + allPublishers.get(publisherNumber - 1).getName());
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
      Ad_hoc_teams ad_hoc_teams2 = new Ad_hoc_teams("Saturday.Nights@gmail.com", "Saturday Nights");
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

   public void displayAllPublishers(){
      List<Publisher> allPublishers =
              this.entityManager.createNamedQuery("ReturnAllPublishers", Publisher.class).getResultList();

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

   public void displayAllBooks(){
      List<Books> allBooks =
              this.entityManager.createNamedQuery("ReturnAllBooks", Books.class).getResultList();

      System.out.println("\tISBN\t\tTITLE\t\t\t\t YEAR PUBLISHED\t\tPUBLISHER\t\t\tAUTHOR");
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

   public void displayAllAdHocTeams(){
      List<Ad_hoc_teams> allAdHocTeams =
              this.entityManager.createNamedQuery("ReturnAllAdHocTeams", Ad_hoc_teams.class).getResultList();

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
