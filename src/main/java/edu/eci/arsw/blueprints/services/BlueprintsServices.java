/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;

import edu.eci.arsw.blueprints.model.Filter.BlueprintsFilter;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 *
 * @author hcadavid
 */
@Service
public class BlueprintsServices {

    @Autowired
    @Qualifier("InMemoryBlueprint")
    BlueprintsPersistence bpp = null;

    @Autowired
    @Qualifier("SubsampleFilter")
    BlueprintsFilter bpf = null;
    
    /**
     * 
     * @param bp the new blueprint
     * @throws BlueprintPersistenceException if a blueprint with the same name already exists,
     *    or any other low-level persistence error occurs.
     */
    public void addNewBlueprint(Blueprint bp) throws BlueprintPersistenceException{
        bpp.saveBlueprint(bp);
    }
    
    /**
     * @returns all the blueprints
     * @return all the blueprints
     */
    public Set<Blueprint> getAllBlueprints(){
        return bpf.applyFilter(bpp.getAllBlueprints());
    }
    
    /**
     * 
     * @param author blueprint's author
     * @param name blueprint's name
     * @return the blueprint of the given name created by the given author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author,String name) throws BlueprintNotFoundException{
        return bpf.applyFilter(bpp.getBlueprint(author, name));
    }
    
    /**
     * 
     * @param author blueprint's author
     * @return all the blueprints of the given author
     * @throws BlueprintNotFoundException if the given author doesn't exist
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException{
        return bpf.applyFilter(bpp.getBlueprintsByAuthor(author));
    }

    /**
     * 
     * @param bp blueprint to be saved
     * @throws BlueprintPersistenceException 
     */
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException{
        bpp.saveBlueprint(bp);
    }

    public void editBlueprint(String author, String bprintname, Blueprint bp) throws BlueprintNotFoundException{
        bpp.editBlueprint(author, bprintname, bp);
    }

    public static void main(String a[]) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        BlueprintsServices bps = ac.getBean(BlueprintsServices.class);
        Point[] pts=new Point[]{new Point(1, 1), new Point(1, 1), new Point(2, 2),
            new Point(3, 3), new Point(3, 3), new Point(4, 4),
            new Point(5, 5), new Point(6, 6), new Point(7, 7)};

        try {
            bps.saveBlueprint(new Blueprint("Jose", "Test01",pts));
            bps.saveBlueprint(new Blueprint("Jose", "Test02",pts));
            bps.saveBlueprint(new Blueprint("Jose", "Test03",pts));
            System.out.println(bps.getBlueprint("Jose","Test01")); 
            System.out.println(bps.getBlueprintsByAuthor("Jose"));
        } catch (Exception e) {e.printStackTrace();}
        
    }
}