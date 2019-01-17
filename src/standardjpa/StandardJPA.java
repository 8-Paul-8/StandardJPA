/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package standardjpa;

import controllers.WebuserJpaController;
import controllers.exceptions.IllegalOrphanException;
import controllers.exceptions.NonexistentEntityException;
import entities.Webuser;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

/**
 *
 * @author nikolaos
 */
public class StandardJPA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("StandardJPAPU");
        WebuserJpaController userController = new WebuserJpaController(emf);
         
        System.out.println("------Users-------");
        System.out.println("There are " + userController.getWebuserCount() + " users");
        List<Webuser> userList = userController.findWebuserEntities();
        for(Webuser user: userList){
            System.out.println(user.getUsername());
        }
        //
        System.out.println("------Inserting new user-------");
        Webuser newUser = new Webuser("melpw", "melpw00", new Date());
        userController.create(newUser);
        //
        System.out.println("------Users-------");
        System.out.println("There are " + userController.getWebuserCount() + " users");
        List<Webuser> userList1 = userController.findWebuserEntities();
        for(Webuser user: userList1){
            System.out.println(user.getUsername());
        }
        //
        System.out.println("------Find User By Id------");
        System.out.println("User with id = 3 is " + userController.findWebuser(3).getUsername());
        System.out.println("------Find User By Name------");
        System.out.println("User with name kostas is ID=" + userController.findWebuserByName("kostas").getUserid());
        System.out.println("------Find User By Name Like------");
        System.out.println("Users with names like '%ri%' are");
        List<Webuser> userList2 = userController.findWebuserByNameLike("ri");
        for(Webuser user: userList2){
            System.out.println(user.getUsername());
        }
        //
        System.out.println("Deleting a user");
        try {
            userController.destroy(userController.findWebuserByName("evgenios").getUserid());
        } catch (IllegalOrphanException ex) {
            System.out.println("Unable to delete user...User does not exist!");
        } catch (NonexistentEntityException ex) {
            System.out.println("User does not exist!");
        } catch (NoResultException ex) {
            System.out.println("User does not exist!");
        } 
        System.out.println("------Users-------");
        System.out.println("There are " + userController.getWebuserCount() + " users");
        List<Webuser> userList3 = userController.findWebuserEntities();
        for(Webuser user: userList3){
            System.out.println(user.getUsername());
        }
        //
        System.out.println("Updating a user");
        try {
            Webuser userToChange = userController.findWebuserByName("nikos");
            userToChange.setUsername("nikolaos");
            userController.edit(userToChange);
        } catch (NonexistentEntityException ex) {
            System.out.println("User does not exist!");
        } catch (Exception ex) {
            System.out.println("Could not update user...User does not exist or there are relations with existing groups...");
        }
        System.out.println("------Users-------");
        System.out.println("There are " + userController.getWebuserCount() + " users");
        List<Webuser> userList4 = userController.findWebuserEntities();
        for(Webuser user: userList4){
            System.out.println(user.getUsername());
        }
        
        emf.close();
    }
    
}
