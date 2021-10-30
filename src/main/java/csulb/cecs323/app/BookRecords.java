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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;


public class BookRecords {
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

      List<Publisher> publishers = new ArrayList<>();

      LOGGER.fine("Begin of Transaction");
      EntityTransaction tx = manager.getTransaction();
      tx.begin();

      Publisher publisher = new Publisher("Al", "323", "at@email");
      Publisher publisher2 = new Publisher("Andrew", "151", "ab@email");
      publishers.add(publisher);
      publishers.add(publisher2);
      publishedBookRecords.createEntity(publishers);

      // Commit the changes so that the new data persists and is visible to other users.
      tx.commit();
      LOGGER.fine("End of Transaction");

      //User interface
      Scanner input = new Scanner(System.in);
      System.out.println("Type in a number to choose an option");
      System.out.println("\n1. Add new book");
      System.out.println("2. List all information");
      System.out.println("3. Delete a book");
      System.out.println("4. Update book");
      System.out.println("5. List all primary keys");

      boolean exitVar = true;
      while(exitVar)
      {
         int userOption = input.nextInt();
         switch(userOption) {
            case 1:
               System.out.println("Option 1");
               exitVar = false;
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


   } // End of the main method

   public void displayPublishers(){

   }

   /**
    * Method to check if a certain input is an integer.
    * 
    * @param input            The value to be checked for integer status.
    * @return                 Whether or not the input is an integer. (True = integer, False = non-integer)    
    */
   public boolean isInteger(String input){
      //int number;
      try{
         Integer.parseInt(input);
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
